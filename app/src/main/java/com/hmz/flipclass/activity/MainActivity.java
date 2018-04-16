package com.hmz.flipclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hmz.flipclass.R;

public class MainActivity extends BaseActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        setTitle("Select Role");
        findViewById(R.id.student).setOnClickListener(this);
        findViewById(R.id.teacher).setOnClickListener(this);
        findViewById(R.id.admin).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.student:
                Intent intent1 = new Intent(this, LoginActivity.class);
                intent1.putExtra("type", 0);
                startActivity(intent1);
                break;
            case R.id.teacher:
                Intent intent2 = new Intent(this, LoginActivity.class);
                intent2.putExtra("type", 1);
                startActivity(intent2);
                break;
            case R.id.admin:
                Intent intent3 = new Intent(this, LoginActivity.class);
                intent3.putExtra("type", 2);
                startActivity(intent3);
                break;
        }
    }
}
