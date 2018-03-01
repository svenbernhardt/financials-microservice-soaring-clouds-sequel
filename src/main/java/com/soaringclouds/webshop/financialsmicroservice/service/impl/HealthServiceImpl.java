package com.soaringclouds.webshop.financialsmicroservice.service.impl;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.HealthCheckResponse;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Status;
import com.soaringclouds.webshop.financialsmicroservice.service.HealthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.google.common.base.Stopwatch;
import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by svb on 27.02.18.
 */
@Service
public class HealthServiceImpl implements HealthService{

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthServiceImpl.class.getName());

    private Stopwatch stopWatch;

    @PostConstruct
    public void init() {

	stopWatch = Stopwatch.createStarted();
    }

    @Override
    public HealthCheckResponse getServiceHealth() {

        final long elapsedTimeSeconds = stopWatch.elapsed(TimeUnit.SECONDS);

	final HealthCheckResponse healthCheckResponse = new HealthCheckResponse();
	healthCheckResponse.setVersion(1.0);
	healthCheckResponse.setUptime((double) elapsedTimeSeconds);
	healthCheckResponse.setStatus(Status.OK);

	LOGGER.info(String.format("Elapsed uptime: %s seconds!", elapsedTimeSeconds));

	return healthCheckResponse;
    }
}
