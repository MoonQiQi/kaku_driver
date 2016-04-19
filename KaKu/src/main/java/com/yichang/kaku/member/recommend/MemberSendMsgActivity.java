package com.yichang.kaku.member.recommend;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.response.ShortURLResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.tools.okhttp.OkHttpUtil;
import com.yichang.kaku.tools.okhttp.RequestCallback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MemberSendMsgActivity extends BaseActivity implements OnClickListener, View.OnFocusChangeListener {
    //    titleBar
    private TextView title, left;
    //    被推荐人姓名，电话
    private EditText et_recommend_name, et_recommend_phone;
    //    按钮：从通讯录导入，发送短信
    private Button btn_recommend_import, btn_recommend_sendmsg;

    private String smsContent = "";
    private String smsUrl = "";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_send_msg);
        init();
    }

    private void init() {
        initTitleBar();

        initTextView();

        initButton();

        smsUrl=getIntent().getStringExtra("smsUrl");
        smsContent=getIntent().getStringExtra("smsContent");
    }




    /*短信内容*/
    private void generateMsgContent() {
        smsContent += "&phone=" + et_recommend_phone.getText().toString().trim() + "&name_user=" + et_recommend_name.getText().toString().trim();
    }

    private void initButton() {

        btn_recommend_sendmsg = (Button) findViewById(R.id.btn_recommend_sendmsg);
        btn_recommend_sendmsg.setOnClickListener(this);

        btn_recommend_import = (Button) findViewById(R.id.btn_recommend_import);
        btn_recommend_import.setOnClickListener(this);
    }

    private void initTextView() {
        et_recommend_name = (EditText) findViewById(R.id.et_recommend_name);
        //et_recommend_name.setSelection(5);
        //et_recommend_name.setOnFocusChangeListener(this);
        et_recommend_phone = (EditText) findViewById(R.id.et_recommend_phone);
        // et_recommend_phone.setOnFocusChangeListener(this);
    }


    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("短信邀请");


    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // TODO Auto-generated method stub
        EditText _v = (EditText) v;
        if (!hasFocus) {// 失去焦点
            _v.setHint(_v.getTag().toString());


        } else {
            String hint = _v.getHint().toString();
            _v.requestFocus();
            /*_v.setSelection(0);*/
            _v.setTag(hint);
            _v.setCursorVisible(true);
            _v.setHint("");
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
        }  else if (R.id.btn_recommend_import == id) {
            /*todo 从通讯录导入*/
            startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);
        } else if (R.id.btn_recommend_sendmsg == id) {
            /*todo 发送短信*/
            if (TextUtils.isEmpty(et_recommend_name.getText().toString())) {
                Toast.makeText(context, "请填写被邀请人姓名", Toast.LENGTH_SHORT).show();
                return;
            } else if (et_recommend_name.getText().toString().length() > 15) {
                Toast.makeText(context, "姓名长度不能超过15", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(et_recommend_phone.getText().toString())) {
                Toast.makeText(context, "请填写被邀请人手机号", Toast.LENGTH_SHORT).show();
                return;
            } else if (!et_recommend_phone.getText().toString().matches("^[1]\\d{10}$")) {
                LogUtil.showShortToast(this, "请输入正确的手机号码");
                return;
            }
            SendMsg();
        }
    }


    private void SendMsg() {

       // generateMsgContent();
        smsUrl += "&phone=" + et_recommend_phone.getText().toString().trim();
        String temp = smsUrl+ "&type=ShortMessage";
        try {
            temp = URLEncoder.encode(temp, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        showProgressDialog();
        OkHttpUtil.getAsync("http://985.so/api.php?format=json&url="+ temp, new RequestCallback<ShortURLResp>(this, ShortURLResp.class) {

            @Override
            public void onSuccess(ShortURLResp obj, String result) {

            }

            @Override
            public void onSuccess1(ShortURLResp obj, String result) {
                Uri smsToUri = Uri.parse("smsto:" + et_recommend_phone.getText().toString().trim());
                Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                intent.putExtra("sms_body", smsContent+ obj.url);
                startActivity(intent);
                stopProgressDialog();
            }

            @Override
            public void onInnerFailure(Request request, IOException e) {
                stopProgressDialog();
            }
        });

    }


    private String username, usernumber;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ContentResolver reContentResolverol = getContentResolver();
            Uri contactData = data.getData();
            @SuppressWarnings("deprecation")
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            while (phone.moveToNext()) {
                usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if (usernumber.length() < 10) {
                    return;
                }
                et_recommend_name.setText(username);

                if ("+86".equals(usernumber.substring(0, 3))) {
                    //去掉空格
                    usernumber = usernumber.replaceAll(" ", "");
                    usernumber = usernumber.replaceAll("-", "");
                    et_recommend_phone.setText(usernumber.substring(3).trim());
                } else {
                    //去掉空格
                    usernumber = usernumber.replaceAll(" ", "");
                    usernumber = usernumber.replaceAll("-", "");
                    et_recommend_phone.setText(usernumber.trim());
                }
            }

        }
    }

}
