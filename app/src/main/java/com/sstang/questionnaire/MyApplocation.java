package com.sstang.questionnaire;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sstang.questionnaire.data.Questionnaire;
import com.sstang.questionnaire.data.User;
import com.sstang.questionnaire.data.UserData;
import com.sstang.questionnaire.util.DensityUtil;
import com.sstang.questionnaire.util.ToastUtil;
import com.sstang.questionnaire.util.Utils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by sstang on 2018/4/3.
 */

public class MyApplocation extends Application {

    private static MyApplocation mInstance;
    public Context mContext;
    public int mWidth, mHeight;
    private Realm mRealm;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();
        mHeight = DensityUtil.getScreenHeight();
        mWidth = DensityUtil.getScreenWidth();
        ToastUtil.init(this);
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("questionnaire.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        mRealm = Realm.getDefaultInstance();
        if (mRealm.isEmpty()) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            Query queryUser = ref.child("users");
            queryUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        final User user = snapshot.getValue(User.class);
                        Log.d("init", user.toString());
                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                try {
                                    UserData userData = realm.createObject(UserData.class, user.mUserCode.trim());
                                    userData.mUserName = user.mUserName.trim();
                                    userData.mPwd = user.mPwd.trim();
                                    if ("学生".equals(user.mType)) {
                                        userData.mType = "学生";
                                    } else {
                                        userData.mType = "老师";
                                        String items[] = Utils.getClassCode();
                                        int len = items.length;
                                        for (int i = 0; i < len; i++) {
                                            realm.copyToRealmOrUpdate(Utils.createData(items[i], userData.mUserCode));
                                        }
                                    }

                                } catch (Exception e) {
//                                        ToastUtil.getInstance().showToast("init user data error!");
                                    Log.e("init", e.getMessage());
                                }
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    ToastUtil.getInstance().showToast("init user data error!");
                }
            });

            Query queryQuestionnaire = ref.child("questionnaire");
            queryQuestionnaire.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        final Questionnaire questionnaire = snapshot.getValue(Questionnaire.class);
                        Log.d("init", questionnaire.toString());
                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                try {
                                    /*String items[] = Utils.getClassCode();
                                    int len = items.length;
                                    for (int i = 0; i < len; i++) {
                                        realm.copyToRealmOrUpdate(questionnaire.convert());
                                    }*/
                                    realm.copyToRealmOrUpdate(questionnaire.convert());

                                } catch (Exception e) {
//                                        ToastUtil.getInstance().showToast("init user data error!");
                                    Log.e("init", e.getMessage());
                                }
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    ToastUtil.getInstance().showToast("init user data error!");
                }
            });
        }
    }

    public static MyApplocation getApplication() {
        return mInstance;
    }
}
