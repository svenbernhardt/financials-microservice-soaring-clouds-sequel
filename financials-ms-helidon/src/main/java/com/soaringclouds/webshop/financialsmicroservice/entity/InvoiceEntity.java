package com.soaringclouds.webshop.financialsmicroservice.entity;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;

/**
 * InvoiceEntity
 */
public class InvoiceEntity extends BaseMongoEntity<Invoice> {

    public static final String IDENTIFIER_ATTRIBUTE_INVOICE = "invoice_id";

    public InvoiceEntity(Invoice pInvoice) {
        super(pInvoice, pInvoice.getInvoiceId(), IDENTIFIER_ATTRIBUTE_INVOICE);
    }
}