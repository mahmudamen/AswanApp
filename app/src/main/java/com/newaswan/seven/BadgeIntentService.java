package com.newaswan.seven;


import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import com.firebase.client.Firebase;

import me.leolin.shortcutbadger.ShortcutBadger;

public class BadgeIntentService extends IntentService {

    private int notificationId = 0;

    public BadgeIntentService() {
        super("BadgeIntentService");

    }

    private NotificationManager mNotificationManager;




    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Firebase.setAndroidContext(this);

        if (intent != null) {
            Intent i = new Intent(this, ChatActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0/*Request code*/, i, PendingIntent.FLAG_UPDATE_CURRENT);
            int badgeCount = intent.getIntExtra("badgeCount", 1 );
            badgeCount += 1;
            boolean success = ShortcutBadger.applyCount(BadgeIntentService.this,badgeCount );
            mNotificationManager.cancel(notificationId);
            notificationId++;
            Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Notification.Builder builder = new Notification.Builder(getApplicationContext())
                    .setContentTitle("رسالة")
                    .setAutoCancel(true)
                    .setSound(notificationSound)
                    .setContentText("لديك رسالة جديدة")
                    .setSmallIcon(R.drawable.lasticon)
                    .setContentIntent(pendingIntent);
            Notification notification = builder.build();
            ShortcutBadger.applyNotification(getApplicationContext(), notification, badgeCount);
            mNotificationManager.notify(notificationId, notification);
        }
    }


}