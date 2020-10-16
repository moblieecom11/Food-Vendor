package com.teamx.zapmeal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class UserDB extends SQLiteOpenHelper {
    Context context;
    private static final String DATABASE_NAME = "UserDB.db";
    private static final int DATABASE_VERSION = 1;
    // table name
    private static final String TABLE_USER = "user_choice";
    // table columns
    private static final String ID = "id";
    private static final String COL_USER = "user";
    //table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_USER + " TEXT" + ")";


    public UserDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // method to store user's status in a database
    public void storeData(String choice){
        SQLiteDatabase objectSqliteDatabase = this.getWritableDatabase();
        ContentValues objectContentValues = new ContentValues();

        try{
        objectContentValues.put(COL_USER, choice);

        long checkIfQueryRuns = objectSqliteDatabase.insert(TABLE_USER, null, objectContentValues);

        if (checkIfQueryRuns != -1) {
            Toast.makeText(context, "Data Added To the Table", Toast.LENGTH_SHORT).show();
            objectSqliteDatabase.close();
        } else {
            Toast.makeText(context, "Failed to add data", Toast.LENGTH_SHORT).show();
        }
    } catch (Exception e) {
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
    }

    //to retreive data from database
    public String retrieveData() {
        // sqlite object
        SQLiteDatabase objectSQLiteDatabase = this.getReadableDatabase();

        String uchoice =null;

        // cursor object
        Cursor objectCursor = objectSQLiteDatabase.rawQuery("select * from " + TABLE_USER, null);
        if (objectCursor.getCount() != 0) {
            while (objectCursor.moveToLast()) {
                uchoice = objectCursor.getString(1);
            }
        } else {
            Toast.makeText(context, "No value in database", Toast.LENGTH_SHORT).show();
            return null;
        }
        return uchoice;
    }
}
