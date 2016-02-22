package com.yichang.kaku.tools;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: Log打印类
 */
public class LogUtil {
    public static boolean sDebug = true;
    public static String sLogTag = "KaKu";

    private static String sdcardPath = null;

    public static void V(String msg) {
        if (sDebug) {
            Log.v(sLogTag, msg);
        }
    }

    public static void V(String msg, Throwable e) {
        if (sDebug) {
            Log.v(sLogTag, msg, e);
        }
    }

    public static void D(String msg) {
        if (sDebug) {
            Log.d(sLogTag, msg);
        }
    }

    public static void D(String msg, Throwable e) {
        if (sDebug) {
            Log.d(sLogTag, msg, e);
        }
    }

    public static void I(String msg) {
        if (sDebug) {
            Log.i(sLogTag, msg);
        }
    }

    public static void I(String msg, Throwable e) {
        if (sDebug) {
            Log.i(sLogTag, msg, e);
        }
    }

    public static void W(String msg) {
        if (sDebug) {
            Log.w(sLogTag, msg);
        }
    }


    public static void W(String msg, Throwable e) {
        if (sDebug) {
            Log.w(sLogTag, msg, e);
        }
    }

    public static void E(String msg) {
        if (sDebug) {
            Log.e(sLogTag, msg);
        }
    }

    public static void E(String msg, Throwable e) {
        if (sDebug) {
            Log.e(sLogTag, msg, e);
        }
    }

    public static void showCommonToast(Context context, String msg, int length) {
        Toast.makeText(context, msg, length).show();
    }

    public static void showShortToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static String write2file(Context context, String tag, String msg) {
        File logFile;
        String date = DateUtil.DateFormatter1.format(new Date());
        String fileName = "eshop-" + date + ".log";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            // 保存到SD卡
            if (sdcardPath == null)
                sdcardPath = Environment.getExternalStorageDirectory()
                        .getAbsolutePath();
            String path = sdcardPath + "/uni2uni/eshop_logs";
            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            logFile = new File(dir, fileName);
        } else {
            // 保存到文件 // /data/data/com.feicui.qrcode/qrcode.log
            logFile = new File(context.getFilesDir(), fileName);
        }
        if (logFile != null) {
            try {
                synchronized (logFile) {
                    String time = DateUtil.TimeFormatter1.format(new Date());
                    FileWriter writer = new FileWriter(logFile, true);
                    writer.write(time);
                    writer.write(" @ ");
                    writer.write(tag);
                    writer.write(":");
                    writer.write("\r\n");
                    writer.write(msg);
                    writer.write("\r\n");
                    writer.write("\r\n");
                    writer.flush();
                    writer.close();
                }
                return fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
