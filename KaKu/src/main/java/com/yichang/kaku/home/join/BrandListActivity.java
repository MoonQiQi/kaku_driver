package com.yichang.kaku.home.join;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.response.BrandListResp;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.tools.okhttp.OkHttpUtil;
import com.yichang.kaku.tools.okhttp.Params;
import com.yichang.kaku.tools.okhttp.RequestCallback;
import com.yichang.kaku.view.widget.QuickIndexBar;
import com.yichang.kaku.webService.UrlCtnt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by 小苏 on 2015/10/20.
 * 品牌列表
 */
public class BrandListActivity extends BaseActivity implements AdapterView.OnItemClickListener, QuickIndexBar.OnLetterChangeListener, View.OnClickListener {

    private InnerTask innerTask;

    private BrandListResp resp;
    private Map<String, Integer> letterMap = new HashMap<String, Integer>();
    private BrandListAdapter adapter;

    private ArrayList<BrandListResp.ItemEntity> list = new ArrayList<>();
    private ListView listview;
    private TextView curLetter;
    private QuickIndexBar indexBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allbrand_list);
        initUI();

        getData();
    }

    private void initUI() {
        setContentView(R.layout.activity_allbrand_list);
        listview = findView(R.id.listview);
        indexBar = findView(R.id.indexbar);
        curLetter = findView(R.id.curLetter);
        View vew = findView(R.id.tv_right);

        ((TextView) findView(R.id.tv_mid)).setText("品牌列表");

        ArrayList<BrandListResp.ItemEntity> choosed = getIntent().getParcelableArrayListExtra("info");
        if (choosed != null) {
            list = choosed;
        }

        /**结束页面*/
        findView(R.id.tv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        vew.setVisibility(View.VISIBLE);
        vew.setOnClickListener(this);
        indexBar.setOnLetterChangeListener(this);
        listview.setOnItemClickListener(this);

        /**结束页面*/
        findView(R.id.tv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getData() {

        showProgressDialog();

        Params.builder builder = new Params.builder();
        builder.p("sid", Utils.getSid())
                .p("code", "9003");

        OkHttpUtil.postAsync(UrlCtnt.BASEIP, builder.build(), new RequestCallback<BrandListResp>(this, BrandListResp.class) {
            @Override
            public void onSuccess(BrandListResp obj, String result) {
                BrandListActivity.this.resp = obj;

                if (obj != null && obj.list != null) {

                    //对集合进行排序
                    Collections.sort(obj.list);

                    int size = obj.list.size();
                    //第0为肯定是A
                    for (int i = 1; i < size; i++) {
                        String currentLetter = obj.list.get(i).getPinyin().charAt(0) + "";
                        String lastLetter = obj.list.get(i - 1).getPinyin().charAt(0) + "";

                        if (i == 0) {
                            letterMap.put(currentLetter, 0);
                            continue;
                        }

                        if (!currentLetter.equals(lastLetter)) {
                            letterMap.put(currentLetter, i);
                        }
                    }

                    adapter = new BrandListAdapter(obj.list, list);
                    listview.setAdapter(adapter);
                }
                stopProgressDialog();
            }

            @Override
            public void onInnerFailure(Request request, IOException e) {
                stopProgressDialog();
                showShortToast("网络连接失败，请稍后再试");
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Utils.Many()){
            return;
        }
        /*已经选择过的条目*/
        if (list.contains(resp.list.get(position))) {
            changBg(view, position);
            list.remove(resp.list.get(position));
            return;
        }

        if (list.size() >= 10) {
            showShortToast("最多只能选择10个品牌");
            return;
        }

        changBg(view, position);
        list.add(resp.list.get(position));
    }

    private void changBg(View view, int position) {
        adapter.itemStateCache.put(position, !adapter.itemStateCache.get(position, false));
        View iv_right = (View) view.getTag();
        iv_right.setVisibility(adapter.itemStateCache.get(position, false) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onLetterChange(String letter) {
        curLetter.setText(letter);
        //listview跳转到相应的letter的position
        if (letterMap.containsKey(letter)) {
            listview.setSelection(letterMap.get(letter));
        }
    }

    @Override
    public void onTouchStart() {
        curLetter.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTouchEnd() {
        if (innerTask == null) {
            innerTask = new InnerTask();
        }
        innerTask.run();
    }


    public void complete() {
        if (!list.isEmpty()) {
            setResult(RESULT_OK, new Intent().putParcelableArrayListExtra("brands", list));
            finish();
        } else {
            showShortToast("请选择品牌");
        }
    }

    @Override
    public void onClick(View v) {
        if (Utils.Many()){
            return;
        }
        complete();
    }

    class InnerTask implements Runnable {

        private final AlphaAnimation animation;

        public InnerTask() {
            animation = new AlphaAnimation(1, 0);
        }

        @Override
        public void run() {
            animation.setDuration(500);
            animation.setStartOffset(500);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    curLetter.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            curLetter.startAnimation(animation);
        }
    }

}
