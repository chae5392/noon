package com.example.han.newnewnoon;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by CHAE on 2015-09-17.
 */
public class NoonDatabase extends SQLiteOpenHelper {

    final public static String TAG = "Database";

    public NoonDatabase (Context context) {
        super(context, "Noon.db", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_SQL1 = "create table if not exists food_pattern ( "
                            + "_id integer primary key autoincrement, "
                            + "a integer, b integer, c integer, d integer, e integer, f integer, g integer);";
        String CREATE_SQL2 = "create table if not exists abode ( "
                            + "_id integer primary key autoincrement, "
                            + "local_name TEXT, addr TEXT, count integer);";
        String CREATE_SQL3 = "create table if not exists food_favorite ( "
                            + "_id integer primary key autoincrement, "
                            + "local_name TEXT, food TEXT, wea TEXT, time TEXT, weight TEXT, date TEXT);";
        String CREATE_SQL4 = "create table if not exists check ( "
                + "_id integer primary key autoincrement, "
                + "checke TEXT);";

        try {
            db.execSQL(CREATE_SQL1);
            db.execSQL(CREATE_SQL2);
            db.execSQL(CREATE_SQL3);
        } catch(Exception ex) {
            Log.e(TAG,"Exception in CREATE_SQL", ex);
        }

        try {
            db.execSQL("insert into food_pattern values (null, 1,1,1,1,1,1,1);");
        } catch(Exception ex) {
            Log.e(TAG,"Exception in INSERT_SQL",ex);
        }
    } // onCreate 끝

    public void onOpen(SQLiteDatabase db) {

    }// onOpen 끝

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " +oldVersion + " to " + newVersion + ".");

        if(newVersion >1) {
            db.execSQL("drop table if exists food_pattern");
            db.execSQL("drop table if exists abode");
            db.execSQL("drop table if exists food_favorite");
            onCreate(db);
        }
    }//onUpgrade 끝끝

}
