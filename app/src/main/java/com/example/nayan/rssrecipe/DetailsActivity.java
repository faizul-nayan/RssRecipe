package com.example.nayan.rssrecipe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView titleTv = (TextView) findViewById(R.id.titleTv);
        TextView pubDateTv = (TextView) findViewById(R.id.pubDateTv);
        TextView descTv = (TextView) findViewById(R.id.detalisTv);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        byte[] byteArray = bundle.getByteArray("image");

        titleTv.setText(bundle.getString("title"));
        pubDateTv.setText(bundle.getString("pubDate"));
        descTv.setText(Html.fromHtml(bundle.getString("desc")));
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));

    }
}
