package com.sstang.questionnaire.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.sstang.questionnaire.R;
import com.sstang.questionnaire.data.UserData;
import com.sstang.questionnaire.util.ToastUtil;

import io.realm.Realm;

/**
 * Created by sstang on 2018/4/8.
 */

public class LoginActivity extends BaseActivity{
    private EditText mNameEt, mPwdEt;
    private int mType;
    private Realm mRealm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mRealm = Realm.getDefaultInstance();
        mType = getIntent().getIntExtra("type", 0);
        initView();
    }

    private void initView(){
        mNameEt = findViewById(R.id.code);
        mPwdEt = findViewById(R.id.pwd);
        if(mType == 0){
            setTitle("Student Login");
        }else if(mType == 1){
            setTitle("Teacher Login");
            mNameEt.setHint("Enter Teacher ID");
        }else{
            setTitle("Administrator Login");
            mNameEt.setHint("Enter Admin ID");
        }
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mNameEt.getText().toString().trim();
                String pwd = mPwdEt.getText().toString().trim();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)){
                    ToastUtil.getInstance().showToast("ID and password cannot be empty!");
                }else if(mType == 2){
                    if("1".equals(name) && "1".equals(pwd)){
                        startActivity(new Intent(LoginActivity.this, UserEditActivity.class));
                        finish();
                    }else{
                        ToastUtil.getInstance().showToast("Wrong ID or password!");
                    }
                }else{
                    UserData data= mRealm.where(UserData.class).equalTo("mUserCode", name).findFirst();
                    if(data == null){
                        ToastUtil.getInstance().showToast("The user doesn't exist!");
                    }else{
                        if(pwd.equals(data.mPwd)){
                            if(mType == 0 && data.mType.equals("学生")){
                                Intent intent = new Intent(LoginActivity.this, QuestionnaireActivity.class);
                                intent.putExtra("studentId", name);
                                startActivity(intent);
                                finish();
                            }else if(mType == 1 &&data.mType.equals("老师")){
                                Intent intent = new Intent(LoginActivity.this, AverageRankActivity.class);
                                intent.putExtra("teacherId", name);
                                startActivity(intent);
                                finish();
                            }else{
                                ToastUtil.getInstance().showToast("Wrong password!");
                            }
                        }else{
                            ToastUtil.getInstance().showToast("Wrong password!");
                        }
                    }
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
