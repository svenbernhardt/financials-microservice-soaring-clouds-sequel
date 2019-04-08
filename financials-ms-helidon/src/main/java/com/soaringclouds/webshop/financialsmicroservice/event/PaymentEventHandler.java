package com.soaringclouds.webshop.financialsmicroservice.event;

import com.soaringclouds.avro.paymentstatus.v1.PaymentStatus;
import com.soaringclouds.webshop.financialsmicroservice.service.PaymentService;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.camel.Message;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.util.logging.Logger;

@Dependent
@Named("paymenteventhandler")
public class PaymentEventHandler implements KafkaEventHandler {

    private static final Logger LOGGER = Logger.getLogger(PaymentEventHandler.class.getName());

    private PaymentService paymentService;

    @Override
    public void handle(Message pMessage) throws Exception {

        LOGGER.info(String.format("Received Payment message: %s", pMessage.getBody()));

        final DatumReader<PaymentStatus> reader = new SpecificDatumReader<>(
                PaymentStatus.class);
        final Decoder jsonDecoder = DecoderFactory.get().jsonDecoder(
                PaymentStatus.getClassSchema(), pMessage.getBody(String.class));
        PaymentStatus paymentStatus = reader.read(new PaymentStatus(), jsonDecoder);

        LOGGER.info(String.format("Converted PaymentStatus message: %s", paymentStatus));
    }
}
