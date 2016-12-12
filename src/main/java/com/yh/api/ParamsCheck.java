package com.yh.api;

import com.yh.utils.AppConstants;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

/**
 * Created by FQ.CHINA on 2016/11/15.
 */
public class ParamsCheck {
    /**
     * 定义要进行验证的参数
     */
    //登录
    private static final String[] LOGIN = {"username","userpass"};

    //推送绑定
    private static final String[] PUSH_BIND = {"userId","clientId"};

    //修改用户密码
    private static final String[] EDIT_USER_PASS = {"userId","userpass"};

    //获取用户信息
    private static final String[] GET_USER_INFO = {"userId"};


    public static boolean checkLogin(HashMap<String, String> params){
        if (params == null || params.size() == 0)
            return false;
        for (String key : LOGIN) {
            String value = params.get(key);
            if (StringUtils.isBlank(value))
                return false;
        }
        String userType = params.get("userType");
        if(StringUtils.isBlank(userType)){
            params.put("userType", AppConstants.USER_TYPE_TEARCH+"");
        }
        return true;
    }

    public static boolean checkPushBind(HashMap<String, String> params) {
        if (params == null || params.size() == 0)
            return false;
        for (String key : PUSH_BIND) {
            String value = params.get(key);
            if (StringUtils.isBlank(value))
                return false;
        }
        return true;
    }

    public static boolean checkEditUserPass(HashMap<String, String> params) {
        if (params == null || params.size() == 0)
            return false;
        for (String key : EDIT_USER_PASS) {
            String value = params.get(key);
            if (StringUtils.isBlank(value))
                return false;
        }
        return true;
    }

    public static boolean checkGetUserInfo(HashMap<String, String> params) {
        if (params == null || params.size() == 0)
            return false;
        for (String key : GET_USER_INFO) {
            String value = params.get(key);
            if (StringUtils.isBlank(value))
                return false;
        }
        return true;
    }

}
