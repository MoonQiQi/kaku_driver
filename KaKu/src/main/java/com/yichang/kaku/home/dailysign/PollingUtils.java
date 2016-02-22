package com.yichang.kaku.home.dailysign;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.yichang.kaku.tools.LogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chaih on 2015/11/24.
 */
public class PollingUtils {

    public static boolean isServiceRun=false;


    //开启轮询服务
    public static void startPollingService(Context context, int seconds, Class<?> cls, String action) {
        isServiceRun=true;
        LogUtil.E("开启签到服务:" + isServiceRun);
        //获取AlarmManager系统服务
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //包装需要执行Service的Intent
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //触发服务的起始时间
        //手机开机到目前的运行时间
        long triggerAtTime = SystemClock.elapsedRealtime();
        //当前时间
        long currentTime = System.currentTimeMillis();
        //手机开机时间
        long startTime = currentTime - triggerAtTime;
        LogUtil.E("startTime:" + startTime);

        Date date = new Date(currentTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //String startDate = dateFormat.format(date) + " " + "10:00:00";
        String startDate = dateFormat.format(date) + " " + "10:00:00";
        LogUtil.E("startDate:" + startDate);
        //先把字符串转成Date类型
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date notifyDate = new Date();
        //此处会抛异常
        try {
            notifyDate = sdf.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //获取毫秒数
        long notifyTime = notifyDate.getTime();
        LogUtil.E("notifyTime1:" + notifyTime);
        LogUtil.E("currentTime:" + currentTime);
        if (notifyTime < currentTime) {
            LogUtil.E("notifyTime++:" + notifyTime);
            notifyTime = notifyTime + 24 * 60 * 60 * 1000;
        }
        LogUtil.E("notifyTime2:" + notifyTime);


        /*long triggerAtTime = SystemClock.elapsedRealtime();*/
        LogUtil.E("triggerAtTime:" + triggerAtTime);
        LogUtil.E("startAtTime:" + (notifyTime - startTime + 1000));

        //使用AlarmManger的setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Service
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, notifyTime - startTime + 1000, 24*60*60*1000, pendingIntent);
    }

    //停止轮询服务
    public static void stopPollingService(Context context, Class<?> cls, String action) {
        isServiceRun=false;
        LogUtil.E("关闭签到服务:" + isServiceRun);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //取消正在执行的服务
        manager.cancel(pendingIntent);
    }
}
