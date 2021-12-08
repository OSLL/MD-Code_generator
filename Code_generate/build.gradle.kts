val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val slf4j_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.serialization") version "1.6.0"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("com.example.ApplicationKt")
}

repositories {
    mavenCentral()
}

kotlin.sourceSets["main"].kotlin.srcDir("src")
sourceSets["main"].resources.srcDir("resources")

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")

    implementation("org.litote.kmongo:kmongo-coroutine-serialization:4.4.0")
    implementation("org.litote.kmongo:kmongo-id:4.4.0")

    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-host-common:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-css-jvm:1.0.0-pre.272-kotlin-1.6.0")
    implementation("io.ktor:ktor-html-builder:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("io.ktor:ktor-server-jetty:$ktor_version")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-apache:$ktor_version")
    implementation("io.ktor:ktor-client-serialization:$ktor_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")

    // logger
    implementation("org.slf4j:slf4j-api:$slf4j_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("ch.qos.logback.contrib:logback-json-classic:0.1.5")
    implementation("ch.qos.logback.contrib:logback-jackson:0.1.5")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "11"
}