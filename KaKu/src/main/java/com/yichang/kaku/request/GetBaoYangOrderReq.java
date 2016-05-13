package com.yichang.kaku.request;

import java.io.Serializable;

public class GetBaoYangOrderReq extends BaseReq implements Serializable {
    public String id_shop;
    public String balance_choose;
    public String id_items;
    public String num_items;
    public String id_driver_coupon;
    public String flag_activity;
}
