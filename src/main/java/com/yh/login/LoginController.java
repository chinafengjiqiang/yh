package com.yh.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(value = "login")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("login/login");
    }



    @RequestMapping(value = "main")
    public ModelAndView main(HttpServletRequest request) {
        return new ModelAndView("login/main");
    }
}
