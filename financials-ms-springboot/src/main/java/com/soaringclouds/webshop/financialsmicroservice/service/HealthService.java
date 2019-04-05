package com.soaringclouds.webshop.financialsmicroservice.service;

import com.soaringclouds.webshop.financialsmicroservice.gen.model.HealthCheckResponse;

/**
 * Created by svb on 27.02.18.
 */
public interface HealthService {

    HealthCheckResponse getServiceHealth();
}
