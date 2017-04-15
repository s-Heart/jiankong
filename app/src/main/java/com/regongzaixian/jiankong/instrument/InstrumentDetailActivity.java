package com.regongzaixian.jiankong.instrument;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.regongzaixian.jiankong.R;
import com.regongzaixian.jiankong.base.BaseActivity;
import com.regongzaixian.jiankong.model.InstrumentEntity;
import com.regongzaixian.jiankong.net.NetManager;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by tony on 2017/4/15.
 */

public class InstrumentDetailActivity extends BaseActivity {
    private int instrmentId;
    private TextView tvInstrumentName;
    private Button btnSet;
    private TextView tvInstrumentModel;
    private TextView tvInstrumentSerial;
    private TextView tvTempRange;
    private TextView tvHumRange;
    private TextView tvKongwenModel;
    private TextView tvInstrumentStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initView();
        initData();
        initPresenter();
    }

    private void initPresenter() {
        queryInstrumentById(instrmentId);
        // TODO: 2017/4/15 queryData

    }

    private void initData() {
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("设定参数");
            }
        });
    }

    private void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    private void queryInstrumentById(int instrmentId) {
        showLoadingDialog();
        Observable<InstrumentEntity> observable = NetManager.getInstance().getApiService().queryInstrumentById(instrmentId);
        NetManager.getInstance().runRxJava(observable, new Subscriber<InstrumentEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                hideLoadingDialog();
                toast(e.getMessage());
            }

            @Override
            public void onNext(InstrumentEntity instrumentEntity) {
                hideLoadingDialog();
                tvInstrumentName.setText(instrumentEntity.getName());
                tvInstrumentModel.setText(instrumentEntity.getModel());
                tvInstrumentSerial.setText(instrumentEntity.getSerialnumber());
                tvTempRange.setText(instrumentEntity.getTemperaturerange());
                tvHumRange.setText(instrumentEntity.getHumidityrange());
                tvKongwenModel.setText(instrumentEntity.getTemperatureindicator());
                tvInstrumentStatus.setText("" + instrumentEntity.getStatus());
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_instrument_detail);
        initToolbar();

        tvInstrumentName = (TextView) findViewById(R.id.tv_instrument_name);
        tvInstrumentModel = (TextView) findViewById(R.id.tv_instrument_model);
        tvInstrumentSerial = (TextView) findViewById(R.id.tv_instrument_serial);
        tvTempRange = (TextView) findViewById(R.id.tv_temp_range);
        tvHumRange = (TextView) findViewById(R.id.tv_hum_range);
        tvKongwenModel = (TextView) findViewById(R.id.tv_kongwen_model);
        tvInstrumentStatus = (TextView) findViewById(R.id.tv_instrument_status);
        btnSet = (Button) findViewById(R.id.btn_set);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        TextView titleTextview = (TextView) toolbar.findViewById(R.id.toolbar_title);
        titleTextview.setText("控制程序");
        titleTextview.setVisibility(View.VISIBLE);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initIntent() {
        instrmentId = (int) getIntent().getLongExtra("instrumentId", 0);
    }
}
