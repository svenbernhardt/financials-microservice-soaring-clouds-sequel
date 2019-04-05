package com.soaringclouds.webshop.financialsmicroservice.entity;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.Payment;

/**
 * PaymentEntity
 */
public class PaymentEntity extends BaseMongoEntity<Payment> {

    public static final String IDENTIFIER_ATTRIBUTE_PAYMENT = "payment_id";

    public PaymentEntity(Payment pPayment) {
        super(pPayment, pPayment.getPaymentId(), IDENTIFIER_ATTRIBUTE_PAYMENT);
    }
}