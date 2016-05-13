package com.yichang.kaku.response;

import com.yichang.kaku.obj.CallListObj;

import java.io.Serializable;
import java.util.List;

public class CallListResp extends BaseResp implements Serializable {
    public String duration_now;
    public List<CallListObj> duration;
}
