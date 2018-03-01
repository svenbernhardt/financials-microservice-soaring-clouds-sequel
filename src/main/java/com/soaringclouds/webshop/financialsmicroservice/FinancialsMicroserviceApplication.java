package com.soaringclouds.webshop.financialsmicroservice;

import com.soaringclouds.webshop.financialsmicroservice.endpoint.FinancialsMicroserviceEndpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication public class FinancialsMicroserviceApplication  extends ResourceConfig {

    public FinancialsMicroserviceApplication() {
        register(FinancialsMicroserviceEndpoint.class);
    }

    public static void main(String[] args) {
	SpringApplication.run(FinancialsMicroserviceApplication.class, args);
    }
}