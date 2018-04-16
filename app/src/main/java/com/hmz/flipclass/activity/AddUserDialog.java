package com.hmz.flipclass.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hmz.flipclass.MyApplocation;
import com.hmz.flipclass.R;
import com.hmz.flipclass.data.UserData;
import com.hmz.flipclass.eventobj.AddUserObj;
import com.hmz.flipclass.util.ToastUtil;
import com.hmz.flipclass.util.Utils;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;

/**
 * Created by sstang on 2018/4/3.
 */

public class AddUserDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private EditText mNameEt, mCodeEt, mPwdEt;
    private int mType;
    private Realm mRealm;
    public AddUserDialog(@NonNull Context context, int type) {
        super(context, R.style.Dialog);
        mContext = context;
        mType = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_user);
        initView();

        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.height = MyApplocation.getApplication().mHeight * 2 / 5;
        lp.width = MyApplocation.getApplication().mWidth * 3 / 4;
        win.setAttributes(lp);

        mRealm = Realm.getDefaultInstance();

        mNameEt.postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.showSoftInput(mContext);
            }
        }, 100);
    }

    @Override
    public void dismiss() {
        mRealm.close();
        super.dismiss();
    }

    private void initView(){
        mNameEt = findViewById(R.id.name);
        mCodeEt = findViewById(R.id.code);
        mPwdEt = findViewById(R.id.pwd);
        if(mType == 1){
            mCodeEt.setHint("Enter Teacher ID");
        }
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.ok).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel:
                dismiss();
                break;
            case R.id.ok:
                if(TextUtils.isEmpty(mNameEt.getText().toString().trim())){
                    ToastUtil.getInstance().showToast("Name cannot be empty!");
                }else if(TextUtils.isEmpty(mCodeEt.getText().toString().trim())){
                    ToastUtil.getInstance().showToast("ID cannot be empty!");
                }else if(TextUtils.isEmpty(mPwdEt.getText().toString().trim())){
                    ToastUtil.getInstance().showToast("Password cannot be empty!");
                }else{
                    mRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            try {
                                UserData user = realm.createObject(UserData.class, mCodeEt.getText().toString().trim());
                                user.mUserName = mNameEt.getText().toString().trim();
                                user.mPwd = mPwdEt.getText().toString().trim();
                                if(mType == 0){
                                    user.mType = "学生";
                                }else{
                                    user.mType = "老师";
                                    String items[] = Utils.getClassCode();
                                    int len = items.length;
                                    for(int i = 0; i < len; i++){
                                        realm.copyToRealmOrUpdate(Utils.createData(items[i], user.mUserCode));
                                    }
                                }
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("users");
                                String userId = myRef.push().getKey();
                                myRef.child(userId).setValue(user.convert());
                            }catch (Exception e){
                                ToastUtil.getInstance().showToast("Teacher ID and student ID cannot be same!");
                            }
                        }
                    });
                    AddUserObj obj = new AddUserObj();
                    obj.mType = mType;
                    EventBus.getDefault().post(obj);
                    dismiss();
                }
                break;
        }
    }
}
