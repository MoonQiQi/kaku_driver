package com.yichang.kaku.home.giftmall;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.ShopCartProductObj;
import com.yichang.kaku.request.ShopCartProductsReq;
import com.yichang.kaku.response.ShopCartProductsResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShopCartActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    //titleBar,返回，购物车，标题
    private TextView left, right, title;
    //	**件商品，总价：¥0.00
    private TextView tv_shopcart_money, tv_shopcart_pay;

    private ListView lv_shopcart_list;

    private List<ShopCartProductObj> list = new ArrayList<>();

    private String strTotalPrice;

    private LinearLayout ll_has_product;
    private RelativeLayout rl_no_product;
    private Button btn_start_exchange;
    //全选按钮
    private CheckBox cbx_shopcart_edit_selected;
    private LinearLayout ll_shopcart_edit_selectall;
    //勾选的购物车商品编号
    private String mId_goods_shopcars = "";
    //    勾选的商品个数
    private int productNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcart);
        init();
    }


    private void init() {
        //初始化titleBar
        initTitleBar();

        ll_has_product = (LinearLayout) findViewById(R.id.ll_has_product);
        rl_no_product = (RelativeLayout) findViewById(R.id.rl_no_product);
        rl_no_product.setOnClickListener(this);

        //初始化购物车列表
        lv_shopcart_list = (ListView) findViewById(R.id.lv_shopcart_list);
        lv_shopcart_list.setOnItemClickListener(this);
        cbx_shopcart_edit_selected = (CheckBox) findViewById(R.id.cbx_shopcart_edit_selected);
        cbx_shopcart_edit_selected.setChecked(true);
        //初始化全选框
        ll_shopcart_edit_selectall = (LinearLayout) findViewById(R.id.ll_shopcart_edit_selectall);
        ll_shopcart_edit_selectall.setOnClickListener(this);

        KaKuApplication.id_goods_shopcars = "";
        getShopCartLst();
    }

    private void setTotalPayPrice() {

        float totalPrice = 0.00f;
        productNum = 0;

       /* for (ShopCartProductObj obj : list) {
            productNum += Integer.parseInt(obj.getNum_shopcar());
            totalPrice += Float.parseFloat(obj.getPrice_goods()) * Float.parseFloat(obj.getNum_shopcar());
        }*/
        String strObjId = "";
        /*for (int i = 0; i < lv_shopcart_list.getChildCount(); i++) {
            View child = lv_shopcart_list.getChildAt(i);
            if (((CheckBox) child.findViewById(R.id.cbx_shopcart_select)).isChecked()) {
                TextView tv_num = (TextView) child.findViewById(R.id.tv_shopcart_num);
                TextView tv_price = (TextView) child.findViewById(R.id.tv_shopcart_price);
                productNum += Integer.parseInt(tv_num.getText().toString().substring(1));
                totalPrice += Float.parseFloat(tv_num.getText().toString().substring(1)) * Float.parseFloat(tv_price.getText().toString());
//      在adapter中为tv_price设置了
                strObjId += tv_price.getTag().toString() + ",";
                mId_goods_shopcars = strObjId;
                LogUtil.E("strObjId:" + mId_goods_shopcars);
            }
        }*/
        for(int i=0;i<list.size();i++){
            ShopCartProductObj obj=list.get(i);
            if(obj.getIsChecked()){

                productNum+=Integer.parseInt(obj.getNum_shopcar());
                totalPrice+=Integer.parseInt(obj.getNum_shopcar())*Float.parseFloat(obj.getPrice_goods());
                strObjId+=obj.getId_goods_shopcar()+",";
            }

        }
        mId_goods_shopcars=strObjId;

        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        strTotalPrice = decimalFormat.format(totalPrice);//format 返回的是字符串
        //tv_shopcart_money.setText(productNum + "件商品，总计:¥" + strTotalPrice);
        tv_shopcart_money.setText("¥" + strTotalPrice);

    }

    private void initPayBar() {
        tv_shopcart_money = (TextView) findViewById(R.id.tv_shopcart_money);
        tv_shopcart_pay = (TextView) findViewById(R.id.tv_shopcart_pay);
        tv_shopcart_pay.setOnClickListener(this);
    }

    private void getShopCartLst() {
        Utils.NoNet(context);
        showProgressDialog();

        ShopCartProductsReq req = new ShopCartProductsReq();
        req.code = "3005";
        req.id_driver = Utils.getIdDriver();

        KaKuApiProvider.getShopCartProductsLst(req, new BaseCallback<ShopCartProductsResp>(ShopCartProductsResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, ShopCartProductsResp t) {
                if (t != null) {
                    LogUtil.E("getShopCartProductsLst res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        //为gridView设置数据
                        setData(t.shopcar);
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

    private void setData(List<ShopCartProductObj> shopcar) {
        if (list != null) {
            list.clear();
        }
        if (shopcar.size() != 0) {
            //		初始化屏幕下方支付工具栏
            initPayBar();

            list.addAll(shopcar);

            ShopCartProductAdapter adapter = new ShopCartProductAdapter(context, list);
            adapter.setCallback(new ShopCartProductAdapter.CallBack() {
                @Override
                public boolean getCheckedState() {
                    return isSelectAll;
                }
            });
            lv_shopcart_list.setAdapter(adapter);
            //计算总金额
            //selectAllItems();
            //setTotalPayPrice();
            //初始化
            productNum = 0;
            final float[] totalPrice = {0.00f};

            //判断全局变量中的所选购物车商品列表是否存在，如不存在正常全选，如存在则只选择上次已选的商品
            if (TextUtils.isEmpty(KaKuApplication.id_goods_shopcars)) {
                mId_goods_shopcars = "";
                for (int i = 0; i < list.size(); i++) {
                    totalPrice[0] += Float.parseFloat(list.get(i).getPrice_goods()) * Integer.parseInt(list.get(i).getNum_shopcar());
                    productNum += Integer.parseInt(list.get(i).getNum_shopcar());
                    mId_goods_shopcars += list.get(i).getId_goods_shopcar() + ",";
                }
                DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                strTotalPrice = decimalFormat.format(totalPrice[0]);//format 返回的是字符串
                //tv_shopcart_money.setText(productNum + "件商品，总计:¥" + strTotalPrice);
                tv_shopcart_money.setText("¥" + strTotalPrice);
            } else {
                //listView setAdapter异步加载，post更新listView方便对childView 进行操作
                mId_goods_shopcars = KaKuApplication.id_goods_shopcars;
                lv_shopcart_list.post(new Runnable() {
                    @Override
                    public void run() {

                        // fileList为与adapter做连结的list总数
                        if (list.size() == lv_shopcart_list.getChildCount()) {
                            //对ListView中的ChildView进行操作。。。
                            for (int j = 0; j < lv_shopcart_list.getChildCount(); j++) {
                                View view = lv_shopcart_list.getChildAt(j);
                                TextView tv_num = (TextView) view.findViewById(R.id.tv_shopcart_num);
                                TextView tv_price = (TextView) view.findViewById(R.id.tv_shopcart_price);
                                CheckBox cbx = (CheckBox) view.findViewById(R.id.cbx_shopcart_select);
                                if (KaKuApplication.id_goods_shopcars.contains(tv_price.getTag().toString().trim())) {

                                    cbx.setChecked(true);
                                    productNum += Integer.parseInt(tv_num.getText().toString().substring(1));
                                    totalPrice[0] += Float.parseFloat(tv_num.getText().toString().substring(1)) * Float.parseFloat(tv_price.getText().toString());
                                } else {
                                    cbx.setChecked(false);
                                    //如果有未选择的商品，则取消全选按钮的选中状态
                                    cbx_shopcart_edit_selected.setChecked(false);
                                }
                            }
                            DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                            strTotalPrice = decimalFormat.format(totalPrice[0]);//format 返回的是字符串
                            //tv_shopcart_money.setText(productNum + "件商品，总计:¥" + strTotalPrice);
                            tv_shopcart_money.setText("¥" + strTotalPrice);
                            KaKuApplication.id_goods_shopcars = "";
                        }


                    }
                });
            }


        } else

        {
            ll_has_product.setVisibility(View.GONE);
            rl_no_product.setVisibility(View.VISIBLE);
            right.setEnabled(false);
            btn_start_exchange = (Button) findViewById(R.id.btn_start_exchange);
            btn_start_exchange.setOnClickListener(this);
        }

    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("编辑");
        right.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("购物车");
    }


    @Override
    public void onClick(View v) {

        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();

        if (R.id.tv_left == id) {
            goToShopMall();
            finish();
        } else if (R.id.tv_right == id) {
//     todo       购物车详细
            goToShopCartEditActivity();

        } else if (R.id.tv_shopcart_pay == id) {
//     todo       转到支付页面
            if (productNum == 0) {
                LogUtil.showShortToast(context, "请勾选商品");
                return;
            }
            MobclickAgent.onEvent(context, "CarGoToPay");
            goToConfirmOrder();
            finish();
        } else if (R.id.btn_start_exchange == id) {
            LogUtil.E("开始购买");
            goToShopMall();

            this.finish();
        } else if (R.id.rl_no_product == id) {

        } else if (R.id.ll_shopcart_edit_selectall == id) {
            //todo 全选
            selectAllItems();
            setTotalPayPrice();
        }

    }

    private void goToShopMall() {
        Intent intent = new Intent(getApplicationContext(), ShopMallActivity.class);
        startActivity(intent);
    }

    private void goToConfirmOrder() {
        Intent intent = new Intent(this, ConfirmOrderActivity.class);
        intent.putExtra("id_goods_shopcars", mId_goods_shopcars);
        KaKuApplication.id_goods_shopcars = mId_goods_shopcars;
        startActivity(intent);
    }

    private void goToShopCartEditActivity() {
        Intent intent = new Intent(ShopCartActivity.this, ShopCartEditActivity.class);
        intent.putExtra("list", (Serializable) list);
        /*ShopCartActivity.this.startActivity(intent);*/
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 0:
                //无数据修改
                break;
            case 1:
                //有数据修改
                getShopCartLst();
                break;
            case 2:
                //有数据修改
                ll_has_product.setVisibility(View.GONE);
                rl_no_product.setVisibility(View.VISIBLE);
                right.setEnabled(false);
                btn_start_exchange = (Button) findViewById(R.id.btn_start_exchange);
                btn_start_exchange.setOnClickListener(this);
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Utils.Many()) {
            return;
        }
        int parentId = parent.getId();
        if (R.id.lv_shopcart_list == parentId) {
            CheckBox cbx = (CheckBox) view.findViewById(R.id.cbx_shopcart_select);
            list.get(position).setIsChecked(!list.get(position).getIsChecked());
            cbx.setChecked(!cbx.isChecked());
            checkSelectedState();
        }
        setTotalPayPrice();
    }

    private boolean isSelectAll = false;

    /*
    *同步Item的checkbox和底部全选按钮的状态
    */
    private void checkSelectedState() {
//        设置列表第一个item初始被选状态为false
        boolean firstSelected = false;
        boolean otherSelected;
        for (int i = 0; i < lv_shopcart_list.getChildCount(); i++) {
            View child = lv_shopcart_list.getChildAt(i);
            if (i == 0) {
//                初始化第一个元素选择状态
                firstSelected = ((CheckBox) child.findViewById(R.id.cbx_shopcart_select)).isChecked();
            } else {
                otherSelected = ((CheckBox) child.findViewById(R.id.cbx_shopcart_select)).isChecked();
//                判断其余元素被选择状态与第一个元素是否相同
//                如相同说明元素状态一致，修改底部checkbox选中状态
//                如不相同修改全选状态为false
                if (firstSelected != otherSelected) {
                    isSelectAll = true;
                    cbx_shopcart_edit_selected.setChecked(false);
                    return;

                }
            }
        }
        isSelectAll = firstSelected;
//        全选
        selectAllItems();

    }

    /*修改所有元素的选中状态*/
    private void selectAllItems() {

        for (int i = 0; i < lv_shopcart_list.getChildCount(); i++) {
            View child = lv_shopcart_list.getChildAt(i);
            ((CheckBox) child.findViewById(R.id.cbx_shopcart_select)).setChecked(isSelectAll);
            cbx_shopcart_edit_selected.setChecked(isSelectAll);
        }
        for (int j=0;j<list.size();j++){
            list.get(j).setIsChecked(isSelectAll);
        }
        isSelectAll = !isSelectAll;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            Intent intent = new Intent(getApplicationContext(), ShopMallActivity.class);
            startActivity(intent);
        }
        return false;
    }
}
