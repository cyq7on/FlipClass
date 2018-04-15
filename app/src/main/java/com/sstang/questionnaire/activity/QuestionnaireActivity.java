package com.sstang.questionnaire.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sstang.questionnaire.R;
import com.sstang.questionnaire.adapter.QuestionnaireAdapter;
import com.sstang.questionnaire.data.QuestionnaireData;
import com.sstang.questionnaire.data.SubjectData;
import com.sstang.questionnaire.data.User;
import com.sstang.questionnaire.data.UserData;
import com.sstang.questionnaire.util.ToastUtil;
import com.sstang.questionnaire.util.Utils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by sstang on 2018/4/3.
 */

public class QuestionnaireActivity extends BaseActivity{

    private Spinner mSpinner, mSpinner1;
    private ArrayAdapter<CharSequence> mSpinnerAdapter, mSpinnerAdapter1;
    private String mCode;
    private String[] mClassCodes;
    public QuestionnaireData mQuestionnaireData;
    public Realm mRealm;
    private ListView mListView;
    private QuestionnaireAdapter mAdapter;
    private boolean mIsFinish = true;
    private String mStudentId;
    private View mFooterView;
    private List<User> mTeacherList = new ArrayList<>();
    private String mTeacher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        mStudentId = getIntent().getStringExtra("studentId");
        mRealm = Realm.getDefaultInstance();
        mClassCodes = Utils.getClassCode();
        initView();
    }

    @Override
    protected void onDestroy() {
        if(mQuestionnaireData != null){
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    for(SubjectData content : mQuestionnaireData.mContents){
                        content.mScore = 0;
                    }
                }
            });
            mRealm.close();
        }
        super.onDestroy();
    }

    private void initView(){
        setTitle("Questionnaire");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if("老师".equals(user.mType)){
                        mTeacherList.add(user);
                    }
                    Log.d("user",user.toString());
                }
                if(mTeacherList.size() == 0){
                    ToastUtil.getInstance().showToast("no teachers here!");
                    finish();
                }
                mSpinner = findViewById(R.id.spinner);
                mSpinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.code, android.R.layout.simple_spinner_item);
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


                List<CharSequence> code = new ArrayList<>();
                for (User teacher : mTeacherList){
                    code.add(teacher.mUserCode + ":" + teacher.mUserName);
                }

                mSpinner1 = findViewById(R.id.spinner1);
                mSpinnerAdapter1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, code);
                mSpinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner1.setAdapter(mSpinnerAdapter1);
                mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        mTeacher =  mTeacherList.get(i).mUserCode;
                        setData();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("test",databaseError.getMessage());
                ToastUtil.getInstance().showToast("Database Error!");
                finish();
            }
        });
//        mTeacherList = Utils.convertUserData(mRealm.where(User.class).equalTo("mType", "老师").findAll());


        mListView = findViewById(R.id.listview);
        mFooterView = LayoutInflater.from(this).inflate(R.layout.view_footer, null);
        mListView.addFooterView(mFooterView, null, false);
        mAdapter = new QuestionnaireAdapter(this);
        mListView.setAdapter(mAdapter);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsFinish = true;
                for(SubjectData content : mQuestionnaireData.mContents){
                    Log.v("sstang", "hahaha====" + content.mSubject + ":"+content.mScore);
                    if(content.mScore == 0){
                        ToastUtil.getInstance().showToast("Please anwser all questions before submitting!");
                        mIsFinish = false;
                        break;
                    }
                }
                if(mIsFinish){
                    ToastUtil.getInstance().showToast("success!");
                    Log.v("sstang", "完成了");
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("questionnaire");
                    String userId = myRef.push().getKey();
                    myRef.child(userId).setValue(mQuestionnaireData.convert());
                    mRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            mRealm.where(UserData.class).equalTo("mUserCode", mStudentId).findFirst().mScore += 1;
                            for(SubjectData content : mQuestionnaireData.mContents){
                                content.mTotolScore += content.mScore;
                                content.mNum += 1;
                                content.mAverageScore = content.mTotolScore / (float)content.mNum;
                            }
                        }
                    });
                    finish();
                }
            }
        });
        findViewById(R.id.rank).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuestionnaireActivity.this, StudentRankActivity.class));
            }
        });
    }



    private void setData(){
        if(!TextUtils.isEmpty(mTeacher)){
            mQuestionnaireData = mRealm.where(QuestionnaireData.class).equalTo("mCode", mCode).equalTo("mTeacherCode", mTeacher).findFirst();
            if(mQuestionnaireData == null){
                mAdapter.clear();
            }else{
                mAdapter.addToList(mQuestionnaireData.mContents);
            }
        }
    }
}
