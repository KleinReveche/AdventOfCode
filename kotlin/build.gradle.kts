import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.ktor.plugin") version "2.3.4"
    id("com.diffplug.spotless") version "6.21.0" apply false
    kotlin("jvm") version "1.9.0"
    application
}

group = "com.kleinreveche"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("com.kleinreveche.adventofcode.AdventOfCodeKt")
}

tasks.register<Copy>("copyInputs") {

    from("../inputs")
    include("**/*.txt")
    into("src/main/resources/inputs")
}


tasks.processResources {
    dependsOn("copyInputs")
}

subprojects {
    apply(plugin = "com.diffplug.spotless")
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("build/**/*.kt")

            ktlint()
        }

        kotlinGradle {
            target("*.gradle.kts")
            ktlint()
        }
    }
}
