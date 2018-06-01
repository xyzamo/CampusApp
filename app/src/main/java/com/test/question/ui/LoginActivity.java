package com.test.question.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


import com.test.question.R;
import com.test.question.base.BaseActivity;
import com.test.question.utils.StatusBarUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText etName;
    private EditText etPwd;
    private TextView btLogin;
    private TextView tv_register;
    private Button btSmsLogin;
    private LoginActivity context;
    private CheckBox remberPass;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setBreoadcast();
        context = this;
        StatusBarUtil.setTranslucent(context,0);
        init();
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            String account = pref.getString("account","");
            String passwo = pref.getString("passwo","");
            etName.setText(account);
            etPwd.setText(passwo);
            remberPass.setChecked(true);
        }
    }

    private void init() {
        etName = (EditText)findViewById(R.id.et_name);
        etPwd = (EditText)findViewById(R.id.et_pwd);
        btLogin = (TextView)findViewById(R.id.bt_login);
        tv_register = (TextView)findViewById(R.id.tv_register);
        btSmsLogin = (Button) findViewById(R.id.id_btn_loginsms);
        remberPass = (CheckBox)findViewById(R.id.rember_psd);

        btLogin.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        btSmsLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_btn_loginsms:
                //Intent intent = new Intent(this,);
                //startActivity(intent);
                break;
            case R.id.bt_login:
                final String name = etName.getText().toString();
                final String password = etPwd.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    onToast("请输入用户名");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    onToast("请输入密码");
                    return;
                }
                showProgressDialog(LoginActivity.this,"登录中...");
                BmobUser bu2 = new BmobUser();
                bu2.setUsername(name);
                bu2.setPassword(password);
                bu2.login(new SaveListener<BmobUser>() {

                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        hidProgressDialog();
                        if (e == null) {
                            onToast("登录成功");
                            editor = pref.edit();
                            if (remberPass.isChecked()){
                                editor.putBoolean("remember_password", true);
                                editor.putString("account",name);
                                editor.putString("passwo",password);
                            }else {
                                editor.clear();
                            }
                            editor.commit();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();

                        } else {
                            onToast("请输入正确的用户名或者密码");
                        }

                    }
                });
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
        }
    }
}
