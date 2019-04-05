package com.soaringclouds.webshop.financialsmicroservice.repository.impl;

import com.soaringclouds.webshop.financialsmicroservice.NativeMongoManager;
import com.soaringclouds.webshop.financialsmicroservice.entity.CustomerAccountEntity;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerAccount;
import com.soaringclouds.webshop.financialsmicroservice.repository.CustomerAccountRepository;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CustomerAccountRepositoryImpl extends BaseRepository<CustomerAccountEntity, CustomerAccount> implements CustomerAccountRepository {

    @Override
    protected String getMongoCollectionName() {
        return NativeMongoManager.MONGO_COLLECTION_CUSTOMERACCOUNT;
    }

    @Override
    protected Class<CustomerAccount> getJsonDocumentClass() {
        return CustomerAccount.class;
    }
}
