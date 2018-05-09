package com.test.question.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.test.question.R;
import com.test.question.bean.CursorTableView;
import com.test.question.bean.Person;
import com.test.question.bean.TimetableView;
import com.test.question.bean.XCursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class CursorFragment extends Fragment {


    private TimetableView mTimetable;
    private String sno;
    private List<CursorTableView> list = new ArrayList<>();
    private TextView tv_y_t;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerview;
    private OptionsPickerView<Object> pvNoLinkOptions;
    private Object[] year = new Object[]{"2013","2014","2015","2016","2017","2018"};
    private Object[] term = new Object[]{"1","2"};
    private SharedPreferences sp;

    public static CursorFragment newInstance() {
        CursorFragment fragment = new CursorFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cursor, null);
        initView(view);
        initYear();
        return view;
    }
    private void initYear() {
        pvNoLinkOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String yearSelect = (String) year[options1];
                String termSelect = (String) term[options2];
                tv_y_t.setText(yearSelect+"年"+termSelect+"学期");
                //存放数据
                SharedPreferences.Editor edit = getActivity().getSharedPreferences("cursor", Context.MODE_PRIVATE).edit();
                edit.putString("year",yearSelect);
                edit.putString("term",termSelect);
                edit.commit();
                loadData(yearSelect,termSelect);
            }
        }).setLabels("年","学期","").build();
        //pvNoLinkOptions.setSelectOptions(2014,1);
        pvNoLinkOptions.setNPicker(Arrays.asList(year),Arrays.asList(term),null);

    }
    private void initView(View view) {
        sno = BmobUser.getCurrentUser(Person.class).getSno();
        mTimetable = (TimetableView)view. findViewById(R.id.timetable);
        //课程表标题，点击显示option选择器
        tv_y_t = (TextView)view. findViewById(R.id.tv_y_t);
        tv_y_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvNoLinkOptions.show();
            }
        });
        //读取数据
        sp = getActivity().getSharedPreferences("cursor", Context.MODE_PRIVATE);
        String year = sp.getString("year","");
        String term = sp.getString("term","");
        if (TextUtils.isEmpty(year)||TextUtils.isEmpty(term)){
            loadData("2014","1");
            tv_y_t.setText(2014+"年"+1+"学期");
        }else{
            loadData(year,term);
            tv_y_t.setText(year+"年"+term+"学期");
        }

    }

    private void loadData(String year ,String term){
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
                   showCursor(diaries);
                } else {
                }
            }

        });
    }
    private String[] getDFE(String time){
        return  time.split("-");
    }

    private void showCursor(List<XCursor> diaries) {
        list.clear();
        for (int i = 0; i < diaries.size(); i++) {
            XCursor cursor = diaries.get(i);
            List<String> times = cursor.getTime();
            for (String time:times) {
                String[] fed = getDFE(time);
                list.add(new CursorTableView(cursor.getCno().getCname(),cursor.getAddress(),fed[1],fed[2],fed[0]));
            }
        }
        mTimetable.loadCourses(list);
        mTimetable.setOnCourseItemClickListener(new TimetableView.OnCourseItemClickListener() {
            @Override
            public void onCourseItemClick(CursorTableView course) {
                //Toast.makeText(getContext()," 查查哈", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
