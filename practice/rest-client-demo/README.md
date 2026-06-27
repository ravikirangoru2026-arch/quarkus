# rest-client-demo

1. Consume external service using restclient
2. Secure application with JKS (Enable SSL)
3. Secure application with P12 (Enable SSL)

## Application development

0. Create application with quarkus-rest-client, quarkus-rest-client-jackson, quarkus-rest-jackson

1. Insecure application(Disable SSL) i.e http://
   
   http://localhost:8080/consume-users
     
```shell script
# REST Client base URL (HTTPS)
jsonplaceholder-api/mp-rest/url=https://jsonplaceholder.typicode.com
# Disable SSL
quarkus.ssl.native=false
quarkus.http.insecure-requests=enabled
```

2. Secure application with JKS keystore (Enable SSL) i.e https://

    Generate JKS key store and keep in src/main/resources
```shell script
# REST Client base URL (HTTPS)
jsonplaceholder-api/mp-rest/url=https://jsonplaceholder.typicode.com

# Enable SSL
quarkus.ssl.native=true
quarkus.http.ssl-port=8443
quarkus.http.insecure-requests=disabled
# Keystore configuration
quarkus.http.ssl.certificate.key-store-file=server-keystore.jks
quarkus.http.ssl.certificate.key-store-password=changeit
quarkus.http.ssl.certificate.key-store-provider=JKS
```
   
3. Secure application with P12 keystore (Enable SSL) i.e https://

    Generate P12 key store and keep in src/main/resources
```shell script
# REST Client base URL (HTTPS)
jsonplaceholder-api/mp-rest/url=https://jsonplaceholder.typicode.com

# Enable SSL
quarkus.ssl.native=true
quarkus.http.ssl-port=8443
quarkus.http.insecure-requests=disabled
# Keystore configuration
quarkus.http.ssl.certificate.key-store-file=server-keystore.p12
quarkus.http.ssl.certificate.key-store-password=changeit
quarkus.http.ssl.certificate.key-store-provider=PKCS12
```

## Package and Run the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
mvn clean install
java -jar target/quarkus-app/quarkus-run.jar

mvn quarkus:dev

```

