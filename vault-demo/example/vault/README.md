# vault

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./gradlew build
```
It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./gradlew build -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/vault-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling.

## Related Guides

- Kubernetes Client ([guide](https://quarkus.io/guides/kubernetes-client)): Interact with Kubernetes and develop Kubernetes Operators
- RESTEasy Classic ([guide](https://quarkus.io/guides/resteasy)): REST endpoint framework implementing Jakarta REST and more
- SmallRye Health ([guide](https://quarkus.io/guides/smallrye-health)): Monitor service health
- Vault ([guide](https://quarkiverse.github.io/quarkiverse-docs/quarkus-vault/dev/index.html)): Store your credentials securely in HashiCorp Vault
- Kubernetes ([guide](https://quarkus.io/guides/kubernetes)): Generate Kubernetes resources from annotations
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)

### SmallRye Health

Monitor your application's health using SmallRye Health

[Related guide section...](https://quarkus.io/guides/smallrye-health)


## Install Vault client
[vault office downloads](https://developer.hashicorp.com/vault/downloads)

```bash
export VAULT_ADDR=https://vault-demo.cch.com:8453
export VAULT_SKIP_VERIFY=true
$ vault login -tls-skip-verify hvs.3nqeHIjaG8aIMZmmBuOPSlM4
```
## Vault enable KV version 2 engine
在啟用版本 2 `kv` 引擎時，必須透過 `secret` 引擎啟用。`secret` 引擎是一個儲存、生成或是加密數據的組件。版本 2 的 `kv` 引擎是一個可存取 key/value 對的功能，並且附帶過去每次異動版本歷程，因此舊的配置是可以被檢索的，下圖為官方提供的圖。

![](https://developer.hashicorp.com/_next/image?url=https%3A%2F%2Fcontent.hashicorp.com%2Fapi%2Fassets%3Fproduct%3Dtutorials%26version%3Dmain%26asset%3Dpublic%252Fimg%252Fvault%252Fversioned-kv-1.png%26width%3D1056%26height%3D353&w=1080&q=75)

對於應用程式存去而言，必須要先配置前述所講的動作。

啟用版本 v2 的 `kv` 引擎

```bash
$ vault secrets enable -version=2 kv
Success! Enabled the kv secrets engine at: kv/
```

透過 `list` 指令可以列出我們創建的 `kv` 路徑引擎
```bash
$ vault secrets list
Path          Type         Accessor              Description
----          ----         --------              -----------
cubbyhole/    cubbyhole    cubbyhole_293822d8    per-token private secret storage
identity/     identity     identity_5bba97e7     identity store
kv/           kv           kv_cd1f8bda           n/a
sys/          system       system_6e9e02ef       system endpoints used for control, policy and debugging
```

在 `kv` 路徑下建立一個 `quarkus` 路徑，並塞入我們要給應用程式的值

```bash
$ vault kv put kv/quarkus/vault-demo mqtt.broker="tcp://localhost:8883" mqtt.username="demo" mqtt.password="demo1234"
======= Secret Path =======
kv/data/quarkus/vault-demo

======= Metadata =======
Key                Value
---                -----
created_time       2023-08-12T14:33:34.390315381Z
custom_metadata    <nil>
deletion_time      n/a
destroyed          false
version            1
```

可以透過 `kv get` 來獲取某路徑下已經定義的鍵值對

```bash
$ vault kv get -mount=kv quarkus/vault-demo
======= Secret Path =======
kv/data/quarkus/vault-demo

======= Metadata =======
Key                Value
---                -----
created_time       2023-08-12T14:33:34.390315381Z
custom_metadata    <nil>
deletion_time      n/a
destroyed          false
version            1 # 上步驟推資料所以異動一次
# 下面為推送的鍵值對
======== Data ========
Key              Value
---              -----
mqtt.broker      tcp://localhost:8883
mqtt.password    demo1234
mqtt.username    demo
```

如果資料定義錯誤想要做修正可以使用 `kv patch` 指令，假設密碼設定錯誤要換成 `demo12345678`

```bash
$ vault kv patch -mount=kv quarkus/vault-demo mqtt.password=demo12345678
======= Secret Path =======
kv/data/quarkus/vault-demo

