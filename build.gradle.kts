// Reconstructed build for LPX 3.9.1 (the hardened DJAR_SECURE build).
// The obfuscated jar shaded and relocated PacketEvents, Adventure (kyori) and Gson under it.ytnoos.lpx.lib.
//
// PacketEvents version: the original jar was built against a PRIVATE LPX fork of PacketEvents,
// "2.14.1-LPX+6aecc331c-SNAPSHOT" (see META-INF/packetevents_*_version.txt in the jar). That artifact is
// not on public Maven, so a clean build cannot reproduce the shipped bytes exactly without it. The public
// base version 2.14.1 is the closest substitute; swap in the private fork if you have it.

plugins {
    java
    id("com.gradleup.shadow") version "8.3.5"
}

group = "it.ytnoos"
version = "3.9.1"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-releases/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")
    compileOnly("io.netty:netty-all:4.1.72.Final")
    compileOnly("org.apache.logging.log4j:log4j-api:2.17.1")
    compileOnly("org.jetbrains:annotations:26.0.2")
    compileOnly("org.jspecify:jspecify:1.0.0")

    implementation("com.github.retrooper:packetevents-spigot:2.14.1")
    implementation("com.google.code.gson:gson:2.11.0")

    // WindowA and PayloadE reference CraftBukkit/NMS v1_8_R3 directly. libs/nms-stubs.jar holds
    // compile-only stubs of those types (sources in stubs/); a real 1.8.8 server jar also works.
    compileOnly(fileTree("libs") { include("*.jar") })
}

sourceSets {
    main {
        java.srcDir("src/main/java")
        resources.srcDir("src/main/resources")
    }
}

tasks.shadowJar {
    archiveClassifier.set("")
    // The original jar crossed these over: the PacketEvents API (com.github) sits under lib/io/github and the
    // Spigot implementation (io.github) under lib/com/github.
    relocate("com.github.retrooper", "it.ytnoos.lpx.lib.io.github.retrooper")
    relocate("io.github.retrooper", "it.ytnoos.lpx.lib.com.github.retrooper")
    relocate("net.kyori", "it.ytnoos.lpx.lib.kyori")
    relocate("com.google.gson", "it.ytnoos.lpx.lib.gson")
}

tasks.build { dependsOn(tasks.shadowJar) }
