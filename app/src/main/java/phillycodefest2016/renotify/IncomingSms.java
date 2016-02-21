package phillycodefest2016.renotify;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class IncomingSms extends BroadcastReceiver {
    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    private String senderNum;
    private String message;
    private final String PACKAGE_NAME = "phillycodefest2016.renotify";


    public void onReceive(Context context, Intent intent) {
        Log.i("SmsReceiver", "senderNum: ");
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    senderNum = phoneNumber;
                    message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);


                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "senderNum: " + senderNum + ", message: " + message, duration);
                    toast.show();


                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.setData(Uri.parse("sms:" + senderNum));
                    PendingIntent piReply = PendingIntent.getActivity(context, 1, smsIntent, 0);


                    Intent snoozeIntent = new Intent(context, BuildUi.class);
                    snoozeIntent.putExtra("passing data", senderNum);
                    snoozeIntent.putExtra("passing second data", message);
                    PendingIntent piSnooze = PendingIntent.getActivity(context, 0, snoozeIntent, 0);


                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(context)
                                    .setSmallIcon(android.R.drawable.ic_popup_reminder)
                                    .setContentTitle(senderNum)
                                    .setContentText(message)
                                    .addAction(android.R.drawable.ic_media_play,
                                            "Reply", piReply)
                                    .addAction(android.R.drawable.ic_lock_idle_alarm,
                                            "Snooze", piSnooze);
                    ;


                    int mNotificationId = 001;
                    NotificationManager mNotify = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                    mNotify.notify(mNotificationId, mBuilder.build());


                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }


}