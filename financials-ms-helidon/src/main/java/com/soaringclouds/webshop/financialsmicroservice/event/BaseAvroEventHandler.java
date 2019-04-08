package com.soaringclouds.webshop.financialsmicroservice.event;

import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.camel.Message;

import java.util.logging.Logger;

public abstract class BaseAvroEventHandler<T> implements KafkaEventHandler {

    private static final Logger LOGGER = Logger.getLogger(BaseAvroEventHandler.class.getName());

    @Override
    public void handle(Message pMessage) throws Exception {

        LOGGER.info(String.format("Incoming Avro message contains event: %s", pMessage.getBody()));

        final DatumReader<T> reader = new SpecificDatumReader<>(
                getEventClass());
        final Decoder jsonDecoder = DecoderFactory.get().jsonDecoder(
                getSchema(), pMessage.getBody(String.class));
        T event = reader.read(getEventClass().newInstance(), jsonDecoder);

        processEvent(event);
    }

    protected abstract void processEvent(T pEvent);

    protected abstract Class<T> getEventClass();

    protected abstract Schema getSchema();
}
