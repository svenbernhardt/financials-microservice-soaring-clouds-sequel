package com.soaringclouds.webshop.financialsmicroservice;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.soaringclouds.webshop.financialsmicroservice.endpoint.Invoices;

import org.jboss.weld.util.collections.Sets;

/**
 * FinancialsMicroserviceApp
 */
public class FinancialsMicroserviceApp extends Application {


    @Override
    public Set<Class<?>> getClasses() {

        return Sets.newHashSet(
                Invoices.class
        );
    }
}