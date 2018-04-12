package com.sstang.questionnaire.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sstang.questionnaire.R;
import com.sstang.questionnaire.data.SubjectData;

/**
 * Created by sstang on 2018/4/8.
 */

public class AllRankAdapter extends AdapterBase<SubjectData>{

    public AllRankAdapter(Context context) {
        super(context);
    }


    @Override
    protected View getExView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_all_rank, parent, false);
            holder.mRankTv = convertView.findViewById(R.id.rank);
            holder.mCodeTv = convertView.findViewById(R.id.code);
            holder.mTitleTv = convertView.findViewById(R.id.title);
            holder.mScoreTv = convertView.findViewById(R.id.score);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        SubjectData userData = getItem(position);
        if(userData != null){
            holder.mRankTv.setText((position + 1) + "");
            holder.mCodeTv.setText(userData.mCode);
            holder.mTitleTv.setText(userData.mSubject);
            if(userData.mNum == 0){
                holder.mScoreTv.setText("0");
            }else{
                holder.mScoreTv.setText(String.format("%.2f", userData.mTotolScore / (float)userData.mNum));
            }
        }
        return convertView;
    }

    class ViewHolder{
        TextView mRankTv, mCodeTv, mTitleTv, mScoreTv;
    }
}
