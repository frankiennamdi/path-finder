# Required Technologies
1. mvn (maven wrappers included)
2. Java 8.x - chosen for some of its appealing functionality like streams, functions and method references.
3. Developed with IDEA Intellij - chosen as my IDE of choice.
4. Spring Boot 2.3.x - Please see requirements for this version of Spring Boot



# Overview

This application create a API for checking if path exist between two cities using a predefined map of cities.

# Default Map

The sample map contains the following paths. And the application is currently configured to be undirected. This
means if a path exists from Boston to New York, then a path exist from New York to Boston. This is configurable
and can be changed.

```
Boston, New York
Philadelphia, Newark
Newark, Boston
Trenton, Albany
```

# API documentation and testing tool using swagger

http://localhost:8080/swagger-ui/#/city-path-finder-controller

# Development and Execution

This section describes the development and execution environment and steps.  

## Build and Run Unit Test

```
./mvnw clean package
```

## Build and Run Test

```
./mvnw clean test
```

## Run Service

```
./mvnw clean package && java -jar target/path-finder-0.0.1-SNAPSHOT.jar
```

## Run Service with Spring Boot

```
./mvnw clean test spring-boot:run

```

## Run service with external city map file

```
 ./mvnw clean package && java -cp target/path-finder-0.0.1-SNAPSHOT.jar -Dloader.path=data org.springframework.boot.loader.PropertiesLauncher --path-finder.graph.file=map/city.txt

```

Where data is the folder where the city.txt file lives under the map folder.

# Services and Example

This section demonstrate call to invoke the services the application provides. 

## Find Path using curl

As mentioned earlier the swagger ui can be used to test the service. In addition, you can also use your browser.

```
curl -X GET -G --data-urlencode "origin=Boston" --data-urlencode "destination=New York" http://localhost:8080/connected
```








