package com.example.nayan.rssrecipe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.support.design.R.attr.layoutManager;

/**
 * Created by Nayan on 6/9/2017.
 */

public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<RssFeedModel> mFeedModelList;
    int value ;
    private static View view;

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        Button button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Button Click:"+getArguments().getInt(ARG_SECTION_NUMBER));

                new FetchFeedTask().execute((Void) null);
                Toast.makeText(getActivity(),"Button clcik",Toast.LENGTH_LONG);
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), RecyclerView.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchFeedTask().execute((Void) null);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView , new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                value = position;
                Toast.makeText(getActivity(), "Coloum index "+ String.valueOf(value),Toast.LENGTH_LONG).show();

                Intent titleIntent = new Intent(getActivity(), DetailsActivity.class);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mFeedModelList.get(position).imageLink.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Bundle bundle = new Bundle();
                bundle.putString("title",mFeedModelList.get(position).title);
                bundle.putString("pubDate",mFeedModelList.get(position).pubDate);
                bundle.putString("desc",mFeedModelList.get(position).description);
                bundle.putByteArray("image", byteArray);

                Log.d("Intent Value", "Place holder Fragment ==> " +mFeedModelList.get(position).title);
                Log.d("Intent Value", "Place holder Fragment ==> " +mFeedModelList.get(position).pubDate);
                Log.d("Intent Value", "Place holder Fragment ==> " +mFeedModelList.get(position).description);
                //Intent intent = new Intent(getActivity(), DetailsActivity.class);
                titleIntent.putExtras(bundle);
                startActivity(titleIntent);

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        view = rootView;
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean network = isNetworkConnected();

        if(network){
            new FetchFeedTask().execute((Void) null);
        }
        else{
            Toast.makeText(getActivity(), "Not connected", Toast.LENGTH_LONG).show();
        }

    }

    public void startASycnc() {
        new FetchFeedTask().execute();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {


        private String urlLinkAll = "bdsob.com/রেসিপি/rss/";
        private String urlLinkSupe = "bdsob.com/স্যুপ-রেসিপি/rss/";
        private String urlLinkMeat = "bdsob.com/মাংস/rss/";
        private String urlLinkSweet = "bdsob.com/মিষ্টি-রেসিপি/rss/";
        private String urlLinkVorta = "bdsob.com/ভর্তা/rss";
        private String urlLinkAchar = "bdsob.com/আচার-ও-চাটনি/rss";
        private String urlLinkCake = "bdsob.com/পিঠা/rss/";
        private String urlLinkSalad = "bdsob.com/সালাদ/rss/";
        private String urlLink = "";


        @Override
        protected void onPreExecute() {

            if(swipeRefreshLayout == null)
                swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
            swipeRefreshLayout.setRefreshing(true);

            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1){ urlLink = urlLinkAll;}
            else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){urlLink = urlLinkSupe;}
            else if(getArguments().getInt(ARG_SECTION_NUMBER) == 3){urlLink = urlLinkMeat;}
            else if(getArguments().getInt(ARG_SECTION_NUMBER) == 4){urlLink = urlLinkSweet;}
            else if(getArguments().getInt(ARG_SECTION_NUMBER) == 5){urlLink = urlLinkVorta;}
            else if(getArguments().getInt(ARG_SECTION_NUMBER) == 6){urlLink = urlLinkAchar;}
            else if(getArguments().getInt(ARG_SECTION_NUMBER) == 7){urlLink = urlLinkCake;}
            else if(getArguments().getInt(ARG_SECTION_NUMBER) == 8){urlLink = urlLinkSalad;}
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (TextUtils.isEmpty(urlLink))
                return false;

            try {
                if(!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
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

            swipeRefreshLayout.setRefreshing(false);

            if (success) {
                recyclerView.setAdapter(new RssFeedListAdapter(mFeedModelList));
            } else{}

                //recyclerView.setAdapter(new RssFeedListAdapter(mFeedModelList));
        }


        public List<RssFeedModel> parseFeed(InputStream inputStream) throws XmlPullParserException,
                IOException {
            String title = null;
            String pubDate = null;
            String description = null;
            String imageUrl = null;
            Bitmap mIcon11 = null;
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
                            || name.equalsIgnoreCase("downvotes") || name.equalsIgnoreCase("guid") || name.equalsIgnoreCase("guid"))
                        continue;

                    if(eventType == XmlPullParser.END_TAG) {
                        if(name.equalsIgnoreCase("item")) {
                            isItem = false;
                        }
                        continue;
                    }

                    if (eventType == XmlPullParser.START_TAG) {
                        if(name.equalsIgnoreCase("item")) {
                            isItem = true;
                            continue;
                        }
                    }


                    if(name.equalsIgnoreCase("media:content")){
                        imageUrl = xmlPullParser.getAttributeValue(null, "url");
                        //realUrl = result;
                        Log.d("Real Url is:", "+++++++++ "+imageUrl );
                        try {
                            InputStream in = new java.net.URL(imageUrl).openStream();
                            mIcon11 = BitmapFactory.decodeStream(in);
                            in.close();
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage());
                            e.printStackTrace();
                        }
                    }

                    Log.d("MyXmlParser", "Parsing name ==> " + name);
                    String result = "";
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

                       /* imageUrl = description.substring(description.indexOf("src=")+5,description.indexOf("jpg")+5);
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
                        }*/

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
                    }
                }

                return items;
            } finally {
                inputStream.close();
            }
        }
    }
}
