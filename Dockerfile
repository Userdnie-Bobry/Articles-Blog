FROM openjdk:17.0.2-slim
COPY ["./", "/source"]
WORKDIR /source
RUN /source/gradlew bootJar
COPY ["./build/libs/*", "app.jar"]
ENTRYPOINT ["java","-jar","app.jar"]