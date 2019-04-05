package com.soaringclouds.webshop.financialsmicroservice.repository;

import java.util.List;

import com.soaringclouds.webshop.financialsmicroservice.entity.InvoiceEntity;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;

/**
 * Created by svb on 24.02.18.
 */
public interface InvoiceRepository extends GenericRepository<InvoiceEntity, Invoice> {

    List<Invoice> findAllByInvoiceIdAndCustomerNo(String pOrderId, String pCustomerNo);
}
