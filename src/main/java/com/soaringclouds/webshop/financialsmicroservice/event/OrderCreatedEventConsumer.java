package com.soaringclouds.webshop.financialsmicroservice.event;

import com.soaringclouds.avro.order.v1.Order;
import com.soaringclouds.avro.paymentstatus.v1.PaymentStatus;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Created by svb on 07.03.18.
 */
@Service
public class OrderCreatedEventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCreatedEventConsumer.class);

    public InvoiceService invoiceService;

    @KafkaListener(topics = "${kafka.topic.order}")
    public void receive(ConsumerRecord<String, Order> consumerRecord) {
	final Order order = consumerRecord.value();

	LOGGER.debug("received payload='{}'", order.toString());
	
	invoiceService.createInvoice(order);
    }
}
