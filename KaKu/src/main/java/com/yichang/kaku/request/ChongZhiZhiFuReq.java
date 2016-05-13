package com.yichang.kaku.request;

import java.io.Serializable;

public class ChongZhiZhiFuReq extends BaseReq implements Serializable {
    public String phone;
    public String type_goods;
    public String price_goods;
    public String price_balance;
    public String price_bill;
    public String sign;
}
