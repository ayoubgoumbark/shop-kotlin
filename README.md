# iMedia24 Coding challenge

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Official Kotlin documentation](https://kotlinlang.org/docs/home.html)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.3/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.3/gradle-plugin/reference/html/#build-image)
* [Flyway database migration tool](https://flywaydb.org/documentation/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

### How to put your app into Docker container 
* create a Docker file with this commandes :

      FROM openjdk:8-jdk-alpine
      ARG JAR_FILE=target/*.jar
      COPY ./build/libs/shop-0.0.1.jar app.jar 
      ENTRYPOINT ["java","-jar","/app.jador"]

* now to build your docker image run the following command in terminal:

      docker build -t your-app-name .

* now running the Docker container with the following command:

      docker run -p 8080:your-app-port your-app-name

