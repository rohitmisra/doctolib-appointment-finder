import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.10"
	kotlin("plugin.spring") version "1.5.10"
}

group = "de.rohitmisra"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation(kotlin("script-runtime"))
	implementation("com.squareup.okhttp:okhttp:2.7.5")
	implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.3")
	implementation("com.slack.api:slack-api-client:1.8.0")
	implementation("se.transmode.gradle:gradle-docker:1.2")

	// Add these dependencies if you want to use the Kotlin DSL for building rich messages
	implementation("com.slack.api:slack-api-model-kotlin-extension:1.8.0")
	implementation("com.slack.api:slack-api-client-kotlin-extension:1.8.0")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
