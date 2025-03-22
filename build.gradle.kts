plugins {
    id("java")
}

group = "dev.mathops.math"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(files("../mathops_commons/out/libs/mathops_commons.jar"))
    implementation(files("../mathops_commons/out/libs/mathops_text.jar"))
}

tasks.test {
    useJUnitPlatform()
}