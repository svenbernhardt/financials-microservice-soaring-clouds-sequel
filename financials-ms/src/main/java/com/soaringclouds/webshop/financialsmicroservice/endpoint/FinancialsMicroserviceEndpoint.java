package com.soaringclouds.webshop.financialsmicroservice.endpoint;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.*;
import com.soaringclouds.webshop.financialsmicroservice.service.CustomerAccountService;
import com.soaringclouds.webshop.financialsmicroservice.service.HealthService;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import com.soaringclouds.webshop.financialsmicroservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
public class FinancialsMicroserviceEndpoint {

    @Autowired private InvoiceService invoiceService;

    @Autowired private HealthService healthService;

    @Autowired private PaymentService paymentService;

    @Autowired private CustomerAccountService customerAccountService;

    @RequestMapping(value = "/api/financials/invoices/{invoice_id}", method = RequestMethod.DELETE)
    public void apiFinancialsInvoicesByInvoiceIdDelete(@PathVariable(value = "invoice_id") String invoiceId) {

	invoiceService.deleteInvoice(invoiceId);
    }

    @RequestMapping(value = "/api/financials/invoices/{invoice_id}", method = RequestMethod.GET)
    public Invoice apiFinancialsInvoicesByInvoiceIdGet(@PathVariable(value = "invoice_id") String invoiceId) {

	return invoiceService.findInvoiceByInvoiceId(invoiceId);
    }

    @RequestMapping(value = "/api/financials/invoices/{invoice_id}", method = RequestMethod.PUT, consumes =
		    "application/json")
    public ResponseMetadata apiFinancialsInvoicesByInvoiceIdPut(@RequestBody @Valid Invoice body,
		    @PathVariable(value = "invoice_id") String invoiceId) {

	return invoiceService.updateInvoice(body);
    }

    @RequestMapping(value = "/api/financials/invoices", method = RequestMethod.GET)
    public List<Invoice> apiFinancialsInvoicesGet(@QueryParam(value = "customer_id") String customerId,
		    @QueryParam(value = "order_id") String orderId) {

	return invoiceService.findInvoiceByCriteria(orderId, customerId);
    }

    @RequestMapping(value = "/api/financials/invoices", method = RequestMethod.POST, consumes =
		    "application/json")
    public ResponseMetadata apiFinancialsInvoicesPost(@RequestBody @Valid Invoice body,
		    @QueryParam(value = "customer_id") String customerId,
		    @QueryParam(value = "order_id") String orderId) {

	return invoiceService.createInvoice(body);
    }

    @RequestMapping(value = "/api/financials/health", method = RequestMethod.GET)
    public HealthCheckResponse apiFinancialsHealthGet() {

	return healthService.getServiceHealth();
    }

    @RequestMapping(value = "/api/financials/payments", method = RequestMethod.GET)
    public List<Payment> apiFinancialsPaymentsGet(@QueryParam(value = "invoice_id") String invoiceId,
		    @QueryParam(value = "customer_id") String customerNo) {
	return paymentService.findPaymentByCriteria(invoiceId, customerNo);
    }

    @RequestMapping(value = "/api/financials/payments", method = RequestMethod.POST, consumes =
		    "application/json")
    public ResponseMetadata apiFinancialsPaymentsPost(@RequestBody @Valid Payment body,
		    @QueryParam(value = "invoice_id") String invoiceId,
		    @QueryParam(value = "customer_id") String customerNo) {
	return paymentService.savePaymentAndUpdateInvoice(body);
    }

    @RequestMapping(value = "/api/financials/customers/{customer_id}/account", method = RequestMethod.GET,
		    consumes = "application/json")
    public CustomerAccount apiFinancialsCustomersAccountByCustomerIdGet(
		    @PathVariable(value = "customer_id") String customerId) {
	return customerAccountService.getCustomerAccount(customerId);
    }
}
