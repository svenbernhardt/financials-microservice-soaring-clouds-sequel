package com.soaringclouds.webshop.financialsmicroservice.service.impl;

import com.google.common.base.Strings;
import com.soaringclouds.webshop.financialsmicroservice.builder.CustomerBuilder;
import com.soaringclouds.webshop.financialsmicroservice.builder.InvoiceBuilder;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Payment;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.PaymentStatus;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.ResponseMetadata;
import com.soaringclouds.webshop.financialsmicroservice.repository.InvoiceRepository;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by svb on 26.02.18.
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired private InvoiceRepository invoiceRepository;

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

	return invoiceRepository
			.findOne(Example.of(InvoiceBuilder.anInvoice().withInvoiceId(pInvoiceId).build()));
    }

    @Override
    public ResponseMetadata createInvoice(Invoice pInvoice) {

	final Invoice insertedInvoice = invoiceRepository.insert(pInvoice);

	return createResponseMetadata(String.format("Invoice successfully created! Invoice-Id: [%s]",
			insertedInvoice.getId()));
    }

    @Override
    public ResponseMetadata updateInvoice(Invoice pUpdatedInvoice) {

	final Invoice savedUpdatedInvoice = invoiceRepository.save(pUpdatedInvoice);

	return createResponseMetadata(String.format("Invoice successfully updated! Invoice-Id: [%s]",
			savedUpdatedInvoice.getId()));
    }

    @Override
    public void updateInvoiceWithPayment(Payment pPayment) {

	final Invoice invoiceToBePaid = findInvoiceByInvoiceId(pPayment.getInvoiceId());
	invoiceToBePaid.setPaymentStatus(PaymentStatus.RECEIVED);
	updateInvoice(invoiceToBePaid);
    }

    @Override
    public void deleteInvoice(String pInvoiceId) {

        final Invoice invoice = findInvoiceByInvoiceId(pInvoiceId);

        invoiceRepository.delete(invoice);
    }

    private ResponseMetadata createResponseMetadata(String pResponseMessage) {

	final ResponseMetadata responseMetadata = new ResponseMetadata();
	responseMetadata.setProcessingMessage(pResponseMessage);
	responseMetadata.error(false);

	return responseMetadata;
    }
}
