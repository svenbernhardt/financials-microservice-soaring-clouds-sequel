package com.soaringclouds.webshop.financialsmicroservice.service.impl;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Payment;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.PaymentStatus;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.ResponseMetadata;
import com.soaringclouds.webshop.financialsmicroservice.repository.PaymentRepository;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import com.soaringclouds.webshop.financialsmicroservice.service.PaymentService;
import org.junit.*;
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

import java.util.List;

import static com.soaringclouds.webshop.financialsmicroservice.TestUtilities.createInvoice;
import static com.soaringclouds.webshop.financialsmicroservice.TestUtilities.createPayment;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

/**
 * Created by svb on 04.03.18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class PaymentServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImplTest.class.getName());

    private static String CUSTOMER_NO = "CGN4711";
    private static String ORDER_ID = "order4711";
    private static String INVOICE_ID = "invoice0815";

    private Payment newPayment;

    @Autowired private PaymentService paymentService;

    @Autowired private PaymentRepository paymentRepository;

    @Autowired private InvoiceService invoiceService;

    @Autowired private MongoTemplate mongoTemplate;

    @ClassRule public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true,
		    "a516817-soaring-payment-status");

    @Before
    public void setUp() {

	invoiceService.createInvoice(createInvoice());

	newPayment = createPayment();
    }

    @After
    public void tearDown() {

	mongoTemplate.dropCollection(Invoice.class);
	mongoTemplate.dropCollection(Payment.class);
    }

    @Test
    @Ignore
    public void whenPaymentIsReceivedThenUpdateInvoiceStatus() {

	final ResponseMetadata response = paymentService.savePaymentAndUpdateInvoice(newPayment);

	assertThat(response, not(nullValue()));
	assertThat(newPayment.getId(), not(nullValue()));

	final Invoice paidInvoice = invoiceService.findInvoiceByInvoiceId(newPayment.getInvoiceId());

	assertThat(paidInvoice.getPaymentStatus(), equalTo(PaymentStatus.RECEIVED));
    }

    @Test
    public void whenSearchPaymentByInvoiceIdThenReturnExactlyOnePayment() {

	paymentRepository.save(newPayment);

	final List<Payment> paymentsForInvoice = paymentService.findPaymentByCriteria(INVOICE_ID, null);

	assertThat(paymentsForInvoice, not(nullValue()));
	assertThat(paymentsForInvoice, hasSize(1));
    }

    @Test
    public void whenSearchPaymentByCustomerNoAndInvoiceIdThenReturnOnePaymentForTheInvoice() {

	paymentRepository.save(newPayment);

	newPayment.setId(null);
	newPayment.setPaymentId("payment667");
	newPayment.setInvoiceId("invoice4711");

	paymentRepository.save(newPayment);

	final List<Payment> paymentsForInvoice = paymentService
			.findPaymentByCriteria(INVOICE_ID, CUSTOMER_NO);

	assertThat(paymentsForInvoice, not(nullValue()));
	assertThat(paymentsForInvoice, hasSize(1));

	assertThat(paymentsForInvoice.get(0).getInvoiceId(), equalTo(INVOICE_ID));
    }

    @Test
    public void whenSearchPaymentByCustomerNoThenReturnAllPaymentsForCustomer() {

	paymentRepository.save(newPayment);

	newPayment.setId(null);
	newPayment.setPaymentId("payment667");
	newPayment.setInvoiceId("invoice4711");

	paymentRepository.save(newPayment);

	final List<Payment> paymentsForInvoice = paymentService.findPaymentByCriteria(null, CUSTOMER_NO);

	assertThat(paymentsForInvoice, not(nullValue()));
	assertThat(paymentsForInvoice, hasSize(2));
    }
}
