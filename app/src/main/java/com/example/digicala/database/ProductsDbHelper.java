package com.example.digicala.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.digicala.entity.Product;
import com.example.digicala.sharedpreferences.SharePrefernce;

import java.util.ArrayList;

public class ProductsDbHelper extends BaseDbHelper {
    Context context;

    public ProductsDbHelper(@Nullable Context context) {
        super(context);
        this.context = context;
    }


    // SharePrefernce sharePrefernce=new SharePrefernce(context);

    public long insert(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FIELD_ID, product.getId());
        cv.put(FIELD_TITLE, product.getTitle());
        cv.put(FIELD_PRICE, product.getPrice());
        cv.put(FIELD_INFORMATION, product.getInformation());
        cv.put(FIELD_IMAGEID, product.getImg());
        cv.put(FIELD_EXISTS, product.isExists());
        cv.put(FIELD_DISCOUNT, product.getOff());
        cv.put(FIELD_CAT, product.getCat());
        cv.put(FIELD_RATE, 0);
        cv.put(FIELD_ORDER, "false");
        cv.put(FIELD_ISIMAGE, product.isIsimage());

        long row = db.insert(TABLE_NAME, null, cv);
        Log.e(BaseDbHelper.class.getSimpleName(), "insert");
        db.close();
        cv.clear();
        return row;


    }

    public ArrayList<Product> select() {

        ArrayList<Product> resualt = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{FIELD_ID, FIELD_TITLE, FIELD_PRICE, FIELD_INFORMATION, FIELD_IMAGEID, FIELD_EXISTS, FIELD_DISCOUNT, FIELD_CAT, FIELD_RATE, FIELD_ORDER, FIELD_ISIMAGE}, null, null, null, null, null);
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String price = cursor.getString(2);
                String information = cursor.getString(3);
                String image_url = cursor.getString(4);
                String exists = cursor.getString(5);
                int discount = cursor.getInt(6);
                String cat = cursor.getString(7);
                int rate = cursor.getInt(8);
                String order = cursor.getString(9);
                String isimage = cursor.getString(10);


                Product product = new Product(id, title, price, image_url, cat, exists, discount, isimage, information);
                if (isimage.equals("false")) {
                    resualt.add(product);
                }

            }
        }
        Log.e(BaseDbHelper.class.getSimpleName(), "select all");
        db.close();
        cursor.close();
        return resualt;
    }

    public Product selectByid(int id) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{FIELD_ID, FIELD_TITLE, FIELD_PRICE, FIELD_INFORMATION, FIELD_IMAGEID, FIELD_EXISTS, FIELD_DISCOUNT, FIELD_CAT, FIELD_RATE, FIELD_ORDER}, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String image_url = cursor.getString(4);
                String title = cursor.getString(1);
                String price = cursor.getString(2);
                String information = cursor.getString(3);
                String exists = cursor.getString(5);
                int discount = cursor.getInt(6);
                String cat = cursor.getString(7);
                int rate = cursor.getInt(8);
                String order = cursor.getString(9);


                Product product = new Product(id, title, price, image_url, cat, information, "false", exists, discount, rate, order);

                return product;

            }
        }

        db.close();
        cursor.close();
        return null;
    }


    public int record_number() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{FIELD_ID, FIELD_TITLE, FIELD_PRICE, FIELD_INFORMATION, FIELD_IMAGEID}, null, null, null, null, null);
        int size = cursor.getCount();
        db.close();
        cursor.close();
        return size;

    }

    public ArrayList<Product> getcategory(String cat) {

        ArrayList<Product> resualt = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Product product;
        // sharePrefernce=new SharePrefernce(context);
        Cursor cursor = db.query(TABLE_NAME, new String[]{FIELD_ID, FIELD_TITLE, FIELD_PRICE, FIELD_INFORMATION, FIELD_IMAGEID, FIELD_EXISTS, FIELD_DISCOUNT, FIELD_CAT, FIELD_RATE, FIELD_ORDER, FIELD_ISIMAGE}, "cat=?", new String[]{String.valueOf(cat)}, null, null, null);
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                int id = cursor.getInt(0);
                String image_url = cursor.getString(4);
                String title = cursor.getString(1);
                String price = cursor.getString(2);
                String information = cursor.getString(3);
                String exists = cursor.getString(5);
                int discount = cursor.getInt(6);
                int rate = cursor.getInt(8);
                String order = cursor.getString(9);
                String isimage = cursor.getString(10);


                product = new Product(id, title, price, image_url, cat, information, isimage, exists, discount, rate, order);

//if (sharePrefernce.gettheme() && isimage.equals("true")){
//    continue;
//}

                resualt.add(product);
            }

        }

        db.close();
        cursor.close();
        return resualt;
    }


    public long insertorder(String order, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FIELD_ORDER, order);
        long row = db.update(TABLE_NAME, cv, FIELD_ID + "=" + id, null);
        Log.e(BaseDbHelper.class.getSimpleName(), "update");
        db.close();
        cv.clear();
        return row;


    }


    public long insertrate(int rate, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FIELD_RATE, rate);
        long row = db.update(TABLE_NAME, cv, FIELD_ID + "=" + id, null);
        Log.e(BaseDbHelper.class.getSimpleName(), "update");
        db.close();
        cv.clear();
        return row;


    }


    public ArrayList<Product> orderd_data() {

        ArrayList<Product> resualt = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{FIELD_ID, FIELD_TITLE, FIELD_PRICE, FIELD_INFORMATION, FIELD_IMAGEID, FIELD_EXISTS, FIELD_DISCOUNT, FIELD_CAT, FIELD_RATE, FIELD_ORDER, FIELD_ISIMAGE}, null, null, null, null, null);

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String price = cursor.getString(2);
                String information = cursor.getString(3);
                String image_url = cursor.getString(4);
                String exists = cursor.getString(5);
                int discount = cursor.getInt(6);
                String cat = cursor.getString(7);
                int rate = cursor.getInt(8);
                String order = cursor.getString(9);
                String isimage = cursor.getString(10);


                Product product = new Product(id, title, price, image_url, cat, exists, discount, isimage, information);
                if (isimage.equals("false") && order.equals("true")) {

                    resualt.add(product);

                }

            }
        }
        Log.e(BaseDbHelper.class.getSimpleName(), "select all");

        db.close();
        cursor.close();
        return resualt;
    }


    public ArrayList<Product> selectdiscount() {

        ArrayList<Product> resualt = new ArrayList<>();
        SharePrefernce sharePrefernce = new SharePrefernce(context);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{FIELD_ID, FIELD_TITLE, FIELD_PRICE, FIELD_INFORMATION, FIELD_IMAGEID, FIELD_EXISTS, FIELD_DISCOUNT, FIELD_CAT, FIELD_RATE, FIELD_ORDER, FIELD_ISIMAGE}, null, null, null, null, null);
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String price = cursor.getString(2);
                String information = cursor.getString(3);
                String image_url = cursor.getString(4);
                String exists = cursor.getString(5);
                int discount = cursor.getInt(6);
                String cat = cursor.getString(7);
                int rate = cursor.getInt(8);
                String order = cursor.getString(9);
                String isimage = cursor.getString(10);


                if (sharePrefernce.gettheme() && isimage.equals("true")) {
                    continue;
                }

                Product product = new Product(id, title, price, image_url, cat, exists, discount, isimage, information);
                if (discount > 0) {
                    resualt.add(product);
                }

            }
        }
        db.close();
        cursor.close();
        return resualt;
    }


    public String get_order_by_id(int id) {

        ArrayList<Product> resualt = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{FIELD_ID, FIELD_TITLE, FIELD_PRICE, FIELD_INFORMATION, FIELD_IMAGEID, FIELD_EXISTS, FIELD_DISCOUNT, FIELD_CAT, FIELD_RATE, FIELD_ORDER, FIELD_ISIMAGE}, "id=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String order = cursor.getString(9);

                return order;

            }
        }
        Log.e(BaseDbHelper.class.getSimpleName(), "select all");

        db.close();
        cursor.close();
        return null;
    }


}
