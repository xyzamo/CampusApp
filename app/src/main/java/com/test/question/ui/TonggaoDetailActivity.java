package com.test.question.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.test.question.R;
import com.test.question.base.BaseActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

public class TonggaoDetailActivity extends BaseActivity implements View.OnClickListener {

    private String txt;
    private TextView tv_tong;
    private Button tv_fujian;
    private String tv_url;

    private String fileName;
    private String group;
    private String url;

    private  WebView web_View;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tonggao_detail);
        onSetTitle(getIntent().getStringExtra("tit"));
        setBreoadcast();
        txt = getIntent().getStringExtra("txt");
        tv_tong = (TextView)findViewById(R.id.tv_tonggao);
        tv_fujian =(Button) findViewById(R.id.tv_fujian_xml);
        tv_tong.setText(txt);
        //设置TextView滑动。
        tv_tong.setMovementMethod(ScrollingMovementMethod.getInstance());
        tv_url = getIntent().getStringExtra("file");
        if (tv_url==null){
            tv_fujian.setVisibility(View.INVISIBLE);
        }else {
            tv_fujian.setOnClickListener(this);
           /* BmobFile bmobfile =new BmobFile("abc.doc","",tv_url);
            downloadFile(bmobfile);*/
        }
    }
   /* public BmobFile(String fileName,String group,String url){
        this.filename = fileName;
        this.group=group;
        this.url = url;
    }*/
    private void downloadFile(BmobFile file){
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
        file.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
                onToast("开始下载");
                        //toast("开始下载...");
            }

            @Override
            public void done(String savePath,BmobException e) {
                if(e==null){
                    onToast("成功");
                    onToast(savePath);
                    //toast("下载成功,保存路径:"+savePath);
                }else{
                    onToast("失败");//toast("下载失败："+e.getErrorCode()+","+e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value, long newworkSpeed) {
                Log.i("bmob","下载进度："+value+","+newworkSpeed);
            }

        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_fujian_xml:
                //File file = new File(getApplicationContext().getCacheDir()+"/bmob/",fileName);
                Intent mintent = new Intent(this, TongFujianActivity.class);
                mintent.putExtra("file",tv_url);
                //onToast(tv_url);
                startActivity(mintent);
                Log.v("hhh",tv_url);
                //Intent intent = getWordFileIntent(file);
                /*Intent intent = getWordFileIntent(file);
                try {
                    this.startActivity(intent);
                }catch (Exception e) {
                    Toast.makeText(this,"找不到可以打开该文件的程序",Toast.LENGTH_SHORT).show();
                }*/
                //getWordFileIntent(file);
        }
    }
    public static Intent getWordFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/WPS Office");
        return intent;
    }
}
