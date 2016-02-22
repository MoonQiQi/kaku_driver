package com.yichang.kaku.response;

import java.io.Serializable;

public class CommitOrderResp extends BaseResp implements Serializable {
	public String no_order;
	public String appid;
	public String noncestr;
	public String package0;
	public String partnerid;
	public String prepay_id;
	public String timestamp;
	public String sign;

	@Override
	public String toString() {
		return "CommitOrderResp{" +
				"no_order='" + no_order + '\'' +
				", appid='" + appid + '\'' +
				", noncestr='" + noncestr + '\'' +
				", package0='" + package0 + '\'' +
				", partnerid='" + partnerid + '\'' +
				", prepay_id='" + prepay_id + '\'' +
				", timestamp='" + timestamp + '\'' +
				", sign='" + sign + '\'' +
				'}';
	}
}
