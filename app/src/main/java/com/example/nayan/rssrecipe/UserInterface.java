package com.example.nayan.rssrecipe;

/**
 * Created by Nayan on 6/11/2017.
 */

public interface UserInterface {

    public static final String DATABASE_NAME = "appData.db";
    public static final int DATABASE_VERSION = 1;
    public static final String DB_PATH = "/data/data/com.example.nayan.rssrecipe/databases/";
    public static final String TABLE_NAME = "user_info";
    public static final String ITEM_ID = "id";
    public static final String ITEM_TITLE = "title";
    public static final String ITEM_TYPE = "type";
    public static final String ITEM_PUBDATE = "pubdate";
    public static final String ITEM_DISCRIPTION = "discription";
    public static final String ITEM_IMAGE = "image";

    public static final String AllITEM = "All";
    public static final String SUPE = "Supe";
    public static final String MEAT = "Meat";
    public static final String SWEET = "Sweet";
    public static final String VORTA = "Vorta";
    public static final String ACHAR = "Achar";
    public static final String CAKE = "Cake";
    public static final String SALAD = "Salad";

    public static final String urlLinkAll = "bdsob.com/রেসিপি/rss/";
    public static final String urlLinkSupe = "bdsob.com/স্যুপ-রেসিপি/rss/";
    public static final String urlLinkMeat = "bdsob.com/মাংস/rss/";
    public static final String urlLinkSweet = "bdsob.com/মিষ্টি-রেসিপি/rss/";
    public static final String urlLinkVorta = "bdsob.com/ভর্তা/rss";
    public static final String urlLinkAchar = "bdsob.com/আচার-ও-চাটনি/rss";
    public static final String urlLinkCake = "bdsob.com/পিঠা/rss/";
    public static final String urlLinkSalad = "bdsob.com/সালাদ/rss/";

    public static final String ARG_SECTION_NUMBER = "section_number";

}
