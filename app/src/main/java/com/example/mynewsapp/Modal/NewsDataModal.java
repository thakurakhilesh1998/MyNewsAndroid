package com.example.mynewsapp.Modal;

public class NewsDataModal {

    String imageUrl;
    String title;
    String desc;
    String date;
    String source;
    String urlToSource;

    public NewsDataModal(String imageUrl, String title, String desc, String date, String source, String urlToSource) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.source = source;
        this.urlToSource = urlToSource;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getDate() {
        return date;
    }

    public String getSource() {
        return source;
    }

    public String getUrlToSource() {
        return urlToSource;
    }
}
