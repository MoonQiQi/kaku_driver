package com.yichang.kaku.response;

import com.yichang.kaku.obj.QuestionAnswerObj;
import com.yichang.kaku.obj.QuestionObj;

import java.io.Serializable;
import java.util.List;

public class QuestionDetailResp extends BaseResp implements Serializable {

    public String read;

    public List<QuestionAnswerObj> list;


    public QuestionObj question;
}
