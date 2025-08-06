plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.moros"
version = "1.7.4-PRE-RELEASE-1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://jitpack.io")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.2-R0.1-SNAPSHOT")
    compileOnly(files("libs/ProjectKorra-1.12.1-PRE-RELEASE-1.jar"))

    implementation("org.bstats:bstats-bukkit:3.1.0")
}

tasks {
    shadowJar {
        archiveClassifier.set("") // No "-all" suffix
        relocate("org.bstats", "me.moros.hyperion.bstats")
        minimize()
    }

    build {
        dependsOn(shadowJar)
    }

    withType<JavaCompile> {
        options.compilerArgs.addAll(listOf("-Xlint:unchecked", "-Xlint:deprecation"))
        options.encoding = "UTF-8"
    }

    withType<AbstractArchiveTask> {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }

    named<Copy>("processResources") {
        expand("pluginVersion" to project.version)
    }
}
