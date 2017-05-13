package com.regongzaixian.jiankong.instrument;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.regongzaixian.jiankong.R;
import com.regongzaixian.jiankong.base.BaseActivity;
import com.regongzaixian.jiankong.model.InstrumentDataEntity;
import com.regongzaixian.jiankong.model.InstrumentEntity;
import com.regongzaixian.jiankong.net.NetManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by tony on 2017/4/15.
 */

public class InstrumentDetailActivity extends BaseActivity implements OnChartValueSelectedListener {
    public static final int SETTING_OK = 0;
    private int instrmentId;
    private TextView tvInstrumentName;
    private Button btnSet;
    private TextView tvInstrumentModel;
    private TextView tvInstrumentId;
    private TextView tvTempRange;
    private TextView tvHumRange;
    private TextView tvKongwenModel;
    private TextView tvInstrumentStatus;
    InstrumentEntity instrumentEntitys;
    private LineChart mChart;
    private ScrollView scrollView;
    private TextView tvTempMain;
    private TextView tvHumMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initView();
        initData();
        initPresenter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SETTING_OK) {
            queryInstrumentById(instrmentId);
        }
    }

    private void initPresenter() {
        queryInstrumentById(instrmentId);
    }


    private void initData() {
        mChart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    scrollView.requestDisallowInterceptTouchEvent(false);//拦截list的点击事件
                } else {
                    scrollView.requestDisallowInterceptTouchEvent(true);//不拦截list的点击事件
                }
                return false;
            }
        });
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSet();

            }
        });
    }

    private void onClickSet() {
        Intent intent = new Intent(this, SettingActivity.class);
        intent.putExtra("tempMax", instrumentEntitys.getTemperaturerangemax());
        intent.putExtra("tempMin", instrumentEntitys.getTemperaturerangemin());
        intent.putExtra("humMax", instrumentEntitys.getHumidityrangemax());
        intent.putExtra("humMin", instrumentEntitys.getHumidityrangemin());
        intent.putExtra("instrumentId", instrmentId);
        startActivityForResult(intent, 2);
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
                instrumentEntitys = instrumentEntity;
                tvInstrumentName.setText(instrumentEntity.getName());
                tvInstrumentModel.setText(instrumentEntity.getModel());
                tvInstrumentId.setText("" + instrumentEntity.getId());
                tvTempRange.setText(instrumentEntity.getTemperaturerange());
                tvHumRange.setText(instrumentEntity.getHumidityrange());
                tvKongwenModel.setText(instrumentEntity.getTemperatureindicator());
                tvInstrumentStatus.setText("" + instrumentEntity.getStatus());
                tvTempMain.setText("" + instrumentEntity.getMeantemperature());
                tvHumMain.setText("" + instrumentEntity.getMeanhumidity());
                refreshChart();
            }
        });
    }

    private void refreshChart() {
        Map<String, String> params = new HashMap<>();
        params.put("where", "{\"instrument\":" + "\"" + instrmentId + "\"" + "}");
        Observable<InstrumentDataEntity> observable = NetManager.getInstance().getApiService().queryInstrumentData(params);
        NetManager.getInstance().runRxJava(observable, new Subscriber<InstrumentDataEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                toast(e.getMessage());
            }

            @Override
            public void onNext(InstrumentDataEntity data) {
                setData(data.getTime().size(), data);
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_instrument_detail);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        initToolbar();
        initChart();

        tvInstrumentName = (TextView) findViewById(R.id.tv_instrument_name);
        tvInstrumentModel = (TextView) findViewById(R.id.tv_instrument_model);
        tvInstrumentId = (TextView) findViewById(R.id.tv_instrument_id);
        tvTempRange = (TextView) findViewById(R.id.tv_temp_range);
        tvHumRange = (TextView) findViewById(R.id.tv_hum_range);
        tvTempMain = (TextView) findViewById(R.id.tv_temp_main);
        tvHumMain = (TextView) findViewById(R.id.tv_hum_main);
        tvKongwenModel = (TextView) findViewById(R.id.tv_kongwen_model);
        tvInstrumentStatus = (TextView) findViewById(R.id.tv_instrument_status);
        btnSet = (Button) findViewById(R.id.btn_set);
    }

    private void initChart() {
        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);

        // add data
