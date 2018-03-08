package com.soaringclouds.webshop.financialsmicroservice.converter;

import com.soaringclouds.avro.order.v1.Order;
import com.soaringclouds.webshop.financialsmicroservice.event.OrderCreatedEventConsumer;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.PaymentStatus;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.soaringclouds.webshop.financialsmicroservice.TestUtilities.createOrder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 * Created by svb on 07.03.18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class Order2InvoiceConverterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Order2InvoiceConverterTest.class);

    @Autowired
    private Order2InvoiceConverter order2InvoiceConverter;

    @ClassRule public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true,
		    "a516817-soaring-order-created");

    @Test
    public void whenOrderIsPassedThenCreateInvoice() {

	final Order order = createOrder();

        final Invoice invoice = order2InvoiceConverter.createInvoice(order);

        LOGGER.debug(String.format("Invoice created from Order: [%s]", invoice));

        assertThat(invoice, not(nullValue()));
        assertThat(invoice.getInvoiceId(), not(nullValue()));
        assertThat(invoice.getOrderId(), equalTo(order.getOrderId()));
        assertThat(invoice.getOrderDate(), equalTo(order.getCreatedAt()));
        assertThat(invoice.getPaymentStatus(), equalTo(PaymentStatus.PENDING));
        assertThat(invoice.getAddresses().size(), equalTo(order.getAddresses().size()));
        assertThat(invoice.getInvoicePositions().size(), equalTo(order.getItems().size()));
        assertThat(invoice.getCustomer().getCustomerNo(), not(nullValue()));
    }
}
