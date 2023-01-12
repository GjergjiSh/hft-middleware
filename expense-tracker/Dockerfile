FROM openjdk:11-jre-slim
RUN mkdir /opt/app
COPY target/ExpenseTracker-0.0.1-SNAPSHOT.jar /opt/app/app.jar
CMD ["java", "-jar", "/opt/app/app.jar"]