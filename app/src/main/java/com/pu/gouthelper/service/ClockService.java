package com.pu.gouthelper.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.pu.gouthelper.R;

/**
 * Created by Requiem on 2016/8/20.
 */
public class ClockService extends Service {
    /**
     * 10:00  来,喝一杯水，放松一下~
     * 13:00  午后一杯水，提提精神吧~
     * 17:00  不管晚饭吃不吃,都先来一杯水润润胃吧~
     * 21:00  不要太晚睡哦,这个时间喝一杯水刚刚好~
     */
    private Intent messageIntent = null;
    private PendingIntent messagePendingIntent = null;
    //通知栏消息
    private int messageNotificationID = 1000;
    private Notification messageNotification = null;
    private NotificationManager messageNotificationManager = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //初始化
        messageNotification = new Notification();
        messageNotification.icon = R.drawable.ic_launcher;  //通知图片
        messageNotification.tickerText = "新消息";         //通知标题
        messageNotification.defaults = Notification.DEFAULT_SOUND;
        messageNotificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        //点击查看
//        messageIntent = new Intent(this,MessageActivity.class);
//        messagePendingIntent = PendingIntent.getActivity(this, 0, messageIntent, 0);

        return super.onStartCommand(intent, flags, startId);
    }

}
