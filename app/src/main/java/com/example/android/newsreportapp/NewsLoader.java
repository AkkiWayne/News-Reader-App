package com.example.android.newsreportapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String murl;
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    public NewsLoader(Context context, String url) {
        super(context);
        murl = url;
    }
    @Override
    public List<News> loadInBackground() {
        if (murl == null) {
            return null;
        }
        return QueryUtils.fetchNewsStoriesData(murl);
    }
}


