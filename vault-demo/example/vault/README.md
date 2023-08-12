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


## Quarkus 整合
Quarkus 使用 `io.quarkiverse.vault:quarkus-vault` 第三方套件整合 Vault，接續將會實作整合部分。