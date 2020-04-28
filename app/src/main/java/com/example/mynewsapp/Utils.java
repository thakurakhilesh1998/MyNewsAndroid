package com.example.mynewsapp;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Utils {

    public static final String API_KEY = "249857e0d6544cdc8994d1eb3d2222f3";
    public static final String API_URL = "https://newsapi.org/v2/top-headlines?";

    public static URL getUrl() throws MalformedURLException {
        Uri uri = Uri.parse(API_URL);
        Uri.Builder uriBuilder = uri.buildUpon();
        uriBuilder.appendQueryParameter("country", "in");
        uriBuilder.appendQueryParameter("category", "general");
        uriBuilder.appendQueryParameter("apikey", API_KEY);
        URL url = new URL(uriBuilder.toString());
        return url;
    }

    public static String sendRequestToServer(URL url) {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        if (url != null) {

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(10000);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonResponse = convertInputIntoString(inputStream);

            } catch (Exception e) {

            }
        }
        return jsonResponse;
    }

    private static String convertInputIntoString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
        return stringBuilder.toString();
    }
}
