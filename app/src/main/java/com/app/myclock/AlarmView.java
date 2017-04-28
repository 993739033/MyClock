package com.app.myclock;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by 知らないのセカイ on 2017/4/26.
 */

public class AlarmView extends LinearLayout {
    private Button Btn_add;
    private ListView listView;
    private AlarmManager alarmManager;
    private static int pendingid=1;

    private static String KEY_ALARM_LIST="alarm";

    public AlarmView(Context context) {
        super(context);
        initView();
    }

    public AlarmView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AlarmView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
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
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getContext()).setItems(new CharSequence[]{"Delete"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                deleteAlarm(position);
                                break;
                            default:
                                break;
                        }
                    }
                }).setTitle("请选择操作").setNegativeButton("CANEL", null).show();

                return true;
            }
        });
    }





    private void deleteAlarm(int position) {
        AlarmDate date= alarmDateArrayAdapter.getItem(position);
        alarmDateArrayAdapter.remove(date);
        saveAlarmList();
        alarmManager.cancel(
                PendingIntent.getBroadcast(getContext(),date.getId(),new Intent(getContext(),AlarmBroadCast.class),0));
    }

    private void addAlarm(){

        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                AlarmDate date = new AlarmDate(c.getTimeInMillis());
                Calendar currentTime = Calendar.getInstance();
                if (c.getTimeInMillis() <= currentTime.getTimeInMillis()) {
                    c.setTimeInMillis(c.getTimeInMillis()+24*60*60*1000);

                }
                alarmDateArrayAdapter.add(new  AlarmDate(c.getTimeInMillis()));
                saveAlarmList();
                /**
                 * 给AlarmManager添加任务
                 */
                date.setId(pendingid++);
                Toast.makeText(getContext(), date.getId()+"", Toast.LENGTH_SHORT).show();
                Intent in=new Intent(getContext(),AlarmBroadCast.class);
                in.putExtra("id", date.getId());
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),1*20*1000,
                        PendingIntent.getBroadcast(getContext(),date.getId(),in,0));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),true).show();
    }

    /**
     * 用于将设置的闹钟保存到preference
     *
     */
    private void saveAlarmList(){
        SharedPreferences.Editor editor = getContext().getSharedPreferences(AlarmView.class.getName(), Context.MODE_PRIVATE).edit();
        StringBuilder stringBuilder = new StringBuilder();
        if (alarmDateArrayAdapter.getCount()!=0) {
            for (int i = 0; i < alarmDateArrayAdapter.getCount(); i++) {
                stringBuilder.append(alarmDateArrayAdapter.getItem(i).getTime()).append(",");
            }
            String content = stringBuilder.toString().substring(0, stringBuilder.length() - 1);
            editor.putString(KEY_ALARM_LIST, content);

        }
        else {
            editor.putString(KEY_ALARM_LIST, "");
        }
        editor.commit();
    }

    /**
     * 用于读取保存的perference数据
     */
    private void readsaveAlarmList() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(AlarmView.class.getName(), Context.MODE_PRIVATE);

        String content = sharedPreferences.getString(KEY_ALARM_LIST, "");
        if (!content.equals("")) {
        String[] dates = content.split(",");
        for (String s : dates) {
            alarmDateArrayAdapter.add(new AlarmDate(Long.parseLong(s)));
        }
     }

    }
    private static class AlarmDate {
        private int id=0;
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
        public int getId(){
            return id;
        }

        public void setId(int id) {
            this.id=id;
        }
    }

}
