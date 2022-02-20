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

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/** --------------------------------------------------------------------------*/

public class Scheduler extends GodotPlugin {
  private String CHANNEL_ID = "godot-notifications";

  public Scheduler(Godot godot) {
    super(godot);
  }

  /** ----------------------------------------------------------------------- */

  public void schedule(String title, String message, int delaySeconds, int notificationId) {
    if(delaySeconds <= 0) return;
    PendingIntent broadcaster = createPendingIntent(title, message, notificationId);

    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.add(Calendar.SECOND, delaySeconds);

    AlarmManager alarm = (AlarmManager)getActivity().getSystemService(getActivity().ALARM_SERVICE);
    if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
      alarm.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcaster);
    } else {
      alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcaster);
    }
  }

  /** ----------------------------------------------------------------------- */

  public void cancel(int notificationId) {
    AlarmManager alarm = (AlarmManager)getActivity().getSystemService(getActivity().ALARM_SERVICE);
    PendingIntent scheduledBroadcast = createPendingIntent("", "", notificationId);
    alarm.cancel(scheduledBroadcast);
  }

  /** ----------------------------------------------------------------------- */

  private PendingIntent createPendingIntent(String title, String message, int notificationId) {
    Intent intent = new Intent(getActivity().getApplicationContext(), Notifier.class);
    intent.putExtra("notificationId", notificationId);
    intent.putExtra("message", message);
    intent.putExtra("title", title);

    PendingIntent broadcaster = PendingIntent.getBroadcast(
      getActivity(),
      notificationId,
      intent,
      PendingIntent.FLAG_UPDATE_CURRENT
    );

    return broadcaster;
  }

  /** ----------------------------------------------------------------------- */

  @NonNull
  @Override
  public String getPluginName() {
    return "GodotAndroidNotifications";
  }

  @NonNull
  @Override
  public List<String> getPluginMethods() {
    return Arrays.asList("schedule", "cancel");
  }

  @NonNull
  @Override
  public Set<SignalInfo> getPluginSignals() {
    Set<SignalInfo> signals = new ArraySet<>();

    return signals;
  }
}
