package com.yh.dic;

import cn.com.iactive.db.IACEntry;

import java.util.List;

/**
 * Created by FQ.CHINA on 2016/10/21.
 */
public interface IDicService {
    List<IACEntry> getDicByType(String type);
}
