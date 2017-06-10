package com.example.nayan.rssrecipe;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

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
