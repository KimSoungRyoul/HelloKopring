FROM openjdk:11.0.15-jdk-slim

ENV SPRING_ACTIVE_PROFILE dev

COPY build/libs/*.jar /HelloKopring/build/libs/application.jar

ENTRYPOINT java -jar -Dspring.main.banner-mode=OFF -Dspring.profiles.active=${SPRING_ACTIVE_PROFILE} -Dspring.output.ansi.enabled=always -Dfile.encoding=UTF-8  /HelloKopring/build/libs/application.jar
