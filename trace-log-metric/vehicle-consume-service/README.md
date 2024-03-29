# vehicle-consume-service Project

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

You can then execute your native executable with: `./build/vehicle-consume-service-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling.

## Related Guides

- Micrometer Registry Prometheus ([guide](https://quarkus.io/guides/micrometer)): Enable Prometheus support for Micrometer
- SmallRye Reactive Messaging - Kafka Connector ([guide](https://quarkus.io/guides/kafka-reactive-getting-started)): Connect to Kafka with Reactive Messaging
- Kubernetes Client ([guide](https://quarkus.io/guides/kubernetes-client)): Interact with Kubernetes and develop Kubernetes Operators
- Kubernetes Config ([guide](https://quarkus.io/guides/kubernetes-config)): Read runtime configuration from Kubernetes ConfigMaps and Secrets
- Kubernetes ([guide](https://quarkus.io/guides/kubernetes)): Generate Kubernetes resources from annotations
- Micrometer metrics ([guide](https://quarkus.io/guides/micrometer)): Instrument the runtime and your application with dimensional metrics using Micrometer.

## Using
佈署  Producer 後，佈署此應用程式，驗證有接收 Producer 服務
```bash
$ curl -N k8s.cch.dev:8080/api/v1/consumer/vehicles
data: Vehicle [vehicleId=JH4KA8160RC002560, speed=12.106257, direction=38.926830038194495, tachometer=2656.5057529328947, dynamical=45.440342, timestamp=Sat Feb 04 06:05:46 GMT 2023]

data: Vehicle [vehicleId=1MEBM62F2JH693379, speed=33.410908, direction=76.03508454296055, tachometer=2554.642966420204, dynamical=27.333454, timestamp=Sat Feb 04 06:05:47 GMT 2023]

data: Vehicle [vehicleId=JH4KA8160RC002560, speed=68.53297, direction=46.222067004172715, tachometer=1725.6985909956766, dynamical=44.207493, timestamp=Sat Feb 04 06:05:48 GMT 2023
```

Metric 部分

```bash
$ curl k8s.cch.dev:8080/api/v1/consumer/q/metrics
```