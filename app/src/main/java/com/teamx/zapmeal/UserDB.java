package com.teamx.zapmeal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class UserDB extends SQLiteOpenHelper {
    Context context;
    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageToBytes;
    private static final String DATABASE_NAME = "UserDB.db";
   //private static final String DATABASE_NAME = "FoodDB.db";
    private static final int DATABASE_VERSION = 1;
    // table name
    public static final String TABLE_USER = "user_choice";
    public static final String TABLE_FOOD = "user_food";
    // table columns
    public static final String COL_USER = "user";
    public static final String COL_FOOD_NAME = "foodname";
    public static final String COL_RATING = "rating";
    public static final String COL_PRICE = "price";
    public static final String COL_Quantity = "quantity";
    public static final String KEY_ID = "id";
    public static final String KEY_IMG_URL = "foodimage";
    //table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_FOOD_NAME + " TEXT, "+ COL_Quantity + " TEXT, "+ COL_USER + " TEXT" + ")";
    private static final String CREATE_TABLE_FOOD= "CREATE TABLE " + TABLE_FOOD + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_IMG_URL + " BLOB, "+ COL_FOOD_NAME + " TEXT, "+ COL_PRICE + " TEXT, " + COL_RATING + " TEXT" + ")";


    public UserDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_FOOD);
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

    // method to store food items in a database
  /*  public void putData(ModelClass modelClass){
        SQLiteDatabase objectSqliteDatabase = this.getWritableDatabase();
        Bitmap imageToStoreBitmap = modelClass.getImage();

        objectByteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);

        imageToBytes = objectByteArrayOutputStream.toByteArray();
        ContentValues objectContentValues = new ContentValues();

        try{
            objectContentValues.put(COL_FOOD_NAME, modelClass.getName());
            objectContentValues.put(COL_PRICE, modelClass.getPrice());
            objectContentValues.put(KEY_IMG_URL, imageToBytes);

            long checkIfQueryRuns = objectSqliteDatabase.insert(TABLE_FOOD, null, objectContentValues);

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

    public ArrayList<ModelClass> getAllData() {
        try {
            SQLiteDatabase objectSQLiteDatabase = this.getReadableDatabase();
            ArrayList<ModelClass> objectModelClassList = new ArrayList<>();

            Cursor objectCursor = objectSQLiteDatabase.rawQuery("select * from "+TABLE_FOOD, null);
            if(objectCursor.getCount() !=0) {
                while (objectCursor.moveToNext()) {
                    byte [] imageByte = objectCursor.getBlob(0);
                    String foodname = objectCursor.getString(1);
                    String price = objectCursor.getString(2);
                    String rating = objectCursor.getString(3);

                    Bitmap objectBitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                    objectModelClassList.add(new ModelClass(price, rating, foodname,objectBitmap));

                }
                return objectModelClassList;
            }
            else {
                Toast.makeText(context, "No value in database", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }*/
    // public void retrieve Purchase Data
    public void retrievePurchaseData(){

    }
    // store user purchase data
    public void storePurchaseData(){

    }
}
