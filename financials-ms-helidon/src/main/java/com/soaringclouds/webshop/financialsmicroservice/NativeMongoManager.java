package com.soaringclouds.webshop.financialsmicroservice;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.soaringclouds.webshop.financialsmicroservice.config.MongoConfig;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.CustomerAccount;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Invoice;
import com.soaringclouds.webshop.financialsmicroservice.gen.model.Payment;
import org.mongojack.JacksonCodecRegistry;
import org.mongojack.JacksonMongoCollection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * NativeMongoManager
 */
@ApplicationScoped
public class NativeMongoManager {

    private final static Logger LOGGER = Logger.getLogger(NativeMongoManager.class.getSimpleName());

    public static final String MONGO_COLLECTION_INVOICE = "InvoiceCollection";
    public static final String MONGO_COLLECTION_CUSTOMERACCOUNT = "CustomerAccountCollection";
    public static final String MONGO_COLLECTION_PAYMENT = "PaymentCollection";

    private MongoDatabase mongoDatabase;

    private JacksonCodecRegistry jacksonCodecRegistry;

    @Inject
    private MongoConfig mongoConfig;

    public NativeMongoManager() {
        jacksonCodecRegistry =
                new JacksonCodecRegistry(ObjectMapperProvider.getCustomObjectMapper());
        jacksonCodecRegistry.addCodecForClass(Invoice.class);
        jacksonCodecRegistry.addCodecForClass(Payment.class);
        jacksonCodecRegistry.addCodecForClass(CustomerAccount.class);
    }

    public <T> JacksonMongoCollection<T> getMongoCollection(String pCollectionName,
                                                            Class<T> pCollectionType) {

        LOGGER.info(String.format("Persistence Config: %s", mongoConfig));

        if (mongoDatabase == null) {
            initializeDatabase();
        }

        MongoCollection<?> coll = mongoDatabase.getCollection(pCollectionName);
        MongoCollection<T> collection =
                coll.withDocumentClass(pCollectionType).withCodecRegistry(jacksonCodecRegistry);

        JacksonMongoCollection.JacksonMongoCollectionBuilder<T> builder =
                JacksonMongoCollection.builder();


        return builder.withObjectMapper(ObjectMapperProvider.getCustomObjectMapper()).build(collection, pCollectionType);
    }

    private void initializeDatabase() {

        final MongoClient mongoClient = new MongoClient(new MongoClientURI(String.format(
                "mongodb://%s:%s", mongoConfig.getDbHost(), mongoConfig.getDbPort())));
        mongoDatabase = mongoClient.getDatabase(mongoConfig.getDbName());
    }

    /**
     * @return the mongoDatabase
     */
    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    /**
     * @param mongoDatabase the mongoDatabase to set
     */
    public void setMongoDatabase(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }
}