package com.soaringclouds.webshop.financialsmicroservice.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.soaringclouds.avro.order.v1.Order;
import com.soaringclouds.webshop.financialsmicroservice.builder.CustomerBuilder;
import com.soaringclouds.webshop.financialsmicroservice.builder.InvoiceBuilder;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Address;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Currency;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.InvoicePosition;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.InvoiceStatus;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.PaymentStatus;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Type;

/**
 * Created by svb on 07.03.18.
 */
public class Order2InvoiceConverter {

	public static Invoice createInvoice(Order pOrder) {

		final InvoiceBuilder invoice = InvoiceBuilder.anInvoice();

		invoice.withOrderId(pOrder.getOrderId().toString()).withInvoiceId(createInvoiceId())
				.withOrderDate(pOrder.getCreatedAt().toString()).withPaymentStatus(PaymentStatus.PENDING)
				.withTotalPrice(pOrder.getTotalPrice())
				.withCurrency(Currency.valueOf(pOrder.getCurrency().name())).withInvoiceStatus(InvoiceStatus.OPEN)
				.withShippingCosts(pOrder.getShipping().getPrice());

		invoice.withAddresses(pOrder.getAddresses().stream().map(pAddress -> {

			final Address address = new Address();

			address.setType(Type.valueOf(pAddress.getName().toString()));
			address.setCity(pAddress.getCity().toString());
			address.setCountry(pAddress.getCountry().toString());
			address.setPostcode(pAddress.getPostcode().toString());
			address.setStreet(pAddress.getLine2().toString());
			address.setStreetNumber(pAddress.getLine1().toString());

			return address;
		}).collect(Collectors.toList()));

		invoice.withCustomer(CustomerBuilder.aCustomer().withCustomerNo(pOrder.getCustomer().getCustomerId().toString())
				.withFirstName(pOrder.getCustomer().getFirstName().toString())
				.withLastName(pOrder.getCustomer().getLastName().toString())
				.withEmail(pOrder.getCustomer().getEmail().toString())
				.withPhone(pOrder.getCustomer().getPhone().toString()).build());

		final AtomicInteger counter = new AtomicInteger(1);

		invoice.withInvoicePositions(pOrder.getItems().stream().map(item -> {

			final InvoicePosition invoicePosition = new InvoicePosition();

			invoicePosition.setPositionId(round(counter.getAndIncrement(), 0));
			invoicePosition.setProductNumber(item.getProductCode().toString());
			invoicePosition.setDescription(item.getDescription().toString());
			invoicePosition.setAmount(round(new Double(item.getQuantity()), 0));
			invoicePosition.setVat(new BigDecimal("19.0"));
			invoicePosition.setNetPrice(round(item.getPrice() / new Double(1.19), 2));
			invoicePosition.setGrossPrice(BigDecimal.valueOf(item.getPrice()));

			return invoicePosition;
		}).collect(Collectors.toList()));

		return invoice.build();
	}

	private static BigDecimal round(double value, int places) {

		if (places < 0) {
			throw new IllegalArgumentException();
		}

		BigDecimal bd = new BigDecimal(value);
		return bd.setScale(places, RoundingMode.HALF_UP);
	}

	private static String createInvoiceId() {

		return String.valueOf(String.format("I%s", System.currentTimeMillis()));
	}
}
