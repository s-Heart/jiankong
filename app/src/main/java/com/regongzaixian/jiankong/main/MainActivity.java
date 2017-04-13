package com.regongzaixian.jiankong.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.regongzaixian.jiankong.R;
import com.regongzaixian.jiankong.base.BaseActivity;
import com.regongzaixian.jiankong.login.view.LoginActivity;
import com.regongzaixian.jiankong.util.Preferences;

/**
 * Author: tony(110618445@qq.com)
 * Date: 2017/4/4
 * Time: 下午3:36
 * Description:
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView mainPageTitle;
    private RelativeLayout viewAddTest;
    private RelativeLayout viewTestedMgr;
    private RelativeLayout viewTest;
    private RelativeLayout viewDataQuery;
    private BroadcastReceiver tokenInvalidReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("token_invalid")) {
                Intent intentNew = new Intent();
                intentNew.setClass(context, MainActivity.class);
                intentNew.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //注意本行的FLAG设置
                startActivity(intentNew);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        registerBroadcast();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tokenInvalidReceiver != null) {
            unregisterReceiver(tokenInvalidReceiver);
        }
    }

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("token_invalid");
        registerReceiver(tokenInvalidReceiver, filter);
    }

    @Override
    protected void onNewIntent(Intent intentFlag) {
        super.onNewIntent(intentFlag);
        //退出
        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intentFlag.getFlags()) != 0) {
            jumpLoginActivity();
        }
    }

    private void jumpLoginActivity() {
        Preferences.getInstance().storeToken("");//清空token,重置retrofit
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        initToolbar();

        viewAddTest = (RelativeLayout) findViewById(R.id.layout_view_add_test);
        viewTestedMgr = (RelativeLayout) findViewById(R.id.layout_tested_mgr);
        viewTest = (RelativeLayout) findViewById(R.id.layout_test);
        viewDataQuery = (RelativeLayout) findViewById(R.id.layout_data_query);

        viewAddTest.setOnClickListener(this);
        viewTestedMgr.setOnClickListener(this);
        viewTest.setOnClickListener(this);
        viewDataQuery.setOnClickListener(this);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        mainPageTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_main_page);
        mainPageTitle.setText("当前用户：" + Preferences.getInstance().getEmail());
        mainPageTitle.setVisibility(View.VISIBLE);

        TextView logoutTv = (TextView) toolbar.findViewById(R.id.toolbar_right_menu);
        logoutTv.setText("安全退出");
        logoutTv.setVisibility(View.VISIBLE);
        logoutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpLoginActivity();
            }
        });
    }

    private void initData() {
    }

    @Override
    public void onClick(View view) {
    }
}
