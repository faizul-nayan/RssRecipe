package com.bdsob.recipes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Faizul Haque Nayan on 6/9/2017.
 */

public class PlaceholderFragment extends Fragment implements UserInterface {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<RssFeedModel> mFeedModelList;
    private static View view;
    ContactManager contactManager;
    private List<RssFeedModel> dataList;


    public PlaceholderFragment() {
    }

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
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                Intent titleIntent = new Intent(getActivity(), DetailsActivity.class);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                boolean network = isNetworkConnected();
                byte[] byteArray;
                Toast.makeText(getActivity(), "Coloum index " + String.valueOf(position), Toast.LENGTH_LONG).show();

                if (network) {
                    mFeedModelList.get(position).imageLink.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();
                    bundle.putString("title", mFeedModelList.get(position).title);
                    bundle.putString("pubDate", mFeedModelList.get(position).pubDate);
                    bundle.putString("desc", mFeedModelList.get(position).description);
                    bundle.putByteArray("image", byteArray);
                } else {
                    dataList.get(position).imageLink.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();

                    bundle.putString("title", dataList.get(position).title);
                    bundle.putString("pubDate", dataList.get(position).pubDate);
                    bundle.putString("desc", dataList.get(position).description);
                    bundle.putByteArray("image", byteArray);
                }


                // Log.d("Intent Value", "Place holder Fragment ==> " + mFeedModelList.get(position).title);
                // Log.d("Intent Value", "Place holder Fragment ==> " + mFeedModelList.get(position).pubDate);
                // Log.d("Intent Value", "Place holder Fragment ==> " + mFeedModelList.get(position).description);
                //Intent intent = new Intent(getActivity(), DetailsActivity.class);
                titleIntent.putExtras(bundle);
                startActivity(titleIntent);

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        getActivity().registerReceiver(myReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        try {
            contactManager = new ContactManager(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataList = new ArrayList<>();
        view = rootView;
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        boolean network = isNetworkConnected();
        if (network)
            new FetchFeedTask().execute((Void) null);
        else {
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                dataList = contactManager.getRecyclerViewData(AllITEM);
                recyclerView.setAdapter(new RssFeedListAdapter(dataList));
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                dataList = contactManager.getRecyclerViewData(SUPE);
                recyclerView.setAdapter(new RssFeedListAdapter(dataList));
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                dataList = contactManager.getRecyclerViewData(MEAT);
                recyclerView.setAdapter(new RssFeedListAdapter(dataList));
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 4) {
                dataList = contactManager.getRecyclerViewData(SWEET);
                recyclerView.setAdapter(new RssFeedListAdapter(dataList));
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 5) {
                dataList = contactManager.getRecyclerViewData(VORTA);
                recyclerView.setAdapter(new RssFeedListAdapter(dataList));
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 6) {
                dataList = contactManager.getRecyclerViewData(ACHAR);
                recyclerView.setAdapter(new RssFeedListAdapter(dataList));
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 7) {
                dataList = contactManager.getRecyclerViewData(CAKE);
                recyclerView.setAdapter(new RssFeedListAdapter(dataList));
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 8) {
                dataList = contactManager.getRecyclerViewData(SALAD);
                recyclerView.setAdapter(new RssFeedListAdapter(dataList));
            }

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(myReceiver);
    }

    public void startASycnc() {
        new FetchFeedTask().execute();
        //boolean temp = new FetchFeedTask.execute().get();
    }

    /*private void updateDataBase() {
        boolean delete = contactManager.deleteData(AllITEM);
         if(delete)
            Log.d("DataBase Delete:", "Successful " );
        else
            Log.d("DataBase Delete", "Unuccessful " );
        boolean result = contactManager.addNewItem(mFeedModelList, AllITEM);
        if (result) {
            Toast.makeText(getActivity(), "Database Insert success", Toast.LENGTH_LONG).show();
            Log.d("DataBase insert:", "Successful ");
        } else
            Toast.makeText(getActivity(), "Database Insert FAILED", Toast.LENGTH_LONG).show();
    }*/

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

        private String urlLink = "";

        @Override
        protected void onPreExecute() {
            if (swipeRefreshLayout == null)
                swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
            swipeRefreshLayout.setRefreshing(true);

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                urlLink = urlLinkAll;
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                urlLink = urlLinkSupe;
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                urlLink = urlLinkMeat;
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 4) {
                urlLink = urlLinkSweet;
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 5) {
                urlLink = urlLinkVorta;
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 6) {
                urlLink = urlLinkAchar;
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 7) {
                urlLink = urlLinkCake;
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 8) {
                urlLink = urlLinkSalad;
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (TextUtils.isEmpty(urlLink))
                return false;

            try {
                if (!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
                    urlLink = "http://" + urlLink;

                URL url = new URL(urlLink);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream(); //url.openConnection().getInputStream();
                mFeedModelList = parseFeed(inputStream);
                inputStream.close();
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
                if (recyclerView != null && mFeedModelList.size() > 0)
                    recyclerView.setAdapter(new RssFeedListAdapter(mFeedModelList));
                else
                    recyclerView.setAdapter(new RssFeedListAdapter(contactManager.getRecyclerViewData(AllITEM)));
            }
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
                    if (name == null || name.equalsIgnoreCase("channel") || name.equalsIgnoreCase("link") ||
                            name.equalsIgnoreCase("language") || name.equalsIgnoreCase("source") ||
                            name.equalsIgnoreCase("dc:creator") || name.equalsIgnoreCase("category")
                            || name.equalsIgnoreCase("votes") || name.equalsIgnoreCase("upvotes")
                            || name.equalsIgnoreCase("downvotes") || name.equalsIgnoreCase("guid") || name.equalsIgnoreCase("guid"))
                        continue;

                    if (eventType == XmlPullParser.END_TAG) {
                        if (name.equalsIgnoreCase("item")) {
                            isItem = false;
                        }
                        continue;
                    }

                    if (eventType == XmlPullParser.START_TAG) {
                        if (name.equalsIgnoreCase("item")) {
                            isItem = true;
                            description = null;
                            continue;
                        }
                    }


                    if (name.equalsIgnoreCase("media:content")) {
                        imageUrl = xmlPullParser.getAttributeValue(null, "url");
                        //realUrl = result;
                        //Log.d("Real Url is:", "+++++++++ " + imageUrl);
                        try {
                            InputStream in = new java.net.URL(imageUrl).openStream();
                            mIcon11 = BitmapFactory.decodeStream(in);
                            in.close();
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage());
                            e.printStackTrace();
                        }
                    }

                    //Log.d("MyXmlParser", "Parsing name ==> " + name);
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
                    }

                    if (title != null && pubDate != null && description != null && mIcon11 != null) {
                        if (isItem) {
                            RssFeedModel item = new RssFeedModel(title, pubDate, description, mIcon11);
                            items.add(item);
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

    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            final android.net.NetworkInfo wifi = connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            final android.net.NetworkInfo mobile = connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (wifi.isAvailable() || mobile.isAvailable()) {
                startASycnc();
                //Log.d("Network Available ", "Flag No ++++++++Fragment");
            }
        }
    };
}
