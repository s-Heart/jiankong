package com.regongzaixian.jiankong.instrument;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.regongzaixian.jiankong.R;
import com.regongzaixian.jiankong.base.BaseActivity;
import com.regongzaixian.jiankong.net.NetManager;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by tony on 2017/4/16.
 */

public class SettingActivity extends BaseActivity {

    private float tempMaxf;
    private float tempMinf;
    private float humMaxf;
    private float humMinf;
    private EditText edTemp;
    private EditText edHum;
    private Button btnSubmit;
    private int instrumentId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent();
        initView();
        initData();
    }

    private void handleIntent() {
        instrumentId = getIntent().getIntExtra("instrumentId", 0);
        String tempMax = getIntent().getStringExtra("tempMax");
        String tempMin = getIntent().getStringExtra("tempMin");
        String humMax = getIntent().getStringExtra("humMax");
        String humMin = getIntent().getStringExtra("humMin");
        if (!tempMax.isEmpty()) {
            tempMaxf = Float.valueOf(tempMax);
        }
        if (!tempMin.isEmpty()) {
            tempMinf = Float.valueOf(tempMin);
        }
        if (!humMax.isEmpty()) {
            humMaxf = Float.valueOf(humMax);
        }
        if (!humMin.isEmpty()) {
            humMinf = Float.valueOf(humMin);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_setting);
        initToolbar();

        edTemp = (EditText) findViewById(R.id.ed_temp);
        edHum = (EditText) findViewById(R.id.ed_hum);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        TextView titleTextview = (TextView) toolbar.findViewById(R.id.toolbar_title);
        titleTextview.setText("设置");
        titleTextview.setVisibility(View.VISIBLE);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkParam()) {
                    doSubmit();
                }
            }
        });
    }

    private void doSubmit() {
        showLoadingDialog();
        Map<String, String> params = new HashMap<>();
        params.put("", "");
        Observable<Object> observable = NetManager.getInstance().getApiService().modifySettings(params);
        NetManager.getInstance().runRxJava(observable, new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                hideLoadingDialog();
                toast(e.getMessage());
            }

            @Override
            public void onNext(Object o) {
                hideLoadingDialog();
                setResult(InstrumentDetailActivity.SETTING_OK);
                finish();
            }
        });
    }

    private boolean checkParam() {
        if (edTemp.getText().toString().isEmpty()) {
            toast("请填写温度参数");
            return false;
        }
        if (edHum.getText().toString().isEmpty()) {
            toast("请填写湿度参数");
            return false;
        }

        float temp = Float.parseFloat(edTemp.getText().toString());
        float hum = Float.parseFloat(edHum.getText().toString());
        if (temp > tempMaxf || temp < tempMinf) {
            toast("请确定温度设定范围在" + tempMinf + "-" + tempMaxf + "之间");
            return false;
        }

        if (hum > humMaxf || hum < humMinf) {
            toast("请确定湿度设定范围在" + humMinf + "-" + humMaxf + "之间");
            return false;
        }
        return true;
    }

    private void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}
