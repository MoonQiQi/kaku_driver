package com.yichang.kaku.response;

import com.yichang.kaku.obj.AddrObj;
import com.yichang.kaku.obj.ConfirmOrderProductObj;

import java.io.Serializable;
import java.util.List;

public class ConfirmOrderResp extends BaseResp implements Serializable {

    public AddrObj addr;
    public List<ConfirmOrderProductObj> shopcar;
    public String point_limit;
    public String price_goods;
    public String price_point;
    public String price_bill;
    public String point_used;
    public String price_balance;
    public String price_transport;
    public String price_coupon;
    public String remark_coupon;
    public String money_balance;
    public String flag_pay;
    public String flag_buy;
    public String flag_buy_chose;
    public String flag_checkstand;
    public String id_driver_coupon;
    public String balance_choose;
    public String point_choose;

}
