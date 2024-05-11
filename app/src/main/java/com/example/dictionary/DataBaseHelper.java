package com.example.dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    // table name
    public static final String FAVORITE_WORDS_TABLE = "FAVORITE_WORDS_TABLE";
    // columns
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_WORD = "WORD";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "myDataBase.db", null, 1);
    }

    // this is called the first time a database is accessed. This should be code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + FAVORITE_WORDS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_WORD + " TEXT)";

        db.execSQL(createTableStatement);

        Log.d("DatabaseHelper", "Database created successfully.");
    }


    // this is called if the database version number changes. It prevents previous users app from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_WORDS_TABLE);
        onCreate(db);

        Log.d("DatabaseHelper", "Database upgraded successfully.");
    }


    public void insertFavoriteWord(String word) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WORD, word);
        long insert = db.insert(FAVORITE_WORDS_TABLE, null, cv);

        db.close();

        Log.d("DatabaseHelper", "Database inserted successfully.");
    }

    public void deleteFavoriteWord(String word) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WORD, word);
        long insert = db.delete(FAVORITE_WORDS_TABLE, COLUMN_WORD + "=?", new String[]{word});

        db.close();
    }

    public List<String> getFavoriteWords() {
        List<String> returnList = new ArrayList<>();

        // get data from database
        String queryString = "SELECT * FROM " + FAVORITE_WORDS_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            // loop through cursor (result set) and put them into the return list.
            do {
                returnList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        else {
            // failure. do not anything to the list.
        }

        // close both the cursor and db when done.
        cursor.close();
        db.close();

        return returnList;
    }
}