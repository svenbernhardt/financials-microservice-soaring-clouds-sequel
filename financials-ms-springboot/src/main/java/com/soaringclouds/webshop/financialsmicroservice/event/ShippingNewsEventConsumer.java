package com.soaringclouds.webshop.financialsmicroservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soaringclouds.webshop.financialsmicroservice.model.ShippingEvent;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Created by svb on 07.03.18.
 */
@Service
public class ShippingNewsEventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingNewsEventConsumer.class);

    private static final String SHIPPING_STATUS_NEW = "new";

    @Autowired private InvoiceService invoiceService;

    @KafkaListener(topics = "${kafka.topic.shippingnews}", containerFactory =
		    "kafkaListenerContainerFactoryNonAvro")
    public void receive(ConsumerRecord<String, String> consumerRecord) {

	final String shippingNewsJsonMessage = consumerRecord.value();
	ShippingEvent shippingEvent = null;

	try {
	    final ObjectMapper shippingEventMapper = new ObjectMapper();
	    shippingEvent = shippingEventMapper.readValue(shippingNewsJsonMessage, ShippingEvent.class);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}

	invoiceService.updateInvoiceWithShippingInformation(shippingEvent);

	/*if (shippingEvent.getPayload().getShippingStatus().equals(SHIPPING_STATUS_NEW)) {


	} else {

	    LOGGER.warn(String.format("Received ShippingEvent with status [%s]",
			    shippingEvent.getPayload().getShippingStatus()));
	}*/

	LOGGER.debug("received payload='{}'", shippingNewsJsonMessage);
    }
}
