package com.soaringclouds.webshop.financialsmicroservice.repository.impl;

import com.google.common.collect.Lists;
import com.soaringclouds.webshop.financialsmicroservice.repository.NativeMongoManager;
import com.soaringclouds.webshop.financialsmicroservice.entity.BaseMongoEntity;
import com.soaringclouds.webshop.financialsmicroservice.repository.GenericRepository;
import com.soaringclouds.webshop.financialsmicroservice.repository.util.DbQueryBuilder;
import org.mongojack.JacksonMongoCollection;

import javax.inject.Inject;
import java.util.List;

/**
 * BaseRepository
 */
public abstract class BaseRepository<T_IN extends BaseMongoEntity, T_OUT> implements GenericRepository<T_IN, T_OUT> {

    private NativeMongoManager nativeMongoManager;

    private JacksonMongoCollection<T_OUT> collection;

    @Inject
    public void setNativeMongoManager(NativeMongoManager pNativeMongoManager) {

        nativeMongoManager = pNativeMongoManager;

        collection = nativeMongoManager.getMongoCollection(getMongoCollectionName()
                , getJsonDocumentClass());
    }

    protected abstract String getMongoCollectionName();

    protected abstract Class<T_OUT> getJsonDocumentClass();

    protected JacksonMongoCollection<T_OUT> getCollection() {
        return collection;
    }

    @Override
    public void create(T_IN pEntity) {

        collection.insert((T_OUT) pEntity.getFinancialDocument());
    }

    @Override
    public void delete(T_IN pEntity) {

        collection.remove(DbQueryBuilder.create().and(pEntity.getIdAttributeName()
                , pEntity.getId()).get());
    }

    @Override
    public List<T_OUT> findAll(Class<T_OUT> pClass) {

        return Lists.newArrayList(collection.find());
    }

    @Override
    public T_OUT update(T_IN pEntity) {

        collection.replaceOne(DbQueryBuilder.create().and(pEntity.getIdAttributeName(),
                pEntity.getId()).get(), (T_OUT) pEntity.getFinancialDocument());

        return findById(pEntity.getIdAttributeName(), pEntity.getId());
    }

    @Override
    public T_OUT findById(String pIdentifierAttribute, String pId) {

        return collection.findOne(DbQueryBuilder.create().and(pIdentifierAttribute,
                pId).get());
    }
}