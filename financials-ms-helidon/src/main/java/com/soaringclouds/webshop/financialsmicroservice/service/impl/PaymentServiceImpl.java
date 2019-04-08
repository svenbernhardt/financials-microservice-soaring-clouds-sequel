package com.soaringclouds.webshop.financialsmicroservice.service.impl;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.google.common.base.Strings;
import com.soaringclouds.webshop.financialsmicroservice.builder.PaymentBuilder;
import com.soaringclouds.webshop.financialsmicroservice.entity.PaymentEntity;
import com.soaringclouds.webshop.financialsmicroservice.event.KafkaMessageProducer;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerAccount;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Payment;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.ResponseMetadata;
import com.soaringclouds.webshop.financialsmicroservice.repository.PaymentRepository;
import com.soaringclouds.webshop.financialsmicroservice.service.CustomerAccountService;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import com.soaringclouds.webshop.financialsmicroservice.service.PaymentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by svb on 04.03.18.
 */
@Dependent
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(PaymentServiceImpl.class.getName());

    @Inject
    private PaymentRepository paymentRepository;

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private CustomerAccountService customerAccountService;

    @Inject
    private KafkaMessageProducer kafkaMessageProducer;

    @Override
    public ResponseMetadata savePaymentAndUpdateInvoice(Payment pPayment) {

        LOGGER.debug(String.format("Updating invoice with payment...."));
        final boolean wasAlreadyPaid = invoiceService.updateInvoiceWithPayment(pPayment);

        LOGGER.debug(String.format("Saving payment...."));
        paymentRepository.create(new PaymentEntity(pPayment));

        final ResponseMetadata responseMetadata = new ResponseMetadata();
        responseMetadata.setError(false);

        if (!wasAlreadyPaid) {

            LOGGER.debug(String.format("Updating Customer account: ..."));
            final CustomerAccount customerAccount = customerAccountService
                    .createOrUpdateCustomerAccount(
                            invoiceService.findInvoiceByInvoiceId(pPayment.getInvoiceId()));

            LOGGER.debug(String.format("Updated Customer account: [%s]", customerAccount));

            kafkaMessageProducer.producePaymentStatusReceived(pPayment);
            LOGGER.debug(String.format("Payment event produced...."));

            responseMetadata.setProcessingMessage(
                    String.format("Payment has been created successfully! ID: [%s]",
                            pPayment.getPaymentId()));
        } else {
            responseMetadata.setProcessingMessage(
                    String.format("Payment was already done before! ID: [%s]",
                            pPayment.getPaymentId()));
        }

        return responseMetadata;
    }

    @Override
    public List<Payment> findPaymentByCriteria(String pInvoiceId, String pCustomerNo) {

        return paymentRepository.findPaymentsByInvoiceIdAndCustomerNo(pInvoiceId, pCustomerNo);
    }

    @Override
    public void deletePayments(String pCustomerId) {

        findPaymentByCriteria(null, pCustomerId).forEach(payment -> paymentRepository.delete(new PaymentEntity(payment)));
    }

    protected void setPaymentRepository(PaymentRepository pPaymentRepository) {
        paymentRepository = pPaymentRepository;
    }

    protected void setInvoiceService(InvoiceService pInvoiceService) {
        invoiceService = pInvoiceService;
    }

    protected void setCustomerAccountService(CustomerAccountService pCustomerAccountService) {
        customerAccountService = pCustomerAccountService;
    }
}
