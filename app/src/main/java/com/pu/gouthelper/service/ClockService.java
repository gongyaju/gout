package com.pu.gouthelper.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.pu.gouthelper.R;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Requiem on 2016/8/20.
 */
public class ClockService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        waterClock();
        return super.onStartCommand(intent, flags, startId);
    }

    private void waterClock() {
        setClock(0, 10, 0);
        setClock(1, 13, 0);
        setClock(2, 17, 0);
        setClock(3, 21, 0);
    }

    private void setClock(int flag, int hour, int minute) {
        Intent intent = new Intent(this, alarmreceiver.class);
        intent.putExtra("flag", flag);
        intent.setAction("com.gout.alarm");
        PendingIntent sender =
                PendingIntent.getBroadcast(this, flag, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getApplication()
                .getSystemService(Context.ALARM_SERVICE);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar1.setTimeInMillis(System.currentTimeMillis());
        calendar1.set(Calendar.HOUR_OF_DAY, hour);
        calendar1.set(Calendar.MINUTE, minute);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);

        long time = calendar1.getTimeInMillis();
//        Calendar nowTime = Calendar.getInstance();
//        long time1 = nowTime.getTimeInMillis();// 当前时间
//        if (time < time1)
//            time += 3600 * 24 * 1000;
        am.setRepeating(AlarmManager.RTC_WAKEUP, time,
                3600 * 24 * 1000, sender);
    }
}
