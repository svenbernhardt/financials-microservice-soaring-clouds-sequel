package com.soaringclouds.webshop.financialsmicroservice.builder;

import java.math.BigDecimal;
import java.util.List;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.Address;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Currency;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Customer;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.InvoicePosition;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.InvoiceStatus;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.PaymentStatus;

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
	invoice.setShippingCosts(BigDecimal.valueOf(shippingCosts));
	return this;
    }

    public InvoiceBuilder withTotalPrice(Double totalPrice) {
	invoice.setTotalPrice(BigDecimal.valueOf(totalPrice));
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

    public Invoice build() { return invoice; }
}
