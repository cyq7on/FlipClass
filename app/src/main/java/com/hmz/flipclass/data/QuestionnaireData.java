package com.hmz.flipclass.data;

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
    public RealmList<SubjectData> mContents = new RealmList<>();

    public Questionnaire convert() {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.mKey = mKey;
        questionnaire.mCode = mCode;
        questionnaire.mTilte = mTilte;
        questionnaire.mTeacherCode = mTeacherCode;
        for (int i = 0; i < mContents.size(); i++) {
            questionnaire.mContents.add(mContents.get(i).convert());
        }
        return questionnaire;
    }
}
