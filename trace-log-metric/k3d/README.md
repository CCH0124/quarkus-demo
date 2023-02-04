Loki (logs)
Promtail (log agent)
Tempo (traces)
Prometheus (metrics)
Alertmanager (handles alerts sent by Prometheus)
Grafana (visualization)

1. install k3d config
```bash
k3d cluster create -c config.yaml --servers-memory 12G
```
2. Install Ingress

```bash
$ helm repo add ingress-nginx  https://kubernetes.github.io/ingress-nginx
$ helm search repo ingress-nginx
NAME                            CHART VERSION   APP VERSION     DESCRIPTION
ingress-nginx/ingress-nginx     4.4.2           1.5.1           Ingress controller for Kubernetes using NGINX a...
$ helm install ingress-nginx ingress-nginx/ingress-nginx --version 4.4.2 --namespace ingress-nginx --create-namespace
$ kubectl get all -n ingress-nginx
NAME                                            READY   STATUS    RESTARTS   AGE
pod/ingress-nginx-controller-7d5fb757db-gx8hp   1/1     Running   0          2m36s

NAME                                         TYPE           CLUSTER-IP      EXTERNAL-IP                        PORT(S)                      AGE
service/ingress-nginx-controller-admission   ClusterIP      10.43.138.141   <none>                             443/TCP                      2m36s
service/ingress-nginx-controller             LoadBalancer   10.43.236.109   172.19.0.2,172.19.0.3,172.19.0.4   80:32369/TCP,443:31027/TCP   2m36s

NAME                                       READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/ingress-nginx-controller   1/1     1            1           2m36s

NAME                                                  DESIRED   CURRENT   READY   AGE
replicaset.apps/ingress-nginx-controller-7d5fb757db   1         1         1       2m36s
```

3. Install grafana chart

```bash
$ helm repo add grafana https://grafana.github.io/helm-charts
$ helm search repo grafana
NAME                                            CHART VERSION   APP VERSION             DESCRIPTION
grafana/grafana                                 6.50.0          9.3.1                   The leading tool for querying and visualizing t...
grafana/grafana-agent                           0.2.1           v0.30.2                 Grafana Agent
grafana/grafana-agent-operator                  0.2.8           0.28.0                  A Helm chart for Grafana Agent Operator
infuseai/primehub-grafana-dashboard-basic       1.3.0           v1.3.0                  Primehub Grafana Dashboard Basic
grafana/enterprise-logs                         2.4.3           v1.5.2                  Grafana Enterprise Logs
grafana/enterprise-logs-simple                  1.2.1           v1.4.0                  DEPRECATED Grafana Enterprise Logs (Simple Scal...
grafana/enterprise-metrics                      1.9.0           v1.7.0                  DEPRECATED Grafana Enterprise Metrics
grafana/fluent-bit                              2.3.2           v2.1.0                  Uses fluent-bit Loki go plugin for gathering lo...
grafana/loki                                    3.10.0          2.7.0                   Helm chart for Grafana Loki in simple, scalable...
grafana/loki-canary                             0.10.0          2.6.1                   Helm chart for Grafana Loki Canary
grafana/loki-distributed                        0.69.0          2.7.1                   Helm chart for Grafana Loki in microservices mode
grafana/loki-simple-scalable                    1.8.11          2.6.1                   Helm chart for Grafana Loki in simple, scalable...
grafana/loki-stack                              2.8.9           v2.6.1                  Loki: like Prometheus, but for logs.
grafana/mimir-distributed                       4.0.1           2.5.0                   Grafana Mimir
grafana/mimir-openshift-experimental            2.1.0           2.0.0                   Grafana Mimir on OpenShift Experiment
grafana/oncall                                  1.1.0           v1.1.5                  Developer-friendly incident response with brill...
grafana/phlare                                  0.1.2           0.1.1                   🔥 horizontally-scalable, highly-available, mul...
grafana/promtail                                6.8.1           2.7.1                   Promtail is an agent which ships the contents o...
grafana/rollout-operator                        0.2.0           v0.2.0                  Grafana rollout-operator
grafana/synthetic-monitoring-agent              0.1.0           v0.9.3-0-gcd7aadd       Grafana's Synthetic Monitoring application. The...
grafana/tempo                                   0.16.8          1.5.0                   Grafana Tempo Single Binary Mode
grafana/tempo-distributed                       0.27.17         1.5.0                   Grafana Tempo in MicroService mode
grafana/tempo-vulture                           0.2.1           1.3.0                   Grafana Tempo Vulture - A tool to monitor Tempo...
```

