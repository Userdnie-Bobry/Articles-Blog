plugins {
	java
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "org"
version = "0.0.1"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jsoup:jsoup:1.15.3")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.1.0")
	implementation("org.hibernate.search:hibernate-search-mapper-orm:7.1.0.Alpha1")
	implementation("org.hibernate.search:hibernate-search-backend-lucene:7.1.0.Alpha1")
	implementation("org.hibernate.orm:hibernate-core:6.4.2.Final")
	implementation("com.google.firebase:firebase-admin:9.2.0")
	implementation("com.atlassian.commonmark:commonmark:0.17.0")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.2.2")
	implementation("org.springframework.boot:spring-boot-maven-plugin:3.2.2")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation("org.springframework.boot:spring-boot-starter-security")
	//implementation("org.springframework.security:spring-security-oauth2-core:6.2.0")
	implementation ("org.keycloak:keycloak-admin-client:23.0.7")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
