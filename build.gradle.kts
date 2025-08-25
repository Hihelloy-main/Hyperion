plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.moros"
version = "1.7.5-PRE-RELEASE-2"

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
    // Provided by server
    compileOnly("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:26.0.2")
    compileOnly("com.projectkorra:projectkorra:1.12.0")

    // Included in plugin jar
    implementation("org.bstats:bstats-bukkit:3.1.0")
    implementation("com.cjcrafter:foliascheduler:0.7.2")
}

tasks {
    shadowJar {
        archiveClassifier.set("")

        // Relocate bStats to avoid plugin conflicts
        relocate("org.bstats", "me.moros.hyperion.bstats")

        // Relocate FoliaScheduler to avoid classpath issues
        relocate("com.cjcrafter.foliascheduler", "me.moros.hyperion.foliascheduler")

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
