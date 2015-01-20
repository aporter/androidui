package course.examples.statusbarwithcustomview;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NotificationStatusBarWithExpandedViewActivity extends Activity {

    // Notification ID to allow for future updates
    private static final int MY_NOTIFICATION_ID = 1;
    private static final String BASE_SOUND_URI = "android.resource://course.examples.statusbarwithcustomview/";

    // Notification Count
    private int mNotificationCount;

    // Notification Text Elements
    private static final CharSequence mTickerText = "This is a Really, Really, Super Long Notification Message!";
    private static final CharSequence mContentText = "You've Been Notified!";

    // Notification Sound and Vibration on Arrival
    private static final Uri soundURI = Uri
            .parse(BASE_SOUND_URI + R.raw.alarm_rooster);
    private static final long[] mVibratePattern = {0, 200, 200, 300};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        // Notification Action Elements
        Intent notificationIntent = new Intent(getApplicationContext(),
                NotificationSpecialActivity.class);
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
                        .setTicker(mTickerText)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.fire_eye_alien))
                        .setAutoCancel(true)
                        .setContentIntent(contentIntent)
                        .setSound(soundURI)
                        .setVibrate(mVibratePattern)
                        .setNumber(++mNotificationCount)
                        .setContentTitle(mContentText)
                        .setStyle(new Notification.BigTextStyle()
                                .bigText(getString(R.string.notification_expanded_view_string)));

                // Pass the Notification to the NotificationManager:
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                // Post the notification
                mNotificationManager.notify(MY_NOTIFICATION_ID,
                        notificationBuilder.build());
            }
        });

    }
}