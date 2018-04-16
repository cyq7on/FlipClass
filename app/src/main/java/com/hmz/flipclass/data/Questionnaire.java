package com.hmz.flipclass.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyq7on on 18-4-15.
 */

public class Questionnaire {
    public String mKey;
    public String mCode;
    public String mTilte;
    public String mTeacherCode;
    public List<Subject> mContents = new ArrayList<>();

    public QuestionnaireData convert() {
        QuestionnaireData questionnaire = new QuestionnaireData();
        questionnaire.mKey = mKey;
        questionnaire.mCode = mCode;
        questionnaire.mTilte = mTilte;
        questionnaire.mTeacherCode = mTeacherCode;
        for (int i = 0; i < mContents.size(); i++) {
            questionnaire.mContents.add(mContents.get(i).convert());
        }
        return questionnaire;
    }

    @Override
    public String toString() {
        return "Questionnaire{" +
                "mKey='" + mKey + '\'' +
                ", mCode='" + mCode + '\'' +
                ", mTilte='" + mTilte + '\'' +
                ", mTeacherCode='" + mTeacherCode + '\'' +
                ", mContents=" + mContents +
                '}';
    }
}
