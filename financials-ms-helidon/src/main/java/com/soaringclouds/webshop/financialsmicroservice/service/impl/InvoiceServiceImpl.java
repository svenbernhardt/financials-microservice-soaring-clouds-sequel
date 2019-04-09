package com.soaringclouds.webshop.financialsmicroservice.service.impl;

import com.soaringclouds.avro.order.v1.Order;
import com.soaringclouds.webshop.financialsmicroservice.converter.Order2InvoiceConverter;
import com.soaringclouds.webshop.financialsmicroservice.entity.InvoiceEntity;
import com.soaringclouds.webshop.financialsmicroservice.exception.InvoiceNotFoundException;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.*;
import com.soaringclouds.webshop.financialsmicroservice.model.ShippingEvent;
import com.soaringclouds.webshop.financialsmicroservice.repository.InvoiceRepository;
import com.soaringclouds.webshop.financialsmicroservice.service.CustomerAccountService;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Created by svb on 26.02.18.
 */
@Dependent
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    @Inject
    private InvoiceRepository invoiceRepository;

    @Inject
    private CustomerAccountService customerAccountService;

    @Override
    public List<Invoice> findInvoiceByCriteria(String pOrderId, String pCustomerNo) {

        return invoiceRepository.findAllByInvoiceIdAndCustomerNo(pOrderId, pCustomerNo);
    }

    @Override
    public Invoice findInvoiceByInvoiceId(String pInvoiceId) {

        Invoice invoiceEntity = invoiceRepository.findById("invoice_id", pInvoiceId);

        if (Objects.isNull(invoiceEntity)) {
            throw new InvoiceNotFoundException(String.format("No invoice found for Invoice-ID " +
					"[%s]", pInvoiceId));
        }

        return invoiceEntity;
    }

    @Override
    public ResponseMetadata createInvoice(Invoice pInvoice) {

        invoiceRepository.create(new InvoiceEntity(pInvoice));
        ResponseMetadata responseMetadata;

        final CustomerAccount customerAccount =
				customerAccountService.createOrUpdateCustomerAccount(pInvoice);

        if (customerAccount.getCustomerStatus() == CustomerStatus.NORMAL) {

            responseMetadata = createResponseMetadata(
                    String.format("Invoice successfully created! Invoice-Id: [%s]",
							pInvoice.getInvoiceId()));
        } else {
            responseMetadata = createResponseMetadata(
                    String.format("Invoice has been created, but customer's status is FROZEN " +
							"now!"));
        }

        return responseMetadata;
    }

    @Override
    public void createInvoice(Order pOrder) {

        createInvoice(Order2InvoiceConverter.createInvoice(pOrder));
    }

    @Override
    public ResponseMetadata updateInvoice(Invoice pUpdatedInvoice) {

        final Invoice savedUpdatedInvoice =
				invoiceRepository.update(new InvoiceEntity(pUpdatedInvoice));

        return createResponseMetadata(
                String.format("Invoice successfully updated! Invoice-Id: [%s]",
						savedUpdatedInvoice.getInvoiceId()));
    }

    @Override
    public boolean updateInvoiceWithPayment(Payment pPayment) {

        boolean wasAlreadyPaid = false;

        final Invoice invoiceToBePaid = findInvoiceByInvoiceId(pPayment.getInvoiceId());

        if (invoiceToBePaid.getPaymentStatus() != PaymentStatus.RECEIVED) {

            invoiceToBePaid.setPaymentStatus(PaymentStatus.RECEIVED);
            invoiceToBePaid.setInvoiceStatus(InvoiceStatus.PAID);
            updateInvoice(invoiceToBePaid);
        } else {
            LOGGER.warn(String.format("Invoice [%s] is already paid!",
					invoiceToBePaid.getInvoiceId()));
            wasAlreadyPaid = true;
        }

        return wasAlreadyPaid;
    }

    @Override
    public void updateInvoiceWithShippingInformation(ShippingEvent pShippingEvent) {

        // FIXME: Think of a different method for synchronizing ShippingEvents and
        // Orders (Caching?)
        // FIXME: Don't throw InvoiceNotFoundException!
        final Invoice invoiceToUpdate =
				findInvoiceByOrderId(pShippingEvent.getPayload().getOrderIdentifier());

        if (invoiceToUpdate.getShippingCosts() != null) {

            LOGGER.debug(String.format("Shipping costs already set for invoice [%s]!",
					invoiceToUpdate.getInvoiceId()));
            return;
        }

        final BigDecimal shippingCosts =
				BigDecimal.valueOf(pShippingEvent.getPayload().getShippingCosts());

        invoiceToUpdate.setShippingCosts(shippingCosts);
        invoiceToUpdate.setTotalPrice(invoiceToUpdate.getTotalPrice().add(shippingCosts));

        updateInvoice(invoiceToUpdate);
    }

    @Override
    public void deleteInvoice(String pInvoiceId) {

        final Invoice invoice = findInvoiceByInvoiceId(pInvoiceId);

        invoiceRepository.delete(new InvoiceEntity(invoice));
        customerAccountService.updateCustomerAccount(invoice);
    }

    @Override
    public void deleteInvoices(String pCustomerId) {

        findInvoiceByCriteria(null, pCustomerId).forEach(invoice -> invoiceRepository.delete(new InvoiceEntity(invoice)));
    }

    @Override
    public void cancelInvoiceByOrderId(String pOrderId) {

        final Invoice invoice = findInvoiceByOrderId(pOrderId);

        invoice.setInvoiceStatus(InvoiceStatus.CANCEL);
        final ResponseMetadata responseMetadata = updateInvoice(invoice);

        LOGGER.debug(String.format("Invoice has been marked as canceled sucessfully! [%s]",
				responseMetadata));

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

            throw new InvoiceNotFoundException(String.format("No unique invoice found for Order ID [%s]", pOrderId));
        }

        return invoices.get(0);
    }

    public void setInvoiceRepository(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public void setCustomerAccountService(CustomerAccountService customerAccountService) {
        this.customerAccountService = customerAccountService;
    }
}
