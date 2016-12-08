package com.yh.login;

import com.yh.api.ErrorsFactory;
import com.yh.api.IApi;
import com.yh.user.IUserService;
import com.yh.utils.AppConstants;
import com.yh.utils.ParamUtils;
import com.yh.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private IApi apiService;

    @RequestMapping(value = "login")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("login/login");
    }


    @RequestMapping(value = "verify")
    @ResponseBody
    public int verify(HttpServletRequest request){
        HashMap<String, Object> retMap = new HashMap<String, Object>();
        HashMap<String,String> params = ParamUtils.getParameters(request);
        params.put("userType", AppConstants.USER_TYPE_ADMIN+"");
        int ret = apiService.login(params,retMap);
        if(ret == ErrorsFactory.Request_Success){
            //设置session
        }
        return ret;
    }
}
