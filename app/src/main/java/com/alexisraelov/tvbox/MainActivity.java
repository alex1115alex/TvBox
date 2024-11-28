package com.alexisraelov.tvbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("", "MainActivity launched");

        // Start the service
        Intent serviceIntent = new Intent(this, BootService.class);
        startForegroundService(serviceIntent); // Start the service
    }
}
