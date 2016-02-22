package com.yichang.kaku.global;


import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.view.widget.DialogRequestProgress;

public class BaseActivity extends FragmentActivity {

    public static Context context;
    public static boolean flag_base = false;

    private DialogRequestProgress progressDialog;// loading对话框


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        MyActivityManager.getInstance().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        //Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        //Bugtags.onPause(this);
    }

    /**
     * 显示请求dialog
     */
    public void showProgressDialog() {
        if (null == progressDialog) {
            progressDialog = new DialogRequestProgress(this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
        }

        getWindow().getDecorView().postDelayed(new DismassDialogTask(progressDialog), 20000);

        progressDialog.show();
    }

    class DismassDialogTask implements Runnable {

        public DismassDialogTask(DialogRequestProgress progressDialog) {
            this.progressDialog = progressDialog;
        }

        private DialogRequestProgress progressDialog;

        @Override
        public void run() {
            progressDialog.dismiss();
        }
    }

    @Override
    public void finish() {
        if (progressDialog!=null){
            stopProgressDialog();
        }
        super.finish();
    }

    /**
     * 停止请求dialog
     */
    public void stopProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag_base = true;
        MyActivityManager.getInstance().removeActivity(this);
    }

    public View inflate(int ResId) {
        return LayoutInflater.from(this).inflate(ResId, null);
    }

    public void showShortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 返回
     */
    public void back(View v) {
        finish();
    }

    /**
     * 数据异常提示,并结束activity
     */
    protected void dataError() {
//		Toast.makeText(context, R.string.alert_data_error, Toast.LENGTH_SHORT)
//				.show();
        finish();
    }

    public <T extends View> T findView(int ResId) {
        return (T) findViewById(ResId);
    }

    public <T extends View> T findView(View rootView, int ResId) {
        return (T) rootView.findViewById(ResId);
    }

    public String getText(TextView textView) {
        if (textView == null) {
            throw new RuntimeException("TextView为空怎么获取内容");
        }
        return textView.getText().toString().trim();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }

        //Bugtags.onDispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
