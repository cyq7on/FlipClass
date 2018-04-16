package com.hmz.flipclass.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hmz.flipclass.R;
import com.hmz.flipclass.data.SubjectData;

/**
 * Created by sstang on 2018/4/8.
 */

public class AverageRankAdapter extends AdapterBase<SubjectData>{


    public AverageRankAdapter(Context context) {
        super(context);
    }

    @Override
    protected View getExView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_average_rank, parent, false);
            holder.mTitleTv = convertView.findViewById(R.id.title);
            holder.mScoreTv = convertView.findViewById(R.id.score);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        SubjectData userData = getItem(position);
        if(userData != null){
            holder.mTitleTv.setText(userData.mSubject);
            if(userData.mNum == 0){
                holder.mScoreTv.setText("Average Score：0");
            }else{
                holder.mScoreTv.setText("Average Score：" + String.format("%.2f", userData.mTotolScore / (float)userData.mNum));
            }
        }
        return convertView;
    }

    class ViewHolder{
        TextView mTitleTv, mScoreTv;
    }
}
