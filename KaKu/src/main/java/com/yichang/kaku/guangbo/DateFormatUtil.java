
package com.yichang.kaku.guangbo;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {
    public static final String DATE_FORMAT_HOUR = "HH:mm:ss";

    public static String getDescriptiveTime(long ms) {
        if (ms <= 0) {
            return "00:00";
        }
        int s = (int) Math.ceil(ms / 1000F);
        int i = s / 60;
        int j = s % 60;
        String min = i < 10 ? "0" + i : String.valueOf(i);
        String sec = j < 10 ? "0" + j : String.valueOf(j);
        return min + ":" + sec;
    }

    public static String getCurrDate(long time) {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_HOUR);
        String dStr = sdf.format(new Date(time));
        return dStr;
    }
}
