plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("native.cocoapods")
    id("co.touchlab.faktory.kmmbridge")
    `maven-publish`
}

version = "1.0.0"

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.logging)
                implementation(libs.napier)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.android)
            }
        }
    }
    cocoapods {
        version = "1.0.1"
        summary = "Some description for a Kotlin/Native module"
        homepage = "https://github.com/asm0dey/ios-app-kotlin-lib"
        source = "{ :git => 'https://github.com/asm0dey/ios-app-kotlin-lib-spec.git' }"
        ios.deploymentTarget = "11.0"
        framework {
            baseName = "shared"
            license = "{ :type => 'MIT' }"
        }
    }
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 33
    defaultConfig {
        minSdk = 28
    }
}
kmmbridge {
    mavenPublishArtifacts("asm0dey/ios-app-kotlin-lib")
    manualVersions()
    cocoapods("https://github.com/asm0dey/ios-app-kotlin-lib-spec.git")
}

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/asm0dey/ios-app-kotlin-lib")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}


//addGithubPackagesRepository()
