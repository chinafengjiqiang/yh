package com.yh.msg;

import com.yh.model.DataModel;

import java.util.HashMap;

/**
 * Created by FQ.CHINA on 2016/11/3.
 */
public interface IMsgService {
    HashMap<String,Object> getMsgList(DataModel dataModel);

    boolean editMsg(HashMap<String,String> msg);
}
