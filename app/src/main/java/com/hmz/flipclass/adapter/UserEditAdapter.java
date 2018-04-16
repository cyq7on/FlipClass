package com.hmz.flipclass.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hmz.flipclass.R;
import com.hmz.flipclass.data.UserData;

/**
 * Created by sstang on 2018/4/3.
 */

public class UserEditAdapter extends AdapterBase<UserData>{

    public UserEditAdapter(Context context) {
        super(context);
    }

    @Override
    protected View getExView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_useredit, parent, false);
            holder.mCodeTv = convertView.findViewById(R.id.code);
            holder.mNameTv = convertView.findViewById(R.id.name);
            holder.mPwdTv = convertView.findViewById(R.id.pwd);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        UserData userData = getItem(position);
        if(userData != null){
            holder.mCodeTv.setText(userData.mUserCode);
            holder.mNameTv.setText(userData.mUserName);
            holder.mPwdTv.setText(userData.mPwd);
        }
        return convertView;
    }

    class ViewHolder{
        TextView mCodeTv, mNameTv, mPwdTv;
    }
}
