package com.example.android.newsreportapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News>{
    public NewsAdapter(@NonNull Context context, @NonNull List<News> news) {
        super(context, 0, news);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null)
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_news, parent, false);
        News currentNewsItem = getItem(position);
        TextView titleTextView = (TextView)itemView.findViewById(R.id.body_textview);
        titleTextView.setText(currentNewsItem.getTitle());
        TextView sectionTextView =(TextView) itemView.findViewById(R.id.head_textview);
        sectionTextView.setText(currentNewsItem.getSection());
        TextView authorTextView = (TextView)itemView.findViewById(R.id.author_textview);
        authorTextView.setText(currentNewsItem.getAuthor());
        TextView dateTextView = (TextView)itemView.findViewById(R.id.date_textview);
        String date = dateFormatter(currentNewsItem.getDate());
        dateTextView.setText(date);
        return itemView;
    }
    private String dateFormatter(String date) {
        String[] parts = date.split("T");
        return parts[0];
    }
}


