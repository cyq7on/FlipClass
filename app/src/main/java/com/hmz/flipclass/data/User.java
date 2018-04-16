package com.hmz.flipclass.data;

public class User {
    public String mUserCode;
    public String mType;
    public String mUserName;
    public String mPwd;
    public int mScore;
    public float mTeacherScore;

    @Override
    public String toString() {
        return "User{" +
                "mUserCode='" + mUserCode + '\'' +
                ", mType='" + mType + '\'' +
                ", mUserName='" + mUserName + '\'' +
                ", mPwd='" + mPwd + '\'' +
                ", mScore=" + mScore +
                ", mTeacherScore=" + mTeacherScore +
                '}';
    }
}
