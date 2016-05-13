package com.yichang.kaku.response;

import com.yichang.kaku.obj.IllegalInfo;

import java.io.Serializable;
import java.util.ArrayList;

public class IllegalQueryResp extends BaseResp implements Serializable {

    public ArrayList<IllegalInfo> lists;

}
