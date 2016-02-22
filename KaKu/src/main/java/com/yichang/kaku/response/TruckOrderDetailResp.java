package com.yichang.kaku.response;

import com.yichang.kaku.obj.ConfirmOrderProductObj;
import com.yichang.kaku.obj.TruckOrderDetailObj;
import com.yichang.kaku.obj.TruckOrderObj;

import java.io.Serializable;
import java.util.List;

public class TruckOrderDetailResp extends BaseResp implements Serializable {
    public List<ConfirmOrderProductObj> shopcar;
    public TruckOrderDetailObj bill;
	
}
