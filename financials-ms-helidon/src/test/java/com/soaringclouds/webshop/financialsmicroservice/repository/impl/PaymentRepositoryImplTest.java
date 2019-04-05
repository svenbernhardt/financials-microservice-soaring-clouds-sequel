package com.soaringclouds.webshop.financialsmicroservice.repository.impl;

import com.google.common.collect.Lists;
import com.soaringclouds.webshop.financialsmicroservice.builder.PaymentBuilder;
import com.soaringclouds.webshop.financialsmicroservice.entity.PaymentEntity;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Payment;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.PaymentState;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.PaymentType;
import com.soaringclouds.webshop.financialsmicroservice.repository.PaymentRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;

public class PaymentRepositoryImplTest extends BaseMongoTest {

    private PaymentRepository paymentRepository;

    private List<Payment> payments;

    @Override
    protected void beforeEachOverrideable() throws Exception {

        paymentRepository = new PaymentRepositoryImpl();
        ((PaymentRepositoryImpl) paymentRepository).setNativeMongoManager(nativeMongoManager);

        payments = Lists.newArrayList(PaymentBuilder.aPayment().withCustomerNo(
                "CGN4711").withInvoiceId(
                "invoice0001").withOrderId("order0001").withPaymentId("payment0001").withPaymentState(PaymentState.APPROVED).withPaymentType(PaymentType.INVOICE).build(), PaymentBuilder.aPayment().withCustomerNo("CGN4711").withInvoiceId(
                "invoice0002").withOrderId("order0002").withPaymentId("payment0002").withPaymentState(PaymentState.APPROVED).withPaymentType(PaymentType.INVOICE).build(), PaymentBuilder.aPayment().withCustomerNo("CGN4712").withInvoiceId(
                "invoice0001").withOrderId("order0001").withPaymentId("payment0001").withPaymentState(PaymentState.APPROVED).withPaymentType(PaymentType.INVOICE).build());

        payments.forEach(payment -> paymentRepository.create(new PaymentEntity(payment)));
    }

    @Test
    public void whenSearchPaymentsByCustomerNoThenReturnPayments() {

        List<Payment> paymentsByInvoiceIdAndCustomerNo =
                paymentRepository.findPaymentsByInvoiceIdAndCustomerNo(null, "CGN4711");

        assertThat(paymentsByInvoiceIdAndCustomerNo, notNullValue());
        assertThat(paymentsByInvoiceIdAndCustomerNo, hasSize(2));
    }

    @Test
    public void whenSearchPaymentsByCustomerNolAndInvoiceIdThenReturnPayments() {

        List<Payment> paymentsByInvoiceIdAndCustomerNo =
                paymentRepository.findPaymentsByInvoiceIdAndCustomerNo("invoice0001", "CGN4712");

        assertThat(paymentsByInvoiceIdAndCustomerNo, notNullValue());
        assertThat(paymentsByInvoiceIdAndCustomerNo, hasSize(1));
    }
}
