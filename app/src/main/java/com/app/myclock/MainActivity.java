package com.app.myclock;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;


public class MainActivity extends AppCompatActivity {
    private TabHost tabHost;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tabTime").setContent(R.id.tabTime).setIndicator("时钟"));
        tabHost.addTab(tabHost.newTabSpec("tabAlarm").setContent(R.id.tabAlarm).setIndicator("闹钟"));
        tabHost.addTab(tabHost.newTabSpec("tabTimer").setContent(R.id.tabTimer).setIndicator("定时器"));
        tabHost.addTab(tabHost.newTabSpec("tabStopWatch").setContent(R.id.tabStopWatch).setIndicator("秒表"));
    }



}
