import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.22"
    kotlin("kapt") version "1.7.22"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.springframework.boot")
}

repositories {
    mavenCentral()
    google()
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        val kotestVersion: String by properties

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")

        testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
        testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "17"
            }
        }
        test {
            useJUnitPlatform()
        }
    }
}
