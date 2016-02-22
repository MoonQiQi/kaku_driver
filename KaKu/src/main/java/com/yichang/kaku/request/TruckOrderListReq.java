package com.yichang.kaku.request;

import java.io.Serializable;

public class TruckOrderListReq extends BaseReq implements Serializable {
    public String id_driver;
    public String state_bill;//待付款A，待收货B，待评价E，返修Z，全部传””
    public String start;
    public String len;
}
