package com.test.colormemorygame.database;

import android.content.ContentValues;
import android.content.Context;

import com.test.colormemorygame.callback.IValueModel;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by mukesh on 2/Dec/2016.
 */
public class InsertCommand implements IValueModel, Serializable {


    private Context context;
    private String rank;
    private String name;
    private String score;
    private String id;


    public InsertCommand(Context context, String rank, String name, String score) {
        this.context = context;
        this.rank = rank;
        this.name = name;
        this.score = score;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ContentValues toValues() {
        ContentValues contentValues = new ContentValues();

        id = UUID.randomUUID().toString();

        contentValues.put(DbHelper.ID, id);
        contentValues.put(DbHelper.RANK, rank);
        contentValues.put(DbHelper.NAME, name);
        contentValues.put(DbHelper.SCORE, Integer.parseInt(score));

        return contentValues;
    }


    @Override
    public ContentValues toUpdates() {
        return null;
    }
}
