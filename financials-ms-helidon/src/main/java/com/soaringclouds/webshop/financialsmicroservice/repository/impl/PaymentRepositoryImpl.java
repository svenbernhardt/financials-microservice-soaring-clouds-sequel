package com.soaringclouds.webshop.financialsmicroservice.repository.impl;

import com.google.common.collect.Lists;
import com.soaringclouds.webshop.financialsmicroservice.repository.NativeMongoManager;
import com.soaringclouds.webshop.financialsmicroservice.entity.CustomerAccountEntity;
import com.soaringclouds.webshop.financialsmicroservice.entity.InvoiceEntity;
import com.soaringclouds.webshop.financialsmicroservice.entity.PaymentEntity;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Payment;
import com.soaringclouds.webshop.financialsmicroservice.repository.PaymentRepository;
import com.soaringclouds.webshop.financialsmicroservice.repository.util.DbQueryBuilder;

import javax.enterprise.context.Dependent;
import java.util.List;

@Dependent
public class PaymentRepositoryImpl extends BaseRepository<PaymentEntity, Payment> implements PaymentRepository {

    @Override
    protected String getMongoCollectionName() {
        return NativeMongoManager.MONGO_COLLECTION_PAYMENT;
    }

    @Override
    protected Class<Payment> getJsonDocumentClass() {
        return Payment.class;
    }

    @Override
    public List<Payment> findPaymentsByInvoiceIdAndCustomerNo(String pInvoiceId,
                                                              String pCustomerNo) {
        return Lists.newArrayList(getCollection().find(DbQueryBuilder.create().and(InvoiceEntity.IDENTIFIER_ATTRIBUTE_INVOICE,
                pInvoiceId).and(
                CustomerAccountEntity.IDENTIFIER_ATTRIBUTE_CUSTOMER_ACCOUNT, pCustomerNo).get()).iterator());
    }
}
