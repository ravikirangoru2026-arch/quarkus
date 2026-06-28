# rest-mdb-curd-reactive-demo

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Application development

0. Create application with the below extentions

```shell script
quarkus-rest : Core reactive REST support
quarkus-rest-jackson : JSON support for reactive REST
quarkus-reactive-mysql-client : MySQL and MariaDB reactive connections through the same underlying MySQL driver
quarkus-hibernate-reactive-panache: Hibernate Reactive with Panache

io.quarkus.hibernate.reactive.panache.PanacheEntityBase for reactive
io.quarkus.hibernate.reactive.panache.PanacheRepository for reactive

@WithSession: for reacd only API
@WithTransaction for write operations like post, put delete..
Uni<..> as return type all api & db service calls.
```

0. Application properties for reactive communication and Database table creation logic/scipt keep in imort.sql file

```shell script
quarkus.datasource.db-kind=mariadb
quarkus.datasource.username=root
quarkus.datasource.password=root
quarkus.datasource.reactive.url=mysql://localhost:3306/quarkus_db_2

quarkus.hibernate-orm.log.sql=true
quarkus.hibernate-orm.sql-load-script=import.sql
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

## Package and Run the application

You can run your application in dev mode that enables live coding using:

```shell script
mvn clean install
java -jar target/quarkus-app/quarkus-run.jar

```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/rest-mdb-curd-reactive-demo-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- REST resources for Hibernate ORM with Panache ([guide](https://quarkus.io/guides/rest-data-panache)): Generate Jakarta REST resources for your Hibernate Panache entities and repositories
- REST ([guide](https://quarkus.io/guides/rest)): Build RESTful web services and APIs using Jakarta REST (formerly JAX-RS)
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it
- JDBC Driver - MariaDB ([guide](https://quarkus.io/guides/datasource)): Connect to the MariaDB database via JDBC

## Provided Code

### REST Data with Panache

Generating Jakarta REST resources with Panache

[Related guide section...](https://quarkus.io/guides/rest-data-panache)


### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
