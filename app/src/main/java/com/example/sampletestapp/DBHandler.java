package com.example.sampletestapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHandler extends SQLiteOpenHelper {
    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "SampleDB_3.sqlite";
    // below int is our database version
    private static final int DB_VERSION = 1;
    // below variable is for our table name.
    private static final String TABLE_NAME = "mycourses";
    // below variable is for our id column.
    private static final String ID_COL = "id";
    // below variable is for date of payment done.
    private static final String DATE_COL = "Date";
    // below variable is for our Amount to be enter
    private static final String AMOUNT_COL = "Amount";


    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        Log.e( "DBHAND", "DB_HANDLER CALLED");
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE_COL + " TEXT,"
                + AMOUNT_COL + " INTEGER)";

        Log.e( "DBHAND", query);

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewCourse(String courseName, CurrentDate selectedDate) {
        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        String StringDate;

        StringDate = Integer.toString(selectedDate.DayOfMonth) + "/" + Integer.toString(selectedDate.Month) + "/" + Integer.toString(selectedDate.Year);
        Log.e( "DBHAND", "addNewCource Called " + StringDate );

        SQLiteDatabase db = this.getWritableDatabase();
        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();
        // on below line we are passing all values
        // along with its key and value pair.
        values.put(DATE_COL, StringDate );
        values.put(AMOUNT_COL, courseName);
        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);
        // at last we are closing our
        // database after adding database.
        db.close();
        Log.e( "DBHAND", "addNewCourse Close Called");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        Log.e( "DBHAND", "onUpgrade Called");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}