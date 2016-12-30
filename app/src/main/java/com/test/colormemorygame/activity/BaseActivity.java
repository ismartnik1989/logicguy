package com.test.colormemorygame.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.colormemorygame.R;
import com.test.colormemorygame.database.DbHelper;

public class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    protected DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        dbHelper=DbHelper.getInstance(this);
    }
}
