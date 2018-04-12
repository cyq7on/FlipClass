package com.sstang.questionnaire.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.sstang.questionnaire.MyApplocation;

/**
 * Created by Administrator on 2017/11/1.
 */

public class SharedPreferencesUtils {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private static SharedPreferencesUtils mSharedPreferencesUtils = null;

    protected SharedPreferencesUtils() {
        mPreferences = MyApplocation.getApplication().getSharedPreferences("com.sstang.questionnaire", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static SharedPreferencesUtils getInstance() {
        if (mSharedPreferencesUtils == null) {
            synchronized (SharedPreferencesUtils.class) {
                if (mSharedPreferencesUtils == null) {
                    mSharedPreferencesUtils = new SharedPreferencesUtils();
                }
            }
        }
        return mSharedPreferencesUtils;
    }


    public boolean getFirst(){
        return mPreferences.getBoolean("first", true);
    }

    public void setFirst(boolean isFirst){
        mEditor.putBoolean("first", isFirst);
        mEditor.commit();
    }
}
