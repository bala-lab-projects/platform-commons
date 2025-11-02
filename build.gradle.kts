plugins {
    kotlin("jvm") version "2.2.20" apply false
    kotlin("plugin.spring") version "2.2.20" apply false
    id("io.github.platform.java-conventions") version "1.0.0" apply false
    id("io.github.platform.spring-conventions") version "1.0.0" apply false
    id("io.github.platform.spring-test-conventions") version "1.0.0" apply false
}

allprojects {
    group = "io.github.platform"
    version = "1.0.0"

    repositories {
        mavenCentral()
        mavenLocal()
    }
}

subprojects {
    afterEvaluate {
        tasks.withType<JavaCompile> {
            options.encoding = "UTF-8"
        }

        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            compilerOptions {
                freeCompilerArgs.set(listOf("-Xjsr305=strict"))
                jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
            }
        }
    }
}
