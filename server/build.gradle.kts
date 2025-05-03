plugins {
    id("java")
}

group = "ru.mihozhereb"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(project(":common"))
    implementation("org.postgresql:postgresql:42.7.5")
}

dependencies {
    implementation("commons-codec:commons-codec:1.18.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().filter { it.exists() }.map { if (it.isDirectory) it else zipTree(it) })

    manifest {
        attributes(
            mapOf(
                "Class-Path" to configurations.runtimeClasspath.get().files.joinToString(" ") { it.name },
                "Main-Class" to "ru.mihozhereb.Main"
            )
        )
    }
}