package com.soaringclouds.webshop.financialsmicroservice.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.bson.types.ObjectId;

/**
 * BaseMongoEntity
 */
public abstract class BaseMongoEntity<T> {

    private String id;

    private String idAttributeName;

    private T financialDocument;

    public BaseMongoEntity() {
    }

    protected BaseMongoEntity(T pFinancialDocument, String pId,
                              String pIdAttributeName) {
        financialDocument = pFinancialDocument;
        id = pId;
        idAttributeName = pIdAttributeName;
    }

    public String getIdAttributeName() {
        return idAttributeName;
    }

    /**
     * @return unique identifier of the concrete financial document
     */
    public String getId() {
        return id;
    }

    /**
     * @return the financialDocument
     */
    public T getFinancialDocument() {
        return financialDocument;
    }
}