package com.app.myclock;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 知らないのセカイ on 2017/4/28.
 */

public class TimerView extends LinearLayout {
    private Timer timer = null;
    private TimerTask timerTask = null;
    private static int getTime;//用于获取设置的总的时间值 单位秒
    private final static int MSG_WHAT_TIME_IS_UP = 1;
    private final static int MSG_WHAT_TIME_Tick = 2;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_WHAT_TIME_IS_UP:
                    new AlertDialog.Builder(getContext()).setTitle("Time is up").setMessage("Time is up yo!").
                            setNegativeButton("ComeBack", null).show();

                    StopTimerTask();
                    Btn_Start.setVisibility(VISIBLE);
                    Btn_Pause.setVisibility(View.GONE);
                    Btn_Reset.setVisibility(View.GONE);
                    Et_Hour.setEnabled(true);
                    Et_Min.setEnabled(true);
                    Et_Second.setEnabled(true);
                    break;
                case MSG_WHAT_TIME_Tick:
                    int hour = getTime / 60 / 60;
                    int min = (getTime / 60) % 60;
                    int second = getTime % 60 % 60;
                    Et_Hour.setText(hour + "");
                    Et_Min.setText(min + "");
                    Et_Second.setText(second + "");
                    break;

                default:
                    break;

            }
        }
    };

    public TimerView(Context context) {
        super(context);
    }

    public TimerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TimerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Button Btn_Start, Btn_Reset, Btn_Pause, Btn_Resume;
    private EditText Et_Hour, Et_Min, Et_Second;

    @Override
    protected void onFinishInflate() {
        /**
         * 在加载视图之后 开始初始化界面
         */
        super.onFinishInflate();
        Btn_Start = (Button) findViewById(R.id.Btn_Start);
        Btn_Reset = (Button) findViewById(R.id.Btn_Reset);
        Btn_Resume = (Button) findViewById(R.id.Btn_Resume);
        Btn_Pause = (Button) findViewById(R.id.Btn_Pause);
        Et_Hour = (EditText) findViewById(R.id.Et_Hour);
        Et_Min = (EditText) findViewById(R.id.Et_Min);
        Et_Second = (EditText) findViewById(R.id.Et_Second);

        Et_Hour.setText("00");
        Et_Min.setText("00");
        Et_Second.setText("00");

        Btn_Start.setEnabled(false);
        Btn_Pause.setVisibility(View.GONE);
        Btn_Resume.setVisibility(View.GONE);
        Btn_Reset.setVisibility(View.GONE);

        /**
         * 为hour，min，second edittext 设置addTextChangedListener
         */

        Et_Hour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    if (Integer.parseInt(s.toString()) > 59) {
                        Et_Hour.setText("59");
                    } else if (Integer.parseInt(s.toString()) < 0) {
                        Et_Hour.setText("00");
                    }
                    CheckToEnableStartButton();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Et_Min.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    if (Integer.parseInt(s.toString()) > 59) {
                        Et_Min.setText("59");
                    } else if (Integer.parseInt(s.toString()) < 0) {
                        Et_Min.setText("00");
                    }
                    CheckToEnableStartButton();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Et_Second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    if (Integer.parseInt(s.toString()) > 59) {
                        Et_Second.setText("59");
                    } else if (Integer.parseInt(s.toString()) < 0) {
                        Et_Second.setText("00");
                    }
                    CheckToEnableStartButton();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Btn_Start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTimerTask();
                Btn_Start.setVisibility(View.GONE);
                Btn_Pause.setVisibility(VISIBLE);
                Btn_Reset.setVisibility(VISIBLE);
                Et_Hour.setEnabled(false);
                Et_Min.setEnabled(false);
                Et_Second.setEnabled(false);
            }
        });
        Btn_Pause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Btn_Pause.setVisibility(View.GONE);
                Btn_Resume.setVisibility(VISIBLE);
                StopTimerTask();
            }
        });
        Btn_Resume.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Btn_Pause.setVisibility(VISIBLE);
                Btn_Resume.setVisibility(View.GONE);
                StartTimerTask();

            }
        });
        Btn_Reset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                StopTimerTask();
                Et_Hour.setEnabled(true);
                Et_Min.setEnabled(true);
                Et_Second.setEnabled(true);
                Btn_Reset.setVisibility(View.GONE);
                Btn_Pause.setVisibility(View.GONE);
                Btn_Resume.setVisibility(View.GONE);
                Btn_Start.setVisibility(VISIBLE);
                Et_Hour.setText("0");
                Et_Min.setText("0");
                Et_Second.setText("0");

            }
        });

    }


    private void StartTimerTask() {
        getTime = Integer.parseInt(TextUtils.isEmpty(Et_Hour.getText().toString()) ? "0" : Et_Hour.getText().toString()) * 60 * 60 +
                Integer.parseInt(TextUtils.isEmpty(Et_Min.getText().toString()) ? "0" : Et_Min.getText().toString()) * 60 +
                Integer.parseInt(TextUtils.isEmpty(Et_Second.getText().toString()) ? "0" : Et_Second.getText().toString());
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    getTime--;
                    handler.sendEmptyMessage(MSG_WHAT_TIME_Tick);
                    if (getTime <= 0) {
                        Message message = new Message();
                        message.what = MSG_WHAT_TIME_IS_UP;
                        handler.sendMessage(message);

                    }

                }
            };

            timer = new Timer();
            timer.schedule(timerTask, 1000, 1000);//第二个参数是延迟触发的时间 第三个参数是重复执行的时间间隔 如 不设置第三个参数 则只进行一次执行

        }


    }

    private void StopTimerTask() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;


        }

    }

    private void CheckToEnableStartButton() {
        Btn_Start.setEnabled((!TextUtils.isEmpty(Et_Hour.getText()) && Integer.parseInt(Et_Hour.getText().toString()) > 0)
                || (!TextUtils.isEmpty(Et_Min.getText()) && Integer.parseInt(Et_Min.getText().toString()) > 0)
                || (!TextUtils.isEmpty(Et_Second.getText()) && Integer.parseInt(Et_Second.getText().toString()) > 0));
    }
}
