package com.example.testapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    public static final String DBNAME = "BeWell.db";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_USERNAME2 = "username";
    public static final String COLUMN_PASSWORD = "password";

    public static final String COLUMN_REPORT = "report";
    public static final String REPORT_TABLE = "reports";
    public static final String COLUMN_ID = "id";
    public static final String TABLE_NAME = "users";

    public DB(@Nullable Context context) {
        super(context, "BeWell.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT" +
                ")";

        String reportTableQuery = "CREATE TABLE " + REPORT_TABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME2 + " TEXT, " +
                COLUMN_REPORT + " TEXT" +
                ")";

        myDB.execSQL(query);

        myDB.execSQL(reportTableQuery);

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, 1);
        cv.put(COLUMN_USERNAME, "admin");
        cv.put(COLUMN_PASSWORD, "admin");
        myDB.insert(TABLE_NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
        myDB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        myDB.execSQL("DROP TABLE IF EXISTS " + REPORT_TABLE);
        onCreate(myDB);
    }

    public boolean insertData(String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_PASSWORD, password);
        long result = myDB.insert(TABLE_NAME, null, cv);
        if (result == -1) return false;
        else
            return true;
    }

    public boolean insertReport(String username, String report) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME2, username);
        cv.put(COLUMN_REPORT, report);
        long result = db.insert(REPORT_TABLE, null, cv);
        if (result == -1) return false;
        else
            return true;
    }

    public boolean removeData(String username, Context context) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        int result = myDB.delete("users", COLUMN_USERNAME + " = ?", new String[]{username});
        if (result == 0) {
            Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where " + COLUMN_USERNAME + " = ?", new String[]{username});
        if (cursor.getCount() > 0) return true;
        else
            return false;
    }

    public boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where " + COLUMN_USERNAME + " = ? and " + COLUMN_PASSWORD + " = ?", new String[]{username, password});
        if (cursor.getCount() > 0) return true;
        else
            return false;
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readAllData2() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COLUMN_USERNAME2, COLUMN_REPORT};
        Cursor cursor = db.query(REPORT_TABLE, columns, null, null, null, null, null);
        return cursor;
    }
}
