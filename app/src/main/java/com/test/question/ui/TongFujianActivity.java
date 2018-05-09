package com.test.question.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.test.question.R;
import com.test.question.base.BaseActivity;


public class TongFujianActivity extends BaseActivity{
    private WebView  web_View;
    private String tv_url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tong_fujian);
        tv_url = getIntent().getStringExtra("file");
        web_View = (WebView) findViewById(R.id.web_view);
        web_View.getSettings().setJavaScriptEnabled(true);
        web_View.setWebViewClient(new WebViewClient());
        tv_url = "https://view.officeapps.live.com/op/view.aspx?src="+tv_url;
        //onToast(tv_url);
        //onToast(tv_url);
        web_View.loadUrl(tv_url);
        //onToast(tv_url);

    }

}
