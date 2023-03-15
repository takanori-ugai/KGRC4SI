import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import io.gitlab.arturbosch.detekt.Detekt
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    kotlin("jvm") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.10"
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
    jacoco
    id("org.jetbrains.dokka") version "1.8.10"
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
//    id("com.github.sherter.google-java-format") version "0.9"
//    kotlin("jupyter.api") version "0.10.1-8"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("com.github.jk1.dependency-license-report") version "2.1"
    id("com.github.spotbugs") version "5.0.13"
    id("com.diffplug.spotless") version "6.12.0"
    application
}

group = "com.fujitsu"
version = "0.3"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.+")
    implementation("io.github.microutils:kotlin-logging:3.0.4")
//    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.+")
    implementation("ch.qos.logback:logback-classic:1.+")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    implementation(kotlin("stdlib-jdk8"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    compileJava {
        options.encoding = "UTF-8"
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }

    compileTestJava {
        options.encoding = "UTF-8"
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }

    test {
        useJUnitPlatform()
        finalizedBy(jacocoTestReport) // report is always generated after tests run
    }

    withType<Detekt>().configureEach {
        // Target version of the generated JVM bytecode. It is used for type resolution.
        jvmTarget = "1.8"
        reports {
            // observe findings in your browser with structure and code snippets
            html.required.set(true)
            // checkstyle like format mainly for integrations like Jenkins
            xml.required.set(true)
            // similar to the console output, contains issue signature to manually edit baseline files
            txt.required.set(true)
            // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations
            // with Github Code Scanning
            sarif.required.set(true)
        }
    }

    jacocoTestReport {
        dependsOn(test) // tests are required to run before generating the report
    }

    withType<ShadowJar> {
        manifest {
            attributes["Main-Class"] = "com.fujitsu.labs.virtualhome.MainKt"
        }
    }
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    reporters {
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.JSON)
        reporter(ReporterType.HTML)
    }
    filter {
        exclude("**/style-violations.kt")
    }
}

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
    // point to your custom config defining rules to run, overwriting default behavior
    config = files("$projectDir/config/detekt.yml")
//    baseline = file("$projectDir/config/baseline.xml") // a way of suppressing issues before introducing detekt
}

spotbugs {
    ignoreFailures.set(true)
}

jacoco {
    toolVersion = "0.8.8"
//    reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}

spotless {
    java {
        target("src/*/java/**/*.java")
        targetExclude("src/jte-classes/**/*.java", "jte-classes/**/*.java")
        // Use the default importOrder configuration
        importOrder()
        removeUnusedImports()

        // Choose one of these formatters.
        googleJavaFormat("1.15.0")
        formatAnnotations()
    }
}

application {
    mainClass.set("jp.live.ugai.tugraph.Test6Kt")
}
