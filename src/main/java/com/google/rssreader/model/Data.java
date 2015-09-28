package com.google.rssreader.model;

/**
 * Created by Roman on 26.09.2015.
 */
public class Data {

    private final String title;
    private final String pubDate;
    private final String author;
    private final String link;
    private final String imageUrl;

    public Data(String title, String pubDate, String author, String link, String imageUrl) {
        super();
        this.title = title;
        this.pubDate = pubDate;
        this.author = author;
        this.link = link;
        this.imageUrl = imageUrl;

    }

    public String getTitle() {
        return title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getAuthor() {
        return author;
    }

    public String getLink() {
        return link;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}