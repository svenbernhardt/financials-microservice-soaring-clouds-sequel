package com.soaringclouds.webshop.financialsmicroservice.event;

import com.soaringclouds.avro.paymentstatus.v1.PaymentStatus;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * Created by svb on 04.03.18.
 */
public class PaymentStatusEventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentStatusEventConsumer.class);

    @KafkaListener(topics = "${kafka.topic.paymentstatus}")
    public void receive(ConsumerRecord<String, PaymentStatus> consumerRecord) {
	PaymentStatus paymentStatus = consumerRecord.value();

	LOGGER.debug("received payload='{}'", paymentStatus.toString());
    }
}
