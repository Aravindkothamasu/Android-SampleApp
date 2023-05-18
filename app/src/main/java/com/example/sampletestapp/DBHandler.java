package com.example.sampletestapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


class CategoryResult {
        String CategoryName;
        int  CategoryValue;
        CategoryResult( int Value, String Name ) {
            CategoryValue = Value;
            CategoryName  = Name;
        }
}


public class DBHandler extends SQLiteOpenHelper {
    // below variable is for our table name.
    private String TABLE_NAME ="";
    public  String []CategoryNameList;
    private final Context mContext;

    // creating a constructor for our database handler.
    public DBHandler(Context context, String DB_NAME) {

        super(context, DB_NAME, null, context.getResources().getInteger(R.integer.DB_VERSION));
        mContext = context;

        CategoryNameList = new String[context.getResources().getInteger(R.integer.CATEGORY_COUNT)];
        CategoryNameList[0] = context.getString(R.string.CATEGORY1);
        CategoryNameList[1] = context.getString(R.string.CATEGORY2);
        CategoryNameList[2] = context.getString(R.string.CATEGORY3);
        CategoryNameList[3] = context.getString(R.string.CATEGORY4);
        CategoryNameList[4] = context.getString(R.string.CATEGORY5);
        CategoryNameList[5] = context.getString(R.string.CATEGORY6);

        Log.e(context.getString(R.string.DB_HANDLER),"InDBHandler");
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        Log.e( mContext.getString(R.string.DB_HANDLER), "OnCreate Called");
    }

    private void createTable(SQLiteDatabase db, CurrentDate selectedDate ) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + mContext.getString(R.string.ID_COL )+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + mContext.getString(R.string.DATE_COL) + " INTEGER, "
                + mContext.getString(R.string.CATGRY_COL) + " TEXT, "
                + mContext.getString(R.string.ITEM_COL)  + " TEXT, "
                + mContext.getString(R.string.AMOUNT_COL) + " INTEGER )";

        Log.e( mContext.getString(R.string.DB_HANDLER), query);

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewCourse( CurrentDate selectedDate, String enteredItem, int enteredAmt, String selectedCategory ) {
        TABLE_NAME = generateTableName(selectedDate);

        // Log.e( mContext.getString(R.string.DB_HANDLER), "Before Calling getWritable Database");
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e( mContext.getString(R.string.DB_HANDLER), "Database "+ TABLE_NAME);

        if( false == doesTableExist(db, TABLE_NAME)) {
            createTable(db, selectedDate);
        }

        if( mContext.getString(R.string.CATEGORY_STOCK_RTN) == selectedCategory) {
            enteredAmt = -1 * enteredAmt;
        }
        Log.e( mContext.getString(R.string.DB_HANDLER), TABLE_NAME + " :-> "+enteredItem +" | "+enteredAmt+" | "+selectedCategory);

        ContentValues values = new ContentValues();

        values.put(  mContext.getString(R.string.DATE_COL), selectedDate.DayOfMonth );
        values.put(mContext.getString(R.string.CATGRY_COL), selectedCategory );
        values.put(  mContext.getString(R.string.ITEM_COL), enteredItem);
        values.put(mContext.getString(R.string.AMOUNT_COL), enteredAmt);

        db.insert(TABLE_NAME, null, values);
        db.close();

        Log.e( mContext.getString(R.string.DB_HANDLER), "addNewCourse Close Called");
    }

    public CategoryResult[] getMonthStats(CurrentDate date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String TableName = null;

        Log.e(mContext.getString(R.string.DB_HANDLER), "Inside getMonth Stats : "+date.Month+"/"+date.Year);
        TableName = generateTableName(date);

        if( false == doesTableExist(db, TableName)) {
            Log.e(mContext.getString(R.string.DB_HANDLER), "Table Ledhu "+TableName);
        } else {
            Log.e(mContext.getString(R.string.DB_HANDLER), "Table Vundhi "+TableName);
            return readAllCategoryAmt(db, TableName);
        }
        return null;
    }

    public CategoryResult[] readAllCategoryAmt( SQLiteDatabase db, String TableName ) {
        CategoryResult [] Rslt = null;
        int Amount = 0;

        Rslt = new CategoryResult[mContext.getResources().getInteger(R.integer.CATEGORY_COUNT)];

        for ( int iterator= 0; iterator < mContext.getResources().getInteger(R.integer.CATEGORY_COUNT); iterator++) {
            Amount = readCategoryAmt(db, TableName, CategoryNameList[iterator]);
            //Log.e(mContext.getString(R.string.DB_HANDLER), "CAT :-> "+CategoryNameList[iterator]+ " || AMOUNT :-> "+Amount);

            Rslt[iterator] = new CategoryResult(Amount, CategoryNameList[iterator]);
            Amount = 0;
        }
        return Rslt;
    }



    public int readCategoryAmt( SQLiteDatabase db, String TableName, String CategoryName ) {
        Cursor cursor = null;
        int Value = 0;

        // Log.e( mContext.getString(R.string.DB_HANDLER), "readCategoryAmt "+TableName+" "+CategoryName );

        cursor = db.query(TableName, new String[] { mContext.getString(R.string.CATGRY_COL), mContext.getString(R.string.AMOUNT_COL)},
                 mContext.getString(R.string.CATGRY_COL) + " =?", new String[] {CategoryName}, null, null, null);
                 // null , null, null, null, null); // TODO : To Read all the elements, use this statement.

        if( cursor != null ) {
            // Log.e( mContext.getString(R.string.DB_HANDLER), "CURSOR COUNT : "+cursor.getCount());
            cursor.moveToFirst();

            for( int i=0; i < cursor.getCount(); i++ ) {
                // Log.e( mContext.getString(R.string.DB_HANDLER), "CAT-> "+ cursor.getString(0) +" AMT " + cursor.getLong(1) );
                Value += cursor.getLong(1);
                cursor.moveToNext();
            }
            cursor.close();
        } else {
            Log.e( mContext.getString(R.string.DB_HANDLER), "CURSOR IS NULL");
            Value = -1;
        }
        return Value;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        Log.e( mContext.getString(R.string.DB_HANDLER), "onUpgrade Called");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /*
    public boolean tableExists(SQLiteDatabase db, String table) {
        boolean result = false;

        Log.e( mContext.getString(R.string.DB_HANDLER), "TABLE NAME : "+table);
        String sql = "select count(*) xcount from sqlite_master where type='table' and name='"
                + table + "'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        if (cursor.getInt(0) > 0)
            result = true;
        cursor.close();
        Log.e( mContext.getString(R.string.DB_HANDLER), "istableExits : "+result);
        return result;
    }
     */

    static String generateTableName( CurrentDate selectedDate ) {
        String TableName;
        TableName = "TABLE_"+Integer.toString(selectedDate.Month)+"_"+Integer.toString(selectedDate.Year);
        return TableName;
    }

    public boolean doesTableExist(SQLiteDatabase db, String tableName) {
        Log.e( mContext.getString(R.string.DB_HANDLER), "TABLE NAME : "+ tableName);

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);

        if( db == null ) {
            Log.e( mContext.getString(R.string.DB_HANDLER), "DB IS NULL");
        }
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                Log.e( mContext.getString(R.string.DB_HANDLER), "doesTableExist : true");
                return true;
            }
            cursor.close();
        }
        Log.e( mContext.getString(R.string.DB_HANDLER), "doesTableExist : false");
        return false;
    }
}