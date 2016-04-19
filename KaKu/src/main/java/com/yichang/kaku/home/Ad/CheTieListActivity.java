package com.yichang.kaku.home.Ad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.CheTieObj;
import com.yichang.kaku.request.CheTieListReq;
import com.yichang.kaku.request.GetAddReq;
import com.yichang.kaku.response.CheTieListResp;
import com.yichang.kaku.response.GetAddResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.popwindow.ValidatePopWindow;
import com.yichang.kaku.view.popwindow.YiYuanPopWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

public class CheTieListActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private ListView lv_chetielist;
    private List<CheTieObj> list_chetie = new ArrayList<CheTieObj>();
    private CheTieAdapter adapter_chetie;
    private Boolean isPwdPopWindowShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chetielist);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("车贴任务");
        lv_chetielist = (ListView) findViewById(R.id.lv_chetielist);
        lv_chetielist.setOnItemClickListener(this);

        GetCheTieList();
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    public void GetCheTieList() {
        showProgressDialog();
        CheTieListReq req = new CheTieListReq();
        req.code = "60031";
        KaKuApiProvider.getCheTieList(req, new KakuResponseListener<CheTieListResp>(this, CheTieListResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("chetielist res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list_chetie = t.adverts;
                        adapter_chetie = new CheTieAdapter(CheTieListActivity.this, list_chetie);
                        lv_chetielist.setAdapter(adapter_chetie);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MobclickAgent.onEvent(context, "CheTieItem");
        KaKuApplication.id_advert = list_chetie.get(position).getId_advert();
        GetAdd();
    }



    private void showValidatePopWindow() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                ValidatePopWindow input =
                        new ValidatePopWindow(CheTieListActivity.this);
                input.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                input.setConfirmListener(new ValidatePopWindow.ConfirmListener() {
                    @Override
                    public void confirmValidateCode(Boolean isConfirmed) {

                    }

                    @Override
                    public void showDialog() {
                        showProgressDialog();
                    }

                    @Override
                    public void stopDialog() {
                        stopProgressDialog();

                    }
                });

                input.show();

            }
        }, 0);
    }

    private void showPwdInputWindow() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                isPwdPopWindowShow = true;

                YiYuanPopWindow input =
                        new YiYuanPopWindow(CheTieListActivity.this, "4006867585");
                input.show();

            }
        }, 200);
    }

    public void GetAdd() {
        showProgressDialog();
        GetAddReq req = new GetAddReq();
        req.code = "60011";
        req.id_driver = Utils.getIdDriver();
        req.id_advert = KaKuApplication.id_advert;
        KaKuApiProvider.GetAdd(req, new KakuResponseListener<GetAddResp>(this, GetAddResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getadd res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.id_advert = t.advert.getId_advert();
                        GoToAdd(t.advert.getFlag_type());
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

        });
    }

    public void GoToAdd(String flag_type) {
        Intent intent = new Intent();
        LogUtil.E("flag:" + flag_type);
        if ("N".equals(flag_type)) {
            intent.setClass(context, Add_NActivity.class);
        } else if ("Y".equals(flag_type)) {
            intent.setClass(context, Add_YActivity.class);
        } else if ("E".equals(flag_type)) {
            intent.setClass(context, Add_EActivity.class);
        } else if ("I".equals(flag_type)) {
            intent.setClass(context, Add_IActivity.class);
        } else if ("F".equals(flag_type)) {
            intent.setClass(context, Add_FActivity.class);
        } else if ("P".equals(flag_type)) {
            intent.setClass(context, Add_PActivity.class);
        } else if ("A".equals(flag_type)){
            intent.setClass(context,CheTieListActivity.class);
        } else if ("M".equals(flag_type)){
            intent.setClass(context,Add_MActivity.class);
        }
        startActivity(intent);
    }

}
