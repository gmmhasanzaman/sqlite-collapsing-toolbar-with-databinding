package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "contact.db";
    public static final String TABLE_NAME = "contact_details";
    public static final String COL_ID = "Id";
    public static final String COL_NAME = "Name";
    public static final String COL_PHONE = "Phone";
    public static final String COL_EMAIL = "Email";
    public static final int DB_VERSION = 1;

    private Context context;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
            + COL_ID + " INTEGER PRIMARY KEY,"
            + COL_NAME + " TEXT,"
            + COL_PHONE + " TEXT, "
            + COL_EMAIL + " TEXT );";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        } catch (Exception e) {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertData(String name, String phone, String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_PHONE, phone);
        contentValues.put(COL_EMAIL, email);

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long id = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();

        return id;

    }

    public Cursor getContact(){
        String allContact = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(allContact,null);

        return cursor;
    }
}
