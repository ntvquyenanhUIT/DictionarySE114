plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.dictionary"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dictionary"
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


}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.google.mlkit:translate:17.0.2")
    implementation ("com.google.mlkit:language-id:17.0.5")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.github.arimorty:floatingsearchview:2.1.1")
    implementation("com.github.zerobranch:SwipeLayout:1.3.1")
    implementation("androidx.activity:activity:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}