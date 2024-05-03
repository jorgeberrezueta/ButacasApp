plugins {
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
}

group = "org.example"
version = "0.0.1"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-mustache")
	implementation("org.springframework.boot:spring-boot-starter-web") {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-json")
	}
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.2.1")
	implementation("jakarta.validation:jakarta.validation-api:3.0.2")
	implementation("com.google.code.gson:gson:2.10.1")
	implementation("org.mindrot:jbcrypt:0.4")

	implementation("org.projectlombok:lombok:1.18.32")
	annotationProcessor("org.projectlombok:lombok:1.18.32")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.5")
	implementation("org.xerial:sqlite-jdbc:3.45.3.0")
	implementation("org.hibernate.orm:hibernate-community-dialects:6.5.0.Final")
	implementation("com.fasterxml.jackson.core:jackson-annotations:2.17.0")
//	implementation("javax.xml.bind:jaxb-api:2.3.1")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testImplementation("org.junit.jupiter:junit-jupiter-engine")
	testImplementation("org.projectlombok:lombok:1.18.32")
	testAnnotationProcessor("org.projectlombok:lombok:1.18.32")

}

tasks.withType<Test> {
	useJUnitPlatform()
}