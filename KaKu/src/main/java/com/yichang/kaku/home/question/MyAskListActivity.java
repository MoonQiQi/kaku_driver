package com.yichang.kaku.home.question;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Request;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.response.MyAskListResp;
import com.yichang.kaku.tools.LoadMoreController;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.tools.okhttp.OkHttpUtil;
import com.yichang.kaku.tools.okhttp.Params;
import com.yichang.kaku.tools.okhttp.RequestCallback;
import com.yichang.kaku.webService.UrlCtnt;

import java.io.IOException;
import java.util.List;

/**
 * Created by xiaosu on 2015/11/17.
 * 我的提问列表
 */
public class MyAskListActivity extends BaseActivity implements LoadMoreController.OnLoadingListner, AdapterView.OnItemClickListener {

    private ListView listView;
    private MyAskListAdapter adapter;
    private Call call;
    private LoadMoreController controller;
    private List<MyAskListResp.ListEntity> list;

    private int curPage = 1;
    private LinearLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_mian);

        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        curPage = 1;
        getData(curPage);
    }

    private void getData(final int page) {
        if (page == 1) {
            showProgressDialog();
        }
        Params.builder builder = new Params.builder();
        builder.p("sid", Utils.getSid())
                .p("id_driver", Utils.getIdDriver())
                .p("limit", String.valueOf(page))
                .p("code", "9006");

        call = OkHttpUtil.postAsync(UrlCtnt.BASEIP, builder.build(), new RequestCallback<MyAskListResp>(this, MyAskListResp.class) {
            @Override
            public void onSuccess(MyAskListResp obj, String result) {
                if (page == 1) {//第一次加载
                    MyAskListActivity.this.list = obj.list;

                    if (obj.list.isEmpty()) {
                        View data_none = View.inflate(MyAskListActivity.this, R.layout.layout_data_none, null);
                        TextView tv_desc = (TextView) data_none.findViewById(R.id.tv_desc);
                        tv_desc.setText("您还没有提问，点击去提问");
                        tv_desc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(MyAskListActivity.this, SubmitAskActivity.class));
                                finish();
                            }
                        });
                        rootView.addView(data_none, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        listView.setVisibility(View.GONE);
                    } else {
                        adapter = new MyAskListAdapter(obj.list, obj.user);
                        listView.setAdapter(adapter);
                        ++curPage;
                    }

                } else {//加载更多
                    if (obj.list.isEmpty()) {
                        controller.updateState(LoadMoreController.STATE_NOMORE);
                    } else {
                        controller.updateState(LoadMoreController.STATE_SUC);
                        MyAskListActivity.this.list.addAll(obj.list);
                        adapter.notifyDataSetChanged();
                        ++curPage;
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onInnerFailure(Request request, IOException e) {
                stopProgressDialog();
                showShortToast("网络连接失败，请稍后再试");
                if (page != 1) {//表示加载更多
                    controller.updateState(LoadMoreController.STATE_FAIL);
                }
            }
        });
    }

    private void initUI() {
        initHead();
        listView = findView(R.id.listview);
        rootView = findView(R.id.root);
        controller = new LoadMoreController(this, listView);
        controller.setOnLoadingListner(this);
        int padding = getResources().getDimensionPixelOffset(R.dimen.x30);
        listView.setDivider(new ColorDrawable(getResources().getColor(R.color.bg_gray)));
        listView.setDividerHeight(padding);
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }
    }

    private void initHead() {
        TextView title = findView(R.id.tv_mid);
        title.setText("我的提问");
        title.setVisibility(View.VISIBLE);
        findView(R.id.ll_search).setVisibility(View.GONE);
        /**结束页面*/
        findView(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onLoading(View loadingview, final LoadMoreController controller) {
        getData(curPage);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Utils.Many()) {
            return;
        }
        startActivity(new Intent(this, QuestionDetailActivity.class).
                putExtra("id_que", list.get(position).id_question));
    }
}
