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

public class DataBaseHelper extends SQLiteOpenHelper implements UserInterface {

    private Context context;


    public DataBaseHelper(Context context) throws IOException {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        createDataBase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // sqLiteDatabase.execSQL("CREATE TABLE '"+ TABLE_NAME +"' ('"+ ITEM_ID +"' integer PRIMARY KEY AUTOINCREMENT,'"+ ITEM_TITLE +"' text NOT NULL,'"+ITEM_TYPE+"' text NOT NULL,'"+ITEM_PUBDATE+"' text,'"+ITEM_DISCRIPTION+"' text,'"+ITEM_IMAGE+"' blob);");
        //Toast.makeText(context, "Table "+ TABLE_NAME +" Create",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }


    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            //do nothing - database already exist
            //Toast.makeText(context, "Database Exits", Toast.LENGTH_LONG).show();
        } else {

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

    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

            //database does't exist yet.
            System.err.println(e.getMessage());
            //Toast.makeText(context, "Database Not Fount", Toast.LENGTH_LONG).show();

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null;
    }

    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
        //Toast.makeText(context, "Database import Successfully", Toast.LENGTH_LONG).show();
    }

}