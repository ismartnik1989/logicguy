package com.test.colormemorygame.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mukesh 2/Dec/2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ColorMemoryGame.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "MainTable";
    public static final String ID = "id";
    public static final String RANK = "Rank";
    public static final String NAME = "name";
    public static final String SCORE = "score";
    private static DbHelper dbHelper;





    private DbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TABLE_NAME +
                        "(" + ID + " text," +
                        RANK + " text," +
                        NAME + " text ," +
                        SCORE + " INTEGER)"
        );



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }


    public boolean insertData(String tableName,ContentValues contentValues)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(tableName, null, contentValues);

        return true;
    }

    public Cursor getAllData() throws CursorIndexOutOfBoundsException{

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from "+ TABLE_NAME +" ORDER BY score DESC ", null );

        return cursor;
    }
    public boolean isThisHighestScore(int currentScore) throws CursorIndexOutOfBoundsException{

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =  db.rawQuery( "select * from "+ TABLE_NAME +" ORDER BY CAST(gpoint AS REAL) "+SCORE+" DESC ", null );
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {

            int storeHighScore=Integer.parseInt(c.getString(3));
            if(currentScore>storeHighScore){
                return true;
            }else{
                return false;
            }

        }else{
            return  true;
        }


    }

    public static synchronized DbHelper getInstance(Context context){

        if(dbHelper==null){
            return dbHelper=new DbHelper(context);
        }else{
            return dbHelper;
        }


    }



}