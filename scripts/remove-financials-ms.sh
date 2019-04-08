#!/usr/bin/env bash

kubectl -n financials delete deployments financials-ms-helidon
kubectl -n financials delete service financials-ms-helidon
kubectl -n financials delete ingress financials-ms-helidon-ingress

kubectl -n financials get all
