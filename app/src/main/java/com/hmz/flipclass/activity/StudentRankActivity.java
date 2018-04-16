package com.hmz.flipclass.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.hmz.flipclass.R;
import com.hmz.flipclass.adapter.StudentRankAdapter;
import com.hmz.flipclass.data.UserData;
import com.hmz.flipclass.util.Utils;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.Sort;

/**
 * Created by sstang on 2018/4/8.
 */

public class StudentRankActivity extends BaseActivity{

    private StudentRankAdapter mAdapter;
    private ListView mListView;
    private Realm mRealm;
    private RealmList<UserData> mStudentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_rank);
        mRealm = Realm.getDefaultInstance();
        initView();
    }

    private void initView(){
        setTitle("Student Ranking");
        mListView = findViewById(R.id.listview);
        mAdapter = new StudentRankAdapter(this);
        mListView.setAdapter(mAdapter);
        mStudentList = Utils.convertUserData(mRealm.where(UserData.class).equalTo("mType", "学生").findAll().sort("mScore", Sort.DESCENDING));
        mAdapter.addToList(mStudentList);
    }

    @Override
    protected void onDestroy() {
        mRealm.close();
        super.onDestroy();
    }
}
