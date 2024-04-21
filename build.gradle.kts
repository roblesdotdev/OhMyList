// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.kotlinter) apply false
    alias(libs.plugins.detekt) apply false
}

apply(from = "buildScripts/git-hooks.gradle")

subprojects {
    apply(
        plugin = rootProject.libs.plugins.kotlinter.get().pluginId
    )
    apply(from = "../buildScripts/detekt.gradle")
}