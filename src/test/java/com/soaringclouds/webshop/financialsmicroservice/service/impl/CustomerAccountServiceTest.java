package com.soaringclouds.webshop.financialsmicroservice.service.impl;

import com.soaringclouds.webshop.financialsmicroservice.builder.CustomerAccountBuilder;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerAccount;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerStatus;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.InvoiceStatus;
import com.soaringclouds.webshop.financialsmicroservice.repository.CustomerAccountRepository;
import com.soaringclouds.webshop.financialsmicroservice.service.CustomerAccountService;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.soaringclouds.webshop.financialsmicroservice.TestUtilities.createInvoice;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by svb on 08.03.18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CustomerAccountServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAccountServiceTest.class);

    @Autowired private CustomerAccountService customerAccountService;

    @Autowired private CustomerAccountRepository customerAccountRepository;

    @Autowired private MongoTemplate mongoTemplate;

    @ClassRule public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true,
		    "a516817-soaring-customer-status");

    @Before
    public void setUp() {

	mongoTemplate.dropCollection(CustomerAccount.class);
    }

    @Test
    public void whenCustomerAccountDoesNotExistThenCreateIt() {

	final Invoice invoice = createInvoice();

	final CustomerAccount customerAccount = customerAccountService.createOrUpdateCustomerAccount
			(invoice);

	assertThat(customerAccount, notNullValue());
	assertThat(customerAccount.getId(), notNullValue());
	assertThat(customerAccount.getFirstName(), equalTo("Sven"));
	assertThat(customerAccount.getLastName(), equalTo("Bernhardt"));
	assertThat(customerAccount.getCustomerStatus(), equalTo(CustomerStatus.NORMAL));
	assertThat(customerAccount.getBalance(), equalTo(-30.96));
    }

    @Test
    public void whenCustomerAccountExistsAndInvoiceIsOpenThenJustUpdateBalance() {

	final Invoice invoice = createInvoice();

	createCustomerAccount(-100d);

	CustomerAccount customerAccount = customerAccountService.createOrUpdateCustomerAccount(invoice);

	assertThat(customerAccount.getBalance(), equalTo(-130.96));
    }

    @Test
    public void whenCustomerAccountExistsAndInvoiceIsCanceledThenJustUpdateBalance() {

	final Invoice invoice = createInvoice();
	invoice.setInvoiceStatus(InvoiceStatus.CANCEL);

	createCustomerAccount(-100d);

	CustomerAccount customerAccount = customerAccountService.createOrUpdateCustomerAccount(invoice);

	assertThat(customerAccount.getBalance(), equalTo(-69.04));
    }

    @Test
    public void whenCustomerAccountExistsAndInvoiceIsPaidThenUpdateBalance() {

	final Invoice invoice = createInvoice();
	invoice.setInvoiceStatus(InvoiceStatus.PAID);

	createCustomerAccount(-30.96d);

	CustomerAccount customerAccount = customerAccountService.createOrUpdateCustomerAccount(invoice);

	assertThat(customerAccount.getBalance(), equalTo(0.0d));
    }

    private CustomerAccount createCustomerAccount(double pStartBalance) {

	CustomerAccount customerAccount = CustomerAccountBuilder.aCustomerAccount().withCustomerNo("CGN4711")
			.withFirstName("Sven").withLastName("Bernhardt")
			.withCustomerStatus(CustomerStatus.NORMAL).withBalance(pStartBalance).build();

	return customerAccountRepository.insert(customerAccount);
    }
}
