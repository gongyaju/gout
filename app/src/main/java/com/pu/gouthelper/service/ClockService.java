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
        Intent intent = new Intent(this, alarmreceiver.class);
        intent.setAction("com.gout.alarm");
        PendingIntent sender =
                PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager am = (AlarmManager) getApplication()
                .getSystemService(Context.ALARM_SERVICE);
        Calendar calendar1 = Calendar.getInstance(Locale.getDefault());
        calendar1.setTimeInMillis(System.currentTimeMillis());
        calendar1.set(Calendar.HOUR_OF_DAY, 10);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, sender);
    }
}
