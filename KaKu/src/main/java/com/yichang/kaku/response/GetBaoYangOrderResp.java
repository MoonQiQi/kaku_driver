package com.yichang.kaku.response;

import com.yichang.kaku.obj.AddrObj;
import com.yichang.kaku.obj.BigOilObj;

import java.io.Serializable;
import java.util.List;

public class GetBaoYangOrderResp extends BaseResp implements Serializable {
    public AddrObj addr;
    public List<BigOilObj> items;
    public String flag_pay;
    public String price_goods;
    public String price_balance;
    public String price_bill;
    public String price_activity;
    public String price_coupon;
    public String money_balance;
    public String price_transport;
    public String num_total;
    public String id_shop;
    public String id_driver_coupon;
    public String remark_coupon;
}
