package com.test.question.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.question.R;
import com.test.question.bean.XCursor;

import java.util.List;


public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder>{
    private Context mContext;
    //承载上下文
    //动态数组承载数据使用

    private List<XCursor> mDataList;

    private LayoutInflater mLayoutInflater;

    public ScoreAdapter(Context mContext, List<XCursor> mDataList){
        this.mContext=mContext;
        this.mDataList=mDataList;
        mLayoutInflater= LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=mLayoutInflater.from(parent.getContext()).inflate(R.layout.item_score,parent,false);
        return new ViewHolder(v);
    }

    //将数据绑定到控件
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final XCursor entity=mDataList.get(position);
        if (null==entity)
            return;
        ViewHolder viewHolder= (ViewHolder) holder;
        viewHolder.tv_name.setText("课名:"+entity.getCno().getCname());
        viewHolder.tv_no.setText("课号:"+entity.getCno().getCno());
        viewHolder.tv_credit.setText("学分:"+entity.getCno().getCredit());
        viewHolder.tv_type.setText("类别:"+entity.getCno().getCtype());
        viewHolder.tv_total.setText("总成绩:"+entity.getTotal());
        viewHolder.tv_bk.setText("补考:"+entity.getMakeup());
        viewHolder.tv_jdf.setText("积点分:"+entity.getJdf());
        viewHolder.tv_gpa.setText("GPA:"+entity.getGpa());
        viewHolder.tv_test.setText("实验:"+entity.getTest());
        viewHolder.tv_tag.setText("备注:"+entity.getTag());

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    //找到控件，将其初始化
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        TextView tv_no;
        TextView tv_credit;
        TextView tv_type;
        TextView tv_total;
        TextView tv_bk;
        TextView tv_jdf;
        TextView tv_gpa;
        TextView tv_test;
        TextView tv_tag;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
            tv_no= (TextView) itemView.findViewById(R.id.tv_no);
            tv_credit= (TextView) itemView.findViewById(R.id.tv_credit);
            tv_type= (TextView) itemView.findViewById(R.id.tv_type);
            tv_total= (TextView) itemView.findViewById(R.id.tv_total);
            tv_bk= (TextView) itemView.findViewById(R.id.tv_bk);
            tv_jdf= (TextView) itemView.findViewById(R.id.tv_jdf);
            tv_gpa= (TextView) itemView.findViewById(R.id.tv_gpa);
            tv_test= (TextView) itemView.findViewById(R.id.tv_test);
            tv_tag= (TextView) itemView.findViewById(R.id.tv_tag);
        }
    }

}