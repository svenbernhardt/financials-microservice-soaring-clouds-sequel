package com.soaringclouds.webshop.financialsmicroservice.service.impl;

import com.soaringclouds.webshop.financialsmicroservice.entity.CustomerAccountEntity;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerAccount;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerStatus;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.InvoiceStatus;
import com.soaringclouds.webshop.financialsmicroservice.repository.CustomerAccountRepository;
import com.soaringclouds.webshop.financialsmicroservice.service.CustomerAccountService;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import com.soaringclouds.webshop.financialsmicroservice.service.PaymentService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Logger;

/**
 * Created by svb on 08.03.18.
 */
@RequestScoped
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private static final Logger LOGGER =
            Logger.getLogger(CustomerAccountServiceImpl.class.getSimpleName());

    // @Autowired private CustomerStatusEventProducer customerStatusEventProducer;

    @Inject
    private CustomerAccountRepository customerAccountRepository;

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private PaymentService paymentService;

    private BigDecimal customerStatusFrozenThreshold = new BigDecimal(1000d);

    @Override
    public CustomerAccount createOrUpdateCustomerAccount(Invoice pInvoice) {

        final CustomerAccount customerAccountExample = createCustomerAccountExample(
                pInvoice.getCustomer().getCustomerNo());

        CustomerAccount customerAccount =
                getCustomerAccount(pInvoice.getCustomer().getCustomerNo());

        if (customerAccount == null) {

            customerAccount = createNewCustomerAccount(pInvoice, customerAccountExample);

        } else {

            customerAccount = updateCustomerAccount(pInvoice, customerAccount);
        }

        return customerAccount;
    }

    @Override
    public CustomerAccount getCustomerAccount(String pCustomerNo) {

        return customerAccountRepository.findById(CustomerAccountEntity.IDENTIFIER_ATTRIBUTE_CUSTOMER_ACCOUNT, pCustomerNo);
    }

    CustomerAccount updateCustomerAccount(Invoice pInvoice, CustomerAccount pCustomerAccount) {

        final BigDecimal currentCustomerAccountBalance = pCustomerAccount.getBalance();

        if (pInvoice.getInvoiceStatus() == InvoiceStatus.OPEN) {
            pCustomerAccount.setBalance(currentCustomerAccountBalance.subtract(pInvoice
                    .getTotalPrice()).setScale(2, RoundingMode.HALF_UP));
        } else {
            pCustomerAccount.setBalance(currentCustomerAccountBalance.add(pInvoice
                    .getTotalPrice()).setScale(2, RoundingMode.HALF_UP));
        }

        handleCustomerAccountStatus(pCustomerAccount);

        final CustomerAccount updatedCustomerAccount =
                customerAccountRepository.update(new CustomerAccountEntity(pCustomerAccount));

        LOGGER.info(String.format(
                "Updated customer account for customer [%s %s, Customer-No: %s]. " + "New " +
                        "balance: [%s], was [%s]",
                updatedCustomerAccount.getFirstName(), updatedCustomerAccount.getLastName(),
                updatedCustomerAccount.getCustomerNo(),
                updatedCustomerAccount.getBalance(), currentCustomerAccountBalance));

        return updatedCustomerAccount;
    }

    @Override
    public CustomerAccount updateCustomerAccount(Invoice pInvoice) {

        return updateCustomerAccount(pInvoice,
                getCustomerAccount(pInvoice.getCustomer().getCustomerNo()));
    }

    @Override
    public void deleteCustomerAccount(String pCustomerId) {

        paymentService.deletePayments(pCustomerId);

        invoiceService.deleteInvoices(pCustomerId);

        final CustomerAccount customerAccount = getCustomerAccount(pCustomerId);

        customerAccountRepository.delete(new CustomerAccountEntity(customerAccount));
    }

    CustomerAccount createNewCustomerAccount(Invoice pInvoice,
                                             CustomerAccount pCustomerAccountExample) {

        pCustomerAccountExample.setFirstName(pInvoice.getCustomer().getFirstName());
        pCustomerAccountExample.setLastName(pInvoice.getCustomer().getLastName());
        pCustomerAccountExample.setCustomerStatus(CustomerStatus.NORMAL);
        pCustomerAccountExample.setBalance(new BigDecimal(0).subtract(pInvoice.getTotalPrice()).setScale(2, RoundingMode.HALF_UP));
        pCustomerAccountExample.setCurrency(pInvoice.getCurrency());

        customerAccountRepository.create(new CustomerAccountEntity(pCustomerAccountExample));

        LOGGER.info(String.format("Created new customer account for customer [%s %s, " +
                        "Customer-No: %s]",
                pCustomerAccountExample.getFirstName(), pCustomerAccountExample.getLastName(),
                pCustomerAccountExample.getCustomerNo()));

        return getCustomerAccount(pCustomerAccountExample.getCustomerNo());
    }

    void handleCustomerAccountStatus(CustomerAccount pCustomerAccount) {

        if (pCustomerAccount.getCustomerStatus() != CustomerStatus.FROZEN) {

            if (!isCustomerBalanceUnderThreshold(pCustomerAccount)) {

                toggleCustomerStatus(pCustomerAccount, CustomerStatus.FROZEN);
                //customerStatusEventProducer.produceCustomerStatusFrozen(customerNo);
            }
        } else {

            if (isCustomerBalanceUnderThreshold(pCustomerAccount)) {
                toggleCustomerStatus(pCustomerAccount, CustomerStatus.NORMAL);
                //customerStatusEventProducer.produceCustomerStatusUnFrozen(customerNo);
            }
        }
    }

    boolean isCustomerBalanceUnderThreshold(CustomerAccount pCustomerAccount) {
        return pCustomerAccount.getBalance().setScale(2, RoundingMode.HALF_UP)
                .compareTo(customerStatusFrozenThreshold.setScale(2, RoundingMode.HALF_UP)) == -1;
    }

    CustomerAccount toggleCustomerStatus(CustomerAccount pCustomerAccount,
                                         CustomerStatus pCustomerStatus) {

        final String customerNo = pCustomerAccount.getCustomerNo();

        LOGGER.info(String.format(
                "Customer [%s] frozen because of unbalanced account! Current balance [%s]",
                customerNo, pCustomerAccount.getBalance()));
        pCustomerAccount.setCustomerStatus(pCustomerStatus);

        return pCustomerAccount;
    }

    CustomerAccount createCustomerAccountExample(String pCustomerNo) {

        final CustomerAccount customerAccountExample = new CustomerAccount();
        customerAccountExample.customerNo(pCustomerNo);

        return customerAccountExample;
    }

    protected void setCustomerAccountRepository(CustomerAccountRepository pCustomerAccountRepository) {
        customerAccountRepository = pCustomerAccountRepository;
    }
}
