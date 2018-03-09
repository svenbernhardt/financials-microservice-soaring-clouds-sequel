package com.soaringclouds.webshop.financialsmicroservice.service.impl;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.ResponseMetadata;
import com.soaringclouds.webshop.financialsmicroservice.repository.InvoiceRepository;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.soaringclouds.webshop.financialsmicroservice.TestUtilities.createInvoice;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by svb on 06.03.18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class InvoiceServiceTest {

    @Autowired private InvoiceService invoiceService;

    @Autowired private InvoiceRepository invoiceRepository;

    @ClassRule public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true,
		    "a516817-soaring-customer-status");

    @Test
    @Ignore
    public void whenCustomerAccountIsUnbalancedThenCreateOrderButReturnThatCustomersAccountIsFrozenNow() {

	final Invoice unpaidInvoice = createInvoice();
	unpaidInvoice.setTotalPrice(new Double(555.99));

	invoiceRepository.insert(unpaidInvoice);

	final Invoice anotherUnpaidInvoice = createInvoice();
	anotherUnpaidInvoice.setTotalPrice(new Double(511.00));

	invoiceRepository.insert(anotherUnpaidInvoice);

	final Invoice newInvoice = createInvoice();

	final ResponseMetadata responseMetadata = invoiceService.createInvoice(newInvoice);

	assertThat(responseMetadata.isError(), equalTo(false));
	assertThat(responseMetadata.getProcessingMessage(),
			equalTo("Invoice has not been generated due to customer's account balance!"));
    }

    @Test
    public void whenCustomerAccountIsBalancedThenCreateOrder() {

	final ResponseMetadata responseMetadata = invoiceService.createInvoice(createInvoice());

	assertThat(responseMetadata.isError(), equalTo(false));
	assertThat(responseMetadata.getProcessingMessage(), startsWith("Invoice successfully created!"));
    }


}
