package com.sstang.questionnaire.data;

/**
 * Created by cyq7on on 18-4-14.
 */

public class Subject {
    public String mCode;
    public String mSubject;
    public int mTotolScore;
    public float mAverageScore;
    public int mScore = 0;
    public int mNum;
    public String mTeacherCode;

    public SubjectData convert() {
        SubjectData subject = new SubjectData();
        subject.mCode = mCode;
        subject.mSubject = mSubject;
        subject.mTotolScore = mTotolScore;
        subject.mAverageScore = mAverageScore;
        subject.mScore = mScore;
        subject.mNum = mNum;
        subject.mTeacherCode = mTeacherCode;
        return subject;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "mCode='" + mCode + '\'' +
                ", mSubject='" + mSubject + '\'' +
                ", mTotolScore=" + mTotolScore +
                ", mAverageScore=" + mAverageScore +
                ", mScore=" + mScore +
                ", mNum=" + mNum +
                ", mTeacherCode='" + mTeacherCode + '\'' +
                '}';
    }
}