======= Metadata =======
Key                Value
---                -----
created_time       2023-08-12T14:41:39.647900006Z
custom_metadata    <nil>
deletion_time      n/a
destroyed          false
version            2 # 此時更新密碼因此版本在被遞增
```

再使用 `kv get` 時該 `mqtt.password` 應當被置換過值。

## Retrieve a specific version
如果想查看過去異動，或是更新前的查看都可以使用 version 來進行。
```bash
$ vault kv get -version=1 -mount=kv quarkus/vault-demo
======= Secret Path =======
kv/data/quarkus/vault-demo

======= Metadata =======
Key                Value
---                -----
created_time       2023-08-12T14:33:34.390315381Z
custom_metadata    <nil>
deletion_time      n/a
destroyed          false
version            1

======== Data ========
Key              Value
---              -----
mqtt.broker      tcp://localhost:8883
mqtt.password    demo1234
mqtt.username    demo
```

預設上 v2 kv 引擎 `Maximum number of versions` 是未設定，如下使用 `read` 指令查詢。但可以依照需求去做調整。

```bash
$ vault read kv/config
Key                     Value
---                     -----
cas_required            false
delete_version_after    0s
max_versions            0 
```

## Delete version
刪除某個版本，`delete` 動作是軟刪除，該版本會被標記是刪除且定義一個 `deletion_time` 時戳，這可用於 `undelete` 操作基本上可以認知成回滾。當 `kv` 版本超過 `max-versions` 設置的數量時，或者使用 `destroy` 操作時，版本的數據才會被*永久刪除*。 

```bash
$ vault kv delete -versions=1 -mount=kv quarkus/vault-demo
Success! Data deleted (if it existed) at: kv/data/quarkus/vault-demo
```

使用 `metadata` 操作獲取詳細版本資訊

```bash
$ vault kv metadata get  -mount=kv quarkus/vault-demo
======== Metadata Path ========
kv/metadata/quarkus/vault-demo

========== Metadata ==========
Key                     Value
---                     -----
cas_required            false
created_time            2023-08-12T14:33:34.390315381Z
current_version         2
custom_metadata         <nil>
delete_version_after    0s
max_versions            0
oldest_version          0
updated_time            2023-08-12T14:41:39.647900006Z

====== Version 1 ======
Key              Value
---              -----
created_time     2023-08-12T14:33:34.390315381Z
deletion_time    2023-08-12T17:12:50.563260118Z # deletion_time 被定義，表示不會被立即移除
destroyed        false

====== Version 2 ======
Key              Value
---              -----
created_time     2023-08-12T14:41:39.647900006Z
deletion_time    n/a
destroyed        false
```

復原版本 1 資料
```bash
$ vault kv undelete -versions=1 -mount=kv quarkus/vault-demo
Success! Data written to: kv/undelete/quarkus/vault-demo
$ vault kv metadata get  -mount=kv quarkus/vault-demo
======== Metadata Path ========
kv/metadata/quarkus/vault-demo
...
====== Version 1 ======
Key              Value
---              -----
created_time     2023-08-12T14:33:34.390315381Z
deletion_time    n/a # 要被刪除時間已經設回原始
destroyed        false

...
```

至於 `destroy` 操作，不接續展示基本上是會直接將指定版本資源刪除，不做任何標記。

## Defined role access to quarkus/vault-demo

以此範例來說應用程式只想要存取 `quarkus/vault-demo` 這個路徑下的 `k/v` 資源，因此要給予適當權限

```bash
# policy.hcl
path "kv/data/quarkus/vault-demo" {
    capabilities = ["read"]
}
```
寫入權限至 Vault 服務中
```bash
$ vault policy write vault policy.hcl
Success! Uploaded policy: vault
$ vault policy list
default
vault # 建立的 policy 名稱是 vault
root
```

有了權限還不夠。截至當前都是使用 `root` 的 `token` 做操作，應用程式的存取不應該使用 `root` 權限進行操作，因此需要再定義一個屬於應用程式存取的 `token`。

透過 `token create` 建立一個 `token`，並賦予該有的權限，時效可用 `ttl` 設定。`token lookup` 可以查看該 `token` 內容像是過期時間(expire_time)、可存取的政策(policies)等等。

```bash
$ vault token create -policy=vault --ttl=768h
Key                  Value
---                  -----
token                hvs.CAESIIDXLAV0-_yh1VJjVQkDxojDEP54lTfT0Y-UsVEBJKBhGh4KHGh2cy5lRFRMeFpBSmpGMGo2ZXRNR0RsTDh3UmI
token_accessor       KqbfPMrF6pmmFLb4n7E1niob
token_duration       768h
token_renewable      true
token_policies       ["default" "vault"]
identity_policies    []
policies             ["default" "vault"]

