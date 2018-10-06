# financials-microservice-soaring-clouds-sequel
The financials microservice for the soaring the clouds sequel showcase

## Build the Sources

The Financials Microservice is a SpringBoot Application that is built using maven.
From within the folder financials-ms call the following Maven Command:

```bash
mvn clean package
```

The Financials Microservice UI is an Oracle JET (Version 4.2) implementation and is
built using Grunt. Building the application is done using the following Grunt command within the folder financials-ui:

```bash
grunt build
```

To serve the UI locally just run from within financials-ui folder:

```bash
grunt serve
```


## Run the Microservice

 * docker-compose up --build -d
 * Base URL for the JET UI is http://localhost:8088
 * Base URL for the service is http://localhost:7777/api/financials

## Testing the Microservice

Testing the Microservice can be done using the included Postman Collection under  postman/soaring-clouds-financials-microservice.postman_collection.json.

Besides the Postman Collection, there are different environment configuration included in the Postman folder:

 * **Local**: Points to the local environment
 * **CCS**: Points to a potentially deployed Container Cloud Service instance
 * **APIPCS**: Points to a potentially deployed API Platform Cloud Service instance
