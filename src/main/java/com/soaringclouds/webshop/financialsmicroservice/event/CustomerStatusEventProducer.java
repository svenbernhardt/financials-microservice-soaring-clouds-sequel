package com.soaringclouds.webshop.financialsmicroservice.event;

import com.soaringclouds.avro.customerstatus.v1.CustomerStatus;
import com.soaringclouds.avro.customerstatus.v1.CustomerStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by svb on 05.03.18.
 */
@Service
public class CustomerStatusEventProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerStatusEventProducer.class.getName());

    @Autowired private KafkaTemplate<String, CustomerStatus> customerStatusProducer;

    @Value("${kafka.topic.customerstatus}") private String kafkaTopic;

    public void produceCustomerStatusFrozen(String pCustomerNo) {
	produceCustomerStatus(pCustomerNo, CustomerStatusEnum.FROZEN);
    }

    public void produceCustomerStatusUnFrozen(String pCustomerNo) {
	produceCustomerStatus(pCustomerNo, CustomerStatusEnum.UNFROZEN);
    }

    private void produceCustomerStatus(String pCustomerNo, CustomerStatusEnum pCustomerStatus) {

	LOGGER.info(String.format("Submitting Customer Status event: [%s, %s] to topic [%s]", pCustomerNo,
			pCustomerStatus, kafkaTopic));

	customerStatusProducer
			.send(kafkaTopic, String.format("%s-%s", pCustomerNo, System.currentTimeMillis()),
					CustomerStatus.newBuilder().setCustomerNo(pCustomerNo)
							.setStatus(pCustomerStatus).build());
    }
}
