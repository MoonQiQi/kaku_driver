package com.yichang.kaku.home.question;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.response.SearchAskResp;
import com.yichang.kaku.tools.LoadMoreController;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.tools.okhttp.OkHttpUtil;
import com.yichang.kaku.tools.okhttp.Params;
import com.yichang.kaku.tools.okhttp.RequestCallback;
import com.yichang.kaku.webService.UrlCtnt;

import java.io.IOException;

/**
 * Created by xiaosu on 2015/11/19.
 */
public class AskSearchActivity extends BaseActivity implements TextView.OnEditorActionListener, AdapterView.OnItemClickListener, LoadMoreController.OnLoadingListner {

    private EditText et_search;
    private ListView listView;
    private SearchAskResp resp;
    private int curPage = 1;
    private LoadMoreController controller;
    private SearchAskAdapter adapter;
    private String keyword;
    private View dataNone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ask);

        initUI();
    }

    private void initUI() {
        listView = findView(R.id.listview);
        controller = new LoadMoreController(this, listView);
        controller.setOnLoadingListner(this);
        listView.setOnItemClickListener(this);
        et_search = findView(R.id.et_search);
        et_search.setOnEditorActionListener(this);
        dataNone = findView(R.id.layout_data_none);
        TextView tv_desc = findView(dataNone, R.id.tv_desc);
        tv_desc.setText("抱歉，没有搜索到数据");
        findView(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getData(String word, final int page) {
        if (page == 1) {
            showProgressDialog();
        }

        Params.builder builder = new Params.builder()
                .p("sid", Utils.getSid())
                .p("content", word)
                .p("limit", String.valueOf(page))
                .p("code", "90011");

        OkHttpUtil.postAsync(UrlCtnt.BASEIP, builder.build(), new RequestCallback<SearchAskResp>(this, SearchAskResp.class) {
            @Override
            public void onSuccess(SearchAskResp obj, String result) {
                if (page == 1) {
                    if (null == obj.list || obj.list.isEmpty()) {
                        dataNone.setVisibility(View.VISIBLE);
                        stopProgressDialog();
                        return;
                    }
                    dataNone.setVisibility(View.GONE);
                    AskSearchActivity.this.resp = obj;
                    adapter = new SearchAskAdapter(obj.list);
                    listView.setAdapter(adapter);
                    ++curPage;
                } else {
                    if (null == obj.list || obj.list.isEmpty()) {
                        controller.updateState(LoadMoreController.STATE_NOMORE);
                    } else {
                        AskSearchActivity.this.resp.list.addAll(obj.list);
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

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {

            keyword = getText(et_search);

            if (TextUtils.isEmpty(keyword)) {
                showShortToast("请输入搜索内容");
                return false;
            }

            getData(keyword, curPage);

            return true;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Utils.Many()) {
            return;
        }
        startActivity(new Intent(this, QuestionDetailActivity.class).
                putExtra("id_que", resp.list.get(position).id_question));
    }

    @Override
    public void onLoading(View loadingview, LoadMoreController controller) {
        getData(keyword, curPage);
    }
}
