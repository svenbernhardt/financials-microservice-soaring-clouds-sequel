package com.soaringclouds.webshop.financialsmicroservice.builder;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.*;

import java.util.List;

/**
 * Created by svb on 08.03.18.
 */
public final class InvoiceBuilder {
    private Invoice invoice;

    private InvoiceBuilder() { invoice = new Invoice(); }

    public static InvoiceBuilder anInvoice() { return new InvoiceBuilder(); }

    public InvoiceBuilder withInvoiceId(String invoiceId) {
	invoice.setInvoiceId(invoiceId);
	return this;
    }

    public InvoiceBuilder withOrderId(String orderId) {
	invoice.setOrderId(orderId);
	return this;
    }

    public InvoiceBuilder withShippingCosts(Double shippingCosts) {
	invoice.setShippingCosts(shippingCosts);
	return this;
    }

    public InvoiceBuilder withTotalPrice(Double totalPrice) {
	invoice.setTotalPrice(totalPrice);
	return this;
    }

    public InvoiceBuilder withOrderDate(String orderDate) {
	invoice.setOrderDate(orderDate);
	return this;
    }

    public InvoiceBuilder withCurrency(Currency currency) {
	invoice.setCurrency(currency);
	return this;
    }

    public InvoiceBuilder withInvoiceStatus(InvoiceStatus invoiceStatus) {
	invoice.setInvoiceStatus(invoiceStatus);
	return this;
    }

    public InvoiceBuilder withPaymentStatus(PaymentStatus paymentStatus) {
	invoice.setPaymentStatus(paymentStatus);
	return this;
    }

    public InvoiceBuilder withCustomer(Customer customer) {
	invoice.setCustomer(customer);
	return this;
    }

    public InvoiceBuilder withAddresses(List<Address> addresses) {
	invoice.setAddresses(addresses);
	return this;
    }

    public InvoiceBuilder withInvoicePositions(List<InvoicePosition> invoicePositions) {
	invoice.setInvoicePositions(invoicePositions);
	return this;
    }

    public InvoiceBuilder withId(String id) {
	invoice.setId(id);
	return this;
    }

    public Invoice build() { return invoice; }
}
