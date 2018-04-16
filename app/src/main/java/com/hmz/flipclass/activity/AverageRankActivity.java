package com.hmz.flipclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.hmz.flipclass.R;
import com.hmz.flipclass.adapter.AverageRankAdapter;
import com.hmz.flipclass.data.QuestionnaireData;
import com.hmz.flipclass.util.Utils;

import io.realm.Realm;

/**
 * Created by sstang on 2018/4/8.
 */

public class AverageRankActivity extends BaseActivity{

    private Spinner mSpinner;
    private ArrayAdapter<CharSequence> mSpinnerAdapter;
    private String mCode;
    private String[] mClassCodes;
    public QuestionnaireData mQuestionnaireData;
    public Realm mRealm;
    private ListView mListView;
    private AverageRankAdapter mAdapter;
    public static String mTeacherCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average_rank);
        mRealm = Realm.getDefaultInstance();
        mTeacherCode = getIntent().getStringExtra("teacherId");
        mClassCodes = Utils.getClassCode();
        initView();
    }

    private void initView(){
        setTitle("Teacher Ranking");
        mSpinner = findViewById(R.id.spinner);
        mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.code, android.R.layout.simple_spinner_item);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mSpinnerAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mCode = mClassCodes[i];
                setData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mListView = findViewById(R.id.listview);
        mAdapter = new AverageRankAdapter(this);
        mListView.setAdapter(mAdapter);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AverageRankActivity.this, AllRankActivity.class));
            }
        });

        findViewById(R.id.rank).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AverageRankActivity.this, TeacherRankActivity.class));
            }
        });
    }

    private void setData(){
        mQuestionnaireData = mRealm.where(QuestionnaireData.class).equalTo("mCode", mCode).equalTo("mTeacherCode",mTeacherCode).findFirst();
        if(mQuestionnaireData == null){
            mAdapter.clear();
        }else{
            mAdapter.addToList(mQuestionnaireData.mContents);
        }
    }

    @Override
    protected void onDestroy() {
        mRealm.close();
        super.onDestroy();
    }
}
