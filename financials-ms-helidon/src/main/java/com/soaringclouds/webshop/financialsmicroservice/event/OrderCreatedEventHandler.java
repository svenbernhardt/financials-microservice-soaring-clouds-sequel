package com.soaringclouds.webshop.financialsmicroservice.event;

import com.soaringclouds.avro.order.v1.Order;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import org.apache.avro.Schema;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
@Named("ordercreatedeventhandler")
public class OrderCreatedEventHandler extends BaseAvroEventHandler<Order> {

    @Inject
    private InvoiceService invoiceService;

    @Override
    protected void processEvent(Order pEvent) {
        invoiceService.createInvoice(pEvent);
    }

    @Override
    protected Class<Order> getEventClass() {
        return Order.class;
    }

    @Override
    protected Schema getSchema() {
        return Order.getClassSchema();
    }
}
