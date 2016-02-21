package phillycodefest2016.renotify;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by paulkim on 2/21/16.
 */
public class BuildUi extends ActionBarActivity{
    private Spinner spinner;
    String[] test;
    private String ACTION_TAG = "android.appwidget.action.NOTIF_UPDATE";
    String senderAddress, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        test = new String[] {"1", "30", "45", "60", "90", "120"};
        senderAddress = getIntent().getStringExtra("passing data");
        message = getIntent().getStringExtra("passing second data");

        spinner = (Spinner)findViewById(R.id.static_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, test);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        final BuildUi activity = this;

        Button btnSnooze = (Button) findViewById(R.id.buttonSnooze);
        btnSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("btnSnooze", "clicked");

                Context context = getApplicationContext();

                // Get the drop down and get the selected value
                Spinner spinner = (Spinner) findViewById(R.id.static_spinner);
                String minutes = spinner.getSelectedItem().toString();
                long milliseconds = Long.parseLong(minutes) * 60000;

                // Create a new AlarmManager
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                // Create our intent and assign it the needed information
                Intent intent = new Intent(SnoozeClass.ACTION);


                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(
                                context,
                                0,
                                intent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );


                alarmManager.set(AlarmManager.RTC_WAKEUP, milliseconds, pendingIntent);
                Log.w("btnSnooze", "alarm created");
                activity.finish();
            }
        });


    }
}
