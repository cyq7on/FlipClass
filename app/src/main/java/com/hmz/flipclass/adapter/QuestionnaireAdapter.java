package com.hmz.flipclass.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hmz.flipclass.R;
import com.hmz.flipclass.activity.QuestionnaireActivity;
import com.hmz.flipclass.data.SubjectData;
import com.hmz.flipclass.widget.RatingBarView;

import io.realm.Realm;

/**
 * Created by sstang on 2018/4/3.
 */

public class QuestionnaireAdapter extends AdapterBase<SubjectData>{

    public QuestionnaireAdapter(QuestionnaireActivity context) {
        super(context);
    }

    @Override
    protected View getExView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_questionnaire, parent, false);
            holder.mTitleTv = convertView.findViewById(R.id.title);
            holder.mRatingBarView = convertView.findViewById(R.id.score);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final SubjectData subjectData = getItem(position);
        if(subjectData != null){
            holder.mTitleTv.setText(subjectData.mSubject);
            Log.v("sstang", "subjectData.mScore=====" + subjectData.mScore);
            holder.mRatingBarView.setSelectedCount(subjectData.mScore - 1);
            holder.mRatingBarView.setOnRatingBarClickListener(new RatingBarView.RatingBarViewClickListener() {
                @Override
                public void onRatingBarClick(LinearLayout parent, View childView, final int star) {
                    Log.v("sstang", "star===" + star);
                    ((QuestionnaireActivity)mContext).mRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Log.v("sstang", "position===" + position);
                            subjectData.mScore = star;
                        }
                    });
                }
            });
        }
        return convertView;
    }

    class ViewHolder{
        TextView mTitleTv;
        RatingBarView mRatingBarView;
    }
}
