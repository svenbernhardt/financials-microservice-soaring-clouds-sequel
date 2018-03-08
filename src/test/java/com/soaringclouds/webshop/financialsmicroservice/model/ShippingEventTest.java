package com.soaringclouds.webshop.financialsmicroservice.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStreamReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by svb on 08.03.18.
 */
@RunWith(SpringRunner.class)
public class ShippingEventTest {

    @Test
    public void whenShippingEventDeliveredJsonStringArrivesThenDeserializeToPojo() throws Exception {

	final ObjectMapper objectMapper = new ObjectMapper();

	final ShippingEvent shippingEventDelivered = objectMapper.readValue(CharStreams
			.toString(new InputStreamReader(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(
							"com/soaringclouds/webshop/financialsmicroservice"
									+ "/service"
									+ "/shippingNews_delivered_example"
									+ ".json"))), ShippingEvent.class);

	assertThat(shippingEventDelivered, not(nullValue()));
	assertThat(shippingEventDelivered.getPayload().getShippingCosts(), equalTo(69));
    }

    @Test
    public void whenShippingEventNewJsonStringArrivesThenDeserializeToPojo() throws Exception {

	final ObjectMapper objectMapper = new ObjectMapper();

	final ShippingEvent shippingEventDelivered = objectMapper.readValue(CharStreams
			.toString(new InputStreamReader(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(
							"com/soaringclouds/webshop/financialsmicroservice"
									+ "/service"
									+ "/shippingNews_new_example"
									+ ".json"))), ShippingEvent.class);

	assertThat(shippingEventDelivered, not(nullValue()));
	assertThat(shippingEventDelivered.getPayload().getShippingCosts(), equalTo(69));
    }
}
