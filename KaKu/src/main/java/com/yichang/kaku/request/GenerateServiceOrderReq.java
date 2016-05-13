package com.yichang.kaku.request;

import java.io.Serializable;

public class GenerateServiceOrderReq extends BaseReq implements Serializable {
    public String time_order;
    public String price_balance;
    public String id_driver_coupon;
    public String flag_activity;
    public String id_shop;
    public String sign;

}
