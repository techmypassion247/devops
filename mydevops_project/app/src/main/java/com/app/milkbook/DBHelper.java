package com.app.milkbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "UserInfo.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_USER_INFO = "user_info";
    private static final String TABLE_RATE_SETUP = "Rate_setup";
    private static final String TABLE_DATA_ENTRY = "Data_Entry";

    // Columns for user_info table
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_MOBILE = "mobile";
    private static final String COLUMN_ADDRESS = "address";

    // Columns for Rate_setup table
    private static final String COLUMN_RATE_ID = "id";
    private static final String COLUMN_FAT_RATE = "FAT_RATE";
    private static final String COLUMN_SNF = "SNF";
    private static final String COLUMN_SNF_RATE = "SNF_RATE";

    // Columns for Data_Entry table
    private static final String COLUMN_ENTRY_ID = "entry_id";
    private static final String COLUMN_CUSTOMER_ID = "customer_id";
    private static final String COLUMN_FAT = "FAT";
    private static final String COLUMN_WEIGHT = "Weight";
    private static final String COLUMN_PERLITRE = "perLitre";
    private static final String COLUMN_RESULT = "result";
    private static final String COLUMN_ENTRY_DATE = "entry_date";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create user_info table
        String CREATE_USER_INFO_TABLE = "CREATE TABLE " + TABLE_USER_INFO + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_MOBILE + " TEXT,"
                + COLUMN_ADDRESS + " TEXT" + ")";
        db.execSQL(CREATE_USER_INFO_TABLE);

        // Create Rate_setup table
        String CREATE_RATE_SETUP_TABLE = "CREATE TABLE " + TABLE_RATE_SETUP + "("
                + COLUMN_RATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FAT_RATE + " TEXT,"
                + COLUMN_SNF + " TEXT,"
                + COLUMN_SNF_RATE + " TEXT" + ")";
        db.execSQL(CREATE_RATE_SETUP_TABLE);

        // Create Data_Entry table
        String CREATE_DATA_ENTRY_TABLE = "CREATE TABLE " + TABLE_DATA_ENTRY + "("
                + COLUMN_ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CUSTOMER_ID + " INTEGER,"
                + COLUMN_FAT + " REAL,"
                + COLUMN_WEIGHT + " REAL,"
                + COLUMN_PERLITRE + " REAL,"
                + COLUMN_RESULT + " REAL,"
                + COLUMN_ENTRY_DATE + " TEXT DEFAULT (datetime('now','localtime'))" + ")";
        db.execSQL(CREATE_DATA_ENTRY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables and recreate them on upgrade
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATE_SETUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA_ENTRY);
        onCreate(db);
    }

    // Method to insert data into the user_info table
    public boolean insertUserInfo(String name, String mobile, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_MOBILE, mobile);
        contentValues.put(COLUMN_ADDRESS, address);

        long result = db.insert(TABLE_USER_INFO, null, contentValues);
        return result != -1; // Returns true if data is inserted successfully
    }

    // Method to retrieve all user info data
    public Cursor getAllUserInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USER_INFO, null);
    }

    // Method to insert data into the Rate_setup table
    public boolean insertRateSetup(String fatRate, String snf, String snfRate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FAT_RATE, fatRate);
        contentValues.put(COLUMN_SNF, snf);
        contentValues.put(COLUMN_SNF_RATE, snfRate);

        long result = db.insert(TABLE_RATE_SETUP, null, contentValues);
        return result != -1; // Returns true if data is inserted successfully
    }

    // Method to retrieve all rate setup data
    public Cursor getAllRateSetup() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_RATE_SETUP, null);
    }

    // Method to insert data into the Data_Entry table
    public boolean insertDataEntry(int customerId, double fat, double weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CUSTOMER_ID, customerId);
        contentValues.put(COLUMN_FAT, fat);
        contentValues.put(COLUMN_WEIGHT, weight);

        long result = db.insert(TABLE_DATA_ENTRY, null, contentValues);
        return result != -1; // Returns true if data is inserted successfully
    }

    // Method to retrieve all data entries
    public Cursor getAllDataEntries() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_DATA_ENTRY, null);
    }
}
