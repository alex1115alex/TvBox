package com.alexisraelov.tvbox;

import static com.alexisraelov.tvbox.config.publicWebUrl;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";
    private WebView webView;
    private Handler handler;
    private boolean isPageLoadedSuccessfully = false;
    private Runnable refreshRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "WebViewActivity launched");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);


        // Initialize WebView
        webView = new WebView(this);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                + "AppleWebKit/537.36 (KHTML, like Gecko) "
                + "Chrome/91.0.4472.124 Safari/537.36");

        // Set WebViewClient to handle loading within the app
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(@NonNull WebView view, @NonNull String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "Page loaded successfully: " + url);
                isPageLoadedSuccessfully = true;
            }

            @Override
            public void onReceivedError(@NonNull WebView view, @NonNull WebResourceRequest request, @NonNull WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.e(TAG, "Failed to load page. Error: " + error.getDescription());
                isPageLoadedSuccessfully = false; // Mark as failed to load
                // Delay reload by 5 seconds to avoid spamming
                handler.postDelayed(() -> {
                    Log.d(TAG, "Retrying to load the page after an error...");
                    webView.loadUrl(publicWebUrl);
                }, 5000); // 5-second delay
            }

            // For older devices
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.e(TAG, "Failed to load page. Error: " + description);
                isPageLoadedSuccessfully = false;
                // Delay reload by 5 seconds to avoid spamming
                handler.postDelayed(() -> {
                    Log.d(TAG, "Retrying to load the page after an error...");
                    webView.loadUrl(publicWebUrl);
                }, 5000); // 5-second delay
            }
        });



        // Load the desired URL
        webView.loadUrl(publicWebUrl);

        // Set the WebView as the content view
        setContentView(webView);

        // Initialize the handler for periodic refresh
        handler = new Handler();

        // Define the refresh runnable
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isPageLoadedSuccessfully) {
                    Log.d(TAG, "Retrying to load the page...");
                    webView.loadUrl(publicWebUrl);
                    setContentView(webView);
                } else {
                    Log.d(TAG, "Page loaded successfully, no need to reload.");
                }
                // Schedule the next check
                handler.postDelayed(this, 5000); // 30 seconds
            }
        };

        // Schedule the first refresh
        handler.postDelayed(refreshRunnable, 5000); // 30 seconds
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && refreshRunnable != null) {
            handler.removeCallbacks(refreshRunnable); // Stop the handler when activity is destroyed
        }
        if (webView != null) {
            webView.destroy(); // Properly destroy the WebView
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
