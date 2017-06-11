package com.example.nayan.rssrecipe;

/**
 * Created by home on 7/20/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "libraryManagementDataBase.db";
    private static final int DATABASE_VERSION = 1;
    public static final String DB_PATH = "/data/data/librarymanagement.we.a4bit.com.libarymanagmentsystem/databases/";

    public static final String USER_ID = "id";
    public static final String FULL_NAME = "fullName";
    public static final String PHONE_NO = "phone";
    public static final String USER_NAME = "username";
    public static final String PASSWORD = "password";
    public static final String STD_ID = "stdId";
    public static final String TABLE_USERINFO = "user_info";

    public static final String TABLE_BOOKLIST = "book_list";
    public static final String BOOK_ID = "book_id";
    public static final String BOOK_NAME = "book_name";
    public static final String BOOK_AUTHOR = "book_author";
    public static final String BOOK_DISCRIPTION = "book_discripation";

    public static final String TABLE_BORROWBOOK = "borrow_book";
    public static final String BORROW_ID = "borrow_id";
    public static final String BORROW_DATE = "borrow_date";
    public static final String BORROW_RETURN_DATE = "return_date";
    public static final String BORROW_STATUS = "borrow_status";


    private Context context;


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sqLiteDatabase.execSQL(CREATE_USERINFO_TABLE);
        sqLiteDatabase.execSQL("CREATE TABLE '"+TABLE_USERINFO+"' ('"+USER_ID+"' integer PRIMARY KEY AUTOINCREMENT,'"+FULL_NAME+"' text NOT NULL,'"+PHONE_NO+"' text NOT NULL,'"+USER_NAME+"' text NOT NULL,'"+PASSWORD+"' text NOT NULL);");
        Toast.makeText(context, "Table "+TABLE_USERINFO+" Create",Toast.LENGTH_LONG).show();
        sqLiteDatabase.execSQL("CREATE TABLE '"+TABLE_BOOKLIST+"' ('"+BOOK_ID+"' integer PRIMARY KEY AUTOINCREMENT,'"+BOOK_NAME+"' text NOT NULL,'"+BOOK_AUTHOR+"' text NOT NULL,'"+BOOK_DISCRIPTION+"' text NOT NULL);");
        Toast.makeText(context, "Table "+TABLE_BOOKLIST+" Create",Toast.LENGTH_LONG).show();
        sqLiteDatabase.execSQL("CREATE TABLE '"+TABLE_BORROWBOOK+"' ('"+BORROW_ID+"' integer PRIMARY KEY AUTOINCREMENT,'"+USER_ID+"' integer NOT NULL,'"+BOOK_ID+"' integer NOT NULL,'"+BORROW_DATE+"' text NOT NULL,'"+BORROW_RETURN_DATE+"' text NOT NULL,'"+BORROW_STATUS+"' text NOT NULL,FOREIGN KEY('"+USER_ID+"') REFERENCES '"+TABLE_USERINFO+"'('"+USER_ID+"'),FOREIGN KEY('"+BOOK_ID+"') REFERENCES '"+TABLE_BOOKLIST+"'('"+BOOK_ID+"'))");
        Toast.makeText(context, "Table "+TABLE_BORROWBOOK+" Create",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }


    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
            Toast.makeText(context, "Database Exits", Toast.LENGTH_LONG).show();
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error(e.getMessage());

            }
        }

    }

    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.
            System.err.println(e.getMessage());
            Toast.makeText(context, "Database Not Fount", Toast.LENGTH_LONG).show();

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
        Toast.makeText(context, "Database import Successfully", Toast.LENGTH_LONG).show();
    }

}