package com.yichang.kaku.response;

import com.yichang.kaku.obj.AddrObj;

import java.io.Serializable;
import java.util.List;

public class AddrResp extends BaseResp implements Serializable {
	public List<AddrObj> addrs;
}
