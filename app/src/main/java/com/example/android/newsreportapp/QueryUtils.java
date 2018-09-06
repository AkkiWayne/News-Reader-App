package com.example.android.newsreportapp;

import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private QueryUtils() {
    }
    public static List<News> fetchNewsStoriesData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
        }
        List<News> newsList = extractFeatureFromJson(jsonResponse);
        return newsList;
    }
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
            }
        } catch (IOException e) {
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    private static List<News> extractFeatureFromJson(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        List<News> newsList = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(newsJSON);
            JSONObject response = root.getJSONObject("response");
            JSONArray resultsArray = response.getJSONArray("results");
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject newsItem = resultsArray.getJSONObject(i);
                String title = newsItem.getString("webTitle");
                String section = newsItem.getString("sectionName");
                String date ="";
                String webUrl = newsItem.getString("webUrl");
                String author = "";
                if (newsItem.has("webPublicationDate")) {
                    date = newsItem.getString("webPublicationDate");
                }

                JSONArray arr = newsItem.optJSONArray("tags");
                if(arr != null ) {
                    JSONArray tagsArr = newsItem.getJSONArray("tags");
                    int tagLength = tagsArr.length();
                    if (tagLength > 0) {
                        for (int j = 0; j < tagLength; j++) {
                            if (j > 0 && j < tagLength - 1) {
                                author += ", ";
                            }
                            if (j > 0 && j == tagLength - 1) {
                                author += " and ";
                            }
                            JSONObject tagItem = tagsArr.getJSONObject(j);
                            author += tagItem.getString("webTitle");
                        }
                    }
                }
                newsList.add(new News(title, section, author, date, webUrl));
            }
        } catch (JSONException e) {
        }
        return newsList;
    }
}
