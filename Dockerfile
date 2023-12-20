FROM openjdk:21-oracle

COPY target/eCommerce-0.0.1-SNAPSHOT.jar /app/eCommerce-0.0.1-SNAPSHOT.jar

WORKDIR /app

ENTRYPOINT ["java", "-jar", "eCommerce-0.0.1-SNAPSHOT.jar"]
