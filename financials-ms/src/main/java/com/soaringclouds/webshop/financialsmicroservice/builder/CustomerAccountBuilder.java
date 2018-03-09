package com.soaringclouds.webshop.financialsmicroservice.builder;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerAccount;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerStatus;

/**
 * Created by svb on 08.03.18.
 */
public final class CustomerAccountBuilder {
    private CustomerAccount customerAccount;

    private CustomerAccountBuilder() { customerAccount = new CustomerAccount(); }

    public static CustomerAccountBuilder aCustomerAccount() { return new CustomerAccountBuilder(); }

    public CustomerAccountBuilder withCustomerNo(String customerNo) {
	customerAccount.setCustomerNo(customerNo);
	return this;
    }

    public CustomerAccountBuilder withFirstName(String firstName) {
	customerAccount.setFirstName(firstName);
	return this;
    }

    public CustomerAccountBuilder withLastName(String lastName) {
	customerAccount.setLastName(lastName);
	return this;
    }

    public CustomerAccountBuilder withBalance(Double balance) {
	customerAccount.setBalance(balance);
	return this;
    }

    public CustomerAccountBuilder withCustomerStatus(CustomerStatus customerStatus) {
	customerAccount.setCustomerStatus(customerStatus);
	return this;
    }

    public CustomerAccountBuilder withId(String id) {
	customerAccount.setId(id);
	return this;
    }

    public CustomerAccount build() { return customerAccount; }
}
