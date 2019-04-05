package com.soaringclouds.webshop.financialsmicroservice.service.impl;

import com.soaringclouds.webshop.financialsmicroservice.entity.InvoiceEntity;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.ResponseMetadata;
import com.soaringclouds.webshop.financialsmicroservice.repository.CustomerAccountRepository;
import com.soaringclouds.webshop.financialsmicroservice.repository.InvoiceRepository;
import com.soaringclouds.webshop.financialsmicroservice.repository.impl.BaseMongoTest;
import com.soaringclouds.webshop.financialsmicroservice.repository.impl.CustomerAccountRepositoryImpl;
import com.soaringclouds.webshop.financialsmicroservice.repository.impl.InvoiceRepositoryImpl;
import com.soaringclouds.webshop.financialsmicroservice.service.CustomerAccountService;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.soaringclouds.webshop.financialsmicroservice.util.TestUtilities.createInvoice;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

public class InvoiceServiceImplTest extends BaseMongoTest {

    private InvoiceService invoiceService;

    private InvoiceRepository invoiceRepository;

    private CustomerAccountService customerAccountService;

    @Override
    protected void beforeEachOverrideable() throws Exception {

        invoiceRepository = new InvoiceRepositoryImpl();
        ((InvoiceRepositoryImpl) invoiceRepository).setNativeMongoManager(nativeMongoManager);


        final CustomerAccountRepository customerAccountRepository = new CustomerAccountRepositoryImpl();
        ((CustomerAccountRepositoryImpl) customerAccountRepository).setNativeMongoManager(nativeMongoManager);

        customerAccountService = new CustomerAccountServiceImpl();
        ((CustomerAccountServiceImpl) customerAccountService).setCustomerAccountRepository(customerAccountRepository);

        invoiceService = new InvoiceServiceImpl();
        ((InvoiceServiceImpl) invoiceService).setInvoiceRepository(invoiceRepository);
        ((InvoiceServiceImpl) invoiceService).setCustomerAccountService(customerAccountService);
    }

    public void whenCustomerAccountIsUnbalancedThenCreateOrderButReturnThatCustomersAccountIsFrozenNow() {

        final Invoice unpaidInvoice = createInvoice();
        unpaidInvoice.setTotalPrice(new BigDecimal(555.99).setScale(2, RoundingMode.HALF_UP));

        invoiceRepository.create(new InvoiceEntity(unpaidInvoice));

        final Invoice anotherUnpaidInvoice = createInvoice();
        anotherUnpaidInvoice.setTotalPrice(new BigDecimal(511.00).setScale(2,
                RoundingMode.HALF_UP));

        invoiceRepository.create(new InvoiceEntity(anotherUnpaidInvoice));

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
        assertThat(responseMetadata.getProcessingMessage(), startsWith("Invoice successfully " +
                "created!"));
    }

}
