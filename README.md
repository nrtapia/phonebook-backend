# PhoneBook Backend

This project was generated with [Spring Initializr](https://start.spring.io/) version 2.12.

## Development server

Run `$ mvn spring-boot:run` for a dev server. REST endpoint to `http://localhost:9090/contacts`. 

## Build

Run `mvn package` to build the project. The build artifacts will be stored in the `target/` directory. 

## Config

To configure Database settings update `src/main/resources/application.properties` change properties `spring.datasource.*`

To enable CORS Origins update `src/main/resources/application.properties` change property `cors.allowed.origins` with the frontend application URL.

## Run

Run `java -jar target/phonebook-backend-0.0.1-SNAPSHOT.jar`

Run with a custom port `java -jar -Dserver.port=7070 target/phonebook-backend-0.0.1-SNAPSHOT.jar`

## Logs

To configure LOG settings update `src/main/resources/logback.xml`


