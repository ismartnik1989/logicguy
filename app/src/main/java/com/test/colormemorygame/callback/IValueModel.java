package com.test.colormemorygame.callback;

import android.content.ContentValues;

/**
 * Created by mukeshk2 on 5/13/2016.
 */
public interface IValueModel {

    String getId();
    ContentValues toValues();
    ContentValues toUpdates();
}
