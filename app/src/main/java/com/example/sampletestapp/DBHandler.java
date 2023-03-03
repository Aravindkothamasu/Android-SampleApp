package com.example.sampletestapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



public class DBHandler extends SQLiteOpenHelper {
    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "SampleDB_4.sqlite";
    // below int is our database version
    private static final int DB_VERSION = 1;
    // below variable is for our table name.
    private String TABLE_NAME ="";
    // below variable is for our id column.
    private static final String ID_COL = "id";
    // below variable is for date of payment done.
    private static final String DATE_COL = "Date";
    // below variable is for our Amount to be enter
    private static final String AMOUNT_COL = "Amount";
    // below variable is for Item Purchase is used( Anthey Dheniki vadeamo ani)
    private static final String ITEM_COL = "ItemPurchase";


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
        Log.e( "DB_HANDLER", "OnCreate Called");
    }

    private void createTable(SQLiteDatabase db, CurrentDate selectedDate ) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE_COL + " INTEGER,"
                + ITEM_COL  + " TEXT, "
                + AMOUNT_COL + " INTEGER )";

        Log.e( "DB_HANDLER", query);

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }
    // this method is use to add new course to our sqlite database.
    public void addNewCourse( CurrentDate selectedDate, String enteredItem, int enteredAmt ) {

        TABLE_NAME = generateTableName(selectedDate);
        Log.e( "DBHAND", "TABLE_NAME : "+ TABLE_NAME);

        // Log.e( "DB_HANDLER", "Before Calling getWritable Database");
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e( "DB_HANDLER", "After Calling getWritable Database "+TABLE_NAME);

        if( false == doesTableExist(db, TABLE_NAME)) {
            createTable(db, selectedDate);
        }

        ContentValues values = new ContentValues();

        values.put(  DATE_COL, selectedDate.DayOfMonth );
        values.put(  ITEM_COL, enteredItem);
        values.put(AMOUNT_COL, enteredAmt);

        db.insert(TABLE_NAME, null, values);
        db.close();

        Log.e( "DB_HANDLER", "addNewCourse Close Called");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        Log.e( "DB_HANDLER", "onUpgrade Called");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /*
    public boolean tableExists(SQLiteDatabase db, String table) {
        boolean result = false;

        Log.e( "DB_HANDLER", "TABLE NAME : "+table);
        String sql = "select count(*) xcount from sqlite_master where type='table' and name='"
                + table + "'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        if (cursor.getInt(0) > 0)
            result = true;
        cursor.close();
        Log.e( "DB_HANDLER", "istableExits : "+result);
        return result;
    }
     */

    static String generateTableName( CurrentDate selectedDate ) {
        String TableName;
        TableName = "TABLE_"+Integer.toString(selectedDate.Month)+"_"+Integer.toString(selectedDate.Year);
        return TableName;
    }

    public boolean doesTableExist(SQLiteDatabase db, String tableName) {
        Log.e( "DB_HANDLER", "TABLE NAME : "+ tableName);

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);

        if( db == null ) {
            Log.e( "DB_HANDLER", "DB IS NULL");
        }
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                Log.e( "DB_HANDLER", "doesTableExist : true");
                return true;
            }
            cursor.close();
        }
        Log.e( "DB_HANDLER", "doesTableExist : false");
        return false;
    }
}