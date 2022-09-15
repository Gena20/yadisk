FROM ibm-semeru-runtimes:open-17-jdk
ARG JAR_FILE=./target/*.jar

COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]