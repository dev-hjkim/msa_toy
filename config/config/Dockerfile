FROM openjdk:11-jdk as builder
MAINTAINER hjkim

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar

FROM openjdk:11-jdk

COPY --from=builder build/libs/*.jar config.jar

RUN rm /bin/sh && ln -s /bin/bash /bin/sh
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=native", "/config.jar"]
