package com.soaringclouds.webshop.financialsmicroservice.service;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.ResponseMetadata;

import java.util.List;

/**
 * Created by svb on 26.02.18.
 */
public interface InvoiceService {

    List<Invoice> findInvoiceByCriteria(String pOrderId, String pCustomerNo);

    Invoice findInvoiceByInvoiceId(String pInvoiceId);

    ResponseMetadata createInvoice(Invoice pInvoice);

    ResponseMetadata updateInvoice(Invoice pUpdatedInvoice);

    void deleteInvoice(String pInvoiceId);
}
