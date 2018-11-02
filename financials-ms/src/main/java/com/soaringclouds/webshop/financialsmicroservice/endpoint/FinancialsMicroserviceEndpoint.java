package com.soaringclouds.webshop.financialsmicroservice.endpoint;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.*;
import com.soaringclouds.webshop.financialsmicroservice.service.CustomerAccountService;
import com.soaringclouds.webshop.financialsmicroservice.service.HealthService;
import com.soaringclouds.webshop.financialsmicroservice.service.InvoiceService;
import com.soaringclouds.webshop.financialsmicroservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FinancialsMicroserviceEndpoint {

    @Autowired private InvoiceService invoiceService;

    @Autowired private HealthService healthService;

    @Autowired private PaymentService paymentService;

    @Autowired private CustomerAccountService customerAccountService;

    @RequestMapping(value = "/api/financials/invoices/{invoice_id}", method = RequestMethod.DELETE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
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
    public List<Invoice> apiFinancialsInvoicesGet(@RequestParam(value = "customer_id", required = false) String customerId,
		    @RequestParam(value = "order_id", required = false) String orderId) {

	return invoiceService.findInvoiceByCriteria(orderId, customerId);
    }

    @RequestMapping(value = "/api/financials/invoices", method = RequestMethod.POST, consumes =
		    "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseMetadata apiFinancialsInvoicesPost(@RequestBody @Valid Invoice body,
		    @RequestParam(value = "customer_id", required = false) String customerId,
		    @RequestParam(value = "order_id", required = false) String orderId) {

	return invoiceService.createInvoice(body);
    }

    @RequestMapping(value = "/api/financials/health", method = RequestMethod.GET)
    public HealthCheckResponse apiFinancialsHealthGet() {

	return healthService.getServiceHealth();
    }

    @RequestMapping(value = "/api/financials/payments", method = RequestMethod.GET)
    public List<Payment> apiFinancialsPaymentsGet(@RequestParam(value = "invoice_id", required = false) String invoiceId,
		    @RequestParam(value = "customer_id", required = false) String customerNo) {
	return paymentService.findPaymentByCriteria(invoiceId, customerNo);
    }

    @RequestMapping(value = "/api/financials/payments", method = RequestMethod.POST, consumes =
		    "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseMetadata apiFinancialsPaymentsPost(@RequestBody @Valid Payment body,
		    @RequestParam(value = "invoice_id", required = false) String invoiceId,
		    @RequestParam(value = "customer_id", required = false) String customerNo) {
	return paymentService.savePaymentAndUpdateInvoice(body);
    }

    @RequestMapping(value = "/api/financials/customers/{customer_id}/account", method = RequestMethod.GET)
    public CustomerAccount apiFinancialsCustomersAccountByCustomerIdGet(
		    @PathVariable(value = "customer_id") String customerId) {
	return customerAccountService.getCustomerAccount(customerId);
    }
}
