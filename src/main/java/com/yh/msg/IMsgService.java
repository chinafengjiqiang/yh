package com.yh.msg;

import cn.com.iactive.db.IACEntry;
import com.yh.model.DataModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by FQ.CHINA on 2016/11/3.
 */
public interface IMsgService {
    HashMap<String,Object> getMsgList(DataModel dataModel);

    boolean editMsg(HashMap<String,String> msg);

    boolean processSend(HashMap<String,String> msg);

    List<IACEntry> getMsgRuleList(int msgId);

    IACEntry getMsgById(int msgId);

    List<String> getPushClientByUsers(List<Integer> userIdList);
}
