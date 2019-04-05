package com.soaringclouds.webshop.financialsmicroservice.config;

import io.helidon.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MongoConfig {

    @Inject
    @ConfigProperty(name = "app.db.host")
    private String dbHost;

    @Inject
    @ConfigProperty(name = "app.db.port")
    private Integer dbPort;

    @Inject
    @ConfigProperty(name = "app.db.name")
    private String dbName;

    @Inject
    private Config config;

    public MongoConfig() {}


    public String getDbHost() {
        return dbHost;
    }

    public Integer getDbPort() {
        return dbPort;
    }

    public void setDbPort(Integer dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public String toString() {
        return "MongoConfig{" +
                "dbHost='" + dbHost + '\'' +
                ", dbPort=" + dbPort +
                ", dbName='" + dbName + '\'' +
                '}';
    }
}
