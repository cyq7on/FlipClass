package com.sstang.questionnaire.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.sstang.questionnaire.R;
import com.sstang.questionnaire.adapter.AllRankAdapter;
import com.sstang.questionnaire.data.SubjectData;
import com.sstang.questionnaire.util.Utils;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.Sort;

/**
 * Created by sstang on 2018/4/8.
 */

public class AllRankActivity extends BaseActivity{

    private AllRankAdapter mAdapter;
    private ListView mListView;
    private Realm mRealm;
    private RealmList<SubjectData> mSubjectList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_rank);
        mRealm = Realm.getDefaultInstance();
        initView();
    }

    private void initView(){
        setTitle("Queation Ranking");
        mListView = findViewById(R.id.listview);
        mAdapter = new AllRankAdapter(this);
        mListView.setAdapter(mAdapter);
        mSubjectList = Utils.convertSubjectData(mRealm.where(SubjectData.class).equalTo("mTeacherCode", AverageRankActivity.mTeacherCode).findAll().sort("mAverageScore", Sort.DESCENDING));
        mAdapter.addToList(mSubjectList);
    }

    @Override
    protected void onDestroy() {
        mRealm.close();
        super.onDestroy();
    }
}
