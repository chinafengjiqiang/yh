package com.yh.api;

import java.util.HashMap;

/**
 * Created by FQ.CHINA on 2016/11/15.
 */
public interface IApi {
    int login(HashMap<String, String> params,HashMap<String, Object> retMap);

    int pushBind(HashMap<String, String> params);

    int editUserPass(HashMap<String,String> params);

    int editUser(HashMap<String,String> params);

    int getUserInfo(HashMap<String,String> params,HashMap<String, Object> retMap);
}
