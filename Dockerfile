FROM openjdk:17-jdk-alpine

COPY target/demo-0.0.1-SNAPSHOT.jar demo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/demo-0.0.1-SNAPSHOT.jar"]


# Copy the SQL script into the container
#COPY ./scripts/init.sql /docker-entrypoint-initdb.d/init.sql