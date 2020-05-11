package com.codekinian.nongkyapp.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_ID;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_LATITUDE;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_LONGITUDE;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_PHOTO;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_PLACE_ID;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_RATING;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_TITLE;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_VICINITY;
import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.TABLE_NAME;

public class DBHelper extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "hitzgenics";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*TODO REVISI : Kelebihan dan Kekurangan koma*/
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_NEW_TABLE = "create table " + TABLE_NAME + " (" +
                COLUMN_ID + " integer primary key autoincrement, " +
                COLUMN_PLACE_ID + " text unique, " +
                COLUMN_TITLE + " text not null, " +
                COLUMN_LATITUDE + " double not null, " +
                COLUMN_LONGITUDE + " double not null, " +
                COLUMN_RATING + " double not null, " +
                COLUMN_VICINITY + " text not null, " +
                COLUMN_PHOTO + " text not null " + ");";
        sqLiteDatabase.execSQL(CREATE_NEW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
