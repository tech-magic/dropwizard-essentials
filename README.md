# Putting it all together

## Prerequisites
Maven 3 and Java 8

## dropwizard, swagger, jdbi

1. dropwizard: An ideal framework for implementing java-based scalable restful micro-services
2. swagger: Auto-generates comprehensive documentation for restful apis
3. jdbi: An annotation driven jdbc-based database wrapper which drastically reduces code in data access layer.

## Configuring the project to eclipse

1. Go to dropwizard-swagger-example folder in a terminal.
2. Type maven eclipse:eclipse
3. Now import the project to eclipse

## Running the project

1. From dropwizard-swagger-example folder, type `mvn clean package`
2. You will see a target folder being created with a jar file dropwizard-swagger-example-1.0.0.jar
3. Now run `java -jar target/dropwizard-swagger-example-1.0.0.jar server config.yml`
4. Type `http://localhost:8080/swagger` to access the restful api

## Configuring Database

1. This sample project is configured to h2 in memory database for easy setup (contact table will be automatically created in memory).
2. However if you want to change database settings to a permanent relational database you can do by editing the `database` section of dropwizard-swagger-example/config.yml file and adding the relavent db driver to the maven pom file. Also do not forget to create a table called `contact` in your relational database as mentioned in `dropwizard-swagger-example/src/main/java/com/ebuilder/microservices/contactservice/dao/ContactDAO.java`.

  