//        setData(20, 30);

        mChart.animateX(2500);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(11f);
        l.setTextColor(Color.BLACK);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(20f);
        xAxis.setGranularity(1f);//粒度
        xAxis.setLabelRotationAngle(15f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(250f + 10f);
        leftAxis.setAxisMinimum(10f - 1f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaximum(100 + 10f);
        rightAxis.setAxisMinimum(40 - 1f);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
    }

    private void setData(int count, final InstrumentDataEntity dataEntity) {
        //refresh x
        XAxis xAxis = mChart.getXAxis();
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(count);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value == 0) {
                    return dataEntity.getTime().get(0);
                } else {
                    return dataEntity.getTime().get((int) (value - 1));
                }
            }
        });
        //refresh y
        if (instrumentEntitys != null) {
            YAxis leftAxis = mChart.getAxisLeft();

            leftAxis.setAxisMaximum(Float.parseFloat(instrumentEntitys.getTemperaturerangemax()) + 10f);
            leftAxis.setAxisMinimum(Float.parseFloat(instrumentEntitys.getTemperaturerangemin()) - 10f);

            YAxis rightAxis = mChart.getAxisRight();
            rightAxis.setAxisMaximum(Float.parseFloat(instrumentEntitys.getHumidityrangemax()) + 10f);
            rightAxis.setAxisMinimum(Float.parseFloat(instrumentEntitys.getHumidityrangemin()) - 10f);
        }


        ArrayList<Entry> yVals1 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yVals1.add(new Entry(i, dataEntity.getTempPV().get(i)));
        }
        ArrayList<Entry> yVals2 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yVals2.add(new Entry(i, dataEntity.getTempSV().get(i)));
        }
        ArrayList<Entry> yVals3 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yVals3.add(new Entry(i, dataEntity.getHumPV().get(i)));
        }
        ArrayList<Entry> yVals4 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yVals4.add(new Entry(i, dataEntity.getHumSV().get(i)));
        }

        LineDataSet set1, set2, set3, set4;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) mChart.getData().getDataSetByIndex(2);
            set4 = (LineDataSet) mChart.getData().getDataSetByIndex(3);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            set3.setValues(yVals3);
            set4.setValues(yVals4);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals1, "温度pv");
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);

            set2 = new LineDataSet(yVals2, "温度sv");
            set2.enableDashedLine(10f, 5f, 0f);
            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set2.setColor(ColorTemplate.getHoloBlue());
            set2.setCircleColor(Color.BLACK);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(ColorTemplate.getHoloBlue());
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            set2.setFormLineWidth(2f);
            set2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set2.setFormSize(15.f);


            // create a dataset and give it a type
            set3 = new LineDataSet(yVals3, "湿度pv");
            set3.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set3.setColor(Color.RED);
            set3.setCircleColor(Color.BLACK);
            set3.setLineWidth(2f);
            set3.setCircleRadius(3f);
            set3.setFillAlpha(65);
            set3.setFillColor(Color.RED);
            set3.setDrawCircleHole(false);
            set3.setHighLightColor(Color.rgb(244, 117, 117));
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set4 = new LineDataSet(yVals4, "湿度sv");
            set4.enableDashedLine(10f, 5f, 0f);
            set4.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set4.setColor(Color.RED);
            set4.setCircleColor(Color.BLACK);
            set4.setLineWidth(2f);
            set4.setCircleRadius(3f);
            set4.setFillAlpha(65);
            set4.setFillColor(ColorTemplate.colorWithAlpha(Color.RED, 200));
            set4.setDrawCircleHole(false);
            set4.setHighLightColor(Color.rgb(244, 117, 117));
            set4.setFormLineWidth(2f);
            set4.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set4.setFormSize(15.f);

            // create a data object with the datasets
            LineData data = new LineData(set1, set2, set3, set4);
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);
            // set data
            mChart.setData(data);
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        mChart.centerViewToAnimated(e.getX(), e.getY(), mChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency(), 500);
    }

    @Override
    public void onNothingSelected() {

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
