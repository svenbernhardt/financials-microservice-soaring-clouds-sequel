package com.soaringclouds.webshop.financialsmicroservice.entity;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerAccount;

/**
 * CustomerAccountEntity
 */
public class CustomerAccountEntity extends BaseMongoEntity<CustomerAccount> {

    public static final String IDENTIFIER_ATTRIBUTE_CUSTOMER_ACCOUNT = "customer_no";

    public CustomerAccountEntity(CustomerAccount pCustomerAccount) {
        super(pCustomerAccount, pCustomerAccount.getCustomerNo(), IDENTIFIER_ATTRIBUTE_CUSTOMER_ACCOUNT);
    }
}