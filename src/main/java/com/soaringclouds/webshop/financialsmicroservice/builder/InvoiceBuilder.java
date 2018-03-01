package com.soaringclouds.webshop.financialsmicroservice.builder;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.Address;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Customer;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.InvoicePosition;

import java.util.List;

/**
 * Created by svb on 25.02.18.
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

    public InvoiceBuilder withOrderDate(String orderDate) {
	invoice.setOrderDate(orderDate);
	return this;
    }

    public InvoiceBuilder withCustomer(Customer customer) {
	invoice.setCustomer(customer);
	return this;
    }

    public InvoiceBuilder withBillingAddress(Address billingAddress) {
	invoice.setBillingAddress(billingAddress);
	return this;
    }

    public InvoiceBuilder withInvoiceAddress(Address invoiceAddress) {
	invoice.setInvoiceAddress(invoiceAddress);
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
