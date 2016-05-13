package com.yichang.kaku.home.huafei;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.request.ChongZhiReq;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.response.ChongZhiResp;
import com.yichang.kaku.response.HuaFeiResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class HuaFeiActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private EditText et_huafei_phone;
    private TextView tv_huafei_50, tv_huafei_100, tv_huafei_money, tv_huafei_shuoming;
    private Button btn_huafei_chongzhi;
    private String phone_shuoming, phone_10086, phone_10010, phone_10000, phone_400, usernumber, huafei_type, type_goods, balance_choose;
    private String price_self_fifty, price_self_hundred, price_others_fifty, price_others_hundred, phone_driver;
    private ImageView iv_huafei_lianxiren;
    private String price_string = "";
    private String flag_can, msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huafei);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("手机充值");
        et_huafei_phone = (EditText) findViewById(R.id.et_huafei_phone);
        et_huafei_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_huafei_phone.getText().toString().trim().length() == 11) {
                    if (et_huafei_phone.getText().toString().trim().equals(phone_driver)) {
                        if ("50".equals(huafei_type)) {
                            type_goods = "SF";
                            price_string = "支付：¥" + price_self_fifty;
                        } else {
                            type_goods = "SH";
                            price_string = "支付：¥" + price_self_hundred;
                        }
                    } else {
                        if ("50".equals(huafei_type)) {
                            type_goods = "OF";
                            price_string = "支付：¥" + price_others_fifty;
                        } else {
                            type_goods = "OH";
                            price_string = "支付：¥" + price_others_hundred;
                        }
                    }
                    tv_huafei_money.setText(Utils.ChangeTextColor(price_string, 3, price_string.length(), getResources().getColor(R.color.color_red)));
                }
            }
        });
        tv_huafei_50 = (TextView) findViewById(R.id.tv_huafei_50);
        tv_huafei_50.setOnClickListener(this);
        tv_huafei_money = (TextView) findViewById(R.id.tv_huafei_money);
        tv_huafei_shuoming = (TextView) findViewById(R.id.tv_huafei_shuoming);
        tv_huafei_100 = (TextView) findViewById(R.id.tv_huafei_100);
        tv_huafei_100.setOnClickListener(this);
        btn_huafei_chongzhi = (Button) findViewById(R.id.btn_huafei_chongzhi);
        btn_huafei_chongzhi.setOnClickListener(this);
        iv_huafei_lianxiren = (ImageView) findViewById(R.id.iv_huafei_lianxiren);
        iv_huafei_lianxiren.setOnClickListener(this);
        phone_shuoming = tv_huafei_shuoming.getText().toString();

        if (getNumbers(phone_shuoming).size() > 0) {
            phone_10086 = getNumbers(phone_shuoming).get(0);
        }
        if (getNumbers(phone_shuoming).size() > 1) {
            phone_10010 = getNumbers(phone_shuoming).get(1);
        }
        if (getNumbers(phone_shuoming).size() > 2) {
            phone_10000 = getNumbers(phone_shuoming).get(2);
        }
        if (getNumbers(phone_shuoming).size() > 3) {
            phone_400 = getNumbers(phone_shuoming).get(3);
        }

        SpannableString spanableInfo = new SpannableString(phone_shuoming);
        spanableInfo.setSpan(new Clickable(clickListener1), phone_shuoming.indexOf(phone_10086),
                phone_shuoming.indexOf(phone_10086) + phone_10086.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanableInfo.setSpan(new Clickable(clickListener2), phone_shuoming.indexOf(phone_10010),
                phone_shuoming.indexOf(phone_10010) + phone_10010.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanableInfo.setSpan(new Clickable(clickListener3), phone_shuoming.indexOf(phone_10000),
                phone_shuoming.indexOf(phone_10000) + phone_10000.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanableInfo.setSpan(new Clickable(clickListener4), phone_shuoming.indexOf(phone_400),
                phone_shuoming.indexOf(phone_400) + phone_400.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv_huafei_shuoming.setText(spanableInfo);  //将处理过的数据set到View里
        tv_huafei_shuoming.setMovementMethod(LinkMovementMethod.getInstance());
        getData();
    }

    private List<String> getNumbers(String content) {
        List<String> digitList = new ArrayList<String>();
        digitList.add("10086");
        digitList.add("10010");
        digitList.add("10000");
        digitList.add("400-6867-585");
        return digitList;
    }

    private OnClickListener clickListener1 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            getClick(phone_10086);
        }
    };

    private OnClickListener clickListener2 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            getClick(phone_10010);
        }
    };

    private OnClickListener clickListener3 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            getClick(phone_10000);
        }
    };

    private OnClickListener clickListener4 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            getClick(phone_400);
        }
    };

    class Clickable extends ClickableSpan {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener l) {
            mListener = l;
        }

        /**
         * 重写父类点击事件
         */
        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);// 设置文字下划线不显示
            ds.setColor(getResources().getColor(R.color.text_blue));// 设置字体颜色
        }
    }

    private void getClick(final String s) { //参数为当前点击的数字字符串
        switch (s) {
            case "10086":
                Utils.Call(context, "10086");
                break;
            case "10010":
                Utils.Call(context, "10010");
                break;
            case "10000":
                Utils.Call(context, "10000");
                break;
            case "400-6867-585":
                Utils.Call(context, "400-6867-585");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        } else if (R.id.iv_huafei_lianxiren == id) {
            startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);
        } else if (R.id.tv_huafei_50 == id) {
            huafei_type = "50";
            tv_huafei_50.setBackgroundResource(R.drawable.bg_50yuan);
            tv_huafei_100.setBackgroundResource(R.drawable.touming_chongzhi);
            if (et_huafei_phone.getText().toString().trim().equals(phone_driver)) {
                type_goods = "SF";
                price_string = "支付：¥" + price_self_fifty;

            } else {
                type_goods = "OF";
                price_string = "支付：¥" + price_others_fifty;

            }
            tv_huafei_money.setText(Utils.ChangeTextColor(price_string, 3, price_string.length(), getResources().getColor(R.color.color_red)));
        } else if (R.id.tv_huafei_100 == id) {
            huafei_type = "100";
            tv_huafei_50.setBackgroundResource(R.drawable.touming_chongzhi);
            tv_huafei_100.setBackgroundResource(R.drawable.bg_50yuan);
            if (et_huafei_phone.getText().toString().trim().equals(phone_driver)) {
                type_goods = "SH";
                price_string = "支付：¥" + price_self_hundred;

            } else {
                type_goods = "OH";
                price_string = "支付：¥" + price_others_hundred;

            }
            tv_huafei_money.setText(Utils.ChangeTextColor(price_string, 3, price_string.length(), getResources().getColor(R.color.color_red)));
        } else if (R.id.btn_huafei_chongzhi == id) {
            if ("N".equals(flag_can)) {
                LogUtil.showShortToast(context, msg);
                return;
            }
            if (!Utils.isMobileNO(et_huafei_phone.getText().toString().trim())) {
                LogUtil.showShortToast(context, "请输入正确的手机号");
                return;
            }
            ChongZhi();
        }
    }

    public void getData() {
        showProgressDialog();
        ExitReq req = new ExitReq();
        req.code = "30030";
        KaKuApiProvider.HuaFei(req, new KakuResponseListener<HuaFeiResp>(context, HuaFeiResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("HuaFei res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SetText(t);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();

            }
        });
    }

    public void SetText(HuaFeiResp t) {
        huafei_type = "100";
        type_goods = "SF";
        flag_can = t.flag_can;
        msg = t.msg;
        price_self_fifty = t.price_self_fifty;
        price_self_hundred = t.price_self_hundred;
        price_others_fifty = t.price_others_fifty;
        price_others_hundred = t.price_others_hundred;
        phone_driver = t.phone_driver;
        et_huafei_phone.setText(phone_driver);

    }

    public void ChongZhi() {
        showProgressDialog();
        ChongZhiReq req = new ChongZhiReq();
        req.phone = et_huafei_phone.getText().toString().trim();
        req.type_goods = type_goods;
        req.balance_choose = "";
        req.price_goods = tv_huafei_money.getText().toString().trim().substring(4);
        KaKuApiProvider.ChongZhi(req, new KakuResponseListener<ChongZhiResp>(context, ChongZhiResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("ChongZhi res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        Intent intent = new Intent(context, HuaFeiZhiFuActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("type_goods", type_goods);
                        bundle.putString("price_goods", tv_huafei_money.getText().toString().trim().substring(4));
                        bundle.putString("phone", et_huafei_phone.getText().toString().trim());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ContentResolver reContentResolverol = getContentResolver();
            Uri contactData = data.getData();
            @SuppressWarnings("deprecation")
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            while (phone.moveToNext()) {
                usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if (usernumber.length() < 10) {
                    return;
                }

                if ("+86".equals(usernumber.substring(0, 3))) {
                    //去掉空格
                    usernumber = usernumber.replaceAll(" ", "");
                    usernumber = usernumber.replaceAll("-", "");
                    et_huafei_phone.setText(usernumber.substring(3).trim());
                } else {
                    //去掉空格
                    usernumber = usernumber.replaceAll(" ", "");
                    usernumber = usernumber.replaceAll("-", "");
                    et_huafei_phone.setText(usernumber.trim());
                }
            }
        }
    }
}
