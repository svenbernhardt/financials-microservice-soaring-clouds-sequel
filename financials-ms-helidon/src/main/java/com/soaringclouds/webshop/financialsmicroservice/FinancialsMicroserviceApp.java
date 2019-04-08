package com.soaringclouds.webshop.financialsmicroservice;

import com.soaringclouds.webshop.financialsmicroservice.config.ObjectMapperContextResolver;
import com.soaringclouds.webshop.financialsmicroservice.endpoint.CustomerAccounts;
import com.soaringclouds.webshop.financialsmicroservice.endpoint.Invoices;
import com.soaringclouds.webshop.financialsmicroservice.endpoint.Payments;
import com.soaringclouds.webshop.financialsmicroservice.filter.CorsFilter;
import org.jboss.weld.util.collections.Sets;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * FinancialsMicroserviceApp
 */
@ApplicationScoped
@ApplicationPath("/api/financials")
public class FinancialsMicroserviceApp extends Application {


    @Override
    public Set<Class<?>> getClasses() {

        return Sets.newHashSet(
                ObjectMapperContextResolver.class,
                CorsFilter.class,
                Invoices.class,
                CustomerAccounts.class,
                Payments.class
        );
    }
}