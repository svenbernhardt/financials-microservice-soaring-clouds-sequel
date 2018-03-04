package com.soaringclouds.webshop.financialsmicroservice.service;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.Payment;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.ResponseMetadata;

import java.util.List;

/**
 * Created by svb on 04.03.18.
 */
public interface PaymentService {

    ResponseMetadata savePaymentAndUpdateInvoice(Payment pPayment);

    List<Payment> findPaymentByCriteria(String pInvoiceId, String pCustomerNo);
}
