package course.examples.statusbar;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NotificationStatusBarActivity extends Activity {

    // Notification ID to allow for future updates
    private static final int MY_NOTIFICATION_ID = 1;
    private static final String BASE_SOUND_URI = "android.resource://course.examples.statusbar/";

    // Notification Count
    private int mNotificationCount;

    // Notification Text Elements
    private static final CharSequence sTickerText = "This is a Really, Really, Super Long Notification Message!";
    private static final CharSequence sContentTitle = "Breaking Notification";
    private static final CharSequence sContentText = "You've Been Notified!";


    // Notification Sound and Vibration on Arrival
    private static final Uri sSoundURI = Uri
            .parse(BASE_SOUND_URI + R.raw.alarm_rooster);
    private static final long[] sVibratePattern = {0, 200, 200, 300};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        // Notification Action Elements
        Intent notificationIntent = new Intent(getApplicationContext(), NotificationSubActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Create a PendingIntent containing underlying notification Intent
        final PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        final Button button = (Button) findViewById(R.id.notify_button);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // Define the Notification's expanded message and Intent:

                Notification.Builder notificationBuilder = new Notification.Builder(
                        getApplicationContext())
                        .setTicker(sTickerText)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setAutoCancel(true)
                        .setContentTitle(sContentTitle)
                        .setContentText(
                                sContentText).setNumber(++mNotificationCount)
                        .setContentIntent(contentIntent).setSound(sSoundURI)
                        .setVibrate(sVibratePattern);

                // Pass the Notification to the NotificationManager:
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(MY_NOTIFICATION_ID,
                        notificationBuilder.build());
            }
        });

    }
}