package com.soaringclouds.webshop.financialsmicroservice.endpoint;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerAccount;
import com.soaringclouds.webshop.financialsmicroservice.service.CustomerAccountService;
import org.eclipse.microprofile.metrics.annotation.Metered;

/**
 * CustomerAccountEndpoint
 */
@Path("customer-accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Metered
public class CustomerAccounts {

    private CustomerAccountService customerAccountService;

    @GET
    @Path("{customer_id}")
    public CustomerAccount getCustomerAccount(
            @PathParam(value = "customer_id") String customerId) {

        return customerAccountService.getCustomerAccount(customerId);
    }

    @DELETE
    @Path("{customer_id}")
    public void deleteCustomerAccount(
            @PathParam(value = "customer_id") String customerId) {

        customerAccountService.deleteCustomerAccount(customerId);
    }

    /**
     * @param customerAccountService the customerAccountService to set
     */
    @Inject
    public void setCustomerAccountService(CustomerAccountService pCustomerAccountService) {
        customerAccountService = pCustomerAccountService;
    }
}