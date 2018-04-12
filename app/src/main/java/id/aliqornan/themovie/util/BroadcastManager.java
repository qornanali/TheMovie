package id.aliqornan.themovie.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import id.aliqornan.themovie.R;
import id.aliqornan.themovie.data.RetrofitClient;
import id.aliqornan.themovie.data.ServiceInterface;
import id.aliqornan.themovie.feature.DetailMovieActivity;
import id.aliqornan.themovie.feature.MainActivity;
import id.aliqornan.themovie.lib.DateFormatter;
import id.aliqornan.themovie.lib.Logger;
import id.aliqornan.themovie.model.Movie;
import id.aliqornan.themovie.model.Response;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by qornanali on 10/04/18.
 */

public class BroadcastManager extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.getInt(ReminderManager.REQUEST_CODE) == ReminderManager.DAILY) {
                showAlarmNotification(context, context.getString(R.string.app_name),
                        context.getString(R.string.open_app), 1, null, MainActivity.class);
            } else if (bundle.getInt(ReminderManager.REQUEST_CODE) == ReminderManager.RELEASE_TODAY) {
                getReleaseToday(context);
            }
        }
    }

    private void getReleaseToday(final Context context) {
        ServiceInterface serviceInterface = RetrofitClient.init(context).create(ServiceInterface.class);
        Call<Response<List<Movie>>> call = serviceInterface.getNowPlayingService();
        call.enqueue(new Callback<Response<List<Movie>>>() {
            @Override
            public void onResponse(Call<Response<List<Movie>>> call, retrofit2.Response<Response<List<Movie>>> response) {
                Calendar now = Calendar.getInstance();
                for (int i = 0; i < response.body().getResults().size(); i++) {
                    Movie item = response.body().getResults().get(i);
                    try {
                        Calendar releaseDate = DateFormatter.format("yyyy-MM-dd").getCalendar(item.getReleaseDate());
                        if (now.get(Calendar.YEAR) == releaseDate.get(Calendar.YEAR) &&
                                now.get(Calendar.MONTH) == releaseDate.get(Calendar.MONTH) &&
                                now.get(Calendar.DAY_OF_MONTH) == releaseDate.get(Calendar.DAY_OF_MONTH)) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("movie", item);
                            showAlarmNotification(context, item.getTitle(),
                                    String.format(context.getString(R.string.notif_release_today),
                                            item.getTitle()), i + 2, bundle, DetailMovieActivity.class);
                        }
                    } catch (Exception e) {
                        Logger.log(Log.ERROR, e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Response<List<Movie>>> call, Throwable t) {
                Logger.log(Log.ERROR, t.getMessage());
            }
        });
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId, Bundle bundle, Class target) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_local_movies)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);
        Intent i = new Intent(context, target);
        if (bundle == null) {
            bundle = new Bundle();
        }
        i.putExtras(bundle);
        PendingIntent contentIntent = PendingIntent.getActivity(context, notifId, i, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        notificationManagerCompat.notify(notifId, builder.build());
    }

}
