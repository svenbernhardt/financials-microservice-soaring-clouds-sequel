package com.soaringclouds.webshop.financialsmicroservice.repository;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by svb on 24.02.18.
 */
public interface InvoiceRepository extends MongoRepository<Invoice, String> {

}
