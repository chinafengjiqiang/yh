package com.yh.select;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by FQ.CHINA on 2016/11/9.
 */
@Controller
@RequestMapping("/select")
public class SelectController {

    @RequestMapping
    public String select(@RequestParam String ret){
        return "select/"+ret;
    }
}
