FROM docker.io/library/maven:3.8.6-openjdk-18 as BUILD

COPY src /usr/yadisk/src
COPY pom.xml /usr/yadisk
RUN mvn -Dmaven.test.skip -f /usr/yadisk/pom.xml clean package

FROM ibm-semeru-runtimes:open-17-jdk

COPY --from=BUILD /usr/yadisk/target/yadisk-1.0-SNAPSHOT.jar application.jar
CMD ["java", "-jar", "application.jar"]