// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.android.library") version "8.0.0" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.10" apply false
}

val clean by tasks.registering(Delete::class) {
    delete(rootProject.buildDir)
}