package com.app.myclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmBroadCast extends BroadcastReceiver {
    private AlarmManager alarmManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "alarm runed"+">>>"+intent.getIntExtra("id",233), Toast.LENGTH_SHORT).show();
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(PendingIntent.getBroadcast(context,intent.getIntExtra("id",22),new Intent(context,AlarmBroadCast.class),0));
        Intent Alarmlayoutintent = new Intent(context, Alarmlayout.class);
        Alarmlayoutintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Alarmlayoutintent);
    }

}
