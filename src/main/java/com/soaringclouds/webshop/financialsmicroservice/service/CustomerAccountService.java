package com.soaringclouds.webshop.financialsmicroservice.service;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerAccount;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;

/**
 * Created by svb on 08.03.18.
 */
public interface CustomerAccountService {

    CustomerAccount createOrUpdateCustomerAccount(Invoice pInvoice);

    CustomerAccount getCustomerAccount(String pCustomerNo);
}
