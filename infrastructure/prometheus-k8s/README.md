# Setting up Prometheus and Grafana on K8s

*Note:* The shown configuration is not intended to be used in production, it is just meant to
be used for development and demo purposes.

## Install Prometheus using Helm

Installing Prometheus is done by executing the following three steps:

```bash
kubectl create namespace monitoring

kubectl create configmap prometheus-config --from-file infrastructure/prometheus-k8s/prometheus-config.yaml -n monitoring

kubectl create -f infrastructure/prometheus-k8s/prometheus-deployment.yaml -n monitoring
```
Afterwards the Graph UI can be accessed using the NodePort (Port: 30900).

## Install Grafana using Helm

```bash
helm install --name grafana stable/grafana --namespace=monitoring
```

To access Grafana from outside the K8s Cluster, the respective endpoint must be exposed using NodePort or Ingress.

```bash
kubectl -n monitoring expose deployment grafana --type=NodePort --name=grafana-nodeport
```

To get the password of Grafana's Admin user, the following command can be executed:

```bash
ubectl get secret --namespace monitoring grafana -o jsonpath="{.data.admin-password}" | base64 --decode ; echo
```
