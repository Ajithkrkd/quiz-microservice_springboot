FROM openjdk:17-alpine3.14
WORKDIR /opt
EXPOSE 8765
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
