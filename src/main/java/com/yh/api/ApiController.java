package com.yh.api;

import com.yh.utils.NumUtils;
import com.yh.utils.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by sara on 16/11/15.
 */
@Controller
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private IApi apiService;

    @RequestMapping(value = "login")
    @ResponseBody
    public HashMap<String,Object> login(HttpServletRequest request){
        HashMap<String,String> params = ParamUtils.getDecoderParameters(request);
        HashMap<String, Object> retMap = new HashMap<String, Object>();
        int ret = apiService.login(params,retMap);
        retMap.put("ret",ret);
        return retMap;
    }


    @RequestMapping(value = "pushBind")
    @ResponseBody
    public HashMap<String,Object> pushBind(HttpServletRequest request){
        HashMap<String,String> params = ParamUtils.getDecoderParameters(request);
        HashMap<String, Object> retMap = new HashMap<String, Object>();
        int ret = apiService.pushBind(params);
        retMap.put("ret",ret);
        return retMap;
    }

}
