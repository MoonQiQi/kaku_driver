package com.yichang.kaku.member.settings;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.request.SubmitSuggestionReq;
import com.yichang.kaku.response.SubmitSuggestionResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class MemberSettingsCommentActivity extends BaseActivity implements OnClickListener {
    //    titleBar
    private TextView title, left, right;
    //选择建议类型
    private RelativeLayout rela_setting_comment;
    private TextView tv_comment_type;
    //输入建议
    private EditText et_setting_comment;
    //提交建议
    private Button btn_setting_submit;
    //字数
    private TextView tv_textsize;
    //存储建议类型,建议内容
    private String type_suggest;
    private String strComment;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_settings_comment);
        init();
    }

    private void init() {

        initTitleBar();

        rela_setting_comment = (RelativeLayout) findViewById(R.id.rela_setting_comment);
        rela_setting_comment.setOnClickListener(this);

        tv_comment_type = (TextView) findViewById(R.id.tv_comment_type);
        //初始化字数显示框
        tv_textsize = (TextView) findViewById(R.id.tv_textsize);

//        初始化文字输入框
        et_setting_comment = (EditText) findViewById(R.id.et_setting_comment);
        et_setting_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String comment = String.valueOf(s);
                if (comment.length() <= 100) {
                    tv_textsize.setText(String.valueOf(s).length() + "/100");
                } else {
                    LogUtil.showShortToast(context, "已超出最大输入字符限制");
                    comment = comment.substring(0, 100);
                    et_setting_comment.setText(comment);
                    Editable ea = et_setting_comment.getEditableText();
                    Selection.setSelection(ea, ea.length());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                strComment = s.toString();
            }
        });


        btn_setting_submit = (Button) findViewById(R.id.btn_setting_submit);
        btn_setting_submit.setOnClickListener(this);
    }


    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("产品吐槽");

        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("吐槽详情");
        right.setOnClickListener(this);
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
        } else if (R.id.tv_right == id) {
            startActivity(new Intent(context, CommentListActivity.class));
        } else if (R.id.rela_setting_comment == id) {
            /*选择反馈类型*/
            Intent intent = new Intent(this, CommentTypeActivity.class);
            startActivityForResult(intent, 0);

        } else if (R.id.btn_setting_submit == id) {
            submit();
        }
    }

    private void submit() {
        if (TextUtils.isEmpty(type_suggest)) {
            LogUtil.showShortToast(context, "请选择吐槽类型");
            return;
        }
        if (TextUtils.isEmpty(strComment)) {
            LogUtil.showShortToast(context, "请给我们提建议吧！");
            return;
        }

        Utils.NoNet(context);

        SubmitSuggestionReq req = new SubmitSuggestionReq();
        req.code = "10030";
        req.id_driver = Utils.getIdDriver();
        req.content_suggest = strComment;
        req.type_suggest = type_suggest;

        KaKuApiProvider.submitSuggestion(req, new KakuResponseListener<SubmitSuggestionResp>(this, SubmitSuggestionResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("submitSuggestion res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        strComment = "";
                        if (!TextUtils.isEmpty(t.point_get)) {
                            //showPopWindow(t.point_get);
                        } else {
                            startActivity(new Intent(MemberSettingsCommentActivity.this, CommentListActivity.class));
                            finish();
                        }

                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    /*private void showPopWindow(String point) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View vPopWindow = inflater.inflate(R.layout.popwindow_daily_sign, null, false);
        final PopupWindow popWindow = new PopupWindow(vPopWindow, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);

        ImageView iv_close = (ImageView) vPopWindow.findViewById(R.id.btn_popwindow_close);
        TextView tv_point = (TextView) vPopWindow.findViewById(R.id.tv_popwindw_point);
        tv_point.setText("+" + point + "积分");
        *//*TextView tv_content = (TextView) vPopWindow.findViewById(R.id.tv_popwindw_content);
        tv_content.setText(content);*//*
        iv_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
                startActivity(new Intent(MemberSettingsCommentActivity.this, CommentListActivity.class));
                finish();
            }
        });

        popWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        resultCode 141:fuction,142:page,143:operate,144:other
        switch (resultCode) {
            case 141:
                type_suggest = "1";
                tv_comment_type.setText("功能意见");
                break;
            case 142:
                type_suggest = "2";
                tv_comment_type.setText("页面意见");
                break;
            case 143:
                type_suggest = "3";
                tv_comment_type.setText("操作意见");
                break;
            case 144:
                type_suggest = "4";
                tv_comment_type.setText("其它意见");
                break;
        }
    }
}
