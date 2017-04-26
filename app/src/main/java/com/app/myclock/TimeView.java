package com.app.myclock;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by 知らないのセカイ on 2017/4/26.
 */

public class TimeView extends LinearLayout {
    private TextView timeText;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
               refreshTime();
                handler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    };
    public TimeView(Context context) {
        super(context);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.VISIBLE) {
            handler.sendEmptyMessage(0);
        }else{
            handler.removeMessages(0);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        timeText = (TextView) findViewById(R.id.showTime);
        if (this.getVisibility() == View.VISIBLE) {
            handler.sendEmptyMessage(0);
        }
    }
    private void refreshTime(){
        Calendar calendar=Calendar.getInstance();
        timeText.setText(String.format("%d:%d:%d", calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)));
    }
}
