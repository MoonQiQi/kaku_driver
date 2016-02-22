package com.yichang.kaku.response;

import com.yichang.kaku.obj.DiscoveryCommentsObj;

import java.io.Serializable;
import java.util.List;

public class DiscoveryDetailResp extends BaseResp implements Serializable {
	public String url;
	public List<DiscoveryCommentsObj> comments;
}
