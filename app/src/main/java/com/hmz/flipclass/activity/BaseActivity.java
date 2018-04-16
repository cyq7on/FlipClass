package com.hmz.flipclass.activity;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.hmz.flipclass.R;

/**
 * Created by sstang on 2018/4/2.
 */

public class BaseActivity extends Activity{
    //返回按钮
    protected View mBackBtn;
    //标题
    protected TextView mTitle;

    protected void setTitle(String title){
        //返回按钮
        this.mBackBtn = this.findViewById(R.id.navigation_back);
        this.mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //标题
        this.mTitle = (TextView) this.findViewById(R.id.title);
        this.mTitle.setText(title);
    }


}
