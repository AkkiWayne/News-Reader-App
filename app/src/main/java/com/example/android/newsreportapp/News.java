package com.example.android.newsreportapp;

public class News {
    private String mtitle;
    private String msection;
    private String mauthor;
    private String mdate;
    private String mUrl;

    public News(String title, String section, String author, String date, String webUrl) {
        mtitle = title;
        msection = section;
        mauthor = author;
        mdate = date;
        mUrl = webUrl;
    }
    public String getTitle() {
        return mtitle;
    }
    public String getSection() {
        return msection;
    }
    public String getAuthor() {
        return mauthor;
    }
    public String getDate() {
        return mdate;
    }
    public String getUrl() {
        return mUrl;
    }
}
