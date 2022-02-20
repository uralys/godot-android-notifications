/*************************************************************************/
/*                       This file is part of:                           */
/*                           GODOT ENGINE                                */
/*                      https://godotengine.org                          */
/*************************************************************************/
/* Copyright (c) 2007-2020 Juan Linietsky, Ariel Manzur.                 */
/* Copyright (c) 2014-2020 Godot Engine contributors (cf. AUTHORS.md).   */
/*                                                                       */
/* Permission is hereby granted, free of charge, to any person obtaining */
/* a copy of this software and associated documentation files (the       */
/* "Software"), to deal in the Software without restriction, including   */
/* without limitation the rights to use, copy, modify, merge, publish,   */
/* distribute, sublicense, and/or sell copies of the Software, and to    */
/* permit persons to whom the Software is furnished to do so, subject to */
/* the following conditions:                                             */
/*                                                                       */
/* The above copyright notice and this permission notice shall be        */
/* included in all copies or substantial portions of the Software.       */
/*                                                                       */
/* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,       */
/* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF    */
/* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.*/
/* IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY  */
/* CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,  */
/* TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE     */
/* SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.                */
/*************************************************************************/

package org.godotengine.godot.plugin.androidnotifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/** --------------------------------------------------------------------------*/

public class Notifier extends BroadcastReceiver {
  private static final String TAG = Notifier.class.getSimpleName();
  public static final String CHANNEL_ID = "godot-notifications";
  private NotificationManagerCompat notifier;
  private Class godotAppClass = null;

  public Notifier(){
    try {
      godotAppClass = Class.forName("com.godot.game.GodotApp");
    } catch (ClassNotFoundException e) {
      Log.e(TAG, "could not find com.godot.game.GodotApp");
    }
  }

  /** ----------------------------------------------------------------------- */

  @Override
  public void onReceive(Context context, Intent intent) {
    notifier = NotificationManagerCompat.from(context);

    int notificationId = intent.getIntExtra("notificationId", 0);
    String message = intent.getStringExtra("message");
    String title = intent.getStringExtra("title");
    Log.i(TAG, "Receive notification: " + message);

    createNotificationChannel(context);
    notify(context, notificationId, title, message);
  }

  /** ----------------------------------------------------------------------- */

  private void createNotificationChannel(Context context) {
    // https://developer.android.com/training/notify-user/channels
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      int channelName = context.getResources().getIdentifier("channel_name", "string", context.getPackageName());
      int channelDescription = context.getResources().getIdentifier("channel_description", "string", context.getPackageName());

      CharSequence name = context.getString(channelName);
      String description = context.getString(channelDescription);
      int importance = NotificationManager.IMPORTANCE_DEFAULT;

      NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
      channel.setDescription(description);

      // Register the channel with the system; you can't change the importance
      // or other notification behaviors after this
      NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
      notificationManager.createNotificationChannel(channel);
    }
  }

  /** ----------------------------------------------------------------------- */

  public void notify(Context context, int notificationId, String title, String message) {
    Intent intent = new Intent(context, godotAppClass);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

    int icon = context.getResources().getIdentifier("icon_notification", "mipmap", context.getPackageName());

    NotificationCompat.Builder notification = new NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(icon)
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true);

    notifier.notify(notificationId, notification.build());
  }
}
