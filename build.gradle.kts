import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val strikt_version: String by project
val mockk_version: String by project
val jetbrains_markdown_version: String by project

plugins {
    kotlin("jvm") version "1.7.21"
    id("io.ktor.plugin") version "2.2.2"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

group = "dev.wenzel"
version = "0.0.1"
application {
    mainClass.set("dev.wenzel.application.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.jetbrains:markdown:$jetbrains_markdown_version")
    implementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("io.strikt:strikt-core:$strikt_version")
    testImplementation("io.mockk:mockk:$mockk_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
