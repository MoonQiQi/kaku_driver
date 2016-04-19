package com.yichang.kaku.member.cash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.tools.BankNameUtil;
import com.yichang.kaku.tools.Utils;

import java.util.ArrayList;
import java.util.List;

public class BankListActivity extends BaseActivity implements OnClickListener,AdapterView.OnItemClickListener {
    //    titleBar
    private TextView title, left, right;
   private ListView lv_bank_list;

    private List<String> mBankName=new ArrayList<>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_bank_list);

        init();
    }

    private void init() {
        initTitleBar();
        lv_bank_list= (ListView) findViewById(R.id.lv_bank_list);
        mBankName.clear();
        mBankName= BankNameUtil.getBankNameList();

        //LogUtil.E("mBankname:"+mBankName);
        lv_bank_list.setAdapter(new ArrayAdapter<String>(this, R.layout.item_bank_list,mBankName));

        lv_bank_list.setOnItemClickListener(this);


    }




    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("选择银行");
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
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//数据是使用Intent返回
        Intent intent = new Intent();
        //把返回数据存入Intent
        intent.putExtra("bankName",mBankName.get(position) );
        //设置返回数据
        BankListActivity.this.setResult(AddBankCardActivity.BANK_NAME_RESPONSE_SUCCESS, intent);
        //关闭Activity
        BankListActivity.this.finish();
    }
}
