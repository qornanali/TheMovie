package id.aliqornan.themovie.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;

/**
 * Created by qornanali on 10/04/18.
 */

public class BroadcastManager extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {

        }
    }

//    public void startReminder() {
//        FirebaseJobDispatcher mDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
//        Job myJob = mDispatcher.newJobBuilder()
//                // kelas service yang akan dipanggil
//                .setService(MyJobService.class)
//                // unique tag untuk identifikasi job
//                .setTag(DISPATCHER_TAG)
//                // one-off job
//                // true job tersebut akan diulang, dan false job tersebut tidak diulang
//                .setRecurring(true)
//                // until_next_boot berarti hanya sampai next boot
//                // forever berarti akan berjalan meskipun sudah reboot
//                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
//                // waktu trigger 0 sampai 60 detik
//                .setTrigger(Trigger.executionWindow(0, 60))
//                // overwrite job dengan tag sama
//                .setReplaceCurrent(true)
//                // set waktu kapan akan dijalankan lagi jika gagal
//                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
//                // set kondisi dari device
//                .setConstraints(
//                        // hanya berjalan saat ada koneksi yang unmetered (contoh Wifi)
//                        Constraint.ON_UNMETERED_NETWORK,
//                        // hanya berjalan ketika device di charge
//                        Constraint.DEVICE_CHARGING
//x
//                        // berjalan saat ada koneksi internet
//                        //Constraint.ON_ANY_NETWORK
//
//                        // berjalan saat device dalam kondisi idle
//                        //Constraint.DEVICE_IDLE
//                )
//                .setExtras(myExtrasBundle)
//                .build();
//
//        mDispatcher.mustSchedule(myJob);
//    }
//
//    public void cancelDispatcher() {
//        mDispatcher.cancel(DISPATCHER_TAG);
//    }
}
