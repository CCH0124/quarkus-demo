# web-app

![Version: 0.1.0](https://img.shields.io/badge/Version-0.1.0-informational?style=for-the-badge)
![Type: application](https://img.shields.io/badge/Type-application-informational?style=for-the-badge)
![AppVersion: 1.16.0](https://img.shields.io/badge/AppVersion-1.16.0-informational?style=for-the-badge)

## web-app Chart Information

[Homepage](https://github.com/CCH0124/quarkus-demo/tree/main/helm-demo)

## Source Code

* <https://github.com/CCH0124/quarkus-demo/tree/main/helm-demo>

## Maintainers

| Name | Email | Url |
| ---- | ------ | --- |
| CCH | <cchong0124@gmail.com> |  |

## Description

A Helm chart for Kubernetes

## Requirements

Kubernetes: `>= v1.19.0 < v1.25.0`

## Values

| Key | Type | Default | Description |
|-----|------|---------|-------------|
| affinity | object | `{}` | Affinity for application pods. |
| app.properties | string | `nil` | Application defined properties. |
| autoscaling.enabled | bool | `false` | Enable autoscaling for the application. |
| autoscaling.maxReplicas | int | `100` | Maximum autoscaling replicas for the application. |
| autoscaling.minReplicas | int | `1` | Minimum autoscaling replicas for the application. |
| autoscaling.targetCPUUtilizationPercentage | int | `80` | Target CPU utilisation percentage for the application. |
| autoscaling.targetMemoryUtilizationPercentage | int | `80` | Target memory utilisation percentage for the application. |
| deploymentAnnotations | object | `{}` | annotations for Deployment resource |
| extraEnv | list | `[]` | Environment variables to add to the application pods |
| extraEnvFrom | list | `[]` | Environment variables from secrets or configmaps to add to the application pods |
| fullnameOverride | string | `""` | Overrides the chart's computed fullname |
| image.pullPolicy | string | `"IfNotPresent"` | Image pull policy |
| image.repository | string | `"registry.hub.docker.com/cch0124/helm-demo"` | Image registry |
| image.tag | string | `"latest"` | Overrides the image tag whose default is the chart appVersion. |
| imagePullSecrets | list | `[]` | Image pull secrets for the application |
| ingress.annotations | object | `{}` | Annotations for the application ingress |
| ingress.className | string | `""` | Ingress Class Name. MAY be required for Kubernetes versions >= 1.18 For example: `ingressClassName: nginx` |
| ingress.enabled | bool | `false` | Specifies whether an ingress for the application should be created |
| ingress.hosts | list | `[{"host":"chart-example.local","paths":[{"path":"/","pathType":"ImplementationSpecific"}]}]` | Hosts configuration for the application ingress |
| ingress.hosts[0].paths[0].pathType | string | `"ImplementationSpecific"` | pathType (e.g. ImplementationSpecific, Prefix, .. etc.) might also be required by some Ingress Controllers |
| ingress.tls | list | `[]` | TLS configuration for the application ingress |
| lifecycle | object | `{"preStop":{"exec":{"command":["sh","-c","sleep 10"]}}}` | Lifecycle for application pods. |
| livenessProbe | object | `{"httpGet":{"path":"/q/health/live","port":"http","scheme":"HTTP"},"initialDelaySeconds":60,"periodSeconds":30,"successThreshold":1,"timeoutSeconds":10}` | Configure the healthcheck for the application |
| livenessProbe.httpGet.path | string | `"/q/health/live"` | This is the liveness check endpoint. Can not overrid. |
| nameOverride | string | `""` | Overrides the chart's name |
| nodeSelector | object | `{}` | Node selector for the application pods. |
| podAnnotations | object | `{}` | Common annotations for all pods |
| podSecurityContext | object | `{"fsGroup":185,"runAsNonRoot":true}` | Common annotations for all pods |
| rbac.create | bool | `true` |  |
| readinessProbe.httpGet.path | string | `"/q/health/ready"` | This is the readiness check endpoint. Can not overrid. |
| readinessProbe.httpGet.port | string | `"http"` |  |
| readinessProbe.httpGet.scheme | string | `"HTTP"` |  |
| readinessProbe.initialDelaySeconds | int | `60` |  |
| readinessProbe.periodSeconds | int | `30` |  |
| readinessProbe.successThreshold | int | `1` |  |
| readinessProbe.timeoutSeconds | int | `10` |  |
| replicaCount | int | (int) Number of application pods | replicaCount is the number of application pods to run. |
| resources | object | `{"limits":{"cpu":"100m","memory":"256Mi"},"requests":{"cpu":"10m","memory":"256Mi"}}` | Resource requests and limits for the application. Ref [kubernetes doc manage-resources-containers](https://kubernetes.io/docs/concepts/configuration/manage-resources-containers/) |
| securityContext | object | `{"allowPrivilegeEscalation":false,"capabilities":{"drop":["ALL"]},"readOnlyRootFilesystem":false,"runAsGroup":185,"runAsUser":185}` | The SecurityContext for the application. |
| service.port | int | `8080` | Port of the application service |
| service.type | string | `"ClusterIP"` | Type of the application service |
| serviceAccount.annotations | object | `{}` | Annotations to add to the service account |
| serviceAccount.create | bool | `true` | Specifies whether a service account should be created |
| serviceAccount.name | string | `"web"` | The name of the service account to use. If not set and create is true, a name is generated using the fullname template |
| tolerations | list | `[]` | Tolerations for the application pods. |
