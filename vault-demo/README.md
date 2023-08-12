## Install Vault
[vault artifacthub](https://artifacthub.io/packages/helm/hashicorp/vault)
[vault-doc helm configuration](https://developer.hashicorp.com/vault/docs/platform/k8s/helm/configuration)
```bash
helm repo add hashicorp https://helm.releases.hashicorp.com
helm search repo hashicorp/vault
quarkus-demo/vault-demo$ helm install vault hashicorp/vault --version 0.25.0 --namespace vault --create-namespace -f values/vault/values.yaml
```

```bash
$ kubectl --namespace vault exec -it vault-0 -- vault operator init
Unseal Key 1: rie1qIyzUi7hBPNkHPgI5FAni67Dwd8or8F1pK5BHGHP
Unseal Key 2: V1JTIz+PZOJRpWen8eGBu3a/f70rurWh57gF7prZTy1+
Unseal Key 3: sbYXRQ74xQJmEC8JjoL6i5W2SzydEK81sI7fyUTX1QFW
Unseal Key 4: 4cAAitfZP5wOy6Dv/9poBuPa8r6HjJ11OYNgDcVUiFMz
Unseal Key 5: kkEnDx8VPY3IzHMIY2zF+DntuH8W+ZhelDlK7Z6e7VIq

Initial Root Token: hvs.3nqeHIjaG8aIMZmmBuOPSlM4

Vault initialized with 5 key shares and a key threshold of 3. Please securely
distribute the key shares printed above. When the Vault is re-sealed,
restarted, or stopped, you must supply at least 3 of these keys to unseal it
before it can start servicing requests.

Vault does not store the generated root key. Without at least 3 keys to
reconstruct the root key, Vault will remain permanently sealed!

It is possible to generate new unseal keys, provided you have a quorum of
existing unseal keys shares. See "vault operator rekey" for more information.
```

```bash
$ kubectl --namespace vault exec -ti vault-0 -- vault operator unseal rie1qIyzUi7hBPNkHPgI5FAni67Dwd8or8F1pK5BHGHP
$ kubectl --namespace vault exec -ti vault-0 -- vault operator unseal sbYXRQ74xQJmEC8JjoL6i5W2SzydEK81sI7fyUTX1QFW
$ kubectl --namespace vault exec -ti vault-0 -- vault operator unseal kkEnDx8VPY3IzHMIY2zF+DntuH8W+ZhelDlK7Z6e7VIq
```

## Install ingress
```bash
$ helm repo add ingress-nginx  https://kubernetes.github.io/ingress-nginx
$ helm search repo ingress-nginx
$ helm install ingress-nginx ingress-nginx/ingress-nginx --version 4.6.0 --namespace ingress-nginx --create-namespace
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

## Install Cert-management

```bash
$ helm repo add jetstack https://charts.jetstack.io
"jetstack" has been added to your repositories
$ helm repo update
$ helm install cert-manager jetstack/cert-manager --namespace cert-manager --create-namespace --version v1.12.0 --set installCRDs=true
```
## Install EMQX
[emqx-operator](https://github.com/emqx/emqx-operator/tree/main/deploy/charts/emqx-operator)

```bash
$ helm repo add emqx https://repos.emqx.io/charts
"emqx" has been added to your repositories
$ helm install emqx-operator emqx/emqx-operator --version 2.1.2 --namespace emqx-operator-system  --create-namespace --set cert-manager.enable=true
```