4. Install promtail

```bash
/k3d/k8s/promtail$ helm install promtail grafana/promtail --version 6.8.1  --namespace observability  --create-namespace -f values.yaml
NAME: promtail
LAST DEPLOYED: Sat Jan 14 16:54:47 2023
NAMESPACE: observability
STATUS: deployed
REVISION: 1
TEST SUITE: None
NOTES:
***********************************************************************
 Welcome to Grafana Promtail
 Chart version: 6.8.1
 Promtail version: 2.7.1
***********************************************************************

Verify the application is working by running these commands:
* kubectl --namespace observability port-forward daemonset/promtail 3101
* curl http://127.0.0.1:3101/metrics
```
5. Install Prometheus、Grafana

```bash
$ helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
$ /kube-prometheus-stack$ helm install prometheus-stack prometheus-community/kube-prometheus-stack  --version 44.0.0 --namespace observability  --create-namespace -f value.yaml
$ kubectl get ingress -n observability
NAME                                    CLASS   HOSTS                ADDRESS                            PORTS   AGE
prometheus-stack-grafana                nginx   grafana.cch.com      172.19.0.2,172.19.0.3,172.19.0.4   80      6m6s
prometheus-stack-kube-prom-prometheus   nginx   prometheus.cch.com   172.19.0.2,172.19.0.3,172.19.0.4   80      15s
```

6. Install Opentelemetry

first Install  cert-manager
```bash
kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.11.0/cert-manager.yaml
```

than

```bash
$ helm repo add open-telemetry https://open-telemetry.github.io/opentelemetry-helm-charts
$ helm search repo open-telemetry
NAME                                    CHART VERSION   APP VERSION     DESCRIPTION
open-telemetry/opentelemetry-collector  0.44.1          0.69.0          OpenTelemetry Collector Helm chart for Kubernetes
open-telemetry/opentelemetry-demo       0.15.4          1.2.1           opentelemetry demo helm chart
open-telemetry/opentelemetry-operator   0.21.1          0.67.0          OpenTelemetry Operator Helm chart for Kubernetes
$ helm install opentelemetry-operator open-telemetry/opentelemetry-operator --version 0.21.1 --namespace observability  --create-namespace
```

```bash
/k8s/opentelemetry$ kubectl -n observability apply -f opentelemetry.yaml # 用 daemonSet
```

7. Install loki

```bash
/k3d/k8s/loki$ helm install loki grafana/loki-distributed --version 0.69.0  --namespace observability  --create-namespace -f values.yaml
NAME: loki
LAST DEPLOYED: Sat Jan 14 17:08:04 2023
NAMESPACE: observability
STATUS: deployed
REVISION: 1
TEST SUITE: None
NOTES:
***********************************************************************
 Welcome to Grafana Loki
 Chart version: 0.69.0
 Loki version: 2.7.1
***********************************************************************

Installed components:
* gateway
* ingester
* distributor
* querier
* query-frontend
```

8. Install Tempo

預設透過 minio 來作為 storage，以模擬 S3

```bash
/tempo$ helm install tempo grafana/tempo-distributed --version 0.27.17 --namespace observability  --create-namespace -f values.yaml
```


## Kafka 

```bash
$ helm repo add strimzi https://strimzi.io/charts/
$ helm install kafka-operator strimzi/strimzi-kafka-operator --version 0.33.0 --namespace dev --create-namespace
```

```bash
/k3d/k8s/kafka$ kubectl apply -f .
```
