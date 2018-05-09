package com.test.question.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.question.R;
import com.test.question.bean.Tonggao;

import java.util.List;


public class TonggaoAdapter extends RecyclerView.Adapter<TonggaoAdapter.ViewHolder> {

    private Context mContext;
    //承载上下文
    //动态数组承载数据使用

    private List<Tonggao> mDataList;


    public TonggaoAdapter(Context mContext, List<Tonggao> mDataList){
        this.mContext=mContext;
        this.mDataList=mDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tonggao,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@Nullable ViewHolder holder, final int position) {
        final Tonggao entity=mDataList.get(position);
        if (null==entity)
            return;
        holder.tv_no.setText(entity.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener!=null){
                    mOnItemClickListener.setOnItemClickListener(view,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    //找到控件，将其初始化
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_no;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_no = (TextView) itemView.findViewById(R.id.tv_to);
            //tv_no.setMovementMethod(ScrollingMovementMethod.getInstance());//设置滑动
        }
    }

    public void setOnItemClickListener(onItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
    private onItemClickListener mOnItemClickListener;

    public interface onItemClickListener{
        void setOnItemClickListener(View view ,int Position);
    }

}
