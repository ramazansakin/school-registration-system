FROM openjdk:11-jre-slim
ADD target/school-registration-system-0.0.1-SNAPSHOT.jar school-registration-system-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "school-registration-system-0.0.1-SNAPSHOT.jar"]