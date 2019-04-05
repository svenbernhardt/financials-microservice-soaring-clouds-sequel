package com.soaringclouds.webshop.financialsmicroservice.repository;

import com.soaringclouds.webshop.financialsmicroservice.entity.PaymentEntity;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Payment;

import javax.enterprise.context.RequestScoped;
import java.util.List;

/**
 * Created by svb on 04.03.18.
 */
@RequestScoped
public interface PaymentRepository extends GenericRepository<PaymentEntity, Payment> {

    List<Payment> findPaymentsByInvoiceIdAndCustomerNo(String pInvoiceId, String pCustomerNo);
}
