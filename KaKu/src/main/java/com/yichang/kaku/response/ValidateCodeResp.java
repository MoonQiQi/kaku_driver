package com.yichang.kaku.response;

import java.io.Serializable;

public class ValidateCodeResp extends BaseResp implements Serializable {
    /*res：   0：校验成功
             1：密令有误
             2：您已抽过奖
      url：   抽奖页面url*/

    public String url;

}
