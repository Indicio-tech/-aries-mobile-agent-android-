# Aries Mobile Agent Android

## Installation
* Add Hyperledger Maven repo `maven { url "https://repo.sovrin.org/repository/maven-public" }`
* Import Aries Mobile Agent Android AAR

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

