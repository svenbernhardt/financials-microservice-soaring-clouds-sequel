package com.soaringclouds.webshop.financialsmicroservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.avro.Schema;
import org.apache.camel.Message;

public abstract class BaseAvroEventHandler<T> implements KafkaEventHandler {

    @Override
    public void handle(Message pMessage) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        T event = objectMapper.readValue(pMessage.getBody(String.class), getEventClass());

        processEvent(event);
    }

    protected abstract void processEvent(T pEvent);

    protected abstract Class<T> getEventClass();
}
