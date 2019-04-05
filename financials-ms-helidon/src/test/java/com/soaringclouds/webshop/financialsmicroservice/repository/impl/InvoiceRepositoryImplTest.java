package com.soaringclouds.webshop.financialsmicroservice.repository.impl;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.soaringclouds.webshop.financialsmicroservice.entity.InvoiceEntity;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.InvoiceStatus;
import com.soaringclouds.webshop.financialsmicroservice.repository.InvoiceRepository;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class InvoiceRepositoryImplTest extends BaseMongoTest {

    private InvoiceRepository invoiceRepository;
    private Invoice invoice;

    @Override
    protected void beforeEachOverrideable() throws Exception {

        invoiceRepository = new InvoiceRepositoryImpl();
        ((BaseRepository)invoiceRepository).setNativeMongoManager(nativeMongoManager);

        byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/example.json"));

        final ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        invoice = mapper.readValue(bytes, Invoice.class);

        invoiceRepository.create(new InvoiceEntity(invoice));
    }

    @Test
    public void whenCallToFindAllThenReturnAllInvoices() {

        List<Invoice> invoices = invoiceRepository.findAll(Invoice.class);

        assertThat(invoices, hasSize(1));

        final Invoice persistetInvoice = invoices.get(0);

        assertThat(persistetInvoice.getInvoiceId(), notNullValue());
        assertThat(persistetInvoice.getInvoiceId(), equalTo(invoice.getInvoiceId()));
        assertThat(persistetInvoice.getOrderId(), equalTo(invoice.getOrderId()));
        assertThat(persistetInvoice.getOrderDate(), equalTo(invoice.getOrderDate()));
        //assertThat(persistetInvoice.getShippingCosts(), equalTo(invoice.getShippingCosts()));
        //assertThat(persistetInvoice.getTotalPrice(), equalTo(invoice.getTotalPrice()));
        assertThat(persistetInvoice.getCurrency(), equalTo(invoice.getCurrency()));
        assertThat(persistetInvoice.getPaymentStatus(), equalTo(invoice.getPaymentStatus()));
    }

    @Test
    public void whenCustomerNoIsPassedThenReturnOnlyInvoicesOfThisCustomer() {

        List<Invoice> invoices = invoiceRepository.findAllByInvoiceIdAndCustomerNo("", "CGN4711");

        assertThat(invoices, hasSize(1));

        final Invoice persistetInvoice = invoices.get(0);

        assertThat(persistetInvoice.getInvoiceId(), notNullValue());
        assertThat(persistetInvoice.getInvoiceId(), equalTo(invoice.getInvoiceId()));
        assertThat(persistetInvoice.getOrderId(), equalTo(invoice.getOrderId()));
        assertThat(persistetInvoice.getOrderDate(), equalTo(invoice.getOrderDate()));
        //assertThat(persistetInvoice.getShippingCosts(), equalTo(invoice.getShippingCosts()));
        //assertThat(persistetInvoice.getTotalPrice(), equalTo(invoice.getTotalPrice()));
        assertThat(persistetInvoice.getCurrency(), equalTo(invoice.getCurrency()));
        assertThat(persistetInvoice.getPaymentStatus(), equalTo(invoice.getPaymentStatus()));
    }

    @Test
    public void whenOrderIdIsPassedThenReturnOnlyInvoicesOfThisOrder() {

        List<Invoice> invoices = invoiceRepository.findAllByInvoiceIdAndCustomerNo("order0801", "");

        assertThat(invoices, hasSize(1));

        final Invoice persistetInvoice = invoices.get(0);

        assertThat(persistetInvoice.getInvoiceId(), notNullValue());
        assertThat(persistetInvoice.getInvoiceId(), equalTo(invoice.getInvoiceId()));
        assertThat(persistetInvoice.getOrderId(), equalTo(invoice.getOrderId()));
        assertThat(persistetInvoice.getOrderDate(), equalTo(invoice.getOrderDate()));
        //assertThat(persistetInvoice.getShippingCosts(), equalTo(invoice.getShippingCosts()));
        //assertThat(persistetInvoice.getTotalPrice(), equalTo(invoice.getTotalPrice()));
        assertThat(persistetInvoice.getCurrency(), equalTo(invoice.getCurrency()));
        assertThat(persistetInvoice.getPaymentStatus(), equalTo(invoice.getPaymentStatus()));
    }

    @Test
    public void whenInvoiceIdIsPassedThenReturnOnlyThisSpecificInvoice() {

        Invoice searchedInvoice = invoiceRepository.findById("invoice_id", invoice.getInvoiceId());

        assertThat(searchedInvoice, notNullValue());


        assertThat(searchedInvoice.getInvoiceId(), notNullValue());
        assertThat(searchedInvoice.getInvoiceId(), equalTo(invoice.getInvoiceId()));
        assertThat(searchedInvoice.getOrderId(), equalTo(invoice.getOrderId()));
        assertThat(searchedInvoice.getOrderDate(), equalTo(invoice.getOrderDate()));
        //assertThat(persistetInvoice.getShippingCosts(), equalTo(invoice.getShippingCosts()));
        //assertThat(persistetInvoice.getTotalPrice(), equalTo(invoice.getTotalPrice()));
        assertThat(searchedInvoice.getCurrency(), equalTo(invoice.getCurrency()));
        assertThat(searchedInvoice.getPaymentStatus(), equalTo(invoice.getPaymentStatus()));
    }

    @Test
    public void whenInvoiceIdIsUpdatedThenReturnUpdatedInvoice() {

        invoice.setInvoiceStatus(InvoiceStatus.PAID);
        Invoice updatedInvoice = invoiceRepository.update(new InvoiceEntity(invoice));

        assertThat(invoice, notNullValue());

        assertThat(updatedInvoice.getInvoiceStatus(), equalTo(InvoiceStatus.PAID));
        assertThat(invoice.getInvoiceId(), notNullValue());
        assertThat(invoice.getInvoiceId(), equalTo(updatedInvoice.getInvoiceId()));
        assertThat(invoice.getOrderId(), equalTo(updatedInvoice.getOrderId()));
        assertThat(invoice.getOrderDate(), equalTo(updatedInvoice.getOrderDate()));
        //assertThat(persistetInvoice.getShippingCosts(), equalTo(invoice.getShippingCosts()));
        //assertThat(persistetInvoice.getTotalPrice(), equalTo(invoice.getTotalPrice()));
        assertThat(invoice.getCurrency(), equalTo(updatedInvoice.getCurrency()));
        assertThat(invoice.getPaymentStatus(), equalTo(updatedInvoice.getPaymentStatus()));
    }

    @Test
    public void whenInvoiceIdIsDeletedThenInvoiceCannotBeFoundAfterwards() {


        invoiceRepository.delete(new InvoiceEntity(invoice));

        List<Invoice> invoices = invoiceRepository.findAll(Invoice.class);

        assertThat(invoices, IsEmptyCollection.empty());
    }

    @Test
    public void whenCustomerNoIsPassedWhichDoesNotExistThenReturnEmptyList() {

        List<Invoice> invoices = invoiceRepository.findAllByInvoiceIdAndCustomerNo("", "CGN4712");

        assertThat(invoices, IsEmptyCollection.empty());
    }
}
