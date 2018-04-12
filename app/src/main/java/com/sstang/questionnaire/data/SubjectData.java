package com.sstang.questionnaire.data;

import io.realm.RealmObject;

/**
 * Created by sstang on 2018/4/3.
 */

public class SubjectData extends RealmObject{
    public String mCode;
    public String mSubject;
    public int mTotolScore;
    public float mAverageScore;
    public int mScore = 0;
    public int mNum;
    public String mTeacherCode;
}
