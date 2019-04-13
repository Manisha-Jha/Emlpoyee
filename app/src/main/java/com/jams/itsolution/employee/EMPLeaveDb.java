package com.jams.itsolution.employee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by kalpesh on 12/13/2017.
 */

public class EMPLeaveDb extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "EMPLeaveDb";

    // ContactItems table name
    private static final String TABLE_NAME = "EMPDB";

    // ContactItems Table Columns names
    private static final String EMP_ID = "EMP_ID";
    private static final String LEAVE = "LEAVE";

    @Nullable
    private SQLiteDatabase mDatabase;


    public EMPLeaveDb(@NonNull Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
        mDatabase = EMPLeaveDb.this.getWritableDatabase();
    }


    // Creating Tables
    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + '(' + EMP_ID + " TEXT ," + LEAVE + " TEXT " + ')';
        db.execSQL(CREATE_CONTACTS_TABLE);

    }


    // Upgrading database
    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public synchronized boolean Add(String empID,String leave){
        mDatabase = openIfNecessary();
        ContentValues contentTab1 = new ContentValues();
        contentTab1.put(EMP_ID , empID);
        contentTab1.put(LEAVE , leave);
        long result = mDatabase.insert(TABLE_NAME,null,contentTab1);
        mDatabase.close();

        if(result<1) {
            return false;
        }else{
            return true;
        }
    }

    public synchronized boolean checkIsAvalable(@NonNull String empID){

        mDatabase = openIfNecessary();

        String Query = "Select * from " + TABLE_NAME + " where " + EMP_ID + " = '"+empID+"'";
        Cursor cursor = mDatabase.rawQuery(Query, null);
        //Log.i("my","quary ->"+Query);
        if(cursor.getCount() <= 0){
            cursor.close();

            return false;
        }else {

            cursor.close();
            return true;
        }

    }

    public synchronized String getDays(@NonNull String empID){

        mDatabase = openIfNecessary();

        String Query = "Select * from " + TABLE_NAME + " where " + EMP_ID + " = '"+empID+"'";
        Cursor cursor = mDatabase.rawQuery(Query, null);
        //Log.i("my","quary ->"+Query);
        String value="0";
        if(cursor.getCount() <= 0){
            cursor.close();

            return value;
        }else {


            if (cursor.moveToFirst()){

                value = cursor.getString(cursor.getColumnIndex(LEAVE));
            }
            cursor.close();

            return value;
        }

    }
    public synchronized void deleteImag(@NonNull String empID) {
        //mDatabase = openIfNecessary();
        // mDatabase.delete(TABLE_NAME, EMP_ID + " = ?", new String[]{empID});
    }

    @NonNull
    private SQLiteDatabase openIfNecessary() {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = this.getWritableDatabase();
        }
        return mDatabase;
    }



    public synchronized Cursor getEMPList() {
        mDatabase = openIfNecessary();

        String Query = "Select * from " + TABLE_NAME ;
        Cursor cursor = mDatabase.rawQuery(Query, null);

        return cursor;
    }




}
