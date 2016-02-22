package com.yichang.kaku.response;

import com.yichang.kaku.obj.Shops_mapObj;

import java.io.Serializable;
import java.util.List;

public class MapFWZResp extends BaseResp implements Serializable {
	public String flag_type;
    public List<Shops_mapObj> shops;
}
