package com.test.colormemorygame.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.test.colormemorygame.R;
import com.test.colormemorygame.database.DbHelper;


public class SplashActivity extends AppCompatActivity {
    private String TAG;
    private Context mContext;
    private final int SPLASH_DISPLAY_LENGTH = 1000;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext=this;
        TAG=getClass().getName();
        DbHelper.getInstance(this);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                navigateToLogin();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    /**
     * @author : mukesh
     * @return : void
     * @created : 2/Dec/2016
     * @method name : navigateToLogin
     * @description : this method help navigate to game screen ;
     */

    private void navigateToLogin(){

        Intent i = new Intent(SplashActivity.this, GameActivity.class);
        startActivity(i);
        finish();
    }


}
