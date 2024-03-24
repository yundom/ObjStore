plugins {
    kotlin("jvm") version "1.9.23"
    application
}

group = "com.github.yundom"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

application {
    mainClass.set("com.github.yundom.MainKt")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}