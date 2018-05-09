package com.test.question.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.test.question.R;
import com.test.question.ui.QuestionActivity;
import com.test.question.ui.ScoreActivity;
import com.test.question.ui.TeacherActivity;
import com.test.question.ui.TonggaoActivity;

import java.util.Arrays;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private OptionsPickerView<Object> pvNoLinkOptions;
    private Object[] year = new Object[]{"2014", "2015", "2016", "2017","2018"};
    private Object[] term = new Object[]{"1", "2","3"};

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, null);
        initView(view);
        initYear();
        return view;
    }

    private void initYear() {
        pvNoLinkOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                //https://blog.csdn.net/daidaishuiping/article/details/68485004
                String yearSelect = (String) year[options1];
                String termSelect = (String) term[options2];
                Intent intent = new Intent(getActivity(), ScoreActivity.class);
                intent.putExtra("year", yearSelect);
                intent.putExtra("term", termSelect);
                startActivity(intent);
            }
        }).setLabels("年", "学期", "").build();
        //二级选择添加数据源
        pvNoLinkOptions.setNPicker(Arrays.asList(year), Arrays.asList(term), null);

    }

    private void initView(View view) {
        RelativeLayout rl_question = (RelativeLayout) view.findViewById(R.id.rl_question);
        RelativeLayout rl_cj = (RelativeLayout) view.findViewById(R.id.rl_cj);
        RelativeLayout rl_teacher = (RelativeLayout) view.findViewById(R.id.rl_teacher);
        RelativeLayout rl_tonggao = (RelativeLayout) view.findViewById(R.id.rl_tonggao);
        rl_question.setOnClickListener(this);
        rl_cj.setOnClickListener(this);
        rl_teacher.setOnClickListener(this);
        rl_tonggao.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_cj:
                pvNoLinkOptions.show();
                break;
            case R.id.rl_question:
                startActivity(new Intent(getActivity(), QuestionActivity.class));
                break;
            case R.id.rl_teacher:
                startActivity(new Intent(getActivity(), TeacherActivity.class));
                break;
            case R.id.rl_tonggao:
                startActivity(new Intent(getActivity(), TonggaoActivity.class));
                break;
        }
    }
}
