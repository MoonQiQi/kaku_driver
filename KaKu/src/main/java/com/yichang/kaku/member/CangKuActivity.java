package com.yichang.kaku.member;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.obj.CangKuObj;
import com.yichang.kaku.request.CangKuReq;
import com.yichang.kaku.response.CangKuResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.popwindow.CangKuWindow;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class CangKuActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private XListView lv_cangku;
    private List<CangKuObj> cangku_list = new ArrayList<>();
    private final static int STEP = 5;
    private int start = 0, pageindex = 0, pagesize = STEP;
    private final static int INDEX = 5;// 一屏显示的个数
    private boolean isShowProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cangku);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("我的仓库");
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("已使用");
        right.setTextColor(getResources().getColor(R.color.red));
        right.setOnClickListener(this);
        lv_cangku = (XListView) findViewById(R.id.lv_cangku);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPullState(false);
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
        } else if (R.id.tv_right == id) {
            startActivity(new Intent(this, CangKu2Activity.class));
        }
    }

    public void CangKu(int pageIndex, int pageSize) {
        showProgressDialog();
        CangKuReq req = new CangKuReq();
        req.code = "400118";
        req.flag_used = "N";
        req.start = String.valueOf(pageIndex);
        req.len = String.valueOf(pageSize);
        KaKuApiProvider.CangKu(req, new KakuResponseListener<CangKuResp>(context, CangKuResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("BaoYangChangeFilter res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t.items);
                        onLoadStop();
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }

                    }
                }
                stopProgressDialog();
            }
        });
    }

    private void setData(List<CangKuObj> list) {
        // TODO Auto-generated method stub
        if (list != null) {
            cangku_list.addAll(list);
        }
        CangKuAdapter adapter = new CangKuAdapter(context, cangku_list);
        adapter.setPop(new CangKuAdapter.Pupinterface() {
            @Override
            public void Pup(String no_id) {
                showCangKuWindow(no_id);
            }
        });
        lv_cangku.setAdapter(adapter);
        lv_cangku.setPullLoadEnable(list.size() < INDEX ? false : true);
        lv_cangku.setSelection(pageindex);
        lv_cangku.setXListViewListener(new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                if (!Utils.checkNetworkConnection(context)) {
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    lv_cangku.stopRefresh();
                    return;
                }
                setPullState(false);
            }

            @Override
            public void onLoadMore() {
                if (!Utils.checkNetworkConnection(context)) {
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    lv_cangku.stopLoadMore();
                    return;
                }
                setPullState(true);
            }
        });
    }

    private void setPullState(boolean isUp) {
        if (isUp) {
            isShowProgress = true;
            start++;
            pageindex = start * STEP;
        } else {
            start = 0;
            pageindex = 0;
            if (cangku_list != null) {
                cangku_list.clear();
            }
        }
        CangKu(pageindex, pagesize);
    }

    private void onLoadStop() {
        lv_cangku.stopRefresh();
        lv_cangku.stopLoadMore();
        lv_cangku.setRefreshTime(DateUtil.dateFormat());
    }

    private void showCangKuWindow(final String id_no) {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                CangKuWindow popWindow = new CangKuWindow(CangKuActivity.this, id_no);
                popWindow.CangKuCallBack(new CangKuWindow.CangKuCallBack() {
                    @Override
                    public void cha() {
                        setPullState(false);
                    }
                });
                popWindow.show();
            }
        }, 0);
    }

}
