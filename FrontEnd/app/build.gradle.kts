plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.tech_mart_application"
    compileSdk = 35


    defaultConfig {
        applicationId = "com.tech_mart_application"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        //noinspection DataBindingWithoutKapt
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.4")
    implementation("com.google.firebase:firebase-auth:23.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // Architectural Components
    val lifecycle_version = "2.8.7"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-process:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("android.arch.persistence.room:compiler:1.1.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.6.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Navigation Components
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.4")


    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //initUI
    implementation("com.intuit.sdp:sdp-android:1.1.1")

    //Circle Indicator
    implementation("me.relex:circleindicator:2.1.6")

    //Hash Password
    implementation("com.ToxicBakery.library.bcrypt:bcrypt:1.0.9")


    //Phone Number
    implementation("io.michaelrocks:libphonenumber-android:8.13.35")

    //Circle ImageView
    implementation ("de.hdodenhof:circleimageview:3.1.0")


    //Momo
    implementation ("com.github.momo-wallet:mobile-sdk:1.0.7")

    //Gemini
    implementation("com.google.ai.client.generativeai:generativeai:0.9.0")

    ///VnPay
//    implementation ("com.google.code.gson:gson:2.11.0")
//    implementation ("com.squareup.okhttp3', name: 'okhttp', version: '3.14.1'")
//    implementation ("name: 'merchant-1.0.25', ext: 'aar'")
}