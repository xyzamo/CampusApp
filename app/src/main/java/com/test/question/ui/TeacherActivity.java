package com.test.question.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.test.question.R;

import com.test.question.adapter.TeacherAdapter;
import com.test.question.base.BaseActivity;

import com.test.question.bean.Teacher;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class TeacherActivity extends BaseActivity {
    private SwipeRefreshLayout swiperefreshlayout;
    private RecyclerView recyclerview;
    private TeacherAdapter adapter;
    private List<Teacher> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        setBreoadcast();
        onSetTitle("老师列表");
        initView();
        loadData();
    }


    private void initView() {
        swiperefreshlayout = (SwipeRefreshLayout)findViewById(R.id.swiperefreshlayout);
        recyclerview = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter = new TeacherAdapter(this,list);
        recyclerview.setAdapter(adapter);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }
    private void loadData(){
        BmobQuery<Teacher> query = new BmobQuery<Teacher>();
        query.order("job");
        query.findObjects(new FindListener<Teacher>() {

            @Override
            public void done(List<Teacher> diaries, BmobException e) {
                if (e == null) {
                    list.clear();
                    for (Teacher tr : diaries) {
                        list.add(tr);
                    }
                    swiperefreshlayout.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                } else {
                    swiperefreshlayout.setRefreshing(false);
                }
            }

        });
    }
}
