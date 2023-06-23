package com.example.colormatrix.DBHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CellsDBHelper extends SQLiteOpenHelper {
    private final Context context;
    private static final String DATABASE_NAME = "Cells.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "cells";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_RED = "red";
    private static final String COLUMN_GREEN = "green";
    private static final String COLUMN_BLUE = "blue";


    public CellsDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_RED + " INTEGER, " +
                        COLUMN_GREEN + " INTEGER, " +
                        COLUMN_BLUE + " INTEGER); ";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addColor(int red, int green, int blue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RED, red);
        cv.put(COLUMN_GREEN, green);
        cv.put(COLUMN_BLUE, blue);
        long result = db.insert(TABLE_NAME, null, cv);
    }

    public void createFirstData(){
        for(int i =0; i<64; i++){
            addColor(122,122,122);
        }
    }

    public void updateData(String row_id,int red, int green, int blue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_RED, red);
        cv.put(COLUMN_GREEN, green);
        cv.put(COLUMN_BLUE, blue);
        long result = db.update(TABLE_NAME, cv, "id=?", new String[]{row_id});
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

}
