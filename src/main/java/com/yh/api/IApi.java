package com.yh.api;

import java.util.HashMap;

/**
 * Created by FQ.CHINA on 2016/11/15.
 */
public interface IApi {
    int login(HashMap<String, String> params,HashMap<String, Object> retMap);

    int pushBind(HashMap<String, String> params);
}
