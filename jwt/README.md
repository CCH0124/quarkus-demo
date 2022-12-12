# jwt Project

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

You can then execute your native executable with: `./build/jwt-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling.

## Related Guides

- REST resources for Hibernate ORM with Panache ([guide](https://quarkus.io/guides/rest-data-panache)): Generate JAX-RS resources for your Hibernate Panache entities and repositories
- Liquibase ([guide](https://quarkus.io/guides/liquibase)): Handle your database schema migrations with Liquibase

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)


## Key pair
```shell
openssl genrsa -out private.pem 2048
openssl rsa -in private.pem -pubout -outform PEM -out public.pem
openssl pkcs8 -topk8 -nocrypt -inform pem -in private.pem -outform pem -out privateKey.pem
```

## API Spec

1. `POST /api/register`

Payload
```json
{
    "account": "madara",
    "username": "madara",
    "email": "madara@gmail.com",
    "password": "1234567890",
    "roles": [
        "USER",
        "MODERATOR",
        "ADMIN"
    ]
}
```

2. `POST http://localhost:8080/api/login`

Payload

```json
{
    "account": "madara",
    "password": "1234567890"
}
```

## Ref
- [bliblidotcom-techblog](https://medium.com/bliblidotcom-techblog/introduction-to-jwt-also-jws-jwe-jwa-jwk-e845b0db9f4e)
- [authentication-and-authorization-using-jwt-on-quarkus](https://ard333.medium.com/authentication-and-authorization-using-jwt-on-quarkus-aca1f844996a)
- [techtalksteve](https://www.techtalksteve.com/blog/3-quarkus-security-with-jwt/)
