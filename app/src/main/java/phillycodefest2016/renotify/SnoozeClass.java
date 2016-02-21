package phillycodefest2016.renotify;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by paulkim on 2/21/16.
 */
public class SnoozeClass extends BroadcastReceiver {

    public static final String ACTION = "ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {


        String key = intent.getStringExtra("passing data");
        String key1 = intent.getStringExtra("passing second data");

        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.setData(Uri.parse("sms:" + key));
        PendingIntent piReply = PendingIntent.getActivity(context, 1, smsIntent, 0);


        Intent snoozeIntent = new Intent(context, BuildUi.class);
        snoozeIntent.putExtra("passing data", key);
        snoozeIntent.putExtra("passing second data", key1);
        PendingIntent piSnooze = PendingIntent.getActivity(context, 0, snoozeIntent, 0);







        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(android.R.drawable.ic_popup_reminder)
                        .setContentTitle(key)
                        .setContentText(key1)
                        .addAction(android.R.drawable.ic_media_play,
                                "Reply", piReply)
                        .addAction(android.R.drawable.ic_lock_idle_alarm,
                                "Snooze", piSnooze);
        ;


        int mNotificationId = 001;
        NotificationManager mNotify = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotify.notify(mNotificationId, mBuilder.build());





    }


}
