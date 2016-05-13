package com.yichang.kaku.response;

import com.yichang.kaku.obj.BaoYangOrderDetailObj;
import com.yichang.kaku.obj.BigOilObj;

import java.io.Serializable;
import java.util.List;

public class BaoYangOrderDetailResp extends BaseResp implements Serializable {
    public BaoYangOrderDetailObj upkeep_bill;
    public List<BigOilObj> shopcar;
    public String state_bill;
    public String flag_type;
}
