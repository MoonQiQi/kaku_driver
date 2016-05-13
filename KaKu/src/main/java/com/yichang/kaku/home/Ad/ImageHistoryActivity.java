package com.yichang.kaku.home.ad;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.ImageHisObj;
import com.yichang.kaku.request.ImageHisReq;
import com.yichang.kaku.response.ImageHisResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class ImageHistoryActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private ListView lv_imagehis;
    private List<ImageHisObj> list_imagehis = new ArrayList<ImageHisObj>();
    private ImageHistoryAdapter adapter;
    private ImageView iv_imagehis_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagehistory);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("上传图片历史");
        lv_imagehis = (ListView) findViewById(R.id.lv_imagehis);
        iv_imagehis_no = (ImageView) findViewById(R.id.iv_imagehis_no);

    }

    @Override
    protected void onStart() {
        super.onStart();
        GetList();
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            GetAdd();
        }
    }

    public void GetList() {
        showProgressDialog();
        ImageHisReq req = new ImageHisReq();
        req.code = "60016";
        req.id_driver = Utils.getIdDriver();
        req.id_advert = KaKuApplication.id_advert;
        KaKuApiProvider.getImageList(req, new KakuResponseListener<ImageHisResp>(this, ImageHisResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getimagelist res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if (t.driver_advert.size() == 0) {
                            iv_imagehis_no.setVisibility(View.VISIBLE);
                        } else {
                            iv_imagehis_no.setVisibility(View.GONE);
                        }

                        list_imagehis = t.driver_advert;
                        adapter = new ImageHistoryAdapter(context, list_imagehis);
                        lv_imagehis.setAdapter(adapter);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }


        });
    }

    public void GetAdd() {
        Utils.GetAdType(baseActivity);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            GetAdd();
        }
        return false;
    }

}
