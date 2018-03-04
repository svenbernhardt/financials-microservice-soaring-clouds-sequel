package com.soaringclouds.webshop.financialsmicroservice.service.impl;

import com.google.common.base.Strings;
import com.soaringclouds.webshop.financialsmicroservice.builder.PaymentBuilder;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Payment;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.PaymentStatus;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.ResponseMetadata;
import com.soaringclouds.webshop.financialsmicroservice.repository.PaymentRepository;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import com.soaringclouds.webshop.financialsmicroservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by svb on 04.03.18.
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class.getName());

    @Autowired private PaymentRepository paymentRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Override
    public ResponseMetadata savePaymentAndUpdateInvoice(Payment pPayment) {

	paymentRepository.save(pPayment);

	invoiceService.updateInvoiceWithPayment(pPayment);

	final ResponseMetadata responseMetadata = new ResponseMetadata();
	responseMetadata.setError(false);
	responseMetadata.setProcessingMessage(
			String.format("Payment has been created successfully! ID: [%s]", pPayment.getId()));

	return responseMetadata;
    }

    @Override
    public List<Payment> findPaymentByCriteria(String pInvoiceId, String pCustomerNo) {

        final PaymentBuilder paymentBuilder = PaymentBuilder.aPayment();

        if(!Strings.isNullOrEmpty(pInvoiceId)) {
            LOGGER.info(String.format("Adding invoice id to criteria [%s]", pInvoiceId));

            paymentBuilder.withInvoiceId(pInvoiceId);
	}

	if(!Strings.isNullOrEmpty(pCustomerNo)) {
	    LOGGER.info(String.format("Adding customer No to criteria [%s]", pCustomerNo));

            paymentBuilder.withCustomerNo(pCustomerNo);
	}

	return paymentRepository.findAll(Example.of(paymentBuilder.build()));
    }
}
