package com.soaringclouds.webshop.financialsmicroservice.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.soaringclouds.webshop.financialsmicroservice.builder.CustomerBuilder;
import com.soaringclouds.webshop.financialsmicroservice.builder.InvoiceBuilder;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStreamReader;
import java.util.List;

import static com.soaringclouds.webshop.financialsmicroservice.TestUtilities.createInvoice;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.text.IsEmptyString.isEmptyString;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@EnableMongoRepositories(basePackages = "com.soaringclouds.webshop.financialsmicroservice.repository")
public class InvoiceRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceRepositoryTest.class.getName());

    private static String CUSTOMER_NO = "CGN4711";
    private static String ORDER_ID = "order4711";
    private static String INVOICE_ID = "invoice0815";

    @Autowired private InvoiceRepository invoiceRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private Invoice unsavedInvoice;

    @Before
    public void setUp() {

	unsavedInvoice = createInvoice();
    }

    @After
    public void tearDown() {

        mongoTemplate.dropCollection(Invoice.class);
    }

    @Test
    public void whenInvoiceIsReceivedThanSaveItToDb() throws Exception {

	assertThat(unsavedInvoice.getId(), nullValue());

	final Invoice invoiceAfterSave = invoiceRepository.insert(unsavedInvoice);

	final List<Invoice> all = invoiceRepository.findAll();

	all.forEach(inv -> LOGGER.info(inv.toString()));

	assertThat(invoiceAfterSave.getId(), not(nullValue()));
	assertThat(invoiceAfterSave.getId(), not(isEmptyString()));
    }

    @Test
    public void whenCustomerNoIsCriteriaThenReturnCorrespondingInvoice() {

	assertThat(unsavedInvoice.getId(), nullValue());

	final Invoice savedInvoice = invoiceRepository.insert(unsavedInvoice);

	Example<Invoice> invoiceCriteria = Example.of(InvoiceBuilder.anInvoice()
			.withCustomer(CustomerBuilder.aCustomer().withCustomerNo(CUSTOMER_NO).build())
			.build());

	final List<Invoice> searchResult = invoiceRepository.findAll(invoiceCriteria);

	assertThat(searchResult, not(nullValue()));
	assertThat(searchResult.get(0).getId(), equalTo(savedInvoice.getId()));
    }

    @Test
    public void whenOrderIdIsCriteriaThenReturnCorrespondingInvoice() {

	assertThat(unsavedInvoice.getId(), nullValue());

	final Invoice savedInvoice = invoiceRepository.insert(unsavedInvoice);

	Example<Invoice> invoiceCriteria = Example
			.of(InvoiceBuilder.anInvoice().withOrderId(ORDER_ID).build());

	final List<Invoice> searchResult = invoiceRepository.findAll(invoiceCriteria);

	assertThat(searchResult, not(nullValue()));
	assertThat(searchResult.get(0).getId(), equalTo(savedInvoice.getId()));
    }

    @Test
    public void whenInvoiceIdIsCriteriaThenReturnCorrespondingInvoice() {

	assertThat(unsavedInvoice.getId(), nullValue());

	final Invoice savedInvoice = invoiceRepository.insert(unsavedInvoice);

	Example<Invoice> invoiceCriteria = Example
			.of(InvoiceBuilder.anInvoice().withInvoiceId(INVOICE_ID).build());

	final Invoice searchResult = invoiceRepository.findOne(invoiceCriteria);

	assertThat(searchResult, not(nullValue()));
	assertThat(searchResult.getId(), equalTo(savedInvoice.getId()));
    }

    @Test
    public void whenInvoicePositionIsChangedThenUpdateInvoice() {

	assertThat(unsavedInvoice.getId(), nullValue());

	final Invoice savedInvoice = invoiceRepository.insert(unsavedInvoice);

	savedInvoice.getInvoicePositions().get(0).setNetPrice(12.99);
	savedInvoice.getInvoicePositions().get(0).setGrossPrice(17.99);

	invoiceRepository.save(savedInvoice);

	final Invoice searchResult = invoiceRepository
			.findOne(Example.of(InvoiceBuilder.anInvoice().withInvoiceId(INVOICE_ID).build()));

	final Invoice originalInvoice = createInvoice();

	assertThat(searchResult.getId(), equalTo(savedInvoice.getId()));
	assertThat(searchResult.getInvoicePositions().get(0).getNetPrice(),
			not(equalTo(originalInvoice.getInvoicePositions().get(0).getNetPrice())));
	assertThat(searchResult.getInvoicePositions().get(0).getGrossPrice(),
			not(equalTo(originalInvoice.getInvoicePositions().get(0).getGrossPrice())));
    }
}
