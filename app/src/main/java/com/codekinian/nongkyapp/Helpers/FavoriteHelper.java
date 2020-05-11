package com.codekinian.nongkyapp.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static com.codekinian.nongkyapp.Helpers.FavoriteColumn.COLUMN_PLACE_ID;

public class FavoriteHelper {
    private static String TABLE_NAME = FavoriteColumn.TABLE_NAME;

    private SQLiteDatabase database;
    private Context context;
    private DBHelper databaseHelper;

    public FavoriteHelper(Context context) {
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException {
        databaseHelper = new DBHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public Cursor queryProvider() {
        return database.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(
                TABLE_NAME,
                null,
                COLUMN_PLACE_ID + " = ?",
                new String[]{id},
                null,
                null,
                null
        );
    }

    public long insertProvider(ContentValues values) {
        return database.insert(
                TABLE_NAME,
                null,
                values
        );
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(
                TABLE_NAME,
                values,
                COLUMN_PLACE_ID + " = ?",
                new String[]{id}
        );
    }

    public int deleteProvider(String id) {
        return database.delete(
                TABLE_NAME,
                COLUMN_PLACE_ID + " = ?",
                new String[]{id}
        );
    }
}
