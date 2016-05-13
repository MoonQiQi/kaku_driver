package com.yichang.kaku.response;

import com.yichang.kaku.obj.ServiceOrderObj;

import java.io.Serializable;
import java.util.List;

public class ServiceOrderListResp extends BaseResp implements Serializable {
    public List<ServiceOrderObj> bills;
    public String state_bill;

}
