package com.yichang.kaku.response;

import com.yichang.kaku.obj.DiscoveryItemObj;

import java.io.Serializable;

public class DiscoveryDetailResp extends BaseResp implements Serializable {
    public String url;
    public DiscoveryItemObj news;
}
