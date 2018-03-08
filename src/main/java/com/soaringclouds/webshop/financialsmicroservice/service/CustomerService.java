package com.soaringclouds.webshop.financialsmicroservice.service;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;

import java.util.List;

/**
 * Created by svb on 05.03.18.
 */
public interface CustomerService {

    boolean checkCustomerBalanceUnderThreshold(List<Invoice> pInvoicesForCustomer);
}
