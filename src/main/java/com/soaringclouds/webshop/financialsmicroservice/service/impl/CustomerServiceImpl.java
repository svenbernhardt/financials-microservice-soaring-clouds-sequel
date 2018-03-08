package com.soaringclouds.webshop.financialsmicroservice.service.impl;

import com.soaringclouds.webshop.financialsmicroservice.event.CustomerStatusEventProducer;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.PaymentStatus;
import com.soaringclouds.webshop.financialsmicroservice.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by svb on 05.03.18.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired private CustomerStatusEventProducer customerStatusEventProducer;

    @Value("${customerstatus.frozen.threshold}") private Double customerStatusFrozenThreshold;

    @Override
    public boolean checkCustomerBalanceUnderThreshold(List<Invoice> pInvoicesForCustomer) {

	final DoubleSummaryStatistics customerBalance = pInvoicesForCustomer.stream()
			.filter(invoice -> invoice.getPaymentStatus() != PaymentStatus.RECEIVED)
			.collect(Collectors.summarizingDouble(Invoice::getTotalPrice));

	boolean customerBalanceUnderThreshold = customerStatusFrozenThreshold > customerBalance.getSum();

	if (!customerBalanceUnderThreshold) {

	    final String customerNo = pInvoicesForCustomer.get(0).getCustomer().getCustomerNo();

	    LOGGER.debug(String.format("Customer [%s] frozen because of unbalanced account! Current "
			    + "balance: "
			    + "[%s]", customerNo, customerBalance.getSum()));
	    customerStatusEventProducer.produceCustomerStatusFrozen(customerNo);
	}

	return customerBalanceUnderThreshold;
    }
}
