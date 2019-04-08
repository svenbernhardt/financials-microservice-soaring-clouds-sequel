package com.soaringclouds.webshop.financialsmicroservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soaringclouds.webshop.financialsmicroservice.model.ShippingEvent;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import org.apache.camel.Message;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
@Named("shippingeventhandler")
public class ShippingEventHandler implements KafkaEventHandler {

    @Inject
    private InvoiceService invoiceService;

    @Override
    public void handle(Message pMessage) throws Exception {

        final ObjectMapper shippingEventMapper = new ObjectMapper();
        final ShippingEvent shippingEvent =
                shippingEventMapper.readValue(pMessage.getBody(String.class),
                        ShippingEvent.class);

        invoiceService.updateInvoiceWithShippingInformation(shippingEvent);
    }
}
