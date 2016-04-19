package com.yichang.kaku.member.cash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.obj.BankCardObj;
import com.yichang.kaku.request.BankCardListReq;
import com.yichang.kaku.response.BankCardListResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

public class BankCardListActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;

    private ListView lv_bank_list;
    private RelativeLayout footerView;

    private List<BankCardObj> list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_bank_list);
        init();
    }

    private void init() {
        initTitleBar();

        lv_bank_list = (ListView) findViewById(R.id.lv_bank_list);
        lv_bank_list.setOnItemClickListener(this);
        footerView = (RelativeLayout) View.inflate(context, R.layout.item_bank_card_add, null);
        footerView.setTag(true);


    }

    @Override
    protected void onStart() {
        lv_bank_list.removeFooterView(footerView);
        getBankCardList();
        super.onStart();
    }

    private void initTitleBar() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("选择银行卡");
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        } else if (R.id.tv_right == id) {
            startActivity(new Intent(this, WithdrawActivity.class));
        }
    }

    public void getBankCardList() {
        if (!Utils.checkNetworkConnection(context)) {
            return;
        }

        BankCardListReq req = new BankCardListReq();
        req.code = "5004";
        req.id_driver = Utils.getIdDriver();

        KaKuApiProvider.getBankCardList(req, new KakuResponseListener<BankCardListResp>(this, BankCardListResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("yue res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t.banks);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }
        });
    }

    private void setData(List<BankCardObj> banks) {

        if (banks!=null){
            list.clear();
            list.addAll(banks);
        }else {
            return;
        }
        lv_bank_list.addFooterView(footerView);
        BankCardAdapter adapter = new BankCardAdapter(context, list);
        lv_bank_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Object obj=view.getTag();
        if(obj.getClass().equals(Boolean.class)){

           // startActivityforResult();
            Intent intent = new Intent(this, AddBankCardActivity.class);
            startActivity(intent);
        }else if(obj.getClass().equals(BankCardAdapter.ViewHolder.class)){

            Intent intent=new Intent();

            String str=list.get(position).getName_bank()+" "+list.get(position).getCard_bank();
            intent.putExtra("cardInfo",str);
            intent.putExtra("id_driver_bank",list.get(position).getId_driver_bank());
            setResult(WithdrawActivity.BANK_INFO_RESPONSE_SUCCESS,intent);
            finish();
        }


    }

    /*private void startActivityforResult() {
        Intent intent = new Intent(this, AddBankCardActivity.class);
        startActivityForResult(intent, WithdrawActivity.BANK_INFO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == WithdrawActivity.BANK_INFO_RESPONSE_SUCCESS) {
            *//*mCardBank = data.getStringExtra("bankcard_no");
			mNameUser = data.getStringExtra("bankcard_name");
			if (!TextUtils.isEmpty(mCardBank)) {
				String temp = mNameUser + "**" + mCardBank.substring(mCardBank.length() - 4, mCardBank.length());
				car_bank.setText(temp);
			}*//*
        }
    }*/
}
