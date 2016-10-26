package com.yh.dic;

import cn.com.iactive.db.IACEntry;

import java.util.HashMap;
import java.util.List;

/**
 * Created by FQ.CHINA on 2016/10/21.
 */
public interface IDicService {
    IACEntry getDicText(String type,int nid);

    List<HashMap<String,Object>> getDicByType(String type);

    List<IACEntry> getDicListByType(String type);
}
