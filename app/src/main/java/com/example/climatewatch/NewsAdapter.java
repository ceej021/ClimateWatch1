package com.example.climatewatch;

import com.bumptech.glide.Glide;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;
    private List<NewsItem> newsList;

    public NewsAdapter(Context context, List<NewsItem> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_news, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsItem currentItem = newsList.get(position);
        if (currentItem != null) {
            holder.titleTextView.setText(currentItem.getTitle());
            holder.sourceTextView.setText(currentItem.getSource());
            holder.publishedAtTextView.setText(currentItem.getPublishedAt());

            String imageUrl = currentItem.getImageUrl();
            Log.d("NewsAdapter", "Image URL: " + imageUrl); // Add this line to log the imageUrl

            if (imageUrl != null && !imageUrl.isEmpty()) {
                // Manually load the image using the provided image URL
                // You can use any image loading library or method of your choice
                // Here's an example using Glide library:
                Glide.with(context)
                        .load(imageUrl)
                        .into(holder.imageView);
            } else {
                // Handle the case where imageUrl is null or empty
                // For example, you could set a default image or hide the ImageView
                holder.imageView.setImageDrawable(null); // Set a default image or hide the ImageView
            }
        } else {
            // Handle the case where currentItem is null
            // For example, you could set default values for title, source, and publishedAt
            holder.titleTextView.setText("");
            holder.sourceTextView.setText("");
            holder.publishedAtTextView.setText("");
            holder.imageView.setImageDrawable(null); // Set a default image or hide the ImageView
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setNewsList(List<NewsItem> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView sourceTextView;
        TextView publishedAtTextView;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            sourceTextView = itemView.findViewById(R.id.sourceTextView);
            publishedAtTextView = itemView.findViewById(R.id.publishedAtTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
