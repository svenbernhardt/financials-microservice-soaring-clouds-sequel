package com.soaringclouds.webshop.financialsmicroservice.service.impl;

import com.soaringclouds.webshop.financialsmicroservice.event.CustomerStatusEventProducer;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerAccount;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerStatus;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.InvoiceStatus;
import com.soaringclouds.webshop.financialsmicroservice.repository.CustomerAccountRepository;
import com.soaringclouds.webshop.financialsmicroservice.service.CustomerAccountService;
import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * Created by svb on 08.03.18.
 */
@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAccountServiceImpl.class);

    @Autowired private CustomerStatusEventProducer customerStatusEventProducer;

    @Autowired private CustomerAccountRepository customerAccountRepository;

    @Value("${customerstatus.frozen.threshold}") private Double customerStatusFrozenThreshold;

    @Override
    public CustomerAccount createOrUpdateCustomerAccount(Invoice pInvoice) {

	final CustomerAccount customerAccountExample = createCustomerAccountExample(
			pInvoice.getCustomer().getCustomerNo());

	CustomerAccount customerAccount = customerAccountRepository
			.findOne(Example.of(customerAccountExample));

	if (customerAccount == null) {

	    customerAccount = createNewCustomerAccount(pInvoice, customerAccountExample);

	} else {

	    customerAccount = updateCustomerAccount(pInvoice, customerAccount);
	}

	return customerAccount;
    }

    @Override
    public CustomerAccount getCustomerAccount(String pCustomerNo) {

	return customerAccountRepository.findOne(Example.of(createCustomerAccountExample(pCustomerNo)));
    }

    CustomerAccount updateCustomerAccount(Invoice pInvoice, CustomerAccount pCustomerAccount) {

	final Double currentCustomerAccountBalance = pCustomerAccount.getBalance();

	if (pInvoice.getInvoiceStatus() == InvoiceStatus.OPEN) {
	    pCustomerAccount.setBalance(
			    Precision.round(currentCustomerAccountBalance - pInvoice.getTotalPrice(), 2));
	} else {
	    pCustomerAccount.setBalance(
			    Precision.round(currentCustomerAccountBalance + pInvoice.getTotalPrice(), 2));
	}

	handleCustomerAccountStatus(pCustomerAccount);

	pCustomerAccount = customerAccountRepository.save(pCustomerAccount);

	LOGGER.debug(String.format("Updated customer account for customer [%s %s, Customer-No: %s]. " +
					"New "
					+ "balance: [%s], was [%s]", pCustomerAccount.getFirstName(),
			pCustomerAccount.getLastName(), pCustomerAccount.getCustomerNo(),
			pCustomerAccount.getBalance(), currentCustomerAccountBalance));

	return pCustomerAccount;
    }

    @Override
    public CustomerAccount updateCustomerAccount(Invoice pInvoice) {

	return updateCustomerAccount(pInvoice, getCustomerAccount(pInvoice.getCustomer().getCustomerNo()));
    }

    CustomerAccount createNewCustomerAccount(Invoice pInvoice, CustomerAccount pCustomerAccountExample) {

	pCustomerAccountExample.setFirstName(pInvoice.getCustomer().getFirstName());
	pCustomerAccountExample.setLastName(pInvoice.getCustomer().getLastName());
	pCustomerAccountExample.setCustomerStatus(CustomerStatus.NORMAL);
	pCustomerAccountExample.setBalance(0d - pInvoice.getTotalPrice());
	pCustomerAccountExample.setCurrency(pInvoice.getCurrency());

	final CustomerAccount customerAccount = customerAccountRepository.insert(pCustomerAccountExample);

	LOGGER.debug(String.format("Created new customer account for customer [%s %s, Customer-No: %s]",
			pCustomerAccountExample.getFirstName(), pCustomerAccountExample.getLastName(),
			pCustomerAccountExample.getCustomerNo()));

	return customerAccount;
    }

    void handleCustomerAccountStatus(CustomerAccount pCustomerAccount) {

	boolean customerBalanceUnderThreshold =
			customerStatusFrozenThreshold <= pCustomerAccount.getBalance();

	if (pCustomerAccount.getCustomerStatus() != CustomerStatus.FROZEN) {

	    if (!customerBalanceUnderThreshold) {

		final String customerNo = pCustomerAccount.getCustomerNo();

		LOGGER.debug(String.format("Customer [%s] frozen because of unbalanced account! Current "
				+ "balance: " + "[%s]", customerNo, pCustomerAccount.getBalance()));
		pCustomerAccount.setCustomerStatus(CustomerStatus.FROZEN);
		customerStatusEventProducer.produceCustomerStatusFrozen(customerNo);
	    }
	}
	else {

	    if(customerBalanceUnderThreshold) {
		final String customerNo = pCustomerAccount.getCustomerNo();

		LOGGER.debug(String.format("Customer [%s] frozen because of unbalanced account! Current "
				+ "balance: " + "[%s]", customerNo, pCustomerAccount.getBalance()));
		pCustomerAccount.setCustomerStatus(CustomerStatus.NORMAL);
		customerStatusEventProducer.produceCustomerStatusUnFrozen(customerNo);
	    }
	}
    }

    CustomerAccount createCustomerAccountExample(String pCustomerNo) {

	final CustomerAccount customerAccountExample = new CustomerAccount();
	customerAccountExample.customerNo(pCustomerNo);

	return customerAccountExample;
    }
}
