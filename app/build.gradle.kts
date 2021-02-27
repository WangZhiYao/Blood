plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "me.zhiyao.blood"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 11
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    sourceSets {
        map { it.java.srcDir("src/${it.name}/kotlin") }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.6")

    implementation(kotlin("stdlib-jdk8:1.4.31"))

    // AndroidX
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.3")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.32-alpha")
    kapt("com.google.dagger:hilt-android-compiler:2.32-alpha")

    // Room
    implementation("androidx.room:room-ktx:2.3.0-beta02")
    kapt("androidx.room:room-compiler:2.3.0-beta02")

    // Paging3
    implementation("androidx.paging:paging-runtime-ktx:3.0.0-beta01")

    // RxBinding
    implementation("com.jakewharton.rxbinding4:rxbinding-material:4.0.0")
}