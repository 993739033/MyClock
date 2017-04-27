package com.app.myclock;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by 知らないのセカイ on 2017/4/26.
 */

public class AlarmView extends LinearLayout {
    private Button Btn_add;
    private ListView listView;

    private static String KEY_ALARM_LIST="alarm";

    public AlarmView(Context context) {
        super(context);
    }

    public AlarmView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AlarmView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private   ArrayAdapter<AlarmView.AlarmDate> alarmDateArrayAdapter;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Btn_add = (Button) findViewById(R.id.Btn_addAlarm);
        listView = (ListView) findViewById(R.id.listview);

    alarmDateArrayAdapter  = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        listView.setAdapter(alarmDateArrayAdapter);
        Btn_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
             addAlarm();
            }
        });
        readsaveAlarmList();
    }
    private void addAlarm(){

        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                Calendar currentTime = Calendar.getInstance();
                if (c.getTimeInMillis() <= currentTime.getTimeInMillis()) {
                    c.setTimeInMillis(c.getTimeInMillis()+24*60*60*1000);

                }
                alarmDateArrayAdapter.add(new  AlarmDate(c.getTimeInMillis()));
                saveAlarmList();
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),true).show();
    }
    private void saveAlarmList(){
        SharedPreferences.Editor editor = getContext().getSharedPreferences(AlarmView.class.getName(), Context.MODE_PRIVATE).edit();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<alarmDateArrayAdapter.getCount();i++) {
            stringBuilder.append(alarmDateArrayAdapter.getItem(i).getTime()).append(",");
        }
        String content = stringBuilder.toString().substring(0, stringBuilder.length() - 1);
        editor.putString(KEY_ALARM_LIST, content);
        editor.commit();

    }
    private void readsaveAlarmList() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(AlarmView.class.getName(), Context.MODE_PRIVATE);

        String content = sharedPreferences.getString(KEY_ALARM_LIST, null);
        if (content != null) {
        String[] dates = content.split(",");
        for (String s : dates) {
            alarmDateArrayAdapter.add(new AlarmDate(Long.parseLong(s)));
        }
     }

    }
    private static class AlarmDate {

        public long getTime() {
            return time;
        }

        public String getTimeLabel() {
            return timeLabel;
        }

        private long time=0;
        private Calendar calendar;
        private String timeLabel="";
        public AlarmDate(long time) {
            this.time=time;
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            timeLabel = calendar.get(Calendar.MONTH)+1+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日"+
            calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
        }

        @Override
        public String toString() {
            return timeLabel;
        }
    }

}
