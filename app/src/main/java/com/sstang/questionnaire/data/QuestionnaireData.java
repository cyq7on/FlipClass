package com.sstang.questionnaire.data;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sstang on 2018/4/3.
 */

public class QuestionnaireData extends RealmObject{
    @PrimaryKey
    public String mKey;
    public String mCode;
    public String mTilte;
    public String mTeacherCode;
    public RealmList<SubjectData> mContents;
}
