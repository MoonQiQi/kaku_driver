package com.yichang.kaku.response;

import com.yichang.kaku.obj.CheTieOrderObj;

import java.io.Serializable;
import java.util.List;

public class CheTieOrderResp extends BaseResp implements Serializable {
	public List<CheTieOrderObj> bills;
}
