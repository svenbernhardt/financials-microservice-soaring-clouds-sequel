package com.soaringclouds.webshop.financialsmicroservice.builder;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.Customer;

/**
 * Created by svb on 25.02.18.
 */
public final class CustomerBuilder {
    private Customer customer;

    private CustomerBuilder() { customer = new Customer(); }

    public static CustomerBuilder aCustomer() { return new CustomerBuilder(); }

    public CustomerBuilder withCustomerNo(String customerNo) {
	customer.setCustomerNo(customerNo);
	return this;
    }

    public CustomerBuilder withFirstName(String firstName) {
	customer.setFirstName(firstName);
	return this;
    }

    public CustomerBuilder withLastName(String lastName) {
	customer.setLastName(lastName);
	return this;
    }

    public CustomerBuilder withEmail(String email) {
	customer.setEmail(email);
	return this;
    }

    public CustomerBuilder withPhone(String phone) {
	customer.setPhone(phone);
	return this;
    }

    public CustomerBuilder withId(String id) {
	customer.setId(id);
	return this;
    }

    public Customer build() { return customer; }
}
