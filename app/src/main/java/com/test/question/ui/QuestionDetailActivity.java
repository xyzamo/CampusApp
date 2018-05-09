package com.test.question.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.test.question.R;
import com.test.question.base.BaseActivity;


public class QuestionDetailActivity extends BaseActivity {

    private String txt;
    private TextView tv_answer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        onSetTitle("解答");
        setBreoadcast();
        txt = getIntent().getStringExtra("txt");
        tv_answer = (TextView)findViewById(R.id.tv_answer);
        tv_answer.setText(txt);
    }


}
