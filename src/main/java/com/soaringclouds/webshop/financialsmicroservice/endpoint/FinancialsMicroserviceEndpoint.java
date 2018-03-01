package com.soaringclouds.webshop.financialsmicroservice.endpoint;

import com.soaringclouds.webshop.financialsmicroservice.gen.api.InvoicesResourcesApi;
import com.soaringclouds.webshop.financialsmicroservice.gen.api.ServiceOperationsResourcesApi;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.HealthCheckResponse;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.ResponseMetadata;
import com.soaringclouds.webshop.financialsmicroservice.service.HealthService;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by svb on 23.02.18.
 */
public class FinancialsMicroserviceEndpoint implements InvoicesResourcesApi, ServiceOperationsResourcesApi {

    @Autowired private InvoiceService invoiceService;

    @Autowired private HealthService healthService;

    @Override
    public void apiFinancialsInvoicesByInvoiceIdDelete(String invoiceId) {

	invoiceService.deleteInvoice(invoiceId);
    }

    @Override
    public Invoice apiFinancialsInvoicesByInvoiceIdGet(String invoiceId) {

	return invoiceService.findInvoiceByInvoiceId(invoiceId);
    }

    @Override
    public ResponseMetadata apiFinancialsInvoicesByInvoiceIdPut(Invoice body, String invoiceId) {

	return invoiceService.updateInvoice(body);
    }

    @Override
    public List<Invoice> apiFinancialsInvoicesGet(String customerId, String orderId) {

	return invoiceService.findInvoiceByCriteria(orderId, customerId);
    }

    @Override
    public ResponseMetadata apiFinancialsInvoicesPost(Invoice body, String customerId, String orderId) {

	return invoiceService.createInvoice(body);
    }

    @Override
    public HealthCheckResponse apiFinancialsHealthGet() {

	return healthService.getServiceHealth();
    }
}
