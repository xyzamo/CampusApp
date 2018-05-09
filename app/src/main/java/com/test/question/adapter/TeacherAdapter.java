package com.test.question.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.test.question.R;
import com.test.question.bean.Teacher;

import java.util.List;


public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder>{
    private Context mContext;
    //承载上下文
    //动态数组承载数据使用

    private List<Teacher> mDataList;


    public TeacherAdapter(Context mContext, List<Teacher> mDataList){
        this.mContext=mContext;
        this.mDataList=mDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher,parent,false);
        return new ViewHolder(v);
    }

    //将数据绑定到控件
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Teacher entity=mDataList.get(position);
        if (null==entity)
            return;
        ViewHolder viewHolder= (ViewHolder) holder;
        viewHolder.tv_name.setText("姓名:"+entity.getName());
        viewHolder.tv_phone.setText("电话:"+entity.getPhone());
        viewHolder.tv_address.setText("办公室:"+entity.getAddress());
        viewHolder.tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call(entity.getPhone());
                Toast.makeText(mContext,entity.getPhone(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    //找到控件，将其初始化
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        TextView tv_phone;
        TextView tv_address;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
            tv_phone= (TextView) itemView.findViewById(R.id.tv_phone);
            tv_address= (TextView) itemView.findViewById(R.id.tv_address);

        }
    }

}