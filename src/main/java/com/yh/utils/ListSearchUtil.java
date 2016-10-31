package com.yh.utils;

import com.yh.model.DataModel;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

/**
 * Created by FQ.CHINA on 2016/10/28.
 */
public class ListSearchUtil {
    private static final String SEARCH_PARAM_NAME = "search";
    public static HashMap<String,Object> getSearchMap(DataModel dataModel){
        String search = dataModel.getValueAsString(SEARCH_PARAM_NAME);
        HashMap<String,Object> params = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(search))
            params.put("SEARCH","%"+search+"%");
        return params;
    }
}
