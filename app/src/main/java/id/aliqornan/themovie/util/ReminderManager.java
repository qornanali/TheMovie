package id.aliqornan.themovie.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;

import id.aliqornan.themovie.data.ReminderPrefences;
import id.aliqornan.themovie.lib.Logger;

/**
 * Created by qornanali on 12/04/18.
 */

public class ReminderManager {

    public static final int DAILY = 100;
    public static final int RELEASE_TODAY = 101;
    public static final String REQUEST_CODE = "request_code";

    Context context;
    ReminderPrefences reminderPrefences;
    AlarmManager alarmManager;

    public ReminderManager(Context context) {
        this.context = context;
        reminderPrefences = new ReminderPrefences(context);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void turnOff(int code) {
        Intent intent = new Intent(context, BroadcastManager.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, code, intent, 0);
        alarmManager.cancel(pendingIntent);
        if (code == DAILY) {
            reminderPrefences.setDailySchedule(0);
        } else if (code == RELEASE_TODAY) {
            reminderPrefences.setReleaseTodaySchedule(0);
        }
    }

    public boolean isTurnOn(){
        return reminderPrefences.isReleaseTodayScheduled() && reminderPrefences.isDailyScheduled();
    }

    public void turnOn(int hour, int min, int sec, int code, Bundle bundle) {
        Intent intent = new Intent(context, BroadcastManager.class);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt(REQUEST_CODE, code);
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, code, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), hour, min, sec);
        alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        if (code == DAILY) {
            reminderPrefences.setDailySchedule(calendar.getTimeInMillis());
        } else if (code == RELEASE_TODAY) {
            reminderPrefences.setReleaseTodaySchedule(calendar.getTimeInMillis());
        }
    }
}
