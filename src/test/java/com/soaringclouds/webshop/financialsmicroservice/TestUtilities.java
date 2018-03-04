package com.soaringclouds.webshop.financialsmicroservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Payment;

import java.io.InputStreamReader;

/**
 * Created by svb on 04.03.18.
 */
public class TestUtilities {

    public static Invoice createInvoice() {

	Invoice invoice = null;
	String invoiceJsonString = null;
	try {
	    invoiceJsonString = CharStreams.toString(new InputStreamReader(
			    Thread.currentThread().getContextClassLoader().getResourceAsStream(
					    "com/soaringclouds/webshop/financialsmicroservice/repository"
							    + "/invoice_example.json")));

	    invoice = new ObjectMapper().readValue(invoiceJsonString, Invoice.class);
	    invoice.setId(null);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}

	return invoice;
    }

    public static Payment createPayment() {

	Payment payment = null;
	String paymentJsonString = null;
	try {
	    paymentJsonString = CharStreams.toString(new InputStreamReader(
			    Thread.currentThread().getContextClassLoader().getResourceAsStream(
					    "com/soaringclouds/webshop/financialsmicroservice/service"
							    + "/payment_example.json")));

	    payment = new ObjectMapper().readValue(paymentJsonString, Payment.class);
	    payment.setId(null);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}

	return payment;
    }
}
