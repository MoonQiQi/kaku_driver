package com.yichang.kaku.response;

import com.yichang.kaku.obj.CheTieTaskObj;

import java.io.Serializable;
import java.util.List;

public class CheTieTaskListResp extends BaseResp implements Serializable {
    public List<CheTieTaskObj> adverts;
    public String flag_show;
    public String url;
}
