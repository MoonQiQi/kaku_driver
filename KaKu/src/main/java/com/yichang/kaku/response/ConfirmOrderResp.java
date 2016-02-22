package com.yichang.kaku.response;

import com.yichang.kaku.obj.ConfirmOrderProductObj;
import com.yichang.kaku.obj.OrderAddressObj;

import java.io.Serializable;
import java.util.List;

public class ConfirmOrderResp extends BaseResp implements Serializable {

    public OrderAddressObj addr;
    public List<ConfirmOrderProductObj> shopcar;
//积分上限
    public String point_limit;
    //商品总价
    public String price_goods;
    //
    public String price_point;
    //实付款
    public String price_bill;
    //可用余额
    public String money_balance;
    //是否设置了支付密码
    public String flag_pay;


}
