plugins {
    id("com.diffplug.spotless") version "6.23.3"
    kotlin("jvm") version "1.9.21"
    application
}

group = "com.kleinreveche"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.slf4j:slf4j-simple:1.7.36")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("com.kleinreveche.adventofcode.AdventOfCodeKt")
}

sourceSets {
    getByName("main") {
        resources.srcDirs("../inputs")
    }
}

subprojects {
    apply(plugin = "com.diffplug.spotless")
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("build/**/*.kt")

            ktlint()
            trimTrailingWhitespace()
            indentWithSpaces()
            endWithNewline()
        }

        kotlinGradle {
            target("*.gradle.kts")
            ktlint()
        }
    }
}