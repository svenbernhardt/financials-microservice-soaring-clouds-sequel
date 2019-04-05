package com.soaringclouds.webshop.financialsmicroservice.service.impl;

import com.soaringclouds.webshop.financialsmicroservice.entity.PaymentEntity;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.*;
import com.soaringclouds.webshop.financialsmicroservice.repository.CustomerAccountRepository;
import com.soaringclouds.webshop.financialsmicroservice.repository.InvoiceRepository;
import com.soaringclouds.webshop.financialsmicroservice.repository.PaymentRepository;
import com.soaringclouds.webshop.financialsmicroservice.repository.impl.BaseMongoTest;
import com.soaringclouds.webshop.financialsmicroservice.repository.impl.CustomerAccountRepositoryImpl;
import com.soaringclouds.webshop.financialsmicroservice.repository.impl.InvoiceRepositoryImpl;
import com.soaringclouds.webshop.financialsmicroservice.repository.impl.PaymentRepositoryImpl;
import com.soaringclouds.webshop.financialsmicroservice.service.CustomerAccountService;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import com.soaringclouds.webshop.financialsmicroservice.service.PaymentService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.logging.Logger;

import static com.soaringclouds.webshop.financialsmicroservice.util.TestUtilities.createInvoice;
import static com.soaringclouds.webshop.financialsmicroservice.util.TestUtilities.createPayment;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;

public class PaymentServiceImplTest extends BaseMongoTest {

    private static final Logger LOGGER = Logger.getLogger(PaymentServiceImplTest.class.getName());

    private static String CUSTOMER_NO = "CGN4711";
    private static String ORDER_ID = "order4711";
    private static String INVOICE_ID = "invoice0815";

    private Payment newPayment;

    private PaymentService paymentService;

    private PaymentRepository paymentRepository;

    private InvoiceService invoiceService;

    private CustomerAccountService customerAccountService;

    private CustomerAccount customerAccountBeforePayment;

    @Override
    protected void beforeEachOverrideable() throws Exception {

        final InvoiceRepository invoiceRepository = new InvoiceRepositoryImpl();
        ((InvoiceRepositoryImpl) invoiceRepository).setNativeMongoManager(nativeMongoManager);

        final CustomerAccountRepository customerAccountRepository = new CustomerAccountRepositoryImpl();
        ((CustomerAccountRepositoryImpl) customerAccountRepository).setNativeMongoManager(nativeMongoManager);

        customerAccountService = new CustomerAccountServiceImpl();
        ((CustomerAccountServiceImpl) customerAccountService).setCustomerAccountRepository(customerAccountRepository);

        invoiceService = new InvoiceServiceImpl();
        ((InvoiceServiceImpl) invoiceService).setInvoiceRepository(invoiceRepository);
        ((InvoiceServiceImpl) invoiceService).setCustomerAccountService(customerAccountService);

        paymentRepository = new PaymentRepositoryImpl();
        ((PaymentRepositoryImpl) paymentRepository).setNativeMongoManager(nativeMongoManager);

        paymentService = new PaymentServiceImpl();
        ((PaymentServiceImpl) paymentService).setCustomerAccountService(customerAccountService);
        ((PaymentServiceImpl) paymentService).setInvoiceService(invoiceService);
        ((PaymentServiceImpl) paymentService).setPaymentRepository(paymentRepository);

        invoiceService.createInvoice(createInvoice());

        newPayment = createPayment();

        customerAccountBeforePayment =
                customerAccountService.getCustomerAccount(newPayment.getCustomerNo());
    }

    @Test
    public void whenPaymentIsReceivedThenUpdateInvoiceAndCustomerAccount() {

        final ResponseMetadata response = paymentService.savePaymentAndUpdateInvoice(newPayment);

        assertThat(response, notNullValue());

        final Invoice paidInvoice =
                invoiceService.findInvoiceByInvoiceId(newPayment.getInvoiceId());

        assertThat(paidInvoice.getPaymentStatus(), equalTo(PaymentStatus.RECEIVED));
        assertThat(paidInvoice.getInvoiceStatus(), equalTo(InvoiceStatus.PAID));

        final CustomerAccount customerAccount = customerAccountService
                .getCustomerAccount(newPayment.getCustomerNo());

        assertThat(customerAccount.getBalance().setScale(2, RoundingMode.HALF_UP),
                equalTo(new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP)));
    }

    @Test
    public void whenSearchPaymentByInvoiceIdThenReturnExactlyOnePayment() {

        paymentRepository.create(new PaymentEntity(newPayment));

        final List<Payment> paymentsForInvoice = paymentService.findPaymentByCriteria(INVOICE_ID,
                null);

        assertThat(paymentsForInvoice, notNullValue());
        assertThat(paymentsForInvoice, hasSize(1));
    }

    @Test
    public void whenSearchPaymentByCustomerNoAndInvoiceIdThenReturnOnePaymentForTheInvoice() {

        paymentRepository.create(new PaymentEntity(newPayment));

        newPayment.setPaymentId("payment667");
        newPayment.setInvoiceId("invoice4711");

        paymentRepository.create(new PaymentEntity(newPayment));

        final List<Payment> paymentsForInvoice = paymentService
                .findPaymentByCriteria(INVOICE_ID, CUSTOMER_NO);

        assertThat(paymentsForInvoice, notNullValue());
        assertThat(paymentsForInvoice, hasSize(1));

        assertThat(paymentsForInvoice.get(0).getInvoiceId(), equalTo(INVOICE_ID));
    }

    @Test
    public void whenSearchPaymentByCustomerNoThenReturnAllPaymentsForCustomer() {

        paymentRepository.create(new PaymentEntity(newPayment));

        newPayment.setPaymentId("payment667");
        newPayment.setInvoiceId("invoice4711");

        paymentRepository.create(new PaymentEntity(newPayment));

        final List<Payment> paymentsForInvoice = paymentService.findPaymentByCriteria(null,
                CUSTOMER_NO);

        assertThat(paymentsForInvoice, notNullValue());
        assertThat(paymentsForInvoice, hasSize(2));
    }
}
