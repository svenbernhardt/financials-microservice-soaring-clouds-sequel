package com.soaringclouds.webshop.financialsmicroservice.service.impl;

import com.google.common.collect.Lists;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.PaymentStatus;
import com.soaringclouds.webshop.financialsmicroservice.service.CustomerService;
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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by svb on 06.03.18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CustomerServiceTest {

    @Autowired private CustomerService customerService;

    @ClassRule public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true,
		    "a516817-soaring-customer-status");

    @Test
    @Ignore
    public void whenToCustomerAccountIsUnbalancedThenRejectOrder() {

	final Invoice paidInvoice = createInvoice();
	paidInvoice.setPaymentStatus(PaymentStatus.RECEIVED);

	final Invoice openInvoiceSmall = createInvoice();
	openInvoiceSmall.setTotalPrice(new Double(120.99));

	final Invoice openInvoiceBig = createInvoice();
	openInvoiceSmall.setTotalPrice(new Double(1200.99));

	final boolean isCustomerAccountBalanceUnderThreshold = customerService
			.checkCustomerBalanceUnderThreshold(
					Lists.newArrayList(paidInvoice, openInvoiceSmall, openInvoiceBig));

	assertThat(isCustomerAccountBalanceUnderThreshold, equalTo(false));
    }

    @Test
    public void whenCustomerAccountIsBalancedThenAcceptOrder() {

	final Invoice paidInvoice = createInvoice();
	paidInvoice.setPaymentStatus(PaymentStatus.RECEIVED);

	final Invoice openInvoiceSmall = createInvoice();
	openInvoiceSmall.setTotalPrice(new Double(120.99));

	final boolean isCustomerAccountBalanceUnderThreshold = customerService
			.checkCustomerBalanceUnderThreshold(
					Lists.newArrayList(paidInvoice, openInvoiceSmall));

	assertThat(isCustomerAccountBalanceUnderThreshold, equalTo(true));
    }

    @Test
    public void whenCustomerAccountHasNoInvoicesThenReturnTrue() {

	final boolean isCustomerAccountBalanceUnderThreshold = customerService
			.checkCustomerBalanceUnderThreshold(Lists.newArrayList());

	assertThat(isCustomerAccountBalanceUnderThreshold, equalTo(true));
    }
}
