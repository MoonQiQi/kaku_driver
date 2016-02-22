package com.yichang.kaku.global;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.lidroid.xutils.util.LogUtils;

import java.util.Stack;

/**
 * @version 1.0
 * @author: 战琪
 * @类 说   明:
 * @创建时间：2014年12月24日 上午10:29:21
 */
public class MyActivityManager {

    // 单例
    private static MyActivityManager instance;
    // Activity的栈队列
    private static Stack<FragmentActivity> activityStack;

    public Activity getActivty(){
        if(activityStack.size()!=0){
            return activityStack.get(activityStack.size()-1);
        }
        return  null;
    }

    // 私有的构造函数，为了创建懒汉式单例
    private MyActivityManager() {
    }

    public int getNum() {
        return activityStack.size();
    }

    /**
     * 获取单例，懒汉模式
     *
     * @return 全局单例
     */
    public static MyActivityManager getInstance() {
        if (null == instance) {
            instance = new MyActivityManager();
        }
        return instance;
    }

    /**
     * 添加FragmentActivity到栈队列
     *
     * @param activity 要添加的activity
     */
    public void addActivity(FragmentActivity activity) {
        if (null == activityStack) {
            activityStack = new Stack<FragmentActivity>();
        }
        activityStack.push(activity);
    }

    /**
     * 从栈队列中移除FragmentActivity
     *
     * @param activity 要移除的activity
     */
    public void removeActivity(FragmentActivity activity) {
        /*if (null == activityStack) {
			activityStack = new Stack<FragmentActivity>();
		}*/
        try {

            if (activityStack != null) {
                activityStack.remove(activity);
            }
        } catch (Exception e) {

        }
    }

    /**
     * 结束所有的Activity
     */
    public void finishAllActivity() {
        if (null != activityStack && !activityStack.isEmpty()) {
            int j = activityStack.size();
            for (int i = 0; i < j; i++) {
                activityStack.pop().finish();
            }
        }
    }

    /**
     * 结束当前最顶端的Activity
     */
    public void finishCurrentActivity() {
        if (null != activityStack && !activityStack.isEmpty()) {
            activityStack.pop().finish();
        }
    }

    /**
     * 回退到指定的Activity
     *
     */
   /*public void backToActivity(Class<?> clazz) {
        if (isActivityExist(clazz)) {
            for (int i = 0; i < activityStack.size(); i++) {
                if (!activityStack.peek().getClass().equals(clazz)) {
                    activityStack.pop().finish();
                }
            }
        } else {
            LogUtils.e("指定的Activity在栈队列中不存在！");
        }
    }*/

    public void backToActivity(Context context,Class<?> toCls) {
        Intent intent = new Intent(context, toCls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }


    /**
     * 结束指定的Activity
     *
     * @param clazz 指定的activity
     */
    public void finishActivity(Class<?> clazz) {
        if (isActivityExist(clazz)) {
            for (int i = 0; i < activityStack.size(); i++) {
                if (activityStack.get(i).getClass().equals(clazz)) {
                    activityStack.remove(i).finish();
                }
            }
        }
    }

    /**
     * 判断指定的Activity是否在栈队列中存在
     *
     * @param clazz
     * @return
     */
    public boolean isActivityExist(Class<?> clazz) {
        if (null != activityStack && !activityStack.isEmpty()) {
            for (int i = 0; i < activityStack.size(); i++) {
                if (activityStack.get(i).getClass().equals(clazz)) {
                    return true;
                }
            }
        } else {
            LogUtils.e("Activity栈队列为空！");
        }
        return false;
    }

    /**
     * 退出应用
     */
    public void appExit() {
        finishAllActivity();
        System.exit(0);
    }
}

