package com.yichang.kaku.home.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.DiscoveryCommentsObj;
import com.yichang.kaku.request.DiscoveryCommentSendReq;
import com.yichang.kaku.request.DiscoveryCommentsReq;
import com.yichang.kaku.response.DiscoveryCommentSendResp;
import com.yichang.kaku.response.DiscoveryCommentsResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class DiscoveryCommentActivity extends BaseActivity implements OnClickListener, OnFocusChangeListener {

    private TextView left, right, title;
    //评论框
    private EditText et_discovery_comment;
    //private TextView tv_textsize;
    private String strComment;

    private XListView xListView;
    private final static int STEP = 5;// 每次加载5条
    private int start = 0, pageindex = 0, pagesize = STEP, sort = 0;
    private final static int INDEX = 4;// 一屏显示的个数
    private boolean isShowProgress = false;
    private List<DiscoveryCommentsObj> choujiang_list = new ArrayList<DiscoveryCommentsObj>();
    private ImageView iv_nothing_discovery_comment;

    private Button btn_comment_send;


    /*无数据和无网络界面*/

    private RelativeLayout layout_data_none, layout_net_none;
    private TextView tv_desc, tv_advice;
    private Button btn_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery_comment);
        init();
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("评论");

        initNoDataLayout();
        xListView = (XListView) findViewById(R.id.lv_discovery_comment);

        //tv_textsize = (TextView) findViewById(R.id.tv_textsize);

        et_discovery_comment = (EditText) findViewById(R.id.et_discovery_comment);
        et_discovery_comment.setOnFocusChangeListener(this);
        et_discovery_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String comment = String.valueOf(s);
                if (comment.length() <= 200) {
                    //tv_textsize.setText(String.valueOf(s).length() + "/200");
                } else {
                    LogUtil.showShortToast(context, "已超出最大输入字符限制");
                    comment = comment.substring(0, 200);
                    et_discovery_comment.setText(comment);
                    Editable ea = et_discovery_comment.getEditableText();
                    Selection.setSelection(ea, ea.length());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                strComment = s.toString();
            }
        });

        btn_comment_send = (Button) findViewById(R.id.btn_comment_send);
        btn_comment_send.setOnClickListener(this);

        setPullState(false);
    }

    /*初始化空白页页面布局*/
    private void initNoDataLayout() {
        layout_data_none = (RelativeLayout) findViewById(R.id.layout_data_none);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_desc.setText("暂无评论");
        tv_advice = (TextView) findViewById(R.id.tv_advice);

        layout_net_none = (RelativeLayout) findViewById(R.id.layout_net_none);

        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(this);

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
        } else if (R.id.btn_comment_send == id) {
            if (TextUtils.isEmpty(strComment)) {
                return;
            } else {
                sendComment();
            }

        } else if (R.id.btn_refresh == id) {

            setPullState(false);

        }
    }

    private void setNoDataLayoutState(View view) {
        layout_data_none.setVisibility(View.GONE);
        layout_net_none.setVisibility(View.GONE);

        view.setVisibility(View.VISIBLE);
    }

    public void getCommentsList(int pageIndex, int pageSize) {
        if (!Utils.checkNetworkConnection(this)) {
            setNoDataLayoutState(layout_net_none);
            return;
        }
        showProgressDialog();
        DiscoveryCommentsReq req = new DiscoveryCommentsReq();
        req.code = "7006";
        req.start = String.valueOf(pageIndex);
        req.len = String.valueOf(pageSize);
        req.id_news = KaKuApplication.discoveryId;
        KaKuApiProvider.getDiscoveryCommentsList(req, new BaseCallback<DiscoveryCommentsResp>(DiscoveryCommentsResp.class) {

            @Override
            public void onSuccessful(int statusCode, Header[] headers, DiscoveryCommentsResp t) {
                // TODO Auto-generated method stub
                stopProgressDialog();
                if (t != null) {
                    LogUtil.E("getCommentsList res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t.comments);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        Toast.makeText(DiscoveryCommentActivity.this, t.msg, Toast.LENGTH_SHORT).show();
                    }
                    onLoadStop();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg,
                                  Throwable error) {
                // TODO Auto-generated method stub
                stopProgressDialog();
            }
        });
    }

    private void setData(List<DiscoveryCommentsObj> list) {
        // TODO Auto-generated method stub
        if (list != null) {
            choujiang_list.addAll(list);
        }
        if (choujiang_list.size() == 0) {
            setNoDataLayoutState(layout_data_none);
            return;
        }else {
            layout_data_none.setVisibility(View.GONE);
        }
        DiscoveryCommentAdapter adapter = new DiscoveryCommentAdapter(DiscoveryCommentActivity.this, choujiang_list);
        xListView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(xListView);

        //xListView.setAdapter(adapter);
        xListView.setPullLoadEnable(list.size() < INDEX ? false : true);
        xListView.setSelection(pageindex);
        xListView.setPullRefreshEnable(false);
        xListView.setXListViewListener(new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                if (!Utils.checkNetworkConnection(DiscoveryCommentActivity.this)) {
                    stopProgressDialog();
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    xListView.stopRefresh();
                    return;
                }
                setPullState(false);
            }

            @Override
            public void onLoadMore() {
                if (!Utils.checkNetworkConnection(DiscoveryCommentActivity.this)) {
                    stopProgressDialog();
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
            // prize_info = new ArrayList<PrizeInfoObj>();
            if (choujiang_list != null) {
                choujiang_list.clear();
            }
        }
        getCommentsList(pageindex, pagesize);
    }

    private void onLoadStop() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(DateUtil.dateFormat());
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText _v = (EditText) v;
        if (!hasFocus) {// 失去焦点
            _v.setHint(_v.getTag().toString());
        } else {
            String hint = _v.getHint().toString();
            _v.setTag(hint);
            _v.setCursorVisible(true);
            _v.setHint("");
        }
    }

    public void sendComment() {
        Utils.NoNet(this);
        showProgressDialog();

        DiscoveryCommentSendReq req = new DiscoveryCommentSendReq();
        req.code = "7007";
        req.id_driver = Utils.getIdDriver();
        req.id_news = KaKuApplication.discoveryId;
        req.content_comment = strComment;
        KaKuApiProvider.sendDiscoveryComment(req, new BaseCallback<DiscoveryCommentSendResp>(DiscoveryCommentSendResp.class) {

            @Override
            public void onSuccessful(int statusCode, Header[] headers,
                                     DiscoveryCommentSendResp t) {
                // TODO Auto-generated method stub
                if (t != null) {
                    if (Constants.RES.equals(t.res)) {

                        LogUtil.E("sendpinglun res: " + t.res);
                        strComment = "";
                        setPullState(false);
                        et_discovery_comment.setText("");
                        et_discovery_comment.setHint("我也说点啥...");

                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                    }
                }
                LogUtil.showShortToast(DiscoveryCommentActivity.this, t.msg);
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg,
                                  Throwable error) {
                // TODO Auto-generated method stub
                stopProgressDialog();
            }
        });
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.height += 5;

        listView.setLayoutParams(params);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            Intent intent = new Intent();
            intent.setClass(DiscoveryCommentActivity.this,DiscoveryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//注意本行的FLAG设置
            startActivity(intent);
            finish();
        }
        return false;
    }
}
