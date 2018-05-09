package com.test.question.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.test.question.ui.LoginActivity;
import com.test.question.ui.PersonActivity;
import com.test.question.R;
import com.test.question.ui.TongWriteActivity;

public class MoreFragment extends Fragment implements View.OnClickListener {

    public static MoreFragment newInstance() {
        MoreFragment fragment = new MoreFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_setting, null);
        initView(view);
        return view;
    }
    private void initView(View view) {
        RelativeLayout rlMe = (RelativeLayout) view.findViewById(R.id.rl_me);
        RelativeLayout rlLoginOut = (RelativeLayout) view.findViewById(R.id.rl_login_out);
        RelativeLayout rlTongWrite = (RelativeLayout)view.findViewById(R.id.rl_tongwrite);
        rlMe.setOnClickListener(this);
        rlLoginOut.setOnClickListener(this);
        rlTongWrite.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_me:
                Intent mIntent = new Intent();
                mIntent.setClass(getActivity(), PersonActivity.class);
                startActivity(mIntent);
                break;
            case R.id.rl_login_out:
                new AlertDialog.Builder(getActivity())
                        .setTitle("提示")
                        .setMessage("确定退出吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();
                                dialogInterface.dismiss();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
                break;
            case R.id.rl_tongwrite:
                Intent nIntent = new Intent();
                nIntent.setClass(getActivity(), TongWriteActivity.class);
                startActivity(nIntent);
                break;
        }
    }
}
