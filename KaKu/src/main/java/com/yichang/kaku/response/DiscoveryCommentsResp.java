package com.yichang.kaku.response;

import com.yichang.kaku.obj.DiscoveryCommentsObj;

import java.io.Serializable;
import java.util.List;

public class DiscoveryCommentsResp extends BaseResp implements Serializable {
    public List<DiscoveryCommentsObj> comments;
}
