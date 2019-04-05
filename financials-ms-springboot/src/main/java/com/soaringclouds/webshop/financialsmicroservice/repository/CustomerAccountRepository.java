package com.soaringclouds.webshop.financialsmicroservice.repository;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by svb on 08.03.18.
 */
public interface CustomerAccountRepository extends MongoRepository<CustomerAccount, String> {
}
