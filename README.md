# godot-android-notifications

Godot Android plugin to send Android notifications

- reference implementation : <https://github.com/godotengine/godot-google-play-billing>

- docs from godot: <https://docs.godotengine.org/en/stable/tutorials/platform/android/android_plugin.html?highlight=notifications#introduction>

## Usage & Docs

## Compiling

Prerequisites:

- Android SDK (platform version 30)
- the Godot Android library (`godot-lib.***.release.aar`) for your version of Godot from the [downloads page](https://godotengine.org/download).

Steps to build:

1. Clone this Git repository
2. Put `godot-lib.***.release.aar` in `./godot-android-notifications/libs/`
3. Run `./gradlew build` in the cloned repository

If the build succeeds, you can find the resulting `.aar` files in `./godot-android-notifications/build/outputs/aar/`.
