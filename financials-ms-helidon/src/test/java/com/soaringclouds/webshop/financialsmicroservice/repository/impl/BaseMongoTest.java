package com.soaringclouds.webshop.financialsmicroservice.repository.impl;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.soaringclouds.webshop.financialsmicroservice.NativeMongoManager;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.logging.Logger;

public class BaseMongoTest {

    protected static final Logger LOGGER =
            Logger.getLogger(BaseMongoTest.class.getSimpleName());

    protected NativeMongoManager nativeMongoManager;

    private MongodExecutable mongodExe;
    private MongodProcess mongod;
    private MongoClient mongo;

    @BeforeEach
    public void beforeEach() throws Exception {

        final MongodStarter starter = MongodStarter.getDefaultInstance();
        String bindIp = "localhost";
        int port = 12345;
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(bindIp, port, Network.localhostIsIPv6()))
                .build();

        mongodExe = starter.prepare(mongodConfig);
        mongod = mongodExe.start();
        mongo = new MongoClient(bindIp, port);


        final MongoClient mongoClient = new MongoClient(new MongoClientURI(String.format(
                "mongodb://%s:%s", bindIp, port)));
        final MongoDatabase mongoDatabase = mongoClient.getDatabase("financials-db");
        nativeMongoManager = new NativeMongoManager();
        nativeMongoManager.setMongoDatabase(mongoDatabase);

        beforeEachOverrideable();
    }

    protected void beforeEachOverrideable() throws Exception {

    }

    @AfterEach
    public void afterEach() throws Exception {
        if (mongod != null) {
            mongod.stop();
            mongodExe.stop();
        }
    }
}
