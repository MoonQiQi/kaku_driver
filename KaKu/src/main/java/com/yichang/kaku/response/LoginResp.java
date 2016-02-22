package com.yichang.kaku.response;

import com.yichang.kaku.obj.DriveObj;

import java.io.Serializable;

public class LoginResp extends BaseResp implements Serializable {
    public DriveObj driver;
    public String id_car;
    public String flag_show;

}
