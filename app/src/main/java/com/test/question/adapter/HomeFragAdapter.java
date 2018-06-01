package com.test.question.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.test.question.R;

import java.util.List;

public class HomeFragAdapter extends RecyclerView.Adapter<HomeFragAdapter.ViewHolder>{
    private Context mContext;
    private List<String> mDataList;

    public HomeFragAdapter(Context context) {
        this.mContext = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_cont;
        public ViewHolder(View view) {
            super(view);
            tv_cont = view.findViewById(R.id.rl_item_stylef_tv);
        }
    }

    @Override
    public HomeFragAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frag_home_style,parent,false);
        return new ViewHolder(v);
    }
    //将数据绑定到控件
    @Override
    public void onBindViewHolder(HomeFragAdapter.ViewHolder holder, int position) {

    }
    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
