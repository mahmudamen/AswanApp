package com.newaswan.seven;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Anonymo on 10/02/2018.
 */

public class StartFirebaseAtBoot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      //  context.startService(new Intent(FirebaseBackgroundService.class.getName()));
     //   context.startService(new Intent(context, FirebaseBackgroundService.class));
    }
}