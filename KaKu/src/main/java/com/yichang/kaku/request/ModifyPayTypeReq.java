package com.yichang.kaku.request;

import java.io.Serializable;

public class ModifyPayTypeReq extends BaseReq implements Serializable {
    public String id_upkeep_bill;
    public String id_driver_coupon;
    public String balance_choose;
    public String price_service;
}
