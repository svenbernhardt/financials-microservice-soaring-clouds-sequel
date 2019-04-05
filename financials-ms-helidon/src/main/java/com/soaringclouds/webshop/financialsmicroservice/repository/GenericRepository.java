package com.soaringclouds.webshop.financialsmicroservice.repository;

import java.util.List;

/**
 * GenericRepository that implements base CRUD functionality
 */
public interface GenericRepository<T_IN, T_OUT> {

    List<T_OUT> findAll(Class<T_OUT> pClass);

    void create(T_IN pEntity);

    T_OUT update(T_IN pEntity);

    void delete(T_IN pEntity);

    T_OUT findById(String pIdentifierAttribute, String pId);
}