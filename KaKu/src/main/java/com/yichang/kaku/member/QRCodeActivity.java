package com.yichang.kaku.member;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.DriveInfoObj;
import com.yichang.kaku.obj.MemberDriverObj;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.Utils;

public class QRCodeActivity extends BaseActivity implements OnClickListener {
    //    titleBar
    private TextView title, left, right;
    //    司机头像，姓名，手机号
    private ImageView iv_scanhead;
    private TextView tv_scanname, tv_scanphone;
    //二维码显示控件
    private ImageView iv_qrcode;
    //二维码提示信息
    private TextView tv_qrcode_msg;

    private DriveInfoObj driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_qrcode);
        init();
    }

    private void init() {

        initTitleBar();
        iv_scanhead = (ImageView) findViewById(R.id.iv_scanhead);
        tv_scanname = (TextView) findViewById(R.id.tv_scanname);
        tv_scanphone = (TextView) findViewById(R.id.tv_scanphone);

       driver = (DriveInfoObj) getIntent().getSerializableExtra("driverInfo");

        iv_qrcode = (ImageView) findViewById(R.id.iv_qrcode);

        generateQRCodeImage(driver.getCode_url());

        setData();

    }

    private void setData() {

        if (!TextUtils.isEmpty(driver.getHead())) {
            BitmapUtil.getInstance(context).download(iv_scanhead, KaKuApplication.qian_zhui + driver.getHead());
        }
        tv_scanname.setText(driver.getName_driver());
        tv_scanphone.setText(driver.getPhone_driver());
    }

    private void generateQRCodeImage(String codeurl) {

       Bitmap creatMyCode = Utils.createQRCode(codeurl);
        //Bitmap creatMyCode = Utils.createQRCode("www.baidu.com");
        iv_qrcode.setImageBitmap(creatMyCode);
    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("我的二维码");


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
        }
    }


}
