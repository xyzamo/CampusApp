package com.test.question.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.test.question.R;
import com.test.question.adapter.ScoreAdapter;
import com.test.question.base.BaseActivity;
import com.test.question.bean.Person;
import com.test.question.bean.XCursor;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class ScoreActivity extends BaseActivity {
    private SwipeRefreshLayout swiperefreshlayout;
    private RecyclerView recyclerview;
    private ScoreAdapter adapter;
    private List<XCursor> list = new ArrayList<>();
    private String year;
    private String term;
    private String sno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        setBreoadcast();

        year = getIntent().getStringExtra("year");
        term =  getIntent().getStringExtra("term");
        sno = BmobUser.getCurrentUser(Person.class).getSno();
        onSetTitle(year+"年"+term+"学期成绩");
        initView();
        loadData();
    }

    private void initView() {
        swiperefreshlayout = (SwipeRefreshLayout)findViewById(R.id.swiperefreshlayout);
        recyclerview = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter = new ScoreAdapter(this,list);
        recyclerview.setAdapter(adapter);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }
    private void loadData(){
        BmobQuery<XCursor> eq2 = new BmobQuery<XCursor>();
        eq2.addWhereEqualTo("Sno", sno);
        BmobQuery<XCursor> eq3 = new BmobQuery<XCursor>();
        eq3.addWhereEqualTo("Cyear", year);
        BmobQuery<XCursor> eq4 = new BmobQuery<XCursor>();
        eq4.addWhereEqualTo("Cterm", term);
        List<BmobQuery<XCursor>> queries = new ArrayList<BmobQuery<XCursor>>();
        queries.add(eq3);
        queries.add(eq4);
        queries.add(eq2);
        BmobQuery<XCursor> query = new BmobQuery<XCursor>();
        query.and(queries);
        query.order("-createdAt");
        query.include("Cno");
        query.findObjects(new FindListener<XCursor>() {

            @Override
            public void done(List<XCursor> diaries, BmobException e) {
                if (e == null) {
                    list.clear();
                    for (XCursor tr : diaries) {
                        if (tr.getTag().equals("正常")){
                            list.add(tr);
                        }
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
