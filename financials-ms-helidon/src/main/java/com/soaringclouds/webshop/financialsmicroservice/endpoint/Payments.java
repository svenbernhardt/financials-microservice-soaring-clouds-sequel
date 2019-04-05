package com.soaringclouds.webshop.financialsmicroservice.endpoint;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.Payment;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.ResponseMetadata;
import com.soaringclouds.webshop.financialsmicroservice.service.PaymentService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.eclipse.microprofile.metrics.annotation.Metered;

/**
 * Payments
 */
@Path("payments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Metered
public class Payments {

    private PaymentService paymentService;

    @GET
    @Produces("application/json")
    public List<Payment> apiFinancialsPaymentsGet(@NotNull @QueryParam(value = "invoice_id") String invoiceId,
            @NotNull @QueryParam(value = "customer_id") String customerNo) {
        return paymentService.findPaymentByCriteria(invoiceId, customerNo);
    }

    @POST
    public ResponseMetadata apiFinancialsPaymentsPost(@RequestBody @Valid Payment body,
            @NotNull @QueryParam(value = "invoice_id") String invoiceId,
            @NotNull @QueryParam(value = "customer_id") String customerNo) {
        return paymentService.savePaymentAndUpdateInvoice(body);
    }

    /**
     * @param paymentService the paymentService to set
     */
    @Inject
    public void setPaymentService(PaymentService pPaymentService) {
        this.paymentService = pPaymentService;
    }
}