# godot-android-notifications

Godot Android plugin to send Android notifications

This plugin implements <https://developer.android.com/guide/topics/ui/notifiers/notifications> for Godot Engine.

## Usage & Docs

## Icon

create a `icon_notification.png` within the android resources in your godot project:

```sh
android/build/res/mimap/icon_notification.png
```

## Compiling

Prerequisites:

- Android SDK (platform version 30)
- the Godot Android library (`godot-lib.***.release.aar`) for your version of Godot from the [downloads page](https://godotengine.org/download).

Steps to build:

1. Clone this Git repository
2. Put `godot-lib.***.release.aar` in `./godot-android-notifications/libs/`
3. Run `./gradlew build` in the cloned repository

If the build succeeds, you can find the resulting `.aar` files in `./godot-android-notifications/build/outputs/aar/`.

Copy this `.aar` and `GodotAndroidNotifications.gdap` in your godot project, into:

```sh
android/plugins/.
```

Now export your Godot project using the [custom build](https://docs.godotengine.org/en/stable/tutorials/export/android_custom_build.html)

## Development

- inspiration from: <https://github.com/DrMoriarty/godot-local-notification/blob/master/android-plugin/>
- reference plugin implementation : <https://github.com/godotengine/godot-google-play-billing>
- docs to create a plugin from godot: <https://docs.godotengine.org/en/stable/tutorials/platform/android/android_plugin.html?highlight=notifications#introduction>
