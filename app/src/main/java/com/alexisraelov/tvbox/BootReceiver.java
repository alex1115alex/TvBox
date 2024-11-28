package com.alexisraelov.tvbox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BOOTRECEIVER", "BootReceiver triggered");
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d("BOOTRECEIVER", "Starting BootService...");
            Intent serviceIntent = new Intent(context, BootService.class);
            context.startForegroundService(serviceIntent); // Start the foreground service
        }
    }
}