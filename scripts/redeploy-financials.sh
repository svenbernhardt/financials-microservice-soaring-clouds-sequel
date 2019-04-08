#!/usr/bin/env bash

kubectl -n financials delete deployments financials-ms-helidon
kubectl -n financials delete service financials-ms-helidon
kubectl -n financials delete ingress financials-ms-helidon-ingress

kubectl -n financials get all

mvn -f ../financials-ms-helidon/pom.xml clean package -DskipTests

docker built -t sbernhardtoc/financials-ms-helidon target
docker push sbernhardtoc/financials-ms-helidon

kubectl -n financials create -f ../financials-ms-helidon/target/app.yaml
