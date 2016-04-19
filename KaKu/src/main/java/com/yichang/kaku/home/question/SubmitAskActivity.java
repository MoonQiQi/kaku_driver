package com.yichang.kaku.home.question;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.response.BaseResp;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.tools.okhttp.OkHttpUtil;
import com.yichang.kaku.tools.okhttp.Params;
import com.yichang.kaku.tools.okhttp.RequestCallback;
import com.yichang.kaku.view.WaitDialog;
import com.yichang.kaku.view.popwindow.PicturePickPopWindow;
import com.yichang.kaku.webService.UrlCtnt;

import java.io.IOException;

/**
 * Created by xiaosu on 2015/11/17.
 */
public class SubmitAskActivity extends BaseActivity implements PicturePickPopWindow.Callback {

    private EditText etContent;
    private TextView characterNum;
    private ImageView iv_shop;
    private PicturePickPopWindow popWindow;
    private WaitDialog waitDialog = new WaitDialog(context);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);

        initView();
    }

    private void initView() {
        etContent = findView(R.id.et_con);
        characterNum = findView(R.id.character_num);
        iv_shop = findView(R.id.iv_shop);
        View tv_right = findView(R.id.tv_right);

        ((TextView) findView(R.id.tv_mid)).setText("提问");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
        /**结束页面*/
        findView(R.id.tv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        etContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        etContent.setCursorVisible(true);
        etContent.setText(getIntent().getStringExtra("intro"));
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                characterNum.setText(String.valueOf(s.length()));
                etContent.setSelection(s.length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 提交数据
     */
    private void sendData() {
        String content = getText(etContent);
        if (TextUtils.isEmpty(content)) {
            showShortToast("请输入提问的内容");
            return;
        }

        waitDialog.show();
        Params.builder builder = new Params.builder();
        builder.p("sid", Utils.getSid())
                .p("content", content)
                .p("id_driver", Utils.getIdDriver())
                .p("code", "90010");

        OkHttpUtil.postAsync(UrlCtnt.BASEIP, builder.build(), new RequestCallback<BaseResp>(this, BaseResp.class) {
            @Override
            public void onSuccess(BaseResp obj, String result) {
                showShortToast("提交成功");
                waitDialog.dismiss();
                startActivity(new Intent(SubmitAskActivity.this, MyAskListActivity.class));
                finish();
            }

            @Override
            public void onInnerFailure(Request request, IOException e) {
                waitDialog.dismiss();
                showShortToast("网络连接失败，请稍后再试");
            }
        });
    }

    /**
     * 选择图片
     *
     * @param view
     */
    public void pickImg(View view) {
        if (popWindow == null) {
            popWindow = new PicturePickPopWindow(this, getResources().getDimensionPixelOffset(R.dimen.x130));
            popWindow.setmCallback(this);
        }
        popWindow.show();
    }

    @Override
    public void photoCutCallback(Bitmap bitmap) {

    }
}
