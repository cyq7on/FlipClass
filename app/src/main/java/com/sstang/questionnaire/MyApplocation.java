package com.sstang.questionnaire;

import android.app.Application;
import android.content.Context;

import com.sstang.questionnaire.util.DensityUtil;
import com.sstang.questionnaire.util.ToastUtil;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by sstang on 2018/4/3.
 */

public class MyApplocation extends Application{

    private static MyApplocation mInstance;
    public Context mContext;
    public int mWidth, mHeight;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();
        mHeight = DensityUtil.getScreenHeight();
        mWidth = DensityUtil.getScreenWidth();
        ToastUtil.init(this);
        Realm.init(this);
        RealmConfiguration config = new  RealmConfiguration.Builder()
                .name("questionnaire.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public static MyApplocation getApplication() {
        return mInstance;
    }
}
