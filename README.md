This plugin worked for Godot 3, using the old plugin API and is now archived.
For Godot 4 use this one: <https://github.com/cengiz-pz/godot-android-notification-scheduler-plugin>

# godot-android-notifications

An Android plugin for Godot 3 to send Android notifications

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

You should get something like:

```
android/plugins
├── GodotAndroidNotifications.1.0.0.release.aar
├── GodotAndroidNotifications.gdap
```

Now export your Godot project using the [custom build](https://docs.godotengine.org/en/stable/tutorials/export/android_custom_build.html)

## API

You have [2 functions](https://github.com/uralys/godot-android-notifications/blob/master/godot-android-notifications/src/main/java/org/godotengine/godot/plugin/androidnotifications/Scheduler.java#L111) available on godot side

## schedule

```
 var notifier = Engine.get_singleton('GodotAndroidNotifications')
 
 var notificationId = 'whatever, I use time stuff to get unique ids'

 notifier.schedule(
        'the title',
        'the message',
        secsToNextNotification,
        notificationId
)
```

## cancel

You can store your `notificationId` anywhere, and use it to cancel the scheduling:

```
notifier.cancel(notificationId)
```


## Development

- inspiration from: <https://github.com/DrMoriarty/godot-local-notification/blob/master/android-plugin/>
- reference plugin implementation : <https://github.com/godotengine/godot-google-play-billing>
- docs to create a plugin from godot: <https://docs.godotengine.org/en/stable/tutorials/platform/android/android_plugin.html?highlight=notifications#introduction>
