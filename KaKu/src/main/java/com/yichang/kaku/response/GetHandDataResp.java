package com.yichang.kaku.response;

import com.yichang.kaku.obj.HandCodeObj;

import java.io.Serializable;
import java.util.List;

public class GetHandDataResp extends BaseResp implements Serializable {
    public String flag_code;
    public List<HandCodeObj> suggestions;
}