$ vault token lookup -accessor KqbfPMrF6pmmFLb4n7E1niob
Key                 Value
---                 -----
accessor            KqbfPMrF6pmmFLb4n7E1niob
creation_time       1692449442
creation_ttl        768h
display_name        token
entity_id           n/a
expire_time         2023-09-20T12:50:42.985961541Z
explicit_max_ttl    0s
id                  n/a
issue_time          2023-08-19T12:50:42.985964311Z
meta                <nil>
num_uses            0
orphan              false
path                auth/token/create
policies            [default vault]
renewable           true
ttl                 767h59m36s
type                service
```

對於 `token` 預設系統 `TTL` 值是 `768h` 如下。

```bash
$ vault read sys/auth/token/tune
Key                  Value
---                  -----
default_lease_ttl    768h
description          token based credentials
force_no_cache       false
max_lease_ttl        768h
token_type           default-service
```

要針對 TTL 進行調整可以使用以下方式

```bash
$ vault write sys/auth/token/tune default_lease_ttl=8h max_lease_ttl=720h
```

## Quarkus 整合

上面章節透過 Vault 的 `secret` 服務建立一個可存放 k/v 物件的資源並將重要的資源存放至該 `secret` 服務。同時間，建立了一個 policy 資源並將其和 token 資源綁在一起給應用程式做存取。

此章節會透過 Quarkus 框架與 Vault 進行一個整合範例。會分成兩部分來說明

1. 地端開發
2. 佈署至 Kubernetes 環境

Quarkus 使用 `io.quarkiverse.vault:quarkus-vault` 第三方套件整合 Vault，接續將會實作整合部分。範例中簡單的使用外部環境變數，如下

```bash
mqtt.broker=
mqtt.username=
mqtt.password=
```

### 地端開發
這邊再進行 Vault 存取的配置來讓 Vault 進行環境變數的注入。因為 `SSL` 非正式所以這邊使用了 `quarkus.tls.trust-all=true` 來進行信任。重點是下面配置

1. `quarkus.vault.authentication.client-token` 表示使用 `token` 方式對 Vault 進行存取
2. `quarkus.vault.url` 表示存取 Vault 的位置
3. `quarkus.vault.kv-secret-engine-mount-path` 啟用 secret 中的 kv 引擎掛載的路徑
4. `quarkus.vault.secret-config-kv-path` 掛載路徑下要存取的子路徑
5. `quarkus.vault.kv-secret-engine-version` 使用的版本，預設是 `2`

```bash
%dev.quarkus.vault.authentication.client-token=hvs.CAESIIDXLAV0-_yh1VJjVQkDxojDEP54lTfT0Y-UsVEBJKBhGh4KHGh2cy5lRFRMeFpBSmpGMGo2ZXRNR0RsTDh3UmI
quarkus.vault.url=https://vault-demo.cch.com:8453
quarkus.vault.kv-secret-engine-mount-path=kv
quarkus.vault.secret-config-kv-path=quarkus/vault-demo
quarkus.vault.kv-secret-engine-version=2
```
這邊的 `%dev` 是指在 `dev` 環境使用。

驗證方面，寫了一個 API 接口，透過該接口可以驗證是否從 Vault 的 kv 引擎獲取資源

```java
    @GET
    @Path("/mqtt/config")
    @Produces(MediaType.APPLICATION_JSON)
    public Response config() {
        return Response.ok(Map.of("url", mqttConnect.broker())).build();
    } 
