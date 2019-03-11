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

### Execute the Microservice locally using docker-compose
For running the Financials Microservice locally using docker-compose, there's a respective config file with simply can be executed as described below:

 * docker-compose up --build -d
 * Base URL for the JET UI is http://localhost:8088
 * Base URL for the service is http://localhost:7777/api/financials

### Running the Microservice in K8s
This section describes how to run the Financials Microservice in a K8S environment.

#### Setting up an Ingress Controller
*Note: If you're already using an Ingress Controller within your Cluster, you can ignore this section.*

Setting up Kong as Ingress Controller is straight-forward and can be done using a respective template. More information about Kong Ingress Controller could be found in Github: https://github.com/Kong/kubernetes-ingress-controller

```bash
curl https://raw.githubusercontent.com/Kong/kubernetes-ingress-controller/master/deploy/single/all-in-one-postgres.yaml \
  | kubectl create -f -
```

After the command has been executed Kong Ingress Controller is set up in a separate Namespace "kong" and can then be used. You can get an overview of the established services using the following command.

```bash
kubectl get service -n kong
```
#### Deploy the Financials Microservice to K8S
The resources needed for K8S deployments are located in the folder k8s-deploy. From here execute the following commands:

  * kubectl create namespace financials
  * kubectl create -f financials-ms.yml -n financials
  * kubectl create -f financials-ui.yml -n financials

## Testing the Microservice

### Dredd API tests

To ensure that the current service implementation is in sync with the API design, Dredd (https://dredd.readthedocs.io/en/latest/index.html) is used for this project.

Before executing the tests you should rebuild the Microservice backend in financials-ms and also
rebuild the respective Docker image:

```bash
cd financials-ms
mvn clean package

docker build . -t <tag_name> (e.g. sbernhardtoc/financials-ms:latest)
```
After ensuring that the implementation is up-to-date, the Dredd tests can be executed as follows:

```bash
dredd
```

This executes the test and delivers an test report on the console level. It also generates a test report
in Apiary.

### Postman tests

Testing the Microservice can be done using the included Postman Collection under  postman/soaring-clouds-financials-microservice.postman_collection.json.

Besides the Postman Collection, there are different environment configuration included in the Postman folder:

 * **Local**: Points to the local environment
 * **CCS**: Points to a potentially deployed Container Cloud Service instance
 * **APIPCS**: Points to a potentially deployed API Platform Cloud Service instance
