package com.yichang.kaku.request;

import java.io.Serializable;

public class NewAddrReq extends BaseReq implements Serializable {
    public String id_driver;
    public String name_addr;
    public String phone_addr;
    public String addr;
    public String flag_default;
    public String id_addr;
    public String id_area;
}
