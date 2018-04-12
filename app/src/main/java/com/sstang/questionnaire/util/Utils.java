package com.sstang.questionnaire.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.sstang.questionnaire.MyApplocation;
import com.sstang.questionnaire.R;
import com.sstang.questionnaire.data.QuestionnaireData;
import com.sstang.questionnaire.data.SubjectData;
import com.sstang.questionnaire.data.UserData;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by sstang on 2018/4/3.
 */

public class Utils {

    public static RealmList<UserData> convertUserData (RealmResults<UserData> realmResults){
        RealmList <UserData> results = new RealmList<UserData>();
        if(realmResults!= null && realmResults.size() > 0){
            results.addAll(realmResults.subList(0, realmResults.size()));
        }
        return results;
    }

    public static RealmList<SubjectData> convertSubjectData (RealmResults<SubjectData> realmResults){
        RealmList <SubjectData> results = new RealmList<SubjectData>();
        if(realmResults!= null && realmResults.size() > 0){
            results.addAll(realmResults.subList(0, realmResults.size()));
        }
        return results;
    }

    /**
     * 显示软键盘，Dialog使用
     *
     * @param context context
     */
    public static void showSoftInput(Context context) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static QuestionnaireData createData(String code, String teacherNum){
        QuestionnaireData questionnaireData = new QuestionnaireData();
        questionnaireData.mKey = code + teacherNum;
        questionnaireData.mCode = code;
        questionnaireData.mTeacherCode = teacherNum;
        questionnaireData.mTilte = "Dear student,\n" +
                "We appreciate your feedback in helping us to monitor and improve the quality of our classes. Score from 1(poor) to 5(excellent). Thank you for taking part in this survey. Your comments are valued.";

        questionnaireData.mContents = new RealmList<SubjectData>();

        SubjectData subjectData1 = new SubjectData();
        subjectData1.mCode = code;
        subjectData1.mScore = 0;
        subjectData1.mTotolScore = 0;
        subjectData1.mTeacherCode = teacherNum;
        subjectData1.mSubject = "1、The lesson was well organized？";
        questionnaireData.mContents.add(subjectData1);

        SubjectData subjectData2 = new SubjectData();
        subjectData2.mCode = code;
        subjectData2.mScore = 0;
        subjectData2.mTotolScore = 0;
        subjectData2.mTeacherCode = teacherNum;
        subjectData2.mSubject = "2、The teacher was well prepared for class？";
        questionnaireData.mContents.add(subjectData2);

        SubjectData subjectData3 = new SubjectData();
        subjectData3.mCode = code;
        subjectData3.mScore = 0;
        subjectData3.mTotolScore = 0;
        subjectData3.mTeacherCode = teacherNum;
        subjectData3.mSubject = "3、The aims of the lesson were clear？";
        questionnaireData.mContents.add(subjectData3);

        SubjectData subjectData4 = new SubjectData();
        subjectData4.mCode = code;
        subjectData4.mScore = 0;
        subjectData4.mTotolScore = 0;
        subjectData4.mTeacherCode = teacherNum;
        subjectData4.mSubject = "4、The teacher's instructions were clear？";
        questionnaireData.mContents.add(subjectData4);

        SubjectData subjectData5 = new SubjectData();
        subjectData5.mCode = code;
        subjectData5.mScore = 0;
        subjectData5.mTotolScore = 0;
        subjectData5.mTeacherCode = teacherNum;
        subjectData5.mSubject = "5、The teacher's explanations were clear？";
        questionnaireData.mContents.add(subjectData5);

        SubjectData subjectData6 = new SubjectData();
        subjectData6.mCode = code;
        subjectData6.mScore = 0;
        subjectData6.mTotolScore = 0;
        subjectData6.mTeacherCode = teacherNum;
        subjectData6.mSubject = "6、The teacher's approach was supportive？";
        questionnaireData.mContents.add(subjectData6);

        SubjectData subjectData7 = new SubjectData();
        subjectData7.mCode = code;
        subjectData7.mScore = 0;
        subjectData7.mTotolScore = 0;
        subjectData7.mTeacherCode = teacherNum;
        subjectData7.mSubject = "7、The teacher encouraged student participation？";
        questionnaireData.mContents.add(subjectData7);

        SubjectData subjectData8 = new SubjectData();
        subjectData8.mCode = code;
        subjectData8.mScore = 0;
        subjectData8.mTotolScore = 0;
        subjectData8.mTeacherCode = teacherNum;
        subjectData8.mSubject = "8、The teacher gave feedback on student performance？";
        questionnaireData.mContents.add(subjectData8);

        SubjectData subjectData9 = new SubjectData();
        subjectData9.mCode = code;
        subjectData9.mScore = 0;
        subjectData9.mTotolScore = 0;
        subjectData9.mTeacherCode = teacherNum;
        subjectData9.mSubject = "9、The contents of this lesson are useful for you？";
        questionnaireData.mContents.add(subjectData9);

        SubjectData subjectData10 = new SubjectData();
        subjectData10.mCode = code;
        subjectData10.mScore = 0;
        subjectData10.mTotolScore = 0;
        subjectData10.mTeacherCode = teacherNum;
        subjectData10.mSubject = "10、You are fine with the difficulty level of the course？";
        questionnaireData.mContents.add(subjectData10);

        return questionnaireData;
    }

    public static String[] getClassCode(){
        String[] items = MyApplocation.getApplication().getResources().getStringArray(R.array.code);
        return items;
    }
}
