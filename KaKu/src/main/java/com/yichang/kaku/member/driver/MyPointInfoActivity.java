package com.yichang.kaku.member.driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.obj.PointHistoryObj;
import com.yichang.kaku.request.PointHistoryReq;
import com.yichang.kaku.response.PointHistoryResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

public class MyPointInfoActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {
    //titleBar,返回，购物车，标题,标题栏布局
    private TextView left, right, title;
    private RelativeLayout rela_titlebar;

    private View view_line_h;
    //显示积分金额
    private TextView tv_point_current, tv_point_accumulate, tv_point_consume;
    //
    private XListView xListView;

    private final static int STEP = 5;
    private int start = 0, pageindex = 0, pagesize = STEP;
    private final static int INDEX = 5;// 一屏显示的个数
    private boolean isShowProgress = false;

    private List<PointHistoryObj> list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_point);
        init();

    }

    private void init() {
        initTitleBar();

        initPointView();

        setPullState(false);
    }

    private void getPointHistoryInfo(int pageindex, int pagesize) {

        Utils.NoNet(context);

        PointHistoryReq req = new PointHistoryReq();
        req.code = "10021";
        req.id_driver = Utils.getIdDriver();
        req.start = String.valueOf(pageindex);
        req.len = String.valueOf(pagesize);

        KaKuApiProvider.getMemberPointInfo(req, new KakuResponseListener<PointHistoryResp>(this, PointHistoryResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getMemberPointInfo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t.point_his, t.point_now);
                        setData(t.historys);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                    onLoadStop();
                }
            }
        });
    }
//设置积分历史
    private void setData(String point_his, String point_now) {
        tv_point_current.setText(point_now);
        tv_point_accumulate.setText(point_his);
        Integer consume=Integer.parseInt(point_his)-Integer.parseInt(point_now);
        tv_point_consume.setText(String.valueOf(consume));

    }
//设置列表数据
    private void setData(List<PointHistoryObj> historys) {
        if (historys != null) {
            list.addAll(historys);
        }
        MyPointInfoAdapter adapter = new MyPointInfoAdapter(this, list);
        xListView.setAdapter(adapter);
        xListView.setPullLoadEnable(list.size() < INDEX ? false : true);
        xListView.setSelection(pageindex);
        xListView.setPullRefreshEnable(false);

        xListView.setXListViewListener(new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                if (!Utils.checkNetworkConnection(context)) {
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    xListView.stopRefresh();
                    return;
                }
                setPullState(false);
            }

            @Override
            public void onLoadMore() {
                if (!Utils.checkNetworkConnection(context)) {
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    xListView.stopLoadMore();
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
            if (list != null) {
                list.clear();
            }
        }
        getPointHistoryInfo(pageindex, pagesize);
    }
    private void onLoadStop() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(DateUtil.dateFormat());
    }

    private void initPointView() {
        tv_point_current = (TextView) findViewById(R.id.tv_point_current);
        tv_point_accumulate = (TextView) findViewById(R.id.tv_point_accumulate);
        tv_point_consume = (TextView) findViewById(R.id.tv_point_consume);

        xListView = (XListView) findViewById(R.id.lv_point_list);
    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);

        right = (TextView) findViewById(R.id.tv_right);
        right.setOnClickListener(this);
/*//设置背景为透明色
        rela_titlebar = (RelativeLayout) findViewById(R.id.include_titlebar);
        rela_titlebar.setBackgroundColor(Color.TRANSPARENT);*/

        view_line_h=findViewById(R.id.view_line_h);
        view_line_h.setVisibility(View.GONE);

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

        }else if (R.id.tv_right==id){
            /*todo 积分规则*/
            startActivity(new Intent(this,PointRulesActivity.class));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
