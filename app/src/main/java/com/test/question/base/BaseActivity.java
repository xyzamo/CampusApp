package com.test.question.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.test.question.R;
import com.test.question.utils.DialogUtils;

import static android.net.ConnectivityManager.TYPE_MOBILE;
import static android.net.ConnectivityManager.TYPE_WIFI;


public class BaseActivity extends AppCompatActivity {

    public void showProgressDialog(Context context , String content) {
        DialogUtils.showProgressDialog(context,content,false);
    }

    //设置标题
    public void onSetTitle(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.at_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.back_arr);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReturn();
            }
        });
        TextView mTitle = (TextView) toolbar.findViewById(R.id.at_title);
        mTitle.setText(title.toString());
    }
    public void onToast(String msg) {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        hidProgressDialog();
    }
    public void onReturn(){
       finish();
    }
    //隐藏对话框（进度条）
    public void hidProgressDialog() {
        DialogUtils.hideProgressDialog();
    }

    public void showError(String error) {

    }
    public void setBreoadcast() {
        BroadcastReceiver receiver=new NetworkChangeReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }
    public class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                switch (networkInfo.getType()) {
                    case TYPE_MOBILE:
                        //Toast.makeText(context, "正在使用2G/3G/4G网络", Toast.LENGTH_SHORT).show();
                        break;
                    case TYPE_WIFI:
                        //Toast.makeText(context, "正在使用wifi上网", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            } else {
                Toast.makeText(context, "请连接网络", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
