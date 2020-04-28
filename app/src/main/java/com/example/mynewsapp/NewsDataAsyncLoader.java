package com.example.mynewsapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.mynewsapp.Modal.NewsDataModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsDataAsyncLoader extends AsyncTaskLoader<ArrayList<NewsDataModal>> {
    private ArrayList<NewsDataModal> newsDataModals;

    public NewsDataAsyncLoader(@NonNull Context context, ArrayList<NewsDataModal> newsDataModals) {
        super(context);
        this.newsDataModals = newsDataModals;
    }

    @Override
    public void deliverResult(@Nullable ArrayList<NewsDataModal> data) {
        super.deliverResult(data);
        Log.e("deliver", "deliver result");
    }

    @Nullable
    @Override
    public ArrayList<NewsDataModal> loadInBackground() {
        URL url = null;
        try {
            url = Utils.getUrl();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String response = Utils.sendRequestToServer(url);
        try {
            setDataToList(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsDataModals;
    }

    private void setDataToList(String data) throws JSONException {
        JSONObject response = new JSONObject(data);
        JSONArray articles = response.getJSONArray("articles");
        for (int i = 0; i < articles.length(); i++) {
            JSONObject object = articles.getJSONObject(i);
            JSONObject name = object.getJSONObject("source");
            newsDataModals.add(new NewsDataModal(object.getString("urlToImage"), object.getString("title"),
                    object.getString("description"), object.getString("publishedAt"), name.getString("name"),
                    object.getString("url")));
        }
    }
}
