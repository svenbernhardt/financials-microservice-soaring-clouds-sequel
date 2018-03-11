package com.soaringclouds.webshop.financialsmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
public class FinancialsMicroserviceApplication extends SpringBootServletInitializer
		implements WebApplicationInitializer {

    public static void main(String[] args) {

	SpringApplication.run(FinancialsMicroserviceApplication.class, args);
    }
}