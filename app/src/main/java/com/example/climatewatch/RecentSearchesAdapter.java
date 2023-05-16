package com.example.climatewatch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecentSearchesAdapter extends RecyclerView.Adapter<RecentSearchesAdapter.RecentSearchViewHolder> {
    private List<String> recentSearches = new ArrayList<>();

    public void setRecentSearches(List<String> recentSearches) {
        this.recentSearches = recentSearches;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecentSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_search, parent, false);
        return new RecentSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentSearchViewHolder holder, int position) {
        String searchLocation = recentSearches.get(position);
        holder.searchLocationTV.setText(searchLocation);
    }

    @Override
    public int getItemCount() {
        return recentSearches.size();
    }

    public static class RecentSearchViewHolder extends RecyclerView.ViewHolder {
        TextView searchLocationTV;

        public RecentSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            searchLocationTV = itemView.findViewById(R.id.searchLocationTV);
        }
    }
}
