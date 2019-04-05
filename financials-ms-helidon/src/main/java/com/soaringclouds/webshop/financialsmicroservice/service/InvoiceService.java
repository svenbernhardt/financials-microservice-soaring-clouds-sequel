package com.soaringclouds.webshop.financialsmicroservice.service;

import java.util.List;

import com.soaringclouds.avro.order.v1.Order;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Payment;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.ResponseMetadata;
import com.soaringclouds.webshop.financialsmicroservice.model.ShippingEvent;

/**
 * Created by svb on 26.02.18.
 */
public interface InvoiceService {

    List<Invoice> findInvoiceByCriteria(String pOrderId, String pCustomerNo);

    Invoice findInvoiceByInvoiceId(String pInvoiceId);

    ResponseMetadata createInvoice(Invoice pInvoice);

    void createInvoice(Order pOrder);

    ResponseMetadata updateInvoice(Invoice pUpdatedInvoice);

    boolean updateInvoiceWithPayment(Payment pPayment);

    void updateInvoiceWithShippingInformation(ShippingEvent pShippingEvent);

    void deleteInvoice(String pInvoiceId);

    void deleteInvoices(String pCustomerId);

    void cancelInvoiceByOrderId(String pOrderId);
}
