FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/tasks-0.0.1-SNAPSHOT.jar task-management.jar
ENTRYPOINT ["java","-jar","/task-management.jar"]
EXPOSE 9000