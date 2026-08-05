plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

import java.util.Properties

// Correctly locate the root project directory, which is one level up
val trueRoot = rootProject.projectDir.parentFile

// Load keystore properties from the true root directory
val keystorePropertiesFile = File(trueRoot, "keystore.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(keystorePropertiesFile.inputStream())
}


android {
    namespace = "com.nothicalnonsence.mealnomeal"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.nothicalnonsence.mealnomeal"
        minSdk = 21
        targetSdk = 36
        versionCode = 23
        versionName = "1.0.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            debugSymbolLevel = "full"
        }
    }

    signingConfigs {
        create("release") {
            // Securely load signing information from the true root project directory
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            storeFile = if (keystoreProperties.getProperty("storeFile") != null) File(trueRoot, keystoreProperties.getProperty("storeFile")) else null
            storePassword = keystoreProperties.getProperty("storePassword")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = false // Set to true if you have many resources
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // TEMPORARY: Using test ID for testers.
            // TODO: Before final public release, uncomment your real ID and delete the test ID line.
            // manifestPlaceholders["admobAppId"] = "pub-9711678354769533"
            manifestPlaceholders["admobAppId"] = "ca-app-pub-3940256099942544~3347511713"
        }
        debug {
            isMinifyEnabled = false
            manifestPlaceholders["admobAppId"] = "ca-app-pub-3940256099942544~3347511713"
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