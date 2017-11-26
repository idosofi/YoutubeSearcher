YoutubeSearcher Android app
===========================
<img align="center" height="256" src="https://i.imgur.com/8dR6mLc.png"/>
A simple Android app designed to to query YouTube for videos/playlists.

Installation
============
Under Android Studio terminal, run "gradlew assembleDebug".
This creates an APK named module_name-debug.apk in project_name/module_name/build/outputs/apk/. The file is already signed with the debug key and aligned with zipalign, so you can immediately install it on a device.

Or to build the APK and immediately install it on a running emulator or connected device, instead invoke installDebug: "gradlew installDebug"

Tests
=====
Under Android Studio terminal, run command "gradlew test connectedAndroidTest" to run unit/instrumented tests
