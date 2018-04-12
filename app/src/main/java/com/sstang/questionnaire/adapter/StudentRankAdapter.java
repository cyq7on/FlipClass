package com.sstang.questionnaire.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sstang.questionnaire.R;
import com.sstang.questionnaire.data.UserData;

/**
 * Created by sstang on 2018/4/8.
 */

public class StudentRankAdapter extends AdapterBase<UserData>{

    public StudentRankAdapter(Context context) {
        super(context);
    }

    @Override
    protected View getExView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.itme_student_rank, parent, false);
            holder.mRankTv = convertView.findViewById(R.id.rank);
            holder.mCodeTv = convertView.findViewById(R.id.code);
            holder.mNameTv = convertView.findViewById(R.id.name);
            holder.mScoreTv = convertView.findViewById(R.id.score1);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        UserData userData = getItem(position);
        if(userData != null){
            holder.mRankTv.setText((position + 1) + "");
            holder.mCodeTv.setText(userData.mUserCode);
            holder.mNameTv.setText(userData.mUserName);
            holder.mScoreTv.setText(userData.mScore + "");
        }
        return convertView;
    }

    class ViewHolder{
        TextView mRankTv, mCodeTv, mNameTv, mScoreTv;
    }
}
