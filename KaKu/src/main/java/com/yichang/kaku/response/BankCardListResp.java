package com.yichang.kaku.response;

import com.yichang.kaku.obj.BankCardObj;

import java.io.Serializable;
import java.util.List;

public class BankCardListResp extends BaseResp implements Serializable {
	public List<BankCardObj> banks;
}
