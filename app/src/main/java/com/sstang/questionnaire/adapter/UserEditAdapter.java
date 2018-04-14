package com.sstang.questionnaire.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sstang.questionnaire.R;
import com.sstang.questionnaire.data.User;

/**
 * Created by sstang on 2018/4/3.
 */

public class UserEditAdapter extends AdapterBase<User>{

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
        User user = getItem(position);
        if(user != null){
            holder.mCodeTv.setText(user.mUserCode);
            holder.mNameTv.setText(user.mUserName);
            holder.mPwdTv.setText(user.mPwd);
        }
        return convertView;
    }

    class ViewHolder{
        TextView mCodeTv, mNameTv, mPwdTv;
    }
}
