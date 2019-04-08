package com.soaringclouds.webshop.financialsmicroservice.repository.impl;

import com.google.common.collect.Lists;
import com.soaringclouds.webshop.financialsmicroservice.repository.NativeMongoManager;
import com.soaringclouds.webshop.financialsmicroservice.entity.InvoiceEntity;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.repository.InvoiceRepository;
import com.soaringclouds.webshop.financialsmicroservice.repository.util.DbQueryBuilder;
import org.eclipse.microprofile.metrics.annotation.Metered;

import javax.enterprise.context.Dependent;
import java.util.List;

/**
 * InvoiceRepositoryImpl
 */
@Dependent
@Metered
public class InvoiceRepositoryImpl extends BaseRepository<InvoiceEntity, Invoice> implements InvoiceRepository {

    @Override
    public List<Invoice> findAllByInvoiceIdAndCustomerNo(String pOrderId, String pCustomerNo) {

        return Lists.newArrayList(getCollection().find(DbQueryBuilder.create().and("order_id",
                pOrderId).
                and("customer.customer_no", pCustomerNo).get()).iterator());
    }

    @Override
    protected String getMongoCollectionName() {
        return NativeMongoManager.MONGO_COLLECTION_INVOICE;
    }

    @Override
    protected Class<Invoice> getJsonDocumentClass() {
        return Invoice.class;
    }
}