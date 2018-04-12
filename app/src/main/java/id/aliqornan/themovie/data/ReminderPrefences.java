package id.aliqornan.themovie.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by qornanali on 11/04/18.
 */

public class ReminderPrefences {

    public static final String RELEASE_TODAY = "release_today";
    public static final String DAILY = "daily";
    static String PREF_NAME = "themoviepref";

    Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public ReminderPrefences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void put(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    private void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    private String get(String key) {
        return sharedPreferences.getString(key, null);
    }

    public String getReleaseToday() {
        return get(RELEASE_TODAY);
    }

    public String getDailySchedule() {
        return get(DAILY);
    }

    public void setDailySchedule(long timestamp) {
        put(DAILY, timestamp == 0 ? null : String.valueOf(timestamp));
    }

    public void setReleaseTodaySchedule(long timestamp) {
        put(RELEASE_TODAY, timestamp == 0 ? null : String.valueOf(timestamp));
    }

    public boolean isReleaseTodayScheduled() {
        return get(RELEASE_TODAY) != null;
    }

    public boolean isDailyScheduled() {
        return get(DAILY) != null;
    }
}
