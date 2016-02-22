package com.yichang.kaku.member.cash;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.request.AddBankCardReq;
import com.yichang.kaku.response.BaseResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

public class AddBankCardActivity extends BaseActivity implements OnClickListener {

    public static int BANK_CITY_REQUEST = 100;
    public static int BANK_CITY_RESPONSE_SUCCESS = 101;
    public static int BANK_NAME_REQUEST = 110;
    public static int BANK_NAME_RESPONSE_SUCCESS = 111;


    private Button btn_bank_card_finish;
    //    titleBar
    private TextView title, left, right;
    //开户人姓名
    private EditText et_bankcard_name;
    /* //开户城市
     private RelativeLayout rela_bankcard_city;
     private TextView tv_bankcard_city;*/
    //银行卡号
    private EditText et_bankcard_no;
    //开户银行
    private RelativeLayout rela_bankcard_bank;
    private TextView tv_bankcard_bank;
    //private EditText et_bankcard_bank;*/

    private String strBankCardNo;

    private View title_member_msg;
    private InputMethodManager imm;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_bankcard_add);

        init();

    }

    private void init() {
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        initTitleBar();
//初始化空白页
        initOthers();

        title_member_msg=findViewById(R.id.title_member_msg);


    }


    private void initOthers() {
        et_bankcard_name = (EditText) findViewById(R.id.et_bankcard_name);
        et_bankcard_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                                0);
                    }
                }
            }
        });
        et_bankcard_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /*rela_bankcard_city = (RelativeLayout) findViewById(R.id.rela_bankcard_city);
        rela_bankcard_city.setOnClickListener(this);
        tv_bankcard_city = (TextView) findViewById(R.id.tv_bankcard_city);*/

        et_bankcard_no = (EditText) findViewById(R.id.et_bankcard_no);
        et_bankcard_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                                0);
                    }
                }
            }
        });
        et_bankcard_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                strBankCardNo = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rela_bankcard_bank = (RelativeLayout) findViewById(R.id.rela_bankcard_bank);
        rela_bankcard_bank.setOnClickListener(this);
        tv_bankcard_bank = (TextView) findViewById(R.id.tv_bankcard_bank);
      /*  et_bankcard_bank= (EditText) findViewById(R.id.et_bankcard_bank);
        et_bankcard_bank.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        btn_bank_card_finish = (Button) findViewById(R.id.btn_bank_card_finish);
        btn_bank_card_finish.setOnClickListener(this);
    }


    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("添加银行卡");
    }


    @Override
    public void onClick(View v) {
        Utils.NoNet(getApplicationContext());
        if (Utils.Many()) {
            return;
        }

        int id = v.getId();
        if (R.id.tv_left == id) {
            setResult();
        }/*else if(R.id.rela_bankcard_city==id){
            startActivityForResult(new Intent(this, BankProvinceActivity.class), BANK_CITY_REQUEST);

        }*/ else if (R.id.rela_bankcard_bank == id) {
            startActivityForResult(new Intent(this, BankListActivity.class), BANK_NAME_REQUEST);

        } else if (R.id.btn_bank_card_finish == id) {
             /*todo 发送短信*/
            if (TextUtils.isEmpty(et_bankcard_name.getText().toString())) {
                Toast.makeText(context, "请填写开户人姓名", Toast.LENGTH_SHORT).show();
                return;
            } else if (et_bankcard_name.getText().toString().length() > 15) {
                Toast.makeText(context, "姓名长度不能超过15", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(et_bankcard_no.getText().toString())) {
                Toast.makeText(context, "请输入银行卡号", Toast.LENGTH_SHORT).show();
                return;
            } else if (et_bankcard_no.getText().length() < 16 || et_bankcard_no.getText().length() >= 20) {
                LogUtil.showShortToast(this, "请输入正确的银行卡号");
                return;
            }
            //setResult();
            addBankCard();

        }
    }

    private void addBankCard() {
        Utils.NoNet(context);
        showProgressDialog();

        AddBankCardReq req = new AddBankCardReq();
        req.code = "5005";
        req.id_driver = Utils.getIdDriver();
        req.driver_bank = et_bankcard_name.getText().toString().trim();
        req.card_bank = et_bankcard_no.getText().toString().trim();
        req.name_bank = tv_bankcard_bank.getText().toString().trim();

        KaKuApiProvider.addNewBankCard(req, new BaseCallback<BaseResp>(BaseResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, BaseResp t) {
                if (t != null) {
                    LogUtil.E("addNewBankCard res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        AddBankCardActivity.this.finish();

                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BANK_NAME_RESPONSE_SUCCESS) {
            tv_bankcard_bank.setText(data.getStringExtra("bankName"));
        } else if (resultCode == BANK_CITY_RESPONSE_SUCCESS) {
            //tv_bankcard_city.setText(data.getStringExtra("bankCity"));
        }
    }

    private void setResult() {


        Intent intent = new Intent();
        intent.putExtra("bankcard_name", et_bankcard_name.getText().toString().trim());
        //intent.putExtra("bankcard_city",tv_bankcard_city.getText().toString().trim());
        intent.putExtra("bankcard_no", et_bankcard_no.getText().toString().trim());
        intent.putExtra("bankcard_bank", tv_bankcard_bank.getText().toString().trim());

        this.setResult(WithdrawActivity.BANK_INFO_RESPONSE_SUCCESS, intent);
        this.finish();

    }


}
