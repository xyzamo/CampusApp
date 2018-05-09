package com.test.question.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.test.question.R;
import com.test.question.adapter.QuestionAdapter;
import com.test.question.base.BaseActivity;
import com.test.question.bean.Question;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class QuestionActivity extends BaseActivity {

    private SwipeRefreshLayout swiperefreshlayout;
    private RecyclerView recyclerview;
    private QuestionAdapter adapter;
    private List<Question> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        onSetTitle("问答列表");
        setBreoadcast();
        initView();
        loadData();
    }

    private void initView() {
        swiperefreshlayout = (SwipeRefreshLayout)findViewById(R.id.swiperefreshlayout);
        recyclerview = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        adapter = new QuestionAdapter(this,list);
        recyclerview.setAdapter(adapter);

        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        adapter.setOnItemClickListener(new QuestionAdapter.onItemClickListener() {
            @Override
            public void setOnItemClickListener(View view, int Position) {
                Intent intent = new Intent();
                intent.setClass(QuestionActivity.this,QuestionDetailActivity.class);
                intent.putExtra("txt",list.get(Position).getAnswer());
                startActivity(intent);
            }
        });
    }
    private void loadData(){
        BmobQuery<Question> query = new BmobQuery<Question>();
        query.order("-createdAt");
        query.findObjects(new FindListener<Question>() {

            @Override
            public void done(List<Question> diaries, BmobException e) {
                if (e == null) {
                    list.clear();
                    for (Question tr : diaries) {
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
