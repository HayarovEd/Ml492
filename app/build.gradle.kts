plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id ("com.huawei.agconnect")
}

android {
    namespace = "org.ph.expert.loan.ending.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "org.ph.expert.loan.ending.app"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("ID492.keystore")
            keyAlias = "org.ph.expert.loan.ending.app"
            storePassword = "mypass"
            keyPassword = "mypass"
        }
        create("release") {
            keyAlias = "org.ph.expert.loan.ending.app"
            keyPassword = "mypass"
            storeFile = file("ID492.keystore")
            storePassword = "mypass"
            enableV2Signing = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.hilt.android)
    ksp (libs.google.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //Yandex
    implementation(libs.analytics)
    implementation(libs.mobmetricalib)


    //MyTracker
    implementation(libs.mytracker.sdk)

    //Appsflyer
    implementation(libs.af.android.sdk)

    //HMS
    implementation(libs.push)
    implementation(libs.hmscoreinstaller)
    implementation(libs.ads.identifier)
    implementation(libs.ads.installreferrer)
    implementation (libs.agconnect.core)
    implementation (libs.hwid)

    //retrofit
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Coil
    implementation(libs.coil.compose)

    // Icons
    implementation(libs.androidx.material.icons.extended)
}