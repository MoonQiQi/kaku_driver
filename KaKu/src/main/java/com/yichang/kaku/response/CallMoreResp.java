package com.yichang.kaku.response;

import com.yichang.kaku.obj.CallMoreObj;

import java.io.Serializable;
import java.util.List;

public class CallMoreResp extends BaseResp implements Serializable {
    public List<CallMoreObj> duration;
}
