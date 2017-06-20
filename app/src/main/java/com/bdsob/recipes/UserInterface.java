package com.bdsob.recipes;

/**
 * Created by Nayan on 6/11/2017.
 */

public interface UserInterface {

    String DATABASE_NAME = "appData.db";
    int DATABASE_VERSION = 1;
    String DB_PATH = "/data/data/com.bdsob.recipes/databases/";
    String TABLE_NAME = "offlinedata";
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

    String urlLinkAll = "bdsob.com/রেসিপি/rss/";
    String urlLinkSupe = "bdsob.com/স্যুপ-রেসিপি/rss/";
    String urlLinkMeat = "bdsob.com/মাংস/rss/";
    String urlLinkSweet = "bdsob.com/মিষ্টি-রেসিপি/rss/";
    String urlLinkVorta = "bdsob.com/ভর্তা/rss";
    String urlLinkAchar = "bdsob.com/আচার-ও-চাটনি/rss";
    String urlLinkCake = "bdsob.com/পিঠা/rss/";
    String urlLinkSalad = "bdsob.com/সালাদ/rss/";

    String ARG_SECTION_NUMBER = "section_number";

}
