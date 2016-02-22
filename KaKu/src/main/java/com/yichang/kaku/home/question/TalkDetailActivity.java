package com.yichang.kaku.home.question;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.QuestionAnswerObj;
import com.yichang.kaku.obj.QuestionObj;
import com.yichang.kaku.response.ConversationBean;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.tools.okhttp.OkHttpUtil;
import com.yichang.kaku.tools.okhttp.Params;
import com.yichang.kaku.tools.okhttp.RequestCallback;
import com.yichang.kaku.webService.UrlCtnt;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiaosu on 2015/11/13.
 * 聊天界面
 */
public class TalkDetailActivity extends BaseActivity {

    private EditText et_content;
    private ListView listView;
    private SimpleDateFormat dateFormat;

    private QuestionObj questionObj;
    private String header_user;
    private String name_user;
    private String sub_id_question;
    private ConversationBean resp;
    private TalkDetailAdapter adapter;
    private ConversationBean.conversationItem firstItem;
    private ConversationBean.conversationItem secondItem;
    private QuestionAnswerObj answerObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_detail);

        dateFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");

        questionObj = KaKuApplication.question;
        header_user = getIntent().getStringExtra("header_user").trim();
        name_user = getIntent().getStringExtra("name_user").trim();
        sub_id_question = getIntent().getStringExtra("sub_id_question").trim();

        answerObj = (QuestionAnswerObj) getIntent().getSerializableExtra("secondItem");

        firstItem = createConversationItem(questionObj.getContent(), Integer.parseInt(questionObj.getId_driver()), "0", questionObj.getTime_create());
        secondItem = createConversationItem(answerObj.getContent(), 0, answerObj.getId_shop_user(), answerObj.getTime_create());

        initUI();
        refreshData();
    }

    private void initUI() {
        listView = findView(R.id.listview);
        TextView tv_name = findView(R.id.tv_name);
        tv_name.setText(answerObj.getName_user());
        findView(R.id.tv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void refreshData() {
        showProgressDialog();
        Params.builder builder = new Params.builder();
        builder.p("sid", Utils.getSid())
                .p("id_question", sub_id_question)
                .p("id_driver", Utils.getIdDriver())
                .p("code", "9008");

        OkHttpUtil.postAsync(UrlCtnt.BASEIP, builder.build(), new RequestCallback<ConversationBean>(this, ConversationBean.class) {
            @Override
            public void onSuccess(ConversationBean obj, String result) {

                if (TextUtils.equals(obj.flag, "Y")) {
                    initTalkFoot();
                }

                TalkDetailActivity.this.resp = obj;
                stopProgressDialog();
                obj.list.addFirst(secondItem);
                obj.list.addFirst(firstItem);
                adapter = new TalkDetailAdapter(obj, questionObj.getHead(), header_user);
                listView.setAdapter(adapter);
            }

            @Override
            public void onInnerFailure(Request request, IOException e) {
                stopProgressDialog();
                showShortToast("网络连接失败，请稍后再试");
                stopProgressDialog();
            }
        });

    }

    private void initTalkFoot() {
        ViewStub vs_foot = findView(R.id.vs_foot);
        View footView = vs_foot.inflate();
        int padding = getResources().getDimensionPixelOffset(R.dimen.x20);
        footView.setPadding(padding, padding, padding, padding);
        et_content = findView(footView, R.id.et_content);
        et_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_content.setSelection(s.length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void sendMsg(View view) {
        if (TextUtils.isEmpty(getText(et_content))) {
            showShortToast("请输入内容");
            return;
        }

        showProgressDialog();
        resp.list.add(createConversationItem(getText(et_content),
                Integer.parseInt(Utils.getIdDriver()),
                "0",
                dateFormat.format(new Date())));
        adapter.updateMsgState(resp.list.size() - 1, ConversationBean.STATE_WAITING);

        Params.builder builder = new Params.builder();
        builder.p("sid", Utils.getSid())
                .p("id_question", sub_id_question)
                .p("id_driver", Utils.getIdDriver())
                .p("content", getText(et_content))
                .p("code", "9009");

        OkHttpUtil.postAsync(UrlCtnt.BASEIP, builder.build(), new RequestCallback<ConversationBean>(this, ConversationBean.class) {
            @Override
            public void onSuccess(ConversationBean obj, String result) {
                stopProgressDialog();
                et_content.setText("");
                adapter.updateMsgState(resp.list.size() - 1, ConversationBean.STATE_SUC);
            }

            @Override
            public void onInnerFailure(Request request, IOException e) {
                stopProgressDialog();
                showShortToast("网络连接失败，请稍后再试");
                stopProgressDialog();
            }
        });
    }

    private ConversationBean.conversationItem createConversationItem(String content, int id_driver, String id_shop_user, String time_create) {
        ConversationBean.conversationItem item = new ConversationBean.conversationItem();
        item.id_driver = id_driver;
        item.content = content;
        item.time_create = time_create;
        item.id_shop_user = id_shop_user;
        item.image = "";
        return item;
    }

}
