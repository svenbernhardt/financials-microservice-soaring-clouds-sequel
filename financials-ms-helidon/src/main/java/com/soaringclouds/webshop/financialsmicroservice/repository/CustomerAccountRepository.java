package com.soaringclouds.webshop.financialsmicroservice.repository;

import com.soaringclouds.webshop.financialsmicroservice.entity.CustomerAccountEntity;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerAccount;

import javax.enterprise.context.RequestScoped;

/**
 * Created by svb on 08.03.18.
 */
@RequestScoped
public interface CustomerAccountRepository extends GenericRepository<CustomerAccountEntity,
        CustomerAccount> {
}
