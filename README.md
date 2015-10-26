# verbum-domini
Deployed application: http://verbumdomini.herokuapp.com/

# Overview
-----

This project is an open data RESTFul API, which provides a set of services for retrieving data about bible translations. Such API let you easily navigate through resources. The availability of those services promotes creation of applications for many platforms like Android, iOS, desktop applications or even web sites using your preferred set of development tools.

# Installation
-----

It is a Java web application project. You have to fulfill some requirements in order to install project artifacts.

## System requirements
- Maven 2
- JDK 1.7
- Database system (relational or MongoDB)

In order to generate the artifacts use the command:
```
mvn clean package
```

## Local deploy
Despite the application is built using Java 7 you need Java 8 to run it locally using embedded Jetty web container. You can use any other web container if you please. It is up to you deploy it to Apache Tomcat 7 and run it with Java 7.

The simplest way may be deploy it to the embedded Jetty runner with command:
```
java $JAVA_OPTS -jar verbum-domini-app/target/dependency/jetty-runner.jar --port $PORT verbum-domini-app/target/verbumdomini.war
```
