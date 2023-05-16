package com.example.climatewatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class InteractiveMapActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_map);

        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progressBar);

        // Enable JavaScript (if required by the website)
        webView.getSettings().setJavaScriptEnabled(true);

        // Set a WebViewClient to handle page navigation and loading within the WebView
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                // Show the progress bar when the page starts loading
                progressBar.setVisibility(ProgressBar.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // Hide the progress bar when the page finishes loading
                progressBar.setVisibility(ProgressBar.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // Handle any errors that occur during page loading
                // You can display an error message or take appropriate action
            }
        });

        // Load the website URL
        webView.loadUrl("https://www.meteoblue.com/en/weather/maps/manila_philippines_1701668#coords=7.01/14.446/121.571&map=windAnimation~rainbow~auto~10%20m%20above%20gnd~none");
        //https://www.windy.com/14.650/121.050?14.674,121.014,5
        //https://earth.nullschool.net/#current/wind/surface/level/orthographic=-239.07,14.81,2485/loc=120.742,14.614
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_weather_forecast) {
            // Handle the menu item click to navigate back to the Weather Forecast section
            // You can start the Weather Forecast activity or perform any other desired action
            // For example:
            openWeatherForecast();
        } else if (id == R.id.nav_news_and_updates) {
            // Handle the menu item click to navigate back to the Weather Forecast section
            // You can start the Weather Forecast activity or perform any other desired action
            // For example:
            openWeatherForecast();
        } else if (id == R.id.nav_interactive_radar_map) {
            // Handle the menu item click to navigate to the Interactive Radar Map section
            // Add your code to navigate to the Interactive Radar Map section or perform any other desired action
            openInteractiveRadarMap();
            return true;
        } else if (id == android.R.id.home) {
            // Handle the toolbar navigation
            // In this case, navigate back to the previous activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openWeatherForecast() {
        // Replace "YourInteractiveRadarMapActivity" with the actual name of your activity class for the Interactive Radar Map section
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openNewsAndUpdates() {
        // Replace "YourInteractiveRadarMapActivity" with the actual name of your activity class for the Interactive Radar Map section
        Intent intent = new Intent(this, NewsAndUpdatesActivity.class);
        startActivity(intent);
    }
    private void openInteractiveRadarMap() {
        // Replace "YourInteractiveRadarMapActivity" with the actual name of your activity class for the Interactive Radar Map section
        Intent intent = new Intent(this, InteractiveMapActivity.class);
        startActivity(intent);
    }
}
