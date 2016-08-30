package com.pu.gouthelper.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.orhanobut.logger.Logger;
import com.pu.gouthelper.R;
import com.pu.gouthelper.activity.MainActivity;

import java.util.Calendar;

/**
 * Created by Requiem on 2016/8/25.
 */
public class alarmreceiver extends BroadcastReceiver {

    private NotificationManager messageNotificationManager = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        messageNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder1 = new Notification.Builder(context);
        builder1.setSmallIcon(R.drawable.ic_launcher); //设置图标
        builder1.setContentTitle("喝水提醒"); //设置标题
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);   //获取小时;
        switch (hour) {
            case 10:
                builder1.setContentText("来,喝一杯水，放松一下~");
                break;
            case 13:
                builder1.setContentText("午后一杯水，提提精神吧~");
                break;
            case 17:
                builder1.setContentText("不管晚饭吃不吃,都先来一杯水润润胃吧~");
                break;
            case 21:
                builder1.setContentText("不要太晚睡哦,这个时间喝一杯水刚刚好~");
                break;
            default:
                builder1.setContentText("来,喝一杯水，放松一下~");
                break;
        }
        builder1.setWhen(System.currentTimeMillis()); //发送时间
        builder1.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
        builder1.setAutoCancel(true);//打开程序后图标消失
//        Intent intent =new Intent (context,MainActivity.class);
//        PendingIntent pendingIntent =PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
//        builder1.setContentIntent(pendingIntent);
        Notification notification1 = builder1.build();
        messageNotificationManager.notify(0, notification1); // 通过通知管理器发送通知

    }
}
