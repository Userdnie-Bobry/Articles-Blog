FROM openjdk:17.0.2-slim

RUN apt-get update && apt-get install -y locales locales-all
ENV LANG ru_RU.UTF-8
ENV LANGUAGE ru_RU:ru
ENV LC_ALL ru_RU.UTF-8

COPY ["./", "/source"]
WORKDIR /source
RUN /source/gradlew bootJar
COPY ["./build/libs/*", "app.jar"]
ENTRYPOINT ["java","-jar","app.jar"]