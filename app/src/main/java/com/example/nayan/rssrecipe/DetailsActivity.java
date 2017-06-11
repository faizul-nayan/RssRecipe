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

    private List<RssFeedModel> mFeedModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView titleTv = (TextView) findViewById(R.id.titleTv);
        TextView pubDateTv = (TextView) findViewById(R.id.pubDateTv);
        TextView descTv = (TextView) findViewById(R.id.detalisTv);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

       /* Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        byte[] byteArray = bundle.getByteArray("image");

        titleTv.setText(bundle.getString("title"));
        pubDateTv.setText(bundle.getString("pubDate"));
        descTv.setText(Html.fromHtml(bundle.getString("desc")));
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));  */

        titleTv.setText("Here should be clicked ");
        titleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"Ciclked",Toast.LENGTH_LONG).show();
                new FetchFeedTask().execute((Void) null);
            }
        });

    }

    public class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {


        private String urlLinkAll = "bdsob.com/রেসিপি/rss/";
        private String urlLink = "bdsob.com/রেসিপি/rss/";


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (TextUtils.isEmpty(urlLink))
                return false;

            try {
                if (!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
                    urlLink = "http://" + urlLink;

                URL url = new URL(urlLink);
                InputStream inputStream = url.openConnection().getInputStream();
                mFeedModelList = parseFeed(inputStream);
                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error", e);
            } catch (XmlPullParserException e) {
                Log.e(TAG, "Error", e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {

            //recyclerView.setAdapter(new RssFeedListAdapter(mFeedModelList));
        }


        public List<RssFeedModel> parseFeed(InputStream inputStream) throws XmlPullParserException,
                IOException {
            String title = null;
            String pubDate = null;
            String description = null;
            String imageUrl = null;
            Bitmap mIcon11 = null;
            String realUrl = null;
            boolean isItem = false;
            List<RssFeedModel> items = new ArrayList<>();

            try {
                XmlPullParser xmlPullParser = Xml.newPullParser();
                xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                xmlPullParser.setInput(inputStream, null);

                xmlPullParser.nextTag();
                while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                    int eventType = xmlPullParser.getEventType();

                    String name = xmlPullParser.getName();
                    if(name == null || name.equalsIgnoreCase("channel") || name.equalsIgnoreCase("link")||
                            name.equalsIgnoreCase("language") || name.equalsIgnoreCase("source")||
                            name.equalsIgnoreCase("dc:creator") || name.equalsIgnoreCase("category")
                            || name.equalsIgnoreCase("votes") || name.equalsIgnoreCase("upvotes")
                            || name.equalsIgnoreCase("downvotes") || name.equalsIgnoreCase("guid"))
                        continue;

                    if(eventType == XmlPullParser.END_TAG) { //EndTag = 3
                        if(name.equalsIgnoreCase("item")) {
                            isItem = false;
                        }
                        continue;
                    }

                    if (eventType == XmlPullParser.START_TAG) { //StartTag = 2
                        if(name.equalsIgnoreCase("item")) {
                            isItem = true;
                            continue;
                        }
                    }

                    Log.d("MyXmlParser", "Parsing name ==> " + name);
                    String result = "";
                    if(name.equalsIgnoreCase("media:content")){
                        realUrl = xmlPullParser.getAttributeValue(null, "url");
                        //realUrl = result;
                        Log.d("Real Url is:", "+++++++++ "+realUrl );
                    }
                    if (xmlPullParser.next() == XmlPullParser.TEXT) {
                        result = xmlPullParser.getText();
                        xmlPullParser.nextTag();
                    }

                    if (name.equalsIgnoreCase("title")) {
                        title = result;
                    } else if (name.equalsIgnoreCase("pubDate")) { //link
                        pubDate = result;
                    } else if (name.equalsIgnoreCase("description")) {
                        description = result;

                        imageUrl = description.substring(description.indexOf("src=")+5,description.indexOf("jpg")+5);
                        //imageUrl = imageUrl.substring(0,imageUrl.length()+3);
                        imageUrl = imageUrl.replace("'/","");
                        Log.d("MyXmlParser", "Image Url ==> " +imageUrl);

                        try {
                            InputStream in = new java.net.URL(imageUrl).openStream();
                            mIcon11 = BitmapFactory.decodeStream(in);
                            in.close();
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage());
                            e.printStackTrace();
                        }

                    }
                    else if(name.equalsIgnoreCase("media:content")){
                        realUrl = xmlPullParser.getAttributeValue(null, "url");
                        //realUrl = result;
                        Log.d("Real Url is:", "........... "+realUrl );
                    }


                    if (title != null && pubDate != null && description != null && mIcon11 != null) {
                        if(isItem) {
                            RssFeedModel item = new RssFeedModel(title, pubDate, description, mIcon11);
                            items.add(item);
                        }
                        else {
                        /*mFeedTitle = title;
                        mFeedLink = link;
                        mFeedDescription = description;*/
                        }

                        title = null;
                        pubDate = null;
                        description = null;
                        mIcon11 = null;
                        imageUrl = null;
                        isItem = false;
                        realUrl = null;
                    }
                }

                return items;
            } finally {
                inputStream.close();
            }
        }
    }
}
