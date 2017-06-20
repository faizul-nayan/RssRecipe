package com.bdsob.recipes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


/**
 * Created by Faizul Haque Nayan on 7/20/2016.
 */

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_details);

        TextView titleTv = (TextView) findViewById(R.id.titleTv);
        TextView pubDateTv = (TextView) findViewById(R.id.pubDateTv);
        TextView descTv = (TextView) findViewById(R.id.detalisTv);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        MobileAds.initialize(this, "YOUR_ADMOB_APP_ID");

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();


        // adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setAdSize(AdSize.BANNER);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        byte[] byteArray = bundle.getByteArray("image");

        titleTv.setText(bundle.getString("title"));
        pubDateTv.setText(bundle.getString("pubDate"));
        descTv.setText(Html.fromHtml(bundle.getString("desc")));
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));

    }
}
