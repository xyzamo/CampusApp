package com.test.question;

import android.app.Application;
import android.content.Context;

import com.test.question.ui.MainActivity;

import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;


public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Bmob.initialize(this, "4557c29cfeab2a38b4de6a5fd0158be8");
        BmobSMS.initialize(this, "4557c29cfeab2a38b4de6a5fd0158be8");
    }
}
