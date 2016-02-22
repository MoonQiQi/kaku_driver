package com.yichang.kaku.home.question;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.QuestionAnswerObj;
import com.yichang.kaku.obj.QuestionObj;
import com.yichang.kaku.response.QuestionDetailResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.tools.okhttp.OkHttpUtil;
import com.yichang.kaku.tools.okhttp.Params;
import com.yichang.kaku.tools.okhttp.RequestCallback;
import com.yichang.kaku.webService.UrlCtnt;

import java.io.IOException;
import java.util.List;

public class QuestionDetailActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;

    ListView lv_question;


    private TextView tv_time_create, tv_question_content, tv_answer_count;
    private List<QuestionAnswerObj> answerObjList;

    private boolean isDriverSelf;
    //问题id
    private String id_question1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("");


        tv_question_content = (TextView) findViewById(R.id.tv_question_content);
        tv_time_create = (TextView) findViewById(R.id.tv_time_create);

        tv_answer_count = (TextView) findViewById(R.id.tv_answer_count);


        lv_question = (ListView) findViewById(R.id.lv_question);
        lv_question.setOnItemClickListener(this);
        //setPullState(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String id_que = getIntent().getStringExtra("id_que");
        LogUtil.E("que" + id_que);
        getQuestionDetail(id_que);
    }

    private void getQuestionDetail(String id_que) {
       /* QuestionDetailReq req=new QuestionDetailReq();
        req.code="9007";*/

        showProgressDialog();
        Params.builder builder = new Params.builder();
        builder.p("sid", Utils.getSid())
                .p("code", "9007")
                .p("id_question", id_que);

        OkHttpUtil.postAsync(UrlCtnt.BASEIP, builder.build(), new RequestCallback<QuestionDetailResp>(this, QuestionDetailResp.class) {
            @Override
            public void onSuccess(QuestionDetailResp obj, String result) {
                tv_answer_count.setText(obj.list.size() + "回答");
                setQuestionData(obj.question);
                boolean isAccept = false;

                isDriverSelf = false;
                if (obj.question.getFlag_question().equals("Y")) {
                    isAccept = true;
                }

                if (obj.question.getId_driver().equals(Utils.getIdDriver())) {
                    isDriverSelf = true;
                }

                answerObjList = obj.list;
                final QuestionDetailAdapter adapter = new QuestionDetailAdapter(QuestionDetailActivity.this, obj.list, obj.question.getId_child(), obj.question.getId_driver(), isAccept, isDriverSelf);
//回调，完成采纳操作
                adapter.setLogicTransaction(new QuestionDetailAdapter.ILogicTransaction() {
                    @Override
                    public void acceptAnswer(String id_question2) {
                        adapter.setmIsAccept(true);
                        adapter.notifyDataSetChanged();

                        sendAcceptAnswer(id_question2);

                    }
                });

                lv_question.setAdapter(adapter);


                stopProgressDialog();
            }

            @Override
            public void onInnerFailure(Request request, IOException e) {
                stopProgressDialog();
                showShortToast("网络连接失败，请稍后再试");
            }
        });
    }

    private void sendAcceptAnswer(String id_question2) {
        showProgressDialog();
        Params.builder builder = new Params.builder();
        builder.p("sid", Utils.getSid())
                .p("code", "90012")
                .p("id_question1", id_question1)
                .p("id_question2", id_question2)
                .p("id_driver", Utils.getIdDriver());

        OkHttpUtil.postAsync(UrlCtnt.BASEIP, builder.build(), new RequestCallback<QuestionDetailResp>(this, QuestionDetailResp.class) {
            @Override
            public void onSuccess(QuestionDetailResp obj, String result) {
                if (Constants.RES.equals(obj.res)) {
                    LogUtil.showShortToast(context, obj.msg);
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

    private void setQuestionData(QuestionObj question) {
//保存question对象，每次加载时会自动赋值，不需要手动清除

        KaKuApplication.question = question;
        tv_time_create.setText(question.getTime_create());
        tv_question_content.setText(question.getContent());

        id_question1 = question.getId_question();

        title.setText(question.getName_driver() + "提问");
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }

        int id = v.getId();
        if (R.id.tv_left == id) {
            QuestionDetailActivity.this.finish();

        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Utils.Many()){
            return;
        }
        gotoTalkActivity(answerObjList.get(position));
    }

    private void gotoTalkActivity(QuestionAnswerObj obj) {
        Intent intent = new Intent(context, TalkDetailActivity.class);

        intent.putExtra("sub_id_question", obj.getId_question());
        intent.putExtra("header_user", obj.getHead_user());
        intent.putExtra("name_user", obj.getName_user());
        intent.putExtra("secondItem", obj);
        context.startActivity(intent);
    }
}


