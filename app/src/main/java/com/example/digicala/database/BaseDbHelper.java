package com.example.digicala.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


import com.example.digicala.entity.Product;

import java.util.ArrayList;

public class BaseDbHelper extends SQLiteOpenHelper {


    static final String DATABASE_NAME = "DigiCala";
    static final String TABLE_NAME = "products";
    static final int DATA_BASE_VERSION = 1;
    static final String FIELD_ID = "id";
    static final String FIELD_TITLE = "title";
    static final String FIELD_PRICE = "price";
    static final String FIELD_DISCOUNT = "discount";
    static final String FIELD_INFORMATION = "information";
    static final String FIELD_IMAGEID = "img_id";
    static final String FIELD_CAT = "cat";
    static final String FIELD_RATE = "rate";
    static final String FIELD_EXISTS = "exist";
    static final String FIELD_ORDER = "orders";
    static final String FIELD_ISIMAGE = "isimage";


    private static final String TAG = "BaseDbHelper";

    public BaseDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATA_BASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        creattable(db);
        Log.e(TAG, "onCreate: ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void creattable(SQLiteDatabase db) {


        String query = "create table if not exists " + TABLE_NAME + " ( " + FIELD_ID + " integer primary key ," + FIELD_TITLE + " varchar(60), " + FIELD_PRICE + " varchar(50)  , " + FIELD_INFORMATION + " varchar(200) , " + FIELD_IMAGEID + " varchar(200) , " + FIELD_EXISTS + " boolean , " + FIELD_DISCOUNT + " integer , " + FIELD_CAT + " varchar(20) , " + FIELD_RATE + " integer , " + FIELD_ORDER + " varchar(20) , " + FIELD_ISIMAGE + " varchar(20))";
        db.execSQL(query);
    }


}

