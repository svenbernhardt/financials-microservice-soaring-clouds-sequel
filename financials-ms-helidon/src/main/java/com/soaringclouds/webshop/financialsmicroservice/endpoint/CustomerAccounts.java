package com.soaringclouds.webshop.financialsmicroservice.endpoint;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerAccount;
import com.soaringclouds.webshop.financialsmicroservice.service.CustomerAccountService;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import com.soaringclouds.webshop.financialsmicroservice.service.PaymentService;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;

/**
 * CustomerAccountEndpoint
 */
@Path("customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Metered
public class CustomerAccounts {

    private CustomerAccountService customerAccountService;

    private InvoiceService invoiceService;

    private PaymentService paymentService;

    @GET
    @Path("{customer_id}/account")
    public CustomerAccount getCustomerAccount(
            @PathParam(value = "customer_id") String customerId) {

        return customerAccountService.getCustomerAccount(customerId);
    }

    @DELETE
    @Path("{customer_id}/account")
    public void deleteCustomerAccount(
            @PathParam(value = "customer_id") String customerId) {

        invoiceService.deleteInvoices(customerId);

        paymentService.deletePayments(customerId);

        customerAccountService.deleteCustomerAccount(customerId);
    }

    /**
     * @param customerAccountService the customerAccountService to set
     */
    @Inject
    public void setCustomerAccountService(CustomerAccountService pCustomerAccountService) {
        customerAccountService = pCustomerAccountService;
    }

    @Inject
    public void setInvoiceService(InvoiceService pInvoiceService) {
        invoiceService = pInvoiceService;
    }

    @Inject
    public void setPaymentService(PaymentService pPaymentService) {
        paymentService = pPaymentService;
    }
}