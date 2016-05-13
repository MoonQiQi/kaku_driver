package com.yichang.kaku.home.call;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * com.yuntongxun.ecdemo.common in ECDemo_Android
 * Created by Jorstin on 2015/6/23.
 */
public class SwipeActivityManager {

    private static final String TAG = "ECSDK_Demo.SwipeActivityManager";

    private static LinkedList<WeakReference<SwipeListener>> mLinkedList = new LinkedList<WeakReference<SwipeListener>>();

    public static void notifySwipe(float scrollParent) {
        if (mLinkedList.size() <= 0) {
            return;
        }
        SwipeListener swipeListener = mLinkedList.get(0).get();
        if (swipeListener == null) {
            return;
        }
        swipeListener.onScrollParent(scrollParent);
    }

    public static void pushCallback(SwipeListener listener) {
        WeakReference<SwipeListener> swipeListenerWeakReference = new WeakReference<SwipeListener>(listener);
        mLinkedList.add(0, swipeListenerWeakReference);
    }

    public static boolean popCallback(SwipeListener listener) {
        int size = mLinkedList.size();
        if (listener == null) {
            return true;
        }
        LinkedList<Integer> list = new LinkedList<Integer>();
        for (int i = 0; i < mLinkedList.size(); i++) {
            if (listener != mLinkedList.get(i).get()) {
                list.add(0, Integer.valueOf(i));
            } else {
                mLinkedList.remove(i);
            }
            if (!listener.isEnableGesture() || list.size() == i) {
                return false;
            }
        }

        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            WeakReference<SwipeListener> remove = mLinkedList.remove(next.intValue());
        }
        return list.isEmpty();
    }


    public static void notifySettle(boolean open, int speed) {
        if (mLinkedList.size() <= 0) {
            return;
        }
        SwipeListener swipeListener = mLinkedList.get(0).get();
        if (swipeListener == null) {
            return;
        }
        swipeListener.notifySettle(open, speed);
    }


    public interface SwipeListener {
        /**
         * Invoke when state change
         *
         * @param scrollPercent scroll percent of this view
         */
        void onScrollParent(float scrollPercent);

        void notifySettle(boolean open, int speed);

        /**
         * 是否可以滑动
         *
         * @return
         */
        boolean isEnableGesture();
    }
}
