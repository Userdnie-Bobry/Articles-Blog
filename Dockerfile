FROM openjdk:17.0.2-jdk-slim

RUN apt-get update && apt-get install -y locales locales-all

# Добавление в Docker JVM сертификатов для работы с API
COPY src/main/resources/certificates/* /usr/local/openjdk-17/lib/security/
RUN chmod 644 /usr/local/openjdk-17/lib/security/cacerts
RUN echo yes | keytool -import -alias gigachat -file /usr/local/openjdk-17/lib/security/gigachat.devices.sberbank.ru.crt -keystore /usr/local/openjdk-17/lib/security/cacerts -trustcacerts -storepass changeit
RUN echo yes | keytool -import -alias ngw -file /usr/local/openjdk-17/lib/security/ngw.devices.sberbank.ru.crt -keystore /usr/local/openjdk-17/lib/security/cacerts -trustcacerts -storepass changeit

ENV LANG ru_RU.UTF-8
ENV LANGUAGE ru_RU:ru
ENV LC_ALL ru_RU.UTF-8

COPY ["./", "/source"]
RUN chmod +x /source/gradlew
WORKDIR /source
RUN /source/gradlew bootJar
COPY ["./build/libs/*", "app.jar"]
ENTRYPOINT ["java","-jar","app.jar"]
