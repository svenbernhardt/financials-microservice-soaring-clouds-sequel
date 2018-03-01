package com.soaringclouds.webshop.financialsmicroservice.service.impl;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.HealthCheckResponse;
import com.soaringclouds.webshop.financialsmicroservice.service.HealthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

/**
 * Created by svb on 27.02.18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class HealthServiceImplTest {

    @Autowired
    private HealthService healthService;

    @Test
    public void whenQueryToServiceHealthThenReturnHealthInformation() throws InterruptedException {

        Thread.sleep(1000);

        final HealthCheckResponse serviceHealth = healthService.getServiceHealth();

        assertThat(serviceHealth.getUptime(), greaterThan(new Double(0)));
    }
}
