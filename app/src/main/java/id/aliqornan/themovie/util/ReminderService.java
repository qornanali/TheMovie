package id.aliqornan.themovie.util;


import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.util.List;

import id.aliqornan.themovie.R;
import id.aliqornan.themovie.data.RetrofitClient;
import id.aliqornan.themovie.data.ServiceInterface;
import id.aliqornan.themovie.model.Movie;
import id.aliqornan.themovie.model.Response;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by qornanali on 10/04/18.
 */

public class ReminderService extends JobService {

    public static final String TAG = ReminderService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters job) {
        getReleaseToday(job);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

    private void getReleaseToday(final JobParameters job) {
        ServiceInterface serviceInterface = RetrofitClient.init(this).create(ServiceInterface.class);
        Call<Response<List<Movie>>> callMovies = serviceInterface.getNowPlayingService();
        callMovies.enqueue(new Callback<Response<List<Movie>>>() {

            @Override
            public void onResponse(Call<Response<List<Movie>>> call, retrofit2.Response<Response<List<Movie>>> response) {

                jobFinished(job, false);
            }

            @Override
            public void onFailure(Call<Response<List<Movie>>> call, Throwable t) {
                jobFinished(job, true);
            }
        });
    }

    private void showNotification(Context context, String title, String message, int notifId) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_stat_local_movies)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }
}
