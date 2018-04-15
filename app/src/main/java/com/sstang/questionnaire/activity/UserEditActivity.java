package com.sstang.questionnaire.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sstang.questionnaire.R;
import com.sstang.questionnaire.adapter.UserEditAdapter;
import com.sstang.questionnaire.data.SubjectData;
import com.sstang.questionnaire.data.UserData;
import com.sstang.questionnaire.eventobj.AddUserObj;
import com.sstang.questionnaire.util.ToastUtil;
import com.sstang.questionnaire.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by sstang on 2018/4/3.
 */

public class UserEditActivity extends BaseActivity implements View.OnClickListener{
    private Spinner mSpinner;
    private ArrayAdapter<CharSequence> mAdapter;
    private UserEditAdapter mEditAdapter;
    private ListView mListView;
    private RealmList<UserData> mStudentList;
    private RealmList<UserData> mTeacherList;
    private Realm mRealm;
    private Button mAddBtn;
    private TextView mCodeTv;
    private int mType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useredit);
        EventBus.getDefault().register(this);
        initView();
        mRealm = Realm.getDefaultInstance();
        mStudentList = Utils.convertUserData(mRealm.where(UserData.class).equalTo("mType", "学生").findAll());
        mTeacherList = Utils.convertUserData(mRealm.where(UserData.class).equalTo("mType", "老师").findAll());
    }

    @Override
    protected void onDestroy() {
        mRealm.close();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initView(){
        setTitle("User Management");
        mSpinner = findViewById(R.id.spinner);
        mAdapter = ArrayAdapter.createFromResource(this, R.array.role, android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    mAddBtn.setText("Add Student");
                    mCodeTv.setText("Student ID");
                    mType = 0;
                    mEditAdapter.addToList(mStudentList);
                }else if(i == 1){
                    mAddBtn.setText("Add Teacher");
                    mCodeTv.setText("Teacher ID");
                    mType = 1;
                    mEditAdapter.addToList(mTeacherList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mCodeTv = findViewById(R.id.code_tv);
        mListView = findViewById(R.id.listview);
        mEditAdapter = new UserEditAdapter(this);
        mListView.setAdapter(mEditAdapter);
        mEditAdapter.addToList(mStudentList);
        mAddBtn = findViewById(R.id.add);
        mAddBtn.setOnClickListener(this);
        findViewById(R.id.reset).setOnClickListener(this);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserEditActivity.this);
                builder.setTitle("WARM PROMPT");
                builder.setMessage("Delete the user?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int n) {

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        String userCode = mType == 0 ? mStudentList.get(i).mUserCode:mTeacherList.get(i).mUserCode;
                        Query applesQuery = ref.child("users").orderByChild("mUserCode").equalTo(userCode);

                        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                    appleSnapshot.getRef().removeValue();
                                }

                                if(mType == 0){
                                    mRealm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            mRealm.where(UserData.class).equalTo("mUserCode", mStudentList.get(i).mUserCode).findFirst().deleteFromRealm();
                                        }
                                    });
                                    mStudentList = Utils.convertUserData(mRealm.where(UserData.class).equalTo("mType", "学生").findAll());
                                    mEditAdapter.addToList(mStudentList);
                                }else{
                                    mRealm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            mRealm.where(UserData.class).equalTo("mUserCode", mTeacherList.get(i).mUserCode).findFirst().deleteFromRealm();
                                        }
                                    });
                                    mTeacherList = Utils.convertUserData(mRealm.where(UserData.class).equalTo("mType", "老师").findAll());
                                    mEditAdapter.addToList(mTeacherList);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("del", "onCancelled", databaseError.toException());
                                ToastUtil.getInstance().showToast("Delete fail!");
                            }
                        });

                    }
                });
                builder.setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddUserObj obj){
        if(obj.mType == 0){
            mStudentList = Utils.convertUserData(mRealm.where(UserData.class).equalTo("mType", "学生").findAll());
            mEditAdapter.addToList(mStudentList);
        }else{
            mTeacherList = Utils.convertUserData(mRealm.where(UserData.class).equalTo("mType", "老师").findAll());
            mEditAdapter.addToList(mTeacherList);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:
                new AddUserDialog(this, mType).show();
                break;
            case R.id.reset:
                reset();
                break;
        }
    }



    private void reset(){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<SubjectData> subjects = mRealm.where(SubjectData.class).findAll();
                for(SubjectData data : subjects){
                    data.mNum = 0;
                    data.mTotolScore = 0;
                    data.mAverageScore = 0;
                }
            }
        });
        ToastUtil.getInstance().showToast("success！");
    }
}
