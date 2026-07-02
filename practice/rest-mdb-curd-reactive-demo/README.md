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

1.a Application properties for reactive communication and Database table creation logic/scipt keep in imort.sql file

```shell script
quarkus.datasource.db-kind=mariadb
quarkus.datasource.username=root
quarkus.datasource.password=root
quarkus.datasource.reactive.url=mysql://localhost:3306/quarkus_db_2

quarkus.hibernate-orm.log.sql=true
quarkus.hibernate-orm.sql-load-script=import.sql
```

1.b Application properties for log (default log no extention required)

```shell script
# Root log level
quarkus.log.level=INFO

# Hibernate SQL logs
#quarkus.hibernate-orm.log.sql=true

# Category-specific logging
quarkus.log.category."com.rk.quarkus".level=DEBUG

#quarkus.log.console.enable=true


# Enable file logging
quarkus.log.file.enable=true
quarkus.log.file.path=logs/quarkus.log
quarkus.log.file.rotation.max-file-size=10M
quarkus.log.file.rotation.max-backup-index=5

```

1.c Application properties for swagger and api documentation

```shell script
# Enable OpenAPI
quarkus.smallrye-openapi.path=/openapi
quarkus.swagger-ui.path=/swagger-ui
quarkus.swagger-ui.always-include=true

#http://localhost:8080/openapi
#http://localhost:8080/swagger-ui

```


2. Exception handling

```shell script
1. Create custom exception: UserNotFoundException
package com.rk.quarkus.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String msg) {
		super(msg);
	}
}

2. Old Way-1 (Not Recommended): Create Exception mapper: UserNotFoundExceptionMapper implements ExceptionMapper<UserNotFoundException>, add @Provider at class level and override required method.

package com.rk.quarkus.exception;

import com.rk.quarkus.dto.ExceptionResponse;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UserNotFoundExceptionMapper implements ExceptionMapper<UserNotFoundException>{

	@Override
	public Response toResponse(UserNotFoundException exception) {
		ExceptionResponse resp=new ExceptionResponse();
		resp.setStatusCode("U001");
		resp.setStatusMsg(exception.getMessage());
		return Response.status(Response.Status.NOT_FOUND).entity(resp).build();
	}

}

3. New Way-2(Recommended): create global exception mapper with @ApplicationScoped and @ServerExceptionMapper annotation on each method.

package com.rk.quarkus.exception;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import com.rk.quarkus.dto.ExceptionResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class GlobalExceptionMapper {

	@ServerExceptionMapper
	public RestResponse<ExceptionResponse> handleUserNotFoundException(UserNotFoundException e) {
		ExceptionResponse resp = new ExceptionResponse();
		resp.setStatusCode("U001");
		resp.setStatusMsg(e.getMessage());
		return RestResponse.status(Response.Status.NOT_FOUND, resp);

	}
}

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
