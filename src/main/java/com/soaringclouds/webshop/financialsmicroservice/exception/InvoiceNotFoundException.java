package com.soaringclouds.webshop.financialsmicroservice.exception;

/**
 * Created by svb on 05.03.18.
 */
public class InvoiceNotFoundException extends RuntimeException {

    public InvoiceNotFoundException(String pMessage) {

        super(pMessage);
    }
}
