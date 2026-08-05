plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

import java.util.Properties

android {
    namespace = "com.nothicalnonsence.mealnomeal"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.nothicalnonsence.mealnomeal"
        minSdk = 21
        targetSdk = 36
        versionCode = 22
        versionName = "1.0.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            debugSymbolLevel = "full"
        }
    }

    signingConfigs {
        create("release") {
            val props = Properties()
            val propsFile = rootProject.file("keystore.properties")
            if (propsFile.exists()) {
                propsFile.inputStream().use { props.load(it) }
            }

            val storeFilePath = (props.getProperty("storeFile") ?: System.getenv("MEALNOMEAL_STORE_FILE"))
            val storePasswordValue = (props.getProperty("storePassword") ?: System.getenv("MEALNOMEAL_STORE_PASSWORD"))
            val keyAliasValue = (props.getProperty("keyAlias") ?: System.getenv("MEALNOMEAL_KEY_ALIAS"))
            val keyPasswordValue = (props.getProperty("keyPassword") ?: System.getenv("MEALNOMEAL_KEY_PASSWORD"))

            if (storeFilePath != null && storePasswordValue != null && keyAliasValue != null && keyPasswordValue != null) {
                storeFile = file(storeFilePath)
                storePassword = storePasswordValue
                keyAlias = keyAliasValue
                keyPassword = keyPasswordValue
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = false // Set to true if you have many resources
            // signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    packaging {
        resources {
            excludes += setOf("META-INF/*")
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")

    implementation("com.google.android.gms:play-services-ads:23.1.0")
}