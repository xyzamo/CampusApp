package com.test.question.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.test.question.R;
import com.test.question.base.BaseActivity;
import com.test.question.bean.Tonggao;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class TongWriteActivity extends BaseActivity {

    private EditText tong_title;
    private EditText tong_content;
    private Button btn_tong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tong_write);
        setBreoadcast();
        //onSetTitle("写通告Activity");
        initView();
    }
    private void initView() {
        tong_title = (EditText) findViewById(R.id.tong_title);
        tong_content = (EditText) findViewById(R.id.tong_content);
        btn_tong = (Button) findViewById(R.id.tong_btn);
        btn_tong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tonggao tgao = new Tonggao();
                tgao.setTitle(tong_title.getText().toString());
                tgao.setContent(tong_content.getText().toString());
                tgao.setTongfile(null);
                if(BmobUser.getCurrentUser().getUsername().equals("123"))
                {
                    tgao.save(new SaveListener<String>(){
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                onToast("上传成功");
                                finish();
                            } else {
                                Toast.makeText(TongWriteActivity.this, "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    onToast("请用管理员账号");
                }
                //onToast("上传成功");
                Log.w("title",tong_title.getText().toString());
            }
        });
    }



}
