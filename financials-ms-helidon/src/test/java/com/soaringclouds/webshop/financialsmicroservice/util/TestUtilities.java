package com.soaringclouds.webshop.financialsmicroservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import com.soaringclouds.avro.order.v1.*;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Payment;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.io.JsonDecoder;
import org.apache.avro.specific.SpecificDatumReader;

import java.io.InputStreamReader;

public class TestUtilities {

    public static Invoice createInvoice() {

        Invoice invoice = null;
        String invoiceJsonString = null;
        try {
            invoiceJsonString = CharStreams.toString(new InputStreamReader(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(
                            "com/soaringclouds/webshop/financialsmicroservice/repository"
                                    + "/invoice_example.json")));

            invoice = new ObjectMapper().readValue(invoiceJsonString, Invoice.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return invoice;
    }

    public static Payment createPayment() {

        Payment payment = null;
        String paymentJsonString = null;
        try {
            paymentJsonString = CharStreams.toString(new InputStreamReader(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(
                            "com/soaringclouds/webshop/financialsmicroservice/service"
                                    + "/payment_example.json")));

            payment = new ObjectMapper().readValue(paymentJsonString, Payment.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return payment;
    }

    public static Order createOrder() {

        Order order = new Order();
        order.setOrderId("order0801");
        order.setShoppingCartId("CGN4711");
        order.setStatus(OrderStatusEnum.SUCCESS);
        order.setCreatedAt("2018-03-04T07:22:35.718Z");
        order.setUpdatedAt("2018-03-04T09:00:58.984Z");
        order.setTotalPrice(68.39);
        order.setCurrency(CurrencyEnum.GBP);
        order.setPayment(OrderPayment.newBuilder().setCardType(CardTypeEnum.VISA_CREDIT).
                setCardNumber("**** **** **** 1111").setStartYear(2019).setStartMonth(1).
                setExpiryYear(2020).setExpiryMonth(6).build());
        order.setCustomer(Customer.newBuilder().setCustomerId("CGN4711").setFirstName("Sven").
                setLastName("Bernhardt").setEmail("sven@bernhardt.de").setPhone("+49 (0) 123 456 " +
                "789").
                setLoyaltyLevel(loyaltyLevelEnum.NONE).build());
        order.setAddresses(Lists.newArrayList(Address.newBuilder().setName("BILLING").setLine1(
                "15").
                setLine2("Ludwig-Jahn-Str.").setPostcode("50858").setCity("Cologne").setCountry(
                "DE").
                setCounty("Northrhine-Westfalia").build()));
        order.setShipping(Shipping.newBuilder().setFirstName("Lucas").setLastName("Jellema").setShippingMethod(shippingMethodEnum.ECONOMY).
                setETA("2018-06-05T07:11:00.000Z").setPrice(10.00).setShippingCompany("Edfex").setShippingId("123456").build());
        order.setSpecialDetails(specialDetails.newBuilder().setDeliveryNotes("Hello!").setGiftWrapping(false).setPersonalMessage("Try to deliver asap!").build());
        order.setItems(Lists.newArrayList(Item.newBuilder().setProductId("abbfc4f9-83d5-49ac-9fa5" +
                "-2909c5dc86e6").setProductCode("AX330T").setProductName("Light Brown Men Shoe 1").setDescription("Light Brown Men Shoe 1").setQuantity(2)
                .setPrice(68.39).setSize(43).setWeight(0.0).setDimension(Dimension.newBuilder().setUnit("cm").setLength(10.2).setHeight(10.4).setWidth(5.4).build())
                .setColor("White").setSku("S15T-Flo-RS").build()));


        /*String orderJsonString = null;
        try {
            orderJsonString = CharStreams.toString(new InputStreamReader(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(
                            "com/soaringclouds/webshop/financialsmicroservice/service"
                                    + "/order_created_event_example.json")));

            DatumReader<Order> orderReader = new SpecificDatumReader<>(Order.class);
            JsonDecoder jsonDecoder = DecoderFactory.get().jsonDecoder(Order.getClassSchema(),
                    orderJsonString);
            order = orderReader.read(new Order(), jsonDecoder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

        return order;
    }

}
