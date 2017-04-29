package com.app.myclock;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 知らないのセカイ on 2017/4/29.
 */

public class StopTimeForWatchView extends LinearLayout implements View.OnClickListener {
    public StopTimeForWatchView(Context context) {
        super(context);
    }

    public StopTimeForWatchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StopTimeForWatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private Button Btn_Start,Btn_Reset,Btn_Pause,Btn_Resume,Btn_Lap;
    private TextView Tv_Hour,Tv_Min,Tv_Second,Tv_MSecond;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Btn_Start = (Button) findViewById(R.id.Stop_Btn_Start);
        Btn_Pause = (Button) findViewById(R.id.Stop_Btn_Pause);
        Btn_Reset = (Button) findViewById(R.id.Stop_Btn_Reset);
        Btn_Resume= (Button) findViewById(R.id.Stop_Btn_Remuse);
        Btn_Lap = (Button) findViewById(R.id.Stop_Btn_Start);

        Tv_Hour = (TextView) findViewById(R.id.Stop_Hour);
        Tv_Min = (TextView) findViewById(R.id.Stop_Min);
        Tv_Second = (TextView) findViewById(R.id.Stop_Second);
        Tv_MSecond= (TextView) findViewById(R.id.Stop_MSecond);

        Btn_Reset.setVisibility(View.GONE);
        Btn_Pause.setVisibility(View.GONE);
        Btn_Resume.setVisibility(GONE);
        Btn_Lap.setVisibility(GONE);

        initView();

    }
    private Timer tiemr;
    private TimerTask timerTask;

    private void initView(){
        Btn_Start.setOnClickListener(this);
        Btn_Pause.setOnClickListener(this);
        Btn_Resume.setOnClickListener(this);
        Btn_Lap.setOnClickListener(this);
        Btn_Reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Stop_Btn_Start:
                break;
            case R.id.Stop_Btn_Remuse:
                break;
            case R.id.Stop_Btn_Lap:
                break;
            case R.id.Stop_Btn_Pause:
                break;
            case R.id.Stop_Btn_Reset:
                break;
        }

    }
}
