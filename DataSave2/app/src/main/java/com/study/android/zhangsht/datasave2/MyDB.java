package com.study.android.zhangsht.datasave2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhangsht on 2016/11/20.
 */

public class MyDB extends SQLiteOpenHelper {
    private static final String DB_Name = "BirthDayReminder";
    private static final String Table_Name = "Contacts";
    private static final int DB_Version = 1;

    public MyDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MyDB(Context context) {
        super(context, DB_Name, null, DB_Version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_Table = "CREATE TABLE if not exists "
                + Table_Name
                + " (_id INTEGER PRIMARY KEY, name TEXT, birth TEXT, gift TEXT)";
        db.execSQL(Create_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insert2DB(String name, String birth, String gift) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("birth", birth);
        cv.put("gift", gift);
        long res = db.insert(Table_Name, null, cv);
        db.close();
    }

    public void updateDB(String name, String birth, String gift) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("birth", birth);
        cv.put("gift", gift);
        String whereClause = "name=?";
        String [] whereArgs = {name};
        long res = db.update(Table_Name, cv, whereClause, whereArgs);
        db.close();
    }

    public void deleteDB(String name) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "name=?";
        String [] whereArgs = {name};
        long res = db.delete(Table_Name, whereClause, whereArgs);
        db.close();
    }

    public boolean isExists(String name) {
        SQLiteDatabase db = getWritableDatabase();
        String [] colums = {"name"};
        String whereClause = "name=?";
        String [] whereArgs = {name};
        Cursor cursor = db.query(Table_Name, colums, whereClause, whereArgs, null, null, null);
        boolean ans = cursor.getCount() != 0;
        cursor.close();
        db.close();
        return ans;
    }
    public Cursor queryDB(String name, String birth, String gift) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(Table_Name, new String[] {"name"}, null, null, null, null, null);
        db.close();
        return cursor;
    }
    public Cursor getAll() {
        SQLiteDatabase db = getWritableDatabase();
        String[] tableColumns = {"_id", "name", "birth", "gift"};
        Cursor c = db.query(Table_Name, tableColumns,
                null, null, null, null, null);
        return c;
    }
}
