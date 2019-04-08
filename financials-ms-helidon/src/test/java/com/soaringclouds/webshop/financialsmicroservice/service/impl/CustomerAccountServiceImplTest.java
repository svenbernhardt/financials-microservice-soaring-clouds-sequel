package com.soaringclouds.webshop.financialsmicroservice.service.impl;

import com.soaringclouds.webshop.financialsmicroservice.builder.CustomerAccountBuilder;
import com.soaringclouds.webshop.financialsmicroservice.entity.CustomerAccountEntity;
import com.soaringclouds.webshop.financialsmicroservice.event.KafkaMessageProducer;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerAccount;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerStatus;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.InvoiceStatus;
import com.soaringclouds.webshop.financialsmicroservice.repository.CustomerAccountRepository;
import com.soaringclouds.webshop.financialsmicroservice.repository.impl.BaseMongoTest;
import com.soaringclouds.webshop.financialsmicroservice.repository.impl.CustomerAccountRepositoryImpl;
import com.soaringclouds.webshop.financialsmicroservice.service.CustomerAccountService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.soaringclouds.webshop.financialsmicroservice.util.TestUtilities.createInvoice;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;

public class CustomerAccountServiceImplTest extends BaseMongoTest {

    public static final String CUSTOMER_ACCOUNT_NO = "CGN4711";

    private CustomerAccountService customerAccountService;

    private CustomerAccountRepository customerAccountRepository;

    private KafkaMessageProducer kafkaMessageProducer;

    private Invoice invoice;

    @Override
    protected void beforeEachOverrideable() throws Exception {

        customerAccountRepository = new CustomerAccountRepositoryImpl();
        ((CustomerAccountRepositoryImpl) customerAccountRepository).setNativeMongoManager(nativeMongoManager);

        customerAccountService = new CustomerAccountServiceImpl();
        ((CustomerAccountServiceImpl) customerAccountService).setCustomerAccountRepository(customerAccountRepository);

        kafkaMessageProducer = mock(KafkaMessageProducer.class);
        ((CustomerAccountServiceImpl) customerAccountService).setKafkaMessageProducer(kafkaMessageProducer);
        invoice = createInvoice();
    }

    @Test
    public void whenCustomerAccountDoesNotExistThenCreateIt() {

        final CustomerAccount customerAccount = customerAccountService.createOrUpdateCustomerAccount
                (invoice);

        assertThat(customerAccount, notNullValue());
        assertThat(customerAccount.getFirstName(), equalTo("Sven"));
        assertThat(customerAccount.getLastName(), equalTo("Bernhardt"));
        assertThat(customerAccount.getCustomerStatus(), equalTo(CustomerStatus.NORMAL));
        assertThat(customerAccount.getBalance().setScale(2, RoundingMode.HALF_UP),
                equalTo(new BigDecimal(-30.96).setScale(2, RoundingMode.HALF_UP)));
    }

    @Test
    public void whenCustomerAccountExistsAndInvoiceIsOpenThenJustUpdateBalance() {

        createCustomerAccount(-100d);

        CustomerAccount customerAccount =
                customerAccountService.createOrUpdateCustomerAccount(invoice);

        assertThat(customerAccount.getBalance().setScale(2, RoundingMode.HALF_UP),
                equalTo(new BigDecimal(-130.96).setScale(2, RoundingMode.HALF_UP)));
    }

    @Test
    public void whenCustomerAccountExistsAndInvoiceIsCanceledThenJustUpdateBalance() {

        invoice.setInvoiceStatus(InvoiceStatus.CANCEL);

        createCustomerAccount(-100d);

        CustomerAccount customerAccount =
                customerAccountService.createOrUpdateCustomerAccount(invoice);

        assertThat(customerAccount.getBalance().setScale(2, RoundingMode.HALF_UP),
                equalTo(new BigDecimal(-69.04).setScale(2, RoundingMode.HALF_UP)));
    }

    @Test
    public void whenCustomerAccountExistsAndInvoiceIsPaidThenUpdateBalance() {

        invoice.setInvoiceStatus(InvoiceStatus.PAID);

        createCustomerAccount(-30.96d);

        CustomerAccount customerAccount =
                customerAccountService.createOrUpdateCustomerAccount(invoice);

        assertThat(customerAccount.getBalance().setScale(2, RoundingMode.HALF_UP),
                equalTo(new BigDecimal(0.0d).setScale(2, RoundingMode.HALF_UP)));
    }

    @Test
    public void whenCustomerAccountBalanceIsLowerThanThresholdThenReturnTrue() {

        final CustomerAccount customerAccount = createCustomerAccount(-30.96);

        assertThat(((CustomerAccountServiceImpl) customerAccountService).isCustomerBalanceUnderThreshold(customerAccount), equalTo(true));
    }

    @Test
    public void whenCustomerAccountBalanceIsHigherThanThresholdThenReturnFalse() {

        final CustomerAccount customerAccount = createCustomerAccount(-1300.96);

        assertThat(((CustomerAccountServiceImpl) customerAccountService).isCustomerBalanceUnderThreshold(customerAccount), equalTo(false));
    }

    @Test
    public void whenCustomerAccountBalanceIsEqualToThresholdThresholdThenReturnFalse() {

        final CustomerAccount customerAccount = createCustomerAccount(-1000);

        assertThat(((CustomerAccountServiceImpl) customerAccountService).isCustomerBalanceUnderThreshold(customerAccount), equalTo(false));
    }

    private CustomerAccount createCustomerAccount(double pStartBalance) {

        CustomerAccount customerAccount =
                CustomerAccountBuilder.aCustomerAccount().withCustomerNo(CUSTOMER_ACCOUNT_NO)
                        .withFirstName("Sven").withLastName("Bernhardt")
                        .withCustomerStatus(CustomerStatus.NORMAL).withBalance(pStartBalance).build();

        customerAccountRepository.create(new CustomerAccountEntity(customerAccount));

        return customerAccountRepository.findById(CustomerAccountEntity.IDENTIFIER_ATTRIBUTE_CUSTOMER_ACCOUNT, CUSTOMER_ACCOUNT_NO);
    }
}
