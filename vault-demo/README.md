## Install Vault
```bash
helm repo add hashicorp https://helm.releases.hashicorp.com
helm search repo hashicorp/vault
quarkus-demo/vault-demo$ helm install vault hashicorp/vault --version 0.24.1 --namespace vault --create-namespace -f values/vault/values.yaml
```

```bash
$ kubectl --namespace vault exec -it vault-0 -- vault operator init
Unseal Key 1: rVN5o7mLD53U0hJs4Dcgm4HeddPHuuVzE8ZVTC024DYM
Unseal Key 2: 3rSQl5ML70nHsc8Gn8tVw7YOL633zYdJNXF68RtN1MXt
Unseal Key 3: Le2YOybILUtO5uGi9rSOxbPehXhvhydzrsdw+/YXaiVH
Unseal Key 4: 2DTSol5IIzo33RDqOgNgvPdJxDzUVs0q3nXdI+fdK+dG
Unseal Key 5: iWaLYeBEQLHS907x3qcAjxFnbEtdO2llqErO517Bac1c

Initial Root Token: hvs.eo6vvYhb06KqlySbYjPsJUeI

Vault initialized with 5 key shares and a key threshold of 3. Please securely
distribute the key shares printed above. When the Vault is re-sealed,
restarted, or stopped, you must supply at least 3 of these keys to unseal it
before it can start servicing requests.

Vault does not store the generated root key. Without at least 3 keys to
reconstruct the root key, Vault will remain permanently sealed!

It is possible to generate new unseal keys, provided you have a quorum of
existing unseal keys shares. See "vault operator rekey" for more information
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
