package com.example.nayan.rssrecipe;

/**
 * Created by Nayan on 6/11/2017.
 */

public interface UserInterface {

    String DATABASE_NAME = "appData.db";
    int DATABASE_VERSION = 1;
    String DB_PATH = "/data/data/com.example.nayan.rssrecipe/databases/";
    String TABLE_NAME = "user_info";
    String ITEM_ID = "id";
    String ITEM_TITLE = "title";
    String ITEM_TYPE = "type";
    String ITEM_PUBDATE = "pubdate";
    String ITEM_DISCRIPTION = "discription";
    String ITEM_IMAGE = "image";

    String AllITEM = "All";
    String SUPE = "Supe";
    String MEAT = "Meat";
    String SWEET = "Sweet";
    String VORTA = "Vorta";
    String ACHAR = "Achar";
    String CAKE = "Cake";
    String SALAD = "Salad";

}
