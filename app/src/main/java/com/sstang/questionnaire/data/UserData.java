package com.sstang.questionnaire.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sstang on 2018/4/3.
 */

public class UserData extends RealmObject{
    @PrimaryKey
    public String mUserCode;
    public String mType;
    public String mUserName;
    public String mPwd;
    public int mScore;
    public float mTeacherScore;
}
