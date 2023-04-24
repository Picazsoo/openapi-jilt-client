plugins {
	java
	id("org.springframework.boot") version "2.7.11"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id("org.openapi.generator") version "6.5.0"
}

group = "cz.metlicka"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2021.0.6"

dependencies {
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("io.github.openfeign:feign-jackson:12.3")
	implementation("io.swagger.core.v3:swagger-annotations:2.2.8")
	implementation("io.swagger.core.v3:swagger-models:2.2.8")
	implementation("javax.validation:validation-api:2.0.1.Final")
	implementation("org.mapstruct:mapstruct:1.5.4.Final")
	implementation("org.openapitools:jackson-databind-nullable:0.2.1")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

val spec = "$rootDir/src/main/resources/openapi.yaml"
val generatedSourcesDir = "$buildDir/generated/sources/openapi"

openApiGenerate {
	generatorName.set("spring")
	inputSpec.set(spec)
	outputDir.set(generatedSourcesDir)
	apiPackage.set("cz.metlicka.client.api")
	modelPackage.set("cz.metlicka.client.model")
	configOptions.set(mapOf(
			"dateLibrary" to "java8",
			"library" to "spring-cloud",
	))
}

sourceSets {
	getByName("main") {
		java {
			srcDir("$generatedSourcesDir/src/main/java")
		}
	}
}

tasks {
	val openApiGenerate by getting

	val compileJava by getting {
		dependsOn(openApiGenerate)
	}
}
