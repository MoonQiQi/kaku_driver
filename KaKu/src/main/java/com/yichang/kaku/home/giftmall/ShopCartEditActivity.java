package com.yichang.kaku.home.giftmall;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.obj.ShopCartProductObj;
import com.yichang.kaku.request.ShopCartDeleteReq;
import com.yichang.kaku.request.ShopCartEditUpdateReq;
import com.yichang.kaku.request.ShopCartProductsReq;
import com.yichang.kaku.response.ShopCartDeleteResp;
import com.yichang.kaku.response.ShopCartEditUpdateResp;
import com.yichang.kaku.response.ShopCartProductsResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.List;

public class ShopCartEditActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    //titleBar,返回，购物车，标题
    private TextView left, right, title;
    //删除
    private TextView tv_shopcart_edit_del;
    private CheckBox cbx_shopcart_select;
    private CheckBox cbx_shopcart_edit_selected;

    private ListView lv_shopcart_edit_list;
    private LinearLayout ll_shopcart_edit_selectall;
    private List<ShopCartProductObj> editLst;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcartedit);
        init();
    }

    private void init() {


        //        初始化titleBar
        initTitleBar();
//		初始化屏幕下方支付工具栏
        initDeleteBar();

        //初始化购物车列表
        lv_shopcart_edit_list = (ListView) findViewById(R.id.lv_shopcart_edit_list);
        lv_shopcart_edit_list.setOnItemClickListener(this);
//        加载购物车项列表
        getShopCartLst();


    }


    private void initDeleteBar() {
        tv_shopcart_edit_del = (TextView) findViewById(R.id.tv_shopcart_edit_del);
        tv_shopcart_edit_del.setOnClickListener(this);
        ll_shopcart_edit_selectall = (LinearLayout) findViewById(R.id.ll_shopcart_edit_selectall);
        ll_shopcart_edit_selectall.setOnClickListener(this);
        cbx_shopcart_edit_selected = (CheckBox) findViewById(R.id.cbx_shopcart_edit_selected);

        cbx_shopcart_select = (CheckBox) findViewById(R.id.cbx_shopcart_select);

    }

    private void getShopCartLst() {
        editLst = (List<ShopCartProductObj>) getIntent().getSerializableExtra("list");
        if (editLst != null) {
            setData(editLst);
        }else {
            finish();
        }
    }

    private void setData(List<ShopCartProductObj> editLst) {
        if (editLst.size() == 0) {
            setResult(2);
            finish();
        }
        ShopCartEditAdapter adapter = new ShopCartEditAdapter(context, editLst);
        lv_shopcart_edit_list.setAdapter(adapter);
    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("完成");
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
/*返回值为1，说明需要重新加载页面，返回值为0不需要重新加载购物车页面*/
            Intent intent = new Intent();
            if (hasDelete) {
                setResult(1);
            } else {
                setResult(0);
            }

            finish();
        } else if (R.id.tv_right == id) {
            //todo 完成
            if (editLst.size() > 0) {
                updateShopCartItems();
            } else {
                Intent intent = new Intent();
                setResult(1);
                finish();
            }

        } else if (R.id.ll_shopcart_edit_selectall == id) {
            //todo 全选
            selectAllItems();

        } else if (R.id.tv_shopcart_edit_del == id) {
            //todo 删除



            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("是否要删除选中的商品？");
            builder.setNegativeButton("是", new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteShopCartItem();
                    hasDelete = true;
                }
            });

            builder.setPositiveButton("否", new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();

        }

    }

    private void deleteShopCartItem() {
        String strDelId = "";
        for (int i = 0; i < lv_shopcart_edit_list.getChildCount(); i++) {
            View child = lv_shopcart_edit_list.getChildAt(i);
            if (((CheckBox) child.findViewById(R.id.cbx_shopcart_select)).isChecked()) {
                strDelId += editLst.get(i).getId_goods_shopcar() + ",";
            }
        }
        if (strDelId.length() == 0) {
            LogUtil.showShortToast(context, "请选择需要删除的商品");
        } else {
            strDelId = strDelId.substring(0, strDelId.length() - 1);

            runDelete(strDelId);

        }


    }

    //    标识是否修改过数据库
    private boolean hasDelete = false;

    private void runDelete(String strDelId) {
        Utils.NoNet(context);
        showProgressDialog();
        ShopCartDeleteReq req = new ShopCartDeleteReq();
        req.code = "3006";
        req.id_goods_shopcars = strDelId;

        KaKuApiProvider.deleteShopCartItem(req, new BaseCallback<ShopCartDeleteResp>(ShopCartDeleteResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, ShopCartDeleteResp t) {
                if (t != null) {
                    LogUtil.E("deleteShopCartItem res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        refreshListView();
                    }else{
                        if (Constants.RES_TEN.equals(t.res)){
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context,t.res);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });
    }

    /*更新列表*/
    private void refreshListView() {
        editLst.clear();
        Utils.NoNet(context);
        //showProgressDialog();

        ShopCartProductsReq req = new ShopCartProductsReq();
        req.code = "3005";
        req.id_driver = Utils.getIdDriver();

        KaKuApiProvider.getShopCartProductsLst(req, new BaseCallback<ShopCartProductsResp>(ShopCartProductsResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, ShopCartProductsResp t) {

                if (t != null) {
                    LogUtil.E("getShopCartProductsLst res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        editLst.addAll(t.shopcar);
                        setData(editLst);
                    }else{
                        if (Constants.RES_TEN.equals(t.res)){
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context,t.msg);
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

    private boolean isSelectAll = true;

    /*修改所有元素的选中状态*/
    private void selectAllItems() {

        for (int i = 0; i < lv_shopcart_edit_list.getChildCount(); i++) {
            View child = lv_shopcart_edit_list.getChildAt(i);
            ((CheckBox) child.findViewById(R.id.cbx_shopcart_select)).setChecked(isSelectAll);
            cbx_shopcart_edit_selected.setChecked(isSelectAll);
        }

        isSelectAll = !isSelectAll;
    }

    /*
    *同步Item的checkbox和底部全选按钮的状态
    */
    private void checkSelectedState() {
//        设置列表第一个item初始被选状态为false
        boolean firstSelected = false;
        boolean otherSelected;
        for (int i = 0; i < lv_shopcart_edit_list.getChildCount(); i++) {
            View child = lv_shopcart_edit_list.getChildAt(i);
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

    private void updateShopCartItems() {
        String strIdDriver = "";
        String strNum = "";
        for (int i = 0; i < editLst.size(); i++) {
            strIdDriver += editLst.get(i).getId_goods_shopcar() + ",";
            strNum += editLst.get(i).getNum_shopcar() + ",";
        }

        LogUtil.E("strIdDriver" + strIdDriver);
        LogUtil.E("strNum" + strNum);

        Utils.NoNet(context);
        showProgressDialog();
        ShopCartEditUpdateReq req = new ShopCartEditUpdateReq();
        req.code = "3004";
        req.id_driver = Utils.getIdDriver();
        req.id_goods_shopcars = strIdDriver.substring(0, strIdDriver.length() - 1);
        req.num_shopcar = strNum.substring(0, strNum.length() - 1);

        KaKuApiProvider.updateShopCart(req, new BaseCallback<ShopCartEditUpdateResp>(ShopCartEditUpdateResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, ShopCartEditUpdateResp t) {
                if (t != null) {
                    LogUtil.E("updateShopCart res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        // LogUtil.showShortToast(context, t.msg);
                        Intent intent = new Intent();
                        setResult(1);
                        finish();
                    }else{
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Utils.Many()){
            return;
        }
        int parentId = parent.getId();
        if (R.id.lv_shopcart_edit_list == parentId) {
            CheckBox cbx = (CheckBox) view.findViewById(R.id.cbx_shopcart_select);
            cbx.setChecked(!cbx.isChecked());
            checkSelectedState();
        }
    }
}
