# Aries Mobile Agent Android

## Installation
* Add Hyperledger Maven repo `maven { url "https://repo.sovrin.org/repository/maven-public" }` in the top level build.gradle
* Set minSdkVersion to 21 in app level build.gradle
* Set ndk version to `20.1.5948944` in app level build.gradle
* Import Aries Mobile Agent Android AAR
* Request external files permission in Android Manifest
* In MainActivity.onCreate() make sure that you are running `Os.setenv("EXTERNAL_STORAGE", getExternalFilesDir(null).getAbsolutePath(), true);` before you use the module.

## Development
### Add module to your project
* Clone this repo outside application repository
* Open Project Structure in Android Studio
* In the Modules tab hit the + button to add a new module
* Select import from the bottom left and then link it to the newly cloned repo
* Select finish for it to be added as a module in your project
### Add module as a dependency to your app
* In the Dependencies tab select your app
* Hit the + button to add a new dependency
* Select `3 Module Dependency`
* Check the box for `aries-mobile-agent-android`
* Hit OK and the module will now be linked


###libc++_shared.so
We manually copied each `libc++_shared.so` from ndk `20.1.5948944` to `src/main/jniLibs`
