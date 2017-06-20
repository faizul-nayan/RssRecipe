package com.bdsob.recipes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faizul Haque Nayan on 7/20/2016.
 */

public class ContactManager {

    private DataBaseHelper helper;
    private SQLiteDatabase database;
    private Context context;

    public ContactManager(Context context) throws IOException {
        this.context = context;
        helper = new DataBaseHelper(context);

    }

    private void open() {
        database = helper.getWritableDatabase();

    }

    private void close() {
        helper.close();
    }


    public boolean addNewItem(List<RssFeedModel> rssFeedModel, String type) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int length = (rssFeedModel.size()) / 4;
        //Log.d("AddNewItem list size:", "+++++++++ " + String.valueOf(length));
        byte[] byteArray = null;
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < length; i++) {
            rssFeedModel.get(i).getImageLink().compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
            contentValues.put(UserInterface.ITEM_TITLE, rssFeedModel.get(i).getTitle());
            contentValues.put(UserInterface.ITEM_TYPE, type);
            contentValues.put(UserInterface.ITEM_PUBDATE, rssFeedModel.get(i).getPubDate());
            contentValues.put(UserInterface.ITEM_DISCRIPTION, rssFeedModel.get(i).getDescription());
            contentValues.put(UserInterface.ITEM_IMAGE, byteArray);
        }
        this.open();
        long inserted = database.insert(UserInterface.TABLE_NAME, null, contentValues);
        this.close();

        if (inserted > 0)
            return true;
        else
            return true;
    }


    public boolean deleteData(String type) {
        this.open();
        int query = database.delete(UserInterface.TABLE_NAME, UserInterface.ITEM_TYPE + "=?", new String[]{type});
        this.close();
        return query > 0;
    }

    public List<RssFeedModel> getRecyclerViewData(String type) {
        List<RssFeedModel> dataList = new ArrayList<>();
        Cursor cursor = null;
        this.open();
        //Cursor cursor = database.query(TABLE_NAME,null,""+ITEM_TYPE+"=?",new String[]{type},null,null,null);
        if (type.equalsIgnoreCase(UserInterface.AllITEM)) {
            cursor = database.query(UserInterface.TABLE_NAME, null, null, null, null, null, null);
        } else {
            cursor = database.query(UserInterface.TABLE_NAME, null, "" + UserInterface.ITEM_TYPE + "=?", new String[]{type}, null, null, null);
        }


        //Log.d("Cursor list size:", "+++++++++ " + cursor.getCount());
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String title = cursor.getString(cursor.getColumnIndex(UserInterface.ITEM_TITLE));
                String pubDate = cursor.getString(cursor.getColumnIndex(UserInterface.ITEM_PUBDATE));
                String discription = cursor.getString(cursor.getColumnIndex(UserInterface.ITEM_DISCRIPTION));
                byte[] imageByte = cursor.getBlob(cursor.getColumnIndex(UserInterface.ITEM_IMAGE));
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                RssFeedModel rssFeedModel = new RssFeedModel(title, pubDate, discription, bitmap);
                dataList.add(rssFeedModel);
                cursor.moveToNext();
            }
            this.close();
            return dataList;
        } else {
            this.close();
            return null;
        }


    }
}

