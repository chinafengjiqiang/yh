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
    private static final String[] LOGIN = {"username","userpass"};

    public static boolean checLogin(HashMap<String, String> params){
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
}