```

最後運行應用程式，嘗試存取 `/hello/mqtt/config` 接口如下，可以看出他確實拿到了資源。

```bash
$ curl http://localhost:8080/hello/mqtt/config
{"url":"tcp://localhost:8883"}
```

對於地端來說雖然 `quarkus.vault.authentication.client-token` 的值已經有限制權限和時效，但還是會習慣把 `quarkus.vault.authentication.client-token` 放置 `.env` 檔案中，這樣基本上原始碼不會出現關於存取 Vault 服務 API 的資源。
 
### Kubernetes 環境

對於佈署至 Kubernetes 環境，Vault 提供了用 `serviceAccount` 的 `token` 存取 Vault 服務的功能。相較於使用上面 `token` 方式，會比較靈活很多，像是不必特別管理 token 是否會過期之類。

要使用 Kubernetes 認證必須執行以下，這邊會使用 root 權限進行操作
1. 啟用該功能
```bash
$ vault auth enable kubernetes
Success! Enabled kubernetes auth method at: kubernetes/
```

2. 用 `/config` 介面配置 Vault 與 Kubernetes 的通訊，使用 Pod 的 `serviceAccount` 的 token。 Vault 將定期重新讀取該檔案以支援短期令牌(short-lived tokens)。這樣 Vault 將嘗試從預設掛載位置 `/var/run/secrets/kubernetes.io/serviceaccount/` 內的 `token` 和 `ca.crt` 加載它們。

```bash
$ vault write auth/kubernetes/config \
    kubernetes_host=https://$KUBERNETES_SERVICE_HOST:$KUBERNETES_SERVICE_PORT
Success! Data written to: auth/kubernetes/config
```

`$KUBERNETES_SERVICE_HOST` 與 `$KUBERNETES_SERVICE_PORT` 是環境變數，需替代真實的值，本人測試時環境變數似乎無法被系統替代。


3. 設定允許被存取的 `namespace` 與 `serviceAccount`

```bash
$ vault write auth/kubernetes/role/quarkus-vault \
> bound_service_account_names=vault \
> bound_service_account_namespaces=prod \
> policies=vault \
> ttl=1h
Success! Data written to: auth/kubernetes/role/quarkus-vault
```

- `quarkus-vault` 是角色名稱，應用程式存取用此角色
- `bound_service_account_names` 可使用該角色的 `serviceAccout` (Kubernetes 資源)
- `bound_service_account_namespaces` 可使用該角色的 `namespace` (Kubernetes 資源)
- `policies` 該角色綁定的政策，這邊會對應上面所設定的政策

一個角色能被多個 kubernetes 中的 namespace 下的多個 serviceAccount 資源所使用，政策同樣也可以綁定多個。


設定好之後，需覆寫應用程式的配置，修正後應當如下

```bash
%prod.quarkus.vault.url=http://vault.vault.svc.cluster.local:8200
%prod.quarkus.vault.authentication.kubernetes.role=quarkus-vault
```

接著透過佈署應用程式測試，應用程式佈署資源如下

```bash
$ kubectl get all -n prod
NAME                         READY   STATUS    RESTARTS   AGE
pod/vault-76cfcb466f-s6z2m   1/1     Running   0          6m15s

NAME            TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
service/vault   ClusterIP   10.43.236.207   <none>        8080/TCP   34m

NAME                    READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/vault   1/1     1            1           34m

NAME                               DESIRED   CURRENT   READY   AGE
replicaset.apps/vault-76cfcb466f   1         1         1       34m
$ kubectl get ingress -n prod
NAME    CLASS   HOSTS                ADDRESS                            PORTS   AGE
vault   nginx   vault-demo.cch.com   172.18.0.2,172.18.0.3,172.18.0.4   80      35m
```

測試

```bash
$ curl http://vault-demo.cch.com:8050/api/v1/hello/mqtt/config
{"url":"tcp://localhost:8883"}
```

這邊，應用程式透過了 Vault 中 kubernete 認證方式對 Vault 進行存取，而非 `token` 認證。

但對於應用程式存取 Vault 除了透過框架提供的方式，還可以使用 Vault 的 sidecar 方式或是 CSI(Container Storage Interface)。