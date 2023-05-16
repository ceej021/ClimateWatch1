package com.example.climatewatch;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class NewsAndUpdatesActivity extends AppCompatActivity {

    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;

    private static final String API_KEY = "5b0a3f49e7cbb8eead3d846bd07e52a3\n";
    private static final String API_URL = "https://gnews.io/api/v4/search?q=weather&country=ph&token=" + API_KEY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_and_updates);

        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the newsAdapter
        newsAdapter = new NewsAdapter(this, new ArrayList<NewsItem>());
        // Set the adapter to the RecyclerView
        newsRecyclerView.setAdapter(newsAdapter);

        // Fetch weather news articles
        FetchNewsTask fetchNewsTask = new FetchNewsTask();
        fetchNewsTask.execute(API_URL);
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
            onBackPressed(); // Navigate back to the previous activity
            return true;
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

    private void openInteractiveRadarMap() {
        // Replace "YourInteractiveRadarMapActivity" with the actual name of your activity class for the Interactive Radar Map section
        Intent intent = new Intent(this, InteractiveMapActivity.class);
        startActivity(intent);
    }

    private class FetchNewsTask extends AsyncTask<String, Void, ArrayList<NewsItem>> {
        @Override
        protected ArrayList<NewsItem> doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                String response = stringBuilder.toString();
                return parseNewsResponse(response);
            } catch (IOException e) {
                Log.e("NewsFetch", "Failed to fetch news. Check API response for details.", e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItem> newsItems) {
            if (newsItems != null) {
                newsAdapter.setNewsList(newsItems);
                newsAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(NewsAndUpdatesActivity.this, "Failed to fetch news", Toast.LENGTH_SHORT).show();
                Log.e("NewsFetch", "Failed to fetch news. Check API response for details.");
            }
        }
    }

    private ArrayList<NewsItem> parseNewsResponse(String response) {
        ArrayList<NewsItem> newsItems = new ArrayList<>();

        try {
            // Log the complete JSON response
            Log.d("NewsParsing", "Response: " + response);

            JSONObject jsonObject = new JSONObject(response);
            JSONArray articles = jsonObject.getJSONArray("articles");

            String[] imageUrls = {
                    "https://cdn4.premiumread.com/?url=https://www.manilatimes.net/manilatimes/uploads/images/2023/05/10/189446.jpg&w=825&q=100&f=webp",
                    "https://cdn4.premiumread.com/?url=https://www.manilatimes.net/theme_manilatimes/images/TMTFB_1920x1008.jpg&w=1920&q=100&f=webp",
                    "https://sa.kapamilya.com/absnews/abscbnnews/media/2023/life/05/07/20230507-hanoi-vietnam-epa.jpg",
                    "https://cdn4.premiumread.com/?url=https://www.manilatimes.net/manilatimes/uploads/images/2023/05/07/188443.jpg&w=700&q=100&f=webp",
                    "https://www.cnnphilippines.com/.imaging/default/dam/cnn/2023/5/4/LPA-Palawan-0504_CNNPH.jpg/jcr:content.jpg?width=750&height=450&crop:1:1,smart",
                    "https://sa.kapamilya.com/absnews/abscbnnews/media/2023/overseas/04/25/quake-(1).jpg",
                    "https://wingatchalian.com/wp-content/uploads/2020/12/PR319_20201211_523.jpg",
                    "https://www.cnnphilippines.com/.imaging/default/dam/cnn/2023/5/4/London-Coronation-Weather_CNNPH.jpg/jcr:content.jpg?width=750&height=450&crop:1:1,smart",
                    "https://www.cnnphilippines.com/.imaging/default/dam/cnn/2023/5/4/LPA-Palawan-0504_CNNPH.jpg/jcr:content.jpg?width=750&height=450&crop:1:1,smart",
                    "https://sa.kapamilya.com/absnews/abscbnnews/media/2023/life/05/03/20230503-heat-index-baseco-swim-jc-16.jpg"
            };

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = article.getString("title");
                String description = article.getString("description");
                String url = article.getString("url");

                String imageUrl = imageUrls[i];

                Log.d("NewsParsing", "Title: " + title);
                Log.d("NewsParsing", "Description: " + description);
                Log.d("NewsParsing", "URL: " + url);
                Log.d("NewsParsing", "Image URL: " + imageUrl);

                // Create a NewsItem object and add it to the list
                NewsItem newsItem = new NewsItem(title, description, imageUrl, url, "", "");
                newsItems.add(newsItem);
            }

            return newsItems;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}

