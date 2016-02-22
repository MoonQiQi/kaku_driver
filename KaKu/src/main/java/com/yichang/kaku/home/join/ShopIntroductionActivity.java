package com.yichang.kaku.home.join;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;


/**
 * Created by 小苏 on 2015-09-10 09：41.
 * Description：店铺介绍
 */
public class ShopIntroductionActivity extends BaseActivity {

    EditText etShopIntro;
    TextView characterNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recommended);
        initView();
    }

    private void initView() {
        etShopIntro = findView(R.id.et_complaints);
        characterNum = findView(R.id.character_num);
        View tv_right = findView(R.id.tv_right);

        ((TextView) findView(R.id.tv_mid)).setText("店铺介绍");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = getText(etShopIntro);
                if (TextUtils.isEmpty(text)) {
                    showShortToast("请输入店铺介绍");
                    return;
                }
                setResult(RESULT_OK, new Intent().putExtra("intro", text));
                finish();
            }
        });
        /**结束页面*/
        findView(R.id.tv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        etShopIntro.setMovementMethod(ScrollingMovementMethod.getInstance());
        etShopIntro.setCursorVisible(true);
        etShopIntro.setText(getIntent().getStringExtra("intro"));
        etShopIntro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                characterNum.setText(String.valueOf(s.length()));
                etShopIntro.setSelection(s.length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
