plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
}

group = "Reptile"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.6")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("org.json:json:20230227")
    implementation("com.theokanning.openai-gpt3-java:client:0.12.0")
}
