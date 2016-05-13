package com.yichang.kaku.request;

import java.io.Serializable;

public class TruckOrderListReq extends BaseReq implements Serializable {
    public String state_bill;//车品商城：待付款A，待收货B，待评价E，返修Z，全部传””  保养：A待付款D待服务E待评价
    public String start;
    public String len;
}
