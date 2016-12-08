package com.yh.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by FQ.CHINA on 2016/12/8.
 */
@Controller
@RequestMapping("/manage/main")
public class MainController {
    @RequestMapping
    public ModelAndView main(HttpServletRequest request) {
        return new ModelAndView("login/main");
    }
}

