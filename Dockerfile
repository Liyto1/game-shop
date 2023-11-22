FROM openjdk:11
COPY target/your-java-app.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]