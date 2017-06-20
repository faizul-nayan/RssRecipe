package com.bdsob.recipes;

import android.graphics.Bitmap;

/**
 * Created by Nayan on 6/8/2017.
 */

public class RssFeedModel {

    public String title;
    public String pubDate;
    public String description;
    public Bitmap imageLink;

    public RssFeedModel(String title, String pubDate, String description, Bitmap imageLink) {
        this.title = title;
        this.pubDate = pubDate;
        this.description = description;
        this.imageLink = imageLink;
    }

    public String getTitle() {
        return title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getImageLink() {
        return imageLink;
    }
}
