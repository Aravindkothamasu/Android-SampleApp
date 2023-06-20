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
        int  Month;
        int  Year;
        boolean isValid;

        CategoryResult( int Value, String Name, int Mnth, int Yr, boolean bValid) {
            CategoryValue = Value;
            CategoryName  = Name;
            Month         = Mnth;
            Year          = Yr;
            isValid       = bValid;
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
        CategoryNameList[6] = context.getString(R.string.CATEGORY7);

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

        if( false == doesTableExist(db, selectedDate)) {
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

    public CategoryResult[] getPeriodicStats( CurrentDate StartDate, CurrentDate EndDate, String selectedCategory ) {
        CategoryResult [] Rslt = null;
        SQLiteDatabase db = this.getReadableDatabase();
        int MonthsCount = 0, Amount = 0;
        CurrentDate temp = new CurrentDate(0,0,0);

        MonthsCount = calculateMonthsCount(StartDate, EndDate);
        Rslt = new CategoryResult[MonthsCount];

        Log.e( mContext.getString(R.string.DB_HANDLER), "MNTHS "+MonthsCount+" "+StartDate.Month+"/"+StartDate.Year+ " To "+EndDate.Month+"/"+EndDate.Year+ " CAT : "+selectedCategory);

        for( int iterator = 0; iterator < MonthsCount; iterator++ ) {
            temp.Year   = StartDate.Year;
            temp.Month  = StartDate.Month;

            for (int i = 0; i < iterator; i++) {
                temp = INCCIRCULARINDEX( temp,12);
            }

            if( false == doesTableExist(db,temp)) {
                Log.e(mContext.getString(R.string.DB_HANDLER), "Table Ledhu  "+ temp.Month+"/"+temp.Year);
                Rslt[iterator] = new CategoryResult( 0, selectedCategory, temp.Month, temp.Year, false);
            } else {
                Amount = readCategoryAmt(db, temp, selectedCategory);
                Rslt[iterator] = new CategoryResult(Amount, selectedCategory, temp.Month, temp.Year, true);
                Log.e(mContext.getString(R.string.DB_HANDLER), "Table Vundhi "+ temp.Month+"/"+temp.Year + " Amt "+Amount );
            }
        }

        db.close();
        return Rslt;
    }

    private CurrentDate INCCIRCULARINDEX( CurrentDate temp, int Len)  {
        if( temp.Month + 1 > Len) {
            temp.Month = 1;
            temp.Year++;
        } else {
            temp.Month++;
        }
        return temp;
    }

    public int calculateMonthsCount(CurrentDate StartDate, CurrentDate EndDate ) {
        int MonthsCount = 0;

        MonthsCount  = ( EndDate.Year - StartDate.Year - 1 ) * 12;
        MonthsCount += 12 - StartDate.Month + EndDate.Month+1; // Adding 1 for including EndDate.Month also
        Log.e(mContext.getString(R.string.DB_HANDLER), "MONTHS COUNT "+MonthsCount);
        return MonthsCount;
    }

    public CategoryResult[] getMonthStats(CurrentDate date) {
        CategoryResult [] Rslt = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Log.e(mContext.getString(R.string.DB_HANDLER), "Inside getMonth Stats : "+date.Month+"/"+date.Year);


        if( false == doesTableExist(db,date)) {
            Log.e(mContext.getString(R.string.DB_HANDLER), "Table Ledhu "+ date.Month+"/"+date.Year);
        } else {
            Log.e(mContext.getString(R.string.DB_HANDLER), "Table Vundhi "+ date.Month+"/"+date.Year);
            Rslt = readAllCategoryAmt(db, date);
        }
        db.close();
        return Rslt;
    }

    public CategoryResult[] readAllCategoryAmt( SQLiteDatabase db, CurrentDate date ) {
        CategoryResult [] Rslt = null;
        int Amount = 0;

        Rslt = new CategoryResult[mContext.getResources().getInteger(R.integer.CATEGORY_COUNT)];

        for ( int iterator= 0; iterator < mContext.getResources().getInteger(R.integer.CATEGORY_COUNT); iterator++) {
            Amount = readCategoryAmt(db, date, CategoryNameList[iterator]);
            //Log.e(mContext.getString(R.string.DB_HANDLER), "CAT :-> "+CategoryNameList[iterator]+ " || AMOUNT :-> "+Amount);

            Rslt[iterator] = new CategoryResult(Amount, CategoryNameList[iterator], date.Month, date.Year, true);
        }
        return Rslt;
    }



    public int readCategoryAmt( SQLiteDatabase db, CurrentDate date, String CategoryName ) {
        Cursor cursor = null;
        int Value = 0;
        String tableName;

        tableName = generateTableName(date);
        // Log.e( mContext.getString(R.string.DB_HANDLER), "readCategoryAmt "+TableName+" "+CategoryName );

        cursor = db.query( tableName, new String[] { mContext.getString(R.string.CATGRY_COL), mContext.getString(R.string.AMOUNT_COL)},
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

    static String generateTableName( CurrentDate selectedDate ) {
        String TableName;
        TableName = "TABLE_"+Integer.toString(selectedDate.Month)+"_"+Integer.toString(selectedDate.Year);
        return TableName;
    }

    public boolean doesTableExist(SQLiteDatabase db,CurrentDate date) {
        String tableName;

        tableName = generateTableName(date);

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);

        if( db == null ) {
            Log.e( mContext.getString(R.string.DB_HANDLER), "DB IS NULL");
        }
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                //Log.e( mContext.getString(R.string.DB_HANDLER), "doesTableExist : true");
                return true;
            }
            cursor.close();
        }
        return false;
    }
}