package com.yichang.kaku.response;

import com.yichang.kaku.obj.TruckOrderObj;

import java.io.Serializable;
import java.util.List;

public class TruckOrderListResp extends BaseResp implements Serializable {
    public List<TruckOrderObj> bills;
	
}
