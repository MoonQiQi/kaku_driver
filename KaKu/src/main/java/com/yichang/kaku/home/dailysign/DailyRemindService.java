package com.yichang.kaku.home.dailysign;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.yichang.kaku.R;
import com.yichang.kaku.tools.LogUtil;

/**
 * Created by Administrator on 2015/11/24.
 */
public class DailyRemindService extends Service {

    public static final String ACTION = "com.yichang.kaku.home.dailysign.DailyRemindService";

    private Notification mNotification;
    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        initNotifiManager();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        LogUtil.E("polling...start" );
        new PollingThread().start();
        //showNotification();
    }

    //初始化通知栏配置
    private void initNotifiManager() {
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int icon = R.drawable.ic_launcher;
        mNotification = new Notification();
        mNotification.icon = icon;
        mNotification.tickerText = "卡库签到提醒";
        mNotification.defaults |= Notification.DEFAULT_SOUND;
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
    }

    //弹出Notification
    private void showNotification() {
        mNotification.when = System.currentTimeMillis();
        //Navigator to the new activity when click the notification title
        Intent i = new Intent(this, DailySignActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, Intent.FLAG_ACTIVITY_NEW_TASK);
        mNotification.setLatestEventInfo(this, getResources().getString(R.string.app_name), "别忘了签到哦!", pendingIntent);
        mManager.notify(0, mNotification);
        //PollingUtils.stopPollingService(DailyRemindService.this, DailyRemindService.class, DailyRemindService.ACTION);
    }

    /**
     * Polling thread
     * 模拟向Server轮询的异步线程
     *
     * @Author chaih
     * @Create 2013-7-13 上午10:18:34
     */
    int count = 0;

    class PollingThread extends Thread {
        @Override
        public void run() {
            showNotification();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.E("Service:onDestroy" + "第" + count + "次轮");

    }

}
