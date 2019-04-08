package com.soaringclouds.webshop.financialsmicroservice.event;

import org.apache.camel.Message;

import java.io.IOException;

public interface KafkaEventHandler {

    void handle(Message pMessage) throws Exception;
}
