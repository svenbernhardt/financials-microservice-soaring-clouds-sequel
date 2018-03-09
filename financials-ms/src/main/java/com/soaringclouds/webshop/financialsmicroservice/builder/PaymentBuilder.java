package com.soaringclouds.webshop.financialsmicroservice.builder;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.Payment;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.PaymentState;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.PaymentType;

/**
 * Created by svb on 04.03.18.
 */
public final class PaymentBuilder {
    private Payment payment;

    private PaymentBuilder() { payment = new Payment(); }

    public static PaymentBuilder aPayment() { return new PaymentBuilder(); }

    public PaymentBuilder withInvoiceId(String invoiceId) {
	payment.setInvoiceId(invoiceId);
	return this;
    }

    public PaymentBuilder withOrderId(String orderId) {
	payment.setOrderId(orderId);
	return this;
    }

    public PaymentBuilder withCustomerNo(String customerNo) {
	payment.setCustomerNo(customerNo);
	return this;
    }

    public PaymentBuilder withPaymentType(PaymentType paymentType) {
	payment.setPaymentType(paymentType);
	return this;
    }

    public PaymentBuilder withPaymentId(String paymentId) {
	payment.setPaymentId(paymentId);
	return this;
    }

    public PaymentBuilder withPaymentState(PaymentState paymentState) {
	payment.setPaymentState(paymentState);
	return this;
    }

    public PaymentBuilder withId(String id) {
	payment.setId(id);
	return this;
    }

    public Payment build() { return payment; }
}
