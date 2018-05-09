package com.test.question.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.test.question.R;
import com.test.question.base.BaseActivity;
import com.test.question.bean.Person;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText etName;
    private EditText etPwd;
    private EditText etRepwd;
    private EditText etPhone;
    private EditText etSno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setBreoadcast();
        onSetTitle("注册");
        init();
    }

    private void init() {
        etName = (EditText) findViewById(R.id.id_et_username);
        etPwd = (EditText) findViewById(R.id.id_et_password);
        etPhone = (EditText) findViewById(R.id.id_et_phone);
        etRepwd = (EditText) findViewById(R.id.id_et_repassword);
        etSno = (EditText) findViewById(R.id.id_et_sno);
        Button mBtnREgister = (Button) findViewById(R.id.id_btn_register);
        mBtnREgister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_register:
                String name = etName.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String sno = etSno.getText().toString().trim();
                String pwdAgain = etRepwd.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    onToast("用户名不能为空");
                    return;
                }
                if (TextUtils.getTrimmedLength(name) < 6) {
                    onToast("用户名长度过短");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    onToast("电话不能为空");
                    return;
                }
                if (TextUtils.isEmpty(sno)) {
                    onToast("学号不能为空");
                    return;
                }
                if (TextUtils.getTrimmedLength(sno) != 9) {
                    onToast("学号长度为9");
                    return;
                }
                if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwdAgain)) {
                    onToast("密码不能为空");
                    return;
                }
                if (TextUtils.getTrimmedLength(pwd) < 6) {
                    onToast("密码长度过短");
                    return;
                }
                if (!pwd.equals(pwdAgain)) {
                    onToast("2次密码不一致");
                    return;
                }
                showProgressDialog(RegisterActivity.this, "注册中...");
                Person user = new Person();
                user.setUsername(name);
                user.setPassword(pwd);
                user.setMobilePhoneNumber(phone);
                user.setSno(sno);
                user.signUp(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        hidProgressDialog();
                        if (e == null) {
                            onToast("注册成功");
                            finish();
                        } else {
                            onToast("注册失败" + e.getLocalizedMessage());
                        }
                    }
                });

                break;

        }
    }
}