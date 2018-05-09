package com.test.question.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.test.question.R;
import com.test.question.fragment.CursorFragment;
import com.test.question.fragment.HomeFragment;
import com.test.question.fragment.MoreFragment;
import com.test.question.bean.Person;

import java.lang.reflect.Method;
import java.util.Arrays;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutHome;
    LinearLayout layoutCursor;
    LinearLayout layoutMore;
    private LinearLayout[] mLayouts;
    private static final String KEY_FRAGMENT_TAG = "fragment_tag";
    private static final String FRAGMENT_TAG_HOME = "fragment_home";
    private static final String FRAGMENT_TAG_CURSOR = "fragment_cursor";
    private static final String FRAGMENT_TAG_MORE = "fragment_more";
    private String[] mFragmentTags = new String[]{ FRAGMENT_TAG_HOME,FRAGMENT_TAG_CURSOR,FRAGMENT_TAG_MORE};
    private String mFragmentCurrentTag = FRAGMENT_TAG_HOME;
    private HomeFragment mProductFragment;
    private CursorFragment mCursorFragment;
    private MoreFragment mMoreFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            restoreFragments();
            mFragmentCurrentTag = savedInstanceState.getString(KEY_FRAGMENT_TAG);
        }
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setListener();

    }

    private void initView() {
        layoutHome = findViewById(R.id.layout_home);
        layoutMore = findViewById(R.id.layout_more);
        layoutCursor = findViewById(R.id.layout_cursor);

    }

    private void initData() {
        mLayouts = new LinearLayout[]{ layoutHome, layoutCursor,layoutMore};
    }
    private void setListener() {
        for (int i = 0; i < mLayouts.length; i++) {
            mLayouts[i].setOnClickListener(this);
        }
        //performClick()方法调用之前，必须有点击事件
        //使用代码主动去调用控件的点击事件
        if (TextUtils.equals(FRAGMENT_TAG_HOME, mFragmentCurrentTag)) {
            layoutHome.performClick();
        }else if (TextUtils.equals(FRAGMENT_TAG_CURSOR, mFragmentCurrentTag)) {
            layoutCursor.performClick();
        }else if (TextUtils.equals(FRAGMENT_TAG_MORE, mFragmentCurrentTag)) {
            layoutMore.performClick();
        }

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_FRAGMENT_TAG, mFragmentCurrentTag);
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onClick(View v) {
        onTabSelect((LinearLayout) v);
    }
    /**
     * 切换tab页
     * @param itemLayout
     */
    public void onTabSelect(LinearLayout itemLayout) {
        int id = itemLayout.getId();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragments(manager, transaction);

        for (int i = 0; i < mLayouts.length; i++) {
            mLayouts[i].setSelected(false);
        }
        itemLayout.setSelected(true);

        if (id == R.id.layout_home) {
            selectedFragment(transaction, mProductFragment, HomeFragment.class, FRAGMENT_TAG_HOME);
        }  else if (id == R.id.layout_cursor) {
            selectedFragment(transaction, mCursorFragment, CursorFragment.class, FRAGMENT_TAG_CURSOR);
        }else if (id == R.id.layout_more) {
            selectedFragment(transaction, mMoreFragment, MoreFragment.class, FRAGMENT_TAG_MORE);
        }
        transaction.commit();
    }
    private void selectedFragment(FragmentTransaction transaction, Fragment fragment, Class<?> clazz, String tag) {
        mFragmentCurrentTag = tag;
        if (fragment == null) {
            try {
                Method newInstanceMethod = clazz.getDeclaredMethod("newInstance");;
                fragment = (Fragment) newInstanceMethod.invoke(null);
                if (fragment instanceof HomeFragment) {
                    mProductFragment = (HomeFragment)fragment;
                } else if (fragment instanceof CursorFragment) {
                    mCursorFragment = (CursorFragment)fragment;
                } else if (fragment instanceof MoreFragment) {
                    mMoreFragment = (MoreFragment)fragment;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //add向Activity state中添加一个Fragment。
            transaction.add(R.id.fragment_container, fragment, tag);
        }
        transaction.show(fragment);
    }
    /**
     * 先全部隐藏
     * @param fragmentManager
     * @param transaction
     */
    private void hideFragments(FragmentManager fragmentManager, FragmentTransaction transaction) {
        for (int i = 0; i < mFragmentTags.length; i++) {
            Fragment fragment = fragmentManager.findFragmentByTag(mFragmentTags[i]);
            if (fragment != null && fragment.isVisible()) {
                transaction.hide(fragment);
            }
        }
    }
    /**
     * 恢复fragment
     */
    private void restoreFragments() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        for (int i = 0; i < mFragmentTags.length; i++) {
            Fragment fragment = manager.findFragmentByTag(mFragmentTags[i]);
            if (fragment instanceof HomeFragment) {
                mProductFragment = (HomeFragment)fragment;
            }  else if (fragment instanceof CursorFragment) {
                mCursorFragment = (CursorFragment)fragment;
            } else if (fragment instanceof MoreFragment) {
                mMoreFragment = (MoreFragment)fragment;
            }
            transaction.hide(fragment);
        }
        transaction.commit();
    }
}
