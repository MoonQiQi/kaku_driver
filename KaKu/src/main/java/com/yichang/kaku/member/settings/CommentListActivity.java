package com.yichang.kaku.member.settings;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.member.driver.MsgAdapter;
import com.yichang.kaku.obj.MemberMsgObj;
import com.yichang.kaku.obj.SuggestionObj;
import com.yichang.kaku.request.GetSuggestionReq;
import com.yichang.kaku.response.GetSuggestionResp;
import com.yichang.kaku.response.SubmitSuggestionResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class CommentListActivity extends BaseActivity implements OnClickListener {
    //    titleBar
    private TextView title, left, right;

    //private ListView lv_settings_comment;

    private XListView xListView;

    private final static int STEP = 5;
    private int start = 0, pageindex = 0, pagesize = STEP;
    private final static int INDEX = 5;// 一屏显示的个数
    private boolean isShowProgress = false;

    private List<SuggestionObj> list=new ArrayList<>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_settings_comment_list);

        init();
    }

    private void init() {

        initTitleBar();
        xListView= (XListView) findViewById(R.id.lv_settings_comment);
        setPullState(false);
        //getSuggestions();
    }


    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("吐槽详情");
    }


    @Override
    public void onClick(View v) {
        Utils.NoNet(getApplicationContext());
        if (Utils.Many()) {
            return;
        }

        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        }
    }

    private void getSuggestions(int pageindex, int pagesize) {

        Utils.NoNet(context);
        showProgressDialog();


        GetSuggestionReq req=new GetSuggestionReq();
        req.code="10031";
        req.id_driver=Utils.getIdDriver();
        req.start = String.valueOf(pageindex);
        req.len = String.valueOf(pagesize);

        KaKuApiProvider.getSuggestions(req, new BaseCallback<GetSuggestionResp>(GetSuggestionResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, GetSuggestionResp t) {
                if (t != null) {
                    LogUtil.E("getSuggestions res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        /*xListView.setAdapter(new CommentListAdapter(context, t.suggests));*/
                        setData(t.suggests);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                    onLoadStop();
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });
    }

    private void setData(List<SuggestionObj> notices) {
        if (notices != null) {
            list.addAll(notices);
        }



        CommentListAdapter adapter = new CommentListAdapter(this, list);
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
        getSuggestions(pageindex,pagesize);
    }
    private void onLoadStop() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(DateUtil.dateFormat());
    }


}
