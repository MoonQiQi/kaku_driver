package com.yichang.kaku.member.address;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.logistics.province.ProvinceActivity;
import com.yichang.kaku.request.NewAddrReq;
import com.yichang.kaku.response.NewAddrResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

public class NewAddrActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    //	private RelativeLayout rela_newaddr_name,rela_newaddr_phone,rela_newaddr_area,rela_newaddr_dizhi;
//	private TextView tv_newaddr_name,tv_newaddr_phone,tv_newaddr_area,tv_newaddr_dizhi;
    private TextView tv_newaddr_name, tv_newaddr_phone, tv_newaddr_dizhi;
    private RelativeLayout rela_newaddr_name, rela_newaddr_phone, rela_newaddr_dizhi;
    private Button btn_addr_save;
    private String string1;
    private String string2;
    private String string3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newaddr);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        if (KaKuApplication.isEditAddr) {
            title.setText("编辑收货地址");
        } else {
            title.setText("新建收货地址");
        }
        rela_newaddr_name = (RelativeLayout) findViewById(R.id.rela_newaddr_name);
        rela_newaddr_name.setOnClickListener(this);
        rela_newaddr_phone = (RelativeLayout) findViewById(R.id.rela_newaddr_phone);
        rela_newaddr_phone.setOnClickListener(this);
/*        rela_newaddr_area = (RelativeLayout) findViewById(R.id.rela_newaddr_area);
        rela_newaddr_area.setOnClickListener(this);*/
        rela_newaddr_dizhi = (RelativeLayout) findViewById(R.id.rela_newaddr_dizhi);
        rela_newaddr_dizhi.setOnClickListener(this);
        tv_newaddr_name = (TextView) findViewById(R.id.tv_newaddr_name);
        tv_newaddr_phone = (TextView) findViewById(R.id.tv_newaddr_phone);
/*        tv_newaddr_area = (TextView) findViewById(R.id.tv_newaddr_area);
        tv_newaddr_area.setText(KaKuApplication.county_addr);*/
        tv_newaddr_dizhi = (TextView) findViewById(R.id.tv_newaddr_dizhi);
        btn_addr_save = (Button) findViewById(R.id.btn_addr_save);
        btn_addr_save.setOnClickListener(this);
        tv_newaddr_name.setText(KaKuApplication.name_addr);
        tv_newaddr_phone.setText(KaKuApplication.phone_addr);
        tv_newaddr_dizhi.setText(KaKuApplication.dizhi_addr);
//        tv_newaddr_area.setText(KaKuApplication.province_addrname + KaKuApplication.city_addrname + KaKuApplication.county_addr);
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            KaKuApplication.name_addr="";
            KaKuApplication.phone_addr="";
            KaKuApplication.dizhi_addr="";
            finish();
        } else if (R.id.rela_newaddr_name == id) {
            ModifyData(0);
        } else if (R.id.rela_newaddr_phone == id) {
            ModifyData(1);
        } /*else if (R.id.rela_newaddr_area == id) {
            ChooseArea();
        }*/ else if (R.id.rela_newaddr_dizhi == id) {
            ModifyData(2);
        } else if (R.id.btn_addr_save == id) {
            if (TextUtils.isEmpty(KaKuApplication.name_addr)) {
                LogUtil.showShortToast(context, "请填写收货人姓名");
                return;
            }
            if (TextUtils.isEmpty(KaKuApplication.phone_addr)) {
                LogUtil.showShortToast(context, "请填写联系方式");
                return;
            }
            /*if (TextUtils.isEmpty(KaKuApplication.county_addr)) {
                LogUtil.showShortToast(context, "请选择所在地区");
                return;
            }*/
            if (TextUtils.isEmpty(KaKuApplication.dizhi_addr)) {
                LogUtil.showShortToast(context, "请填写详细地址");
                return;
            }
            NewAddr();
            ///finish();
        }
    }

    public void ModifyData(int num) {
        Intent intent = new Intent(this, TextActivity.class);
        if (num == 0) {
            intent.putExtra("title", "name");
            //intent.putExtra("content", "修改联系人姓名");

        } else if (num == 1) {
            intent.putExtra("title", "phone");
            //intent.putExtra("content", "修改联系电话");

        } else if (num == 2) {
            intent.putExtra("title", "address");
            //intent.putExtra("content", "修改详细地址");
        }
        startActivityForResult(intent, num);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (data != null) {
                    string1 = data.getExtras().getString("string");
                    KaKuApplication.name_addr = string1;

                }
                break;

            case 1:
                if (data != null) {
                    string2 = data.getExtras().getString("string");

                    KaKuApplication.phone_addr = string2;

                }
                break;

            case 2:
                if (data != null) {
                    string3 = data.getExtras().getString("string");
                    KaKuApplication.dizhi_addr = string3;
                }
                break;

        }
        tv_newaddr_name.setText(KaKuApplication.name_addr);
        tv_newaddr_phone.setText(KaKuApplication.phone_addr);
        tv_newaddr_dizhi.setText(KaKuApplication.dizhi_addr);
//        tv_newaddr_area.setText(KaKuApplication.province_addrname + KaKuApplication.city_addrname + KaKuApplication.county_addr);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void NewAddr() {
//        在AddrActivity中更改状态
        //KaKuApplication.isEditAddr = false;
        //
        Utils.NoNet(context);
        showProgressDialog();
        NewAddrReq req = new NewAddrReq();
        req.code = "10015";
        req.id_driver = Utils.getIdDriver();
        req.flag_default = KaKuApplication.flag_addr;
        req.id_addr = KaKuApplication.id_dizhi;
        req.addr = KaKuApplication.dizhi_addr;
        req.name_addr = KaKuApplication.name_addr;
        req.phone_addr = KaKuApplication.phone_addr;
        KaKuApiProvider.NewAddr(req, new BaseCallback<NewAddrResp>(NewAddrResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, NewAddrResp t) {
                if (t != null) {
                    LogUtil.E("newaddr res: " + t.res);
                    if (Constants.RES.equals(t.res) || Constants.RES_TWO.equals(t.res)) {
                        finish();

                    }else {
                        if (Constants.RES_TEN.equals(t.res)){
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });
    }

    public void ChooseArea() {
        Intent intent = new Intent(context, ProvinceActivity.class);
        startActivity(intent);
    }
}
