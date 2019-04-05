package com.soaringclouds.webshop.financialsmicroservice.endpoint;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.ResponseMetadata;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.eclipse.microprofile.metrics.annotation.Metered;

@Path("invoices")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Metered
public class Invoices {

    private static final Logger LOGGER = Logger.getLogger(Invoices.class.getSimpleName());

    @Inject
    private InvoiceService invoiceService;

    @DELETE
    @Path("{invoice_id}")
    public void deleteInvoice(@PathParam(value = "invoice_id") String invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
    }

    @GET
    @Path("{invoice_id}")
    public Invoice findInvoicedById(@PathParam(value = "invoice_id") String invoiceId) {

        Invoice invoiceByInvoiceId = invoiceService.findInvoiceByInvoiceId(invoiceId);

        final String invoices = String.format("[findInvoicedById] Invoice -> %s", invoiceByInvoiceId);

        LOGGER.info(invoices);

        return invoiceByInvoiceId;


    }

    @PUT
    @Path("{invoice_id}")
    public ResponseMetadata updateInvoice(Invoice body,
                                          @PathParam(value = "invoice_id") String invoiceId) {

        return invoiceService.updateInvoice(body);
    }

    @GET
    public List<Invoice> findInvoiceByCustomerAndOrderId(@QueryParam(value = "customer_id") String customerId,
                                                         @QueryParam(value = "order_id") String orderId) {

        List<Invoice> invoiceByInvoiceId = invoiceService.findInvoiceByCriteria(orderId, customerId);

        final String invoices = String.format("[findInvoiceByCustomerAndOrderId] Invoice -> %s", invoiceByInvoiceId.size() > 0 ? invoiceByInvoiceId.get(0) : "null");

        LOGGER.info(invoices);

        return invoiceService.findInvoiceByCriteria(orderId, customerId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseMetadata createInvoice(Invoice body,
                                          @QueryParam(value = "customer_id") String customerId,
                                          @QueryParam(value = "order_id") String orderId) {

        LOGGER.info(String.format("[createInvoice] Invoice -> %s", body));

        return invoiceService.createInvoice(body);
    }
}
