package com.soaringclouds.webshop.financialsmicroservice.service.impl;

import com.google.common.base.Strings;
import com.soaringclouds.avro.order.v1.Order;
import com.soaringclouds.webshop.financialsmicroservice.builder.CustomerBuilder;
import com.soaringclouds.webshop.financialsmicroservice.builder.InvoiceBuilder;
import com.soaringclouds.webshop.financialsmicroservice.converter.Order2InvoiceConverter;
import com.soaringclouds.webshop.financialsmicroservice.exception.InvoiceNotFoundException;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.*;
import com.soaringclouds.webshop.financialsmicroservice.model.ShippingEvent;
import com.soaringclouds.webshop.financialsmicroservice.repository.InvoiceRepository;
import com.soaringclouds.webshop.financialsmicroservice.service.CustomerAccountService;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by svb on 26.02.18.
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    @Autowired private InvoiceRepository invoiceRepository;

    @Autowired private Order2InvoiceConverter order2InvoiceConverter;

    @Autowired private CustomerAccountService customerAccountService;

    @Override
    public List<Invoice> findInvoiceByCriteria(String pOrderId, String pCustomerNo) {

	final InvoiceBuilder invoiceBuilder = InvoiceBuilder.anInvoice();

	if (!Strings.isNullOrEmpty(pOrderId)) {
	    invoiceBuilder.withOrderId(pOrderId);
	}

	if (!Strings.isNullOrEmpty(pCustomerNo)) {
	    invoiceBuilder.withCustomer(CustomerBuilder.aCustomer().withCustomerNo(pCustomerNo).build());
	}

	return invoiceRepository.findAll(Example.of(invoiceBuilder.build()));
    }

    @Override
    public Invoice findInvoiceByInvoiceId(String pInvoiceId) {

	Invoice invoice = invoiceRepository
			.findOne(Example.of(InvoiceBuilder.anInvoice().withInvoiceId(pInvoiceId).build()));

	if (invoice == null) {
	    throw new InvoiceNotFoundException(
			    String.format("No invoice found for Invoice-ID [%s]", pInvoiceId));
	}

	return invoice;
    }

    @Override
    public ResponseMetadata createInvoice(Invoice pInvoice) {

	final CustomerAccount customerAccount = customerAccountService
			.createOrUpdateCustomerAccount(pInvoice);

	final Invoice insertedInvoice = invoiceRepository.insert(pInvoice);
	ResponseMetadata responseMetadata;

	if (customerAccount.getCustomerStatus() == CustomerStatus.NORMAL) {

	    responseMetadata = createResponseMetadata(
			    String.format("Invoice successfully created! Invoice-Id: [%s]",
					    insertedInvoice.getId()));
	} else {
	    responseMetadata = createResponseMetadata(
			    String.format("Invoice has been created, but customer's status is FROZEN now!"));
	}

	return responseMetadata;
    }

    @Override
    public void createInvoice(Order pOrder) {

	createInvoice(order2InvoiceConverter.createInvoice(pOrder));
    }

    @Override
    public ResponseMetadata updateInvoice(Invoice pUpdatedInvoice) {

	final Invoice savedUpdatedInvoice = invoiceRepository.save(pUpdatedInvoice);

	return createResponseMetadata(String.format("Invoice successfully updated! Invoice-Id: [%s]",
			savedUpdatedInvoice.getId()));
    }

    @Override
    public boolean updateInvoiceWithPayment(Payment pPayment) {

	boolean wasAlreadyPaid = false;

	final Invoice invoiceToBePaid = findInvoiceByInvoiceId(pPayment.getInvoiceId());

	if (invoiceToBePaid.getPaymentStatus() != PaymentStatus.RECEIVED) {
	    invoiceToBePaid.setPaymentStatus(PaymentStatus.RECEIVED);
	    updateInvoice(invoiceToBePaid);
	} else {
	    LOGGER.warn(String.format("Invoice [%s] is already paid!", invoiceToBePaid.getInvoiceId()));
	    wasAlreadyPaid = true;
	}

	return wasAlreadyPaid;
    }

    @Override
    public void updateInvoiceWithShippingInformation(ShippingEvent pShippingEvent) {

	final Invoice invoiceToUpdate = findInvoiceByOrderId(
			pShippingEvent.getPayload().getOrderIdentifier());

	final Double shippingCosts = new Double(pShippingEvent.getPayload().getShippingCosts());

	invoiceToUpdate.setShippingCosts(shippingCosts);
	invoiceToUpdate.setTotalPrice(invoiceToUpdate.getTotalPrice() + shippingCosts);

	updateInvoice(invoiceToUpdate);
    }

    @Override
    public void deleteInvoice(String pInvoiceId) {

	final Invoice invoice = findInvoiceByInvoiceId(pInvoiceId);

	invoiceRepository.delete(invoice);
    }

    @Override
    public void cancelInvoiceByOrderId(String pOrderId) {

	final Invoice invoice = findInvoiceByOrderId(pOrderId);

	invoice.setInvoiceStatus(InvoiceStatus.CANCEL);
	final ResponseMetadata responseMetadata = updateInvoice(invoice);

	LOGGER.debug(String
			.format("Invoice has been marked as canceled sucessfully! [%s]", responseMetadata));

    }

    private ResponseMetadata createResponseMetadata(String pResponseMessage) {

	final ResponseMetadata responseMetadata = new ResponseMetadata();
	responseMetadata.setProcessingMessage(pResponseMessage);
	responseMetadata.error(false);

	return responseMetadata;
    }

    private Invoice findInvoiceByOrderId(String pOrderId) {

	final List<Invoice> invoices = findInvoiceByCriteria(pOrderId, null);

	if (invoices == null || invoices.size() != 1) {

	    throw new InvoiceNotFoundException(
			    String.format("No unique invoice found for Order ID [%s]", pOrderId));
	}

	return invoices.get(0);
    }
}
