package com.yichang.kaku.request;

import java.io.Serializable;

public class LoginReq extends BaseReq implements Serializable {
	public String phone_driver;
    public String id_equipment;
    public String version_equipment;
    public String driver_equipment;
    public String vcode;
    public String var_lon;
    public String var_lat;
}
