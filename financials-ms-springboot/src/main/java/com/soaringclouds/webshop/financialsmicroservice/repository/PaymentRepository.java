package com.soaringclouds.webshop.financialsmicroservice.repository;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by svb on 04.03.18.
 */
public interface PaymentRepository extends MongoRepository<Payment, String> {
}
