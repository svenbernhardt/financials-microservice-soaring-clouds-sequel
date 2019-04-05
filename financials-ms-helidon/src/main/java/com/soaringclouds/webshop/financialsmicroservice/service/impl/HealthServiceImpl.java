package com.soaringclouds.webshop.financialsmicroservice.service.impl;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.HealthCheckResponse;
import com.soaringclouds.webshop.financialsmicroservice.service.HealthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by svb on 27.02.18.
 */
public class HealthServiceImpl implements HealthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthServiceImpl.class.getName());

    @Override
    public HealthCheckResponse getServiceHealth() {
        return null;
    }

    // private Stopwatch stopWatch;

    // @PostConstruct
    // public void init() {

	// stopWatch = Stopwatch.createStarted();
    // }

    // @Override
    // public HealthCheckResponse getServiceHealth() {

    //     final long elapsedTimeSeconds = stopWatch.elapsed(TimeUnit.SECONDS);

	// final HealthCheckResponse healthCheckResponse = new HealthCheckResponse();
	// healthCheckResponse.setVersion(1.0);
	// healthCheckResponse.setUptime((double) elapsedTimeSeconds);
	// healthCheckResponse.setStatus(Status.OK);

	// LOGGER.info(String.format("Elapsed uptime: %s seconds!", elapsedTimeSeconds));

	// return healthCheckResponse;
    // }
}
