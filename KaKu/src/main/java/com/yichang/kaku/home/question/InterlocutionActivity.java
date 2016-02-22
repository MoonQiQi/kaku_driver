package com.yichang.kaku.home.question;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.okhttp.Request;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.response.AskMianResp;
import com.yichang.kaku.tools.LoadMoreController;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.tools.okhttp.OkHttpUtil;
import com.yichang.kaku.tools.okhttp.Params;
import com.yichang.kaku.tools.okhttp.RequestCallback;
import com.yichang.kaku.webService.UrlCtnt;

import java.io.IOException;

/**
 * Created by xiaosu on 2015/11/12.
 * 问答的主页
 */
public class InterlocutionActivity extends BaseActivity implements AdapterView.OnItemClickListener, LoadMoreController.OnLoadingListner {

    private ListView listView;
    public AskMianResp resp;
    private int curPage = 1;
    private AskMainAdapter adapter;
    private LoadMoreController controller;

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
                .p("code", "9005")
                .p("limit", String.valueOf(page));

        OkHttpUtil.postAsync(UrlCtnt.BASEIP, builder.build(), new RequestCallback<AskMianResp>(this, AskMianResp.class) {

            @Override
            public void onSuccess(AskMianResp obj, String result) {
                if (page == 1) {
                    InterlocutionActivity.this.resp = obj;
                    adapter = new AskMainAdapter(obj.list);
                    listView.setAdapter(adapter);
                    ++curPage;
                } else {
                    if (obj.list.isEmpty()) {
                        controller.updateState(LoadMoreController.STATE_NOMORE);
                    } else {
                        controller.updateState(LoadMoreController.STATE_SUC);
                        InterlocutionActivity.this.resp.list.addAll(obj.list);
                        adapter.notifyDataSetChanged();
                        ++curPage;
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onInnerFailure(Request request, IOException e) {
                showShortToast("网络连接失败，请稍后再试");
                stopProgressDialog();
                if (page != 1) {//表示加载更多
                    controller.updateState(LoadMoreController.STATE_FAIL);
                }
            }
        });
    }

    private void initUI() {
        View headView = inflate(R.layout.layout_ask_main_head);
        listView = findView(R.id.listview);
        listView.addHeaderView(headView);
        listView.setOnItemClickListener(this);
        controller = new LoadMoreController(this, listView);
        controller.setOnLoadingListner(this);
        /**结束页面*/
        findView(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 立即提问
     *
     * @param view
     */
    public void fastAsk(View view) {
        startActivity(new Intent(this, SubmitAskActivity.class));
    }

    /**
     * 我的提问列表
     *
     * @param view
     */
    public void myAsk(View view) {
        startActivity(new Intent(this, MyAskListActivity.class));
    }

    /**
     * 搜索
     *
     * @param view
     */
    public void toSearch(View view) {
        startActivity(new Intent(this, AskSearchActivity.class));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Utils.Many()){
            return;
        }
        if (position == 0) {
            return;
        }
        startActivity(new Intent(this, QuestionDetailActivity.class).
                putExtra("id_que", resp.list.get(position - 1).id_question));
    }

    @Override
    public void onLoading(View loadingview, LoadMoreController controller) {
        getData(curPage);
    }
}
