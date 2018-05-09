package com.test.question.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.test.question.R;
import com.test.question.base.BaseActivity;
import com.test.question.bean.Person;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;


public class PersonActivity extends BaseActivity implements View.OnClickListener {
    private PersonActivity context;
    private TextView tv_phone;
    private TextView tv_name;
    private static final int Take_Photo = 0;
    private ImageView mIvImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_person_info);
        setBreoadcast();
        onSetTitle("个人信息");
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Person user = BmobUser.getCurrentUser(Person.class);
        tv_name.setText(BmobUser.getCurrentUser().getUsername());
        tv_phone.setText(BmobUser.getCurrentUser().getMobilePhoneNumber() + "");
        BmobFile picFile = user.getIcon();
        if (picFile!=null){
            String url = picFile.getUrl();
            if (!TextUtils.isEmpty(url)){
                Glide.with(PersonActivity.this)
                        .load(url)
                        .placeholder(R.drawable.ic_fail)
                        .into(mIvImg);
            }

        }
    }

    private void initView() {
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        mIvImg = (ImageView) findViewById(R.id.iv_img);
        tv_name = (TextView) findViewById(R.id.tv_name);
        RelativeLayout rl_pwd = (RelativeLayout) findViewById(R.id.rl_pwd);
        RelativeLayout rl_pic = (RelativeLayout) findViewById(R.id.rl_pic);
        rl_pwd.setOnClickListener(this);
        rl_pic.setOnClickListener(this);
        RelativeLayout rl_phone = (RelativeLayout) findViewById(R.id.rl_phone);
        rl_phone.setOnClickListener(this);
    }

    @SuppressLint("SdCardPath")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Take_Photo:
                    if (data != null) {
                        String imageName = data.getStringExtra("imageName");
                        updateAvatarInServer(imageName);

                    }
                    break;


            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void updateAvatarInServer(String imageName) {
        final BmobFile bmobFile = new BmobFile(new File("/sdcard/food/" + imageName));
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                Person person = new Person();
                person.setIcon(bmobFile);
                BmobUser user = BmobUser.getCurrentUser();
                person.update(user.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Glide.with(PersonActivity.this)
                                    .load(bmobFile.getUrl())
                                    .placeholder(R.drawable.ic_fail)
                                    .into(mIvImg);
                            Toast.makeText(context, "图片上传成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "图片上传失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_pic:
                Intent intent = new Intent();
                intent.setClass(context, SubmitImageActivity.class);
                startActivityForResult(intent, Take_Photo);
                break;
            case R.id.rl_phone:
                Intent PIntent = new Intent();
                PIntent.setClass(context,ChangeActivity.class);
                PIntent.putExtra("type","phone");
                startActivity(PIntent);
                break;
            case R.id.rl_pwd:
                Intent passWordIntent = new Intent();
                passWordIntent.setClass(context,ChangeActivity.class);
                passWordIntent.putExtra("type","password");
                startActivity(passWordIntent);
                break;


        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
