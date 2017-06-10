package com.example.nayan.rssrecipe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

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

        //List<RssFeedModel> thumbs=
                //(List<RssFeedModel>)bundle.getParcelable("value");
        //RssFeedModel rssFeedModel = bundle.getParcelable("value");

        byte[] byteArray = bundle.getByteArray("image");

        titleTv.setText(bundle.getString("title"));
        pubDateTv.setText(bundle.getString("pubDate"));
        descTv.setText(Html.fromHtml(bundle.getString("desc")));
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
        //imageView.setImageBitmap((Bitmap) getIntent().getParcelableExtra("image"));
        //Log.d("Intent Value", "DetalisActivity ==> " +getIntent().getStringExtra("title"));
        //Log.d("Intent Value", "DetalisActivity ==> " +getIntent().getStringExtra("date"));
        //Log.d("Intent Value", "DetalisActivity ==> " +getIntent().getStringExtra("desc"));
    }
}
