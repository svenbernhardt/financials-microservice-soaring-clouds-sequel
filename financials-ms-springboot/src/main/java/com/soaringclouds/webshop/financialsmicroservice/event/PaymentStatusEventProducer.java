package com.soaringclouds.webshop.financialsmicroservice.event;

import com.soaringclouds.avro.paymentstatus.v1.PaymentStatus;
import com.soaringclouds.avro.paymentstatus.v1.PaymentStatusEnum;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by svb on 04.03.18.
 */
@Service
public class PaymentStatusEventProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentStatusEventProducer.class.getName());

    @Autowired private KafkaTemplate<String, PaymentStatus> paymentStatusProducer;

    @Value("${kafka.topic.paymentstatus}") private String kafkaTopic;

    public void producePaymentStatusReceived(Payment pPayment) {

        LOGGER.info(String.format("Submitting Payment Received event: [%s] to topic [%s]", pPayment.toString(), kafkaTopic));

	paymentStatusProducer.send(kafkaTopic, pPayment.getPaymentId(),
			PaymentStatus.newBuilder().setPaymentId(pPayment.getPaymentId())
					.setOrderId(pPayment.getOrderId())
					.setStatus(PaymentStatusEnum.PAYMENT_RECEIVED).build());
    }
}
