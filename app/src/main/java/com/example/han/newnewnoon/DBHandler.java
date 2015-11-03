package com.example.han.newnewnoon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHandler {
    private NoonDatabase helper;
    private SQLiteDatabase db;

    private DBHandler(Context ctx) {
        this.helper = new NoonDatabase(ctx);
        this.db = helper.getWritableDatabase();
    }

    public static DBHandler open(Context ctx) throws SQLException {
        DBHandler handler = new DBHandler(ctx);

        return handler;
    }

    public void close() {
        helper.close();
    }

    public long insert2(String car_name) {
        ContentValues values = new ContentValues();
        values.put("car_name", car_name);

        return db.insert("cars", null, values);
    }

    public Cursor select(int id) throws SQLException {
        Cursor cursor = db.query(true, "cars", new String[] { "_id", "car_name" }, "_id"
                + "=" + id, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor selectAll() throws SQLException {
        Cursor cursor = db.query(true, "cars",
                new String[] {"_id", "car_name"},
                null,
                null,null, null, null, null);

        if (cursor != null) { cursor.moveToFirst(); }

        return cursor;
    }

    public long insert(String str) {
        ContentValues values = new ContentValues();
        values.put("_id", 0);
        values.put("checke", str);
        Log.i("widget","click->DBinsert:"+str);
        return db.insert("check",null,values);
    }
}
