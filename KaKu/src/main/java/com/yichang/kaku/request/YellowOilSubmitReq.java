package com.yichang.kaku.request;

import java.io.Serializable;

public class YellowOilSubmitReq extends BaseReq implements Serializable {
    public String id_upkeep_bill;
    public String type_pay;
    public String id_driver_coupon;
    public String price_service;
    public String sign;
}
