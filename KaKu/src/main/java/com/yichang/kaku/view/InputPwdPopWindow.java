package com.yichang.kaku.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.member.cash.SetWithDrawCodeActivity;
import com.yichang.kaku.payhelper.wxpay.net.sourceforge.simcpux.MD5;
import com.yichang.kaku.request.CheckWithDrawCodeReq;
import com.yichang.kaku.response.BaseResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

public class InputPwdPopWindow extends PopupWindow {

    private BaseActivity context;

    private String strPwd;
    private final SecurityPasswordEditText sEdit;


    public InputPwdPopWindow(final BaseActivity context) {
        super(context);
        this.context = context;

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#77000000")));
        setOutsideTouchable(false);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        final View view = context.inflate(R.layout.layout_input_pwd_confirm);
        setContentView(view);

        LinearLayout ll_pwd_container= (LinearLayout) view.findViewById(R.id.ll_pwd_container);

        sEdit = (SecurityPasswordEditText) view.findViewById(R.id.et_s_pwd);
        sEdit.setSecurityEditCompleListener(new SecurityPasswordEditText.SecurityEditCompleListener() {
            @Override
            public void onNumCompleted(String num) {
                strPwd = num;
                if(num.length()==6){

                    checkCode(num);
                }
            }
        });


        ImageView iv_close= (ImageView) view.findViewById(R.id.iv_close);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });

        TextView tv_forget_code = (TextView) view.findViewById(R.id.tv_forget_code);
        tv_forget_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SetWithDrawCodeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("isPassExist", true);
                intent.putExtra("flag_next_activity", "CONFIRM_ORDER");
                context.startActivity(intent);
            }
        });

    }

    public void show() {
        showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    private void checkCode(final String code) {
        Utils.NoNet(context);
        mListener.showDialog();


        CheckWithDrawCodeReq req = new CheckWithDrawCodeReq();
        req.code = "5008";
        req.id_driver = Utils.getIdDriver();
        req.pay_pass = code;
        req.sign = genAppSign(req.id_driver);


        KaKuApiProvider.checkWithDrawCode(req, new BaseCallback<BaseResp>(BaseResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, BaseResp t) {
                if (t != null) {
                    LogUtil.E("checkWithDrawCode res: " + t.res);
                    if (Constants.RES.equals(t.res)) {

                        mListener.confirmPwd(true);
                        mListener.stopDialog();

                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                        }

                        LogUtil.showShortToast(context, t.msg);
                        sEdit.clearSecurityEdit();
                        mListener.stopDialog();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                mListener.stopDialog();
            }
        });


    }

    private String genAppSign(String id_driver) {
        StringBuilder sb = new StringBuilder();
        //拼接签名字符串
        sb.append("id_driver=");
        sb.append(id_driver);


        sb.append("&key=");
        sb.append(Constants.MSGKEY);
        LogUtil.E("sb:" + sb);

        String appSign1 = MD5.getMessageDigest(sb.toString().getBytes());
        String appSign = MD5.getMessageDigest(appSign1.getBytes()).toUpperCase();
        return appSign;
    }

    public interface ConfirmListener {
        void confirmPwd(Boolean isConfirmed);

        void showDialog();

        void stopDialog();
    }

    ;

    private ConfirmListener mListener;

    public void setConfirmListener(ConfirmListener listener) {
        this.mListener = listener;
    }
}
