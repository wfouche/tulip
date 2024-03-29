/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 */

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

plugins {

    // Gradle owasp plugin
    // $ ./gradlew dependencyCheckAnalyze --info
    id("org.owasp.dependencycheck") version "9.0.10"

    // Gradle versions plugin
    // $ ./gradlew dependencyUpdates
    id("com.github.ben-manes.versions") version "0.51.0"

    // Add support for AsciidoctorJ
    id("org.asciidoctor.jvm.convert") version "4.0.2"

    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.9.23"

    // detekt - A static code analyzer for Kotlin
    // $ ./gradlew detekt
    // id("io.gitlab.arturbosch.detekt") version("1.23.5")

    // apply the Pkl plugin - https://pkl-lang.org/index.html
    id("org.pkl-lang") version("0.25.2")
    // if the `idea` plugin is applied, the Pkl plugin makes generated code visible to IntelliJ IDEA
    idea

    // Apply the application plugin to add support for building a CLI application.
    application
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    mavenCentral()
}

dependencies {
    // Include JAR files in libs folder
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    //implementation("org.jetbrains.kotlin:kotlin-stdlib")

    implementation(platform("org.http4k:http4k-bom:5.14.1.0"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-client-apache")
    implementation("org.http4k:http4k-server-undertow")
    implementation("org.http4k:http4k-serverless-lambda")

    // okhttp
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.12")

    // JSON Kotlin Parser.
    implementation("com.beust:klaxon:5.6")

    // Conversant disruptor - https://github.com/conversant/disruptor
    implementation("com.conversantmedia:disruptor:1.2.21")

    // LMAX disruptor - https://github.com/LMAX-Exchange/disruptor
    implementation("com.lmax:disruptor:4.0.0")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}


application {
    // Default JVM settings

    // G1GC (default), Java 11
    // applicationDefaultJvmArgs = listOf("-server", "-Xmx4096m", "-XX:+UseG1GC", "-XX:+ParallelRefProcEnabled", "-XX:+UseDynamicNumberOfGCThreads")
    // applicationDefaultJvmArgs = listOf("-server", "-Xms4096m", "-Xmx4096m", "-XX:+UseG1GC", "-XX:+ParallelRefProcEnabled", "-XX:+UseDynamicNumberOfGCThreads", "-javaagent:./runtime/glowroot/glowroot.jar")

    // Java 21
    // https://docs.oracle.com/en/java/javase/21/gctuning/z-garbage-collector.html
    applicationDefaultJvmArgs = listOf("-server", "-Xmx4096m", "-XX:+UseZGC", "-XX:+ZGenerational", "-XX:+UseDynamicNumberOfGCThreads")

    // applicationDefaultJvmArgs = listOf("-server", "-Xmx4096m", "-Xmx4096m", "-XX:+UseShenandoahGC")
    // applicationDefaultJvmArgs = listOf("-server", "-Xmx4096m", "-Xmx4096m", "-XX:+UseParallelGC")

    // Java 8
    //, "-XX:+UseParallelGC")

    // Define the main class for the application
    mainClass.set("LoadTest1")
}

dependencyCheck {
    analyzers.assemblyEnabled = false
    nvd.apiKey = "ef1927c1-51b6-4b5c-a1b9-fb10b5e43725"
    nvd.delay = 2000
}
