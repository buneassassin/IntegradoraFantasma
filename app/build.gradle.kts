plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.primerp.integradora"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.primerp.integradora"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation ("androidx.transition:transition:1.4.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.activity)
    implementation(libs.play.services.base)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.5.3") // o la última versión
    implementation ("androidx.navigation:navigation-ui-ktx:2.5.3") // o la última versión
    implementation ("com.google.android.material:material:1.7.0")
    implementation("com.airbnb.android:lottie:5.0.3")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    testImplementation ("org.mockito:mockito-core:4.11.0")

    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")






}