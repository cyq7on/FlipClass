package com.hmz.flipclass.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.hmz.flipclass.R;
import com.hmz.flipclass.adapter.TeacherRankAdapter;
import com.hmz.flipclass.data.SubjectData;
import com.hmz.flipclass.data.UserData;
import com.hmz.flipclass.util.Utils;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.Sort;

/**
 * Created by sstang on 2018/4/8.
 */

public class TeacherRankActivity extends BaseActivity{

    private Realm mRealm;
    private RealmList<UserData> mTeacherList;
    private TeacherRankAdapter mAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_rank);
        mRealm = Realm.getDefaultInstance();
        initView();
    }

    private void initView(){
        setTitle("Teacher Ranking");
        mListView = findViewById(R.id.listview);
        mAdapter = new TeacherRankAdapter(this);
        mListView.setAdapter(mAdapter);

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mTeacherList = Utils.convertUserData(mRealm.where(UserData.class).equalTo("mType", "老师").findAll());
                if(mTeacherList != null){
                    for(UserData teacher : mTeacherList){
                        RealmList<SubjectData> subjectList = Utils.convertSubjectData(mRealm.where(SubjectData.class).equalTo("mTeacherCode", teacher.mUserCode).findAll());
                        float score = 0;
                        int num = 0;
                        for(SubjectData subjectData : subjectList){
                            if(subjectData.mAverageScore > 0){
                                score += subjectData.mAverageScore;
                                num++;
                            }
                        }
                        if(num > 0){
                            teacher.mTeacherScore = score / num;
                        }else{
                            teacher.mTeacherScore = 0;
                        }
                    }
                    mTeacherList = Utils.convertUserData(mRealm.where(UserData.class).equalTo("mType", "老师").findAll().sort("mTeacherScore", Sort.DESCENDING));
                    mAdapter.addToList(mTeacherList);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        mRealm.close();
        super.onDestroy();
    }
}
