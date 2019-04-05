package com.soaringclouds.webshop.financialsmicroservice.repository.util;

import com.google.common.base.Strings;
import org.mongojack.DBQuery;

import java.util.HashMap;
import java.util.Map;

public class DbQueryBuilder {

    private DBQuery.Query query;
    private static Map<String, String> params = new HashMap<>();

    private DbQueryBuilder() {

        query = DBQuery.empty();
    }

    public static DbQueryBuilder create() {

        return new DbQueryBuilder();
    }

    public DbQueryBuilder and(String pKey, String pValue) {

        if (!Strings.isNullOrEmpty(pValue)) {
            query.and(DBQuery.is(pKey, pValue));
        }

        return this;
    }

    public DBQuery.Query get() {
        return query;
    }
}
