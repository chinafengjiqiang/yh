package com.yh.dic;

import cn.com.iactive.db.IACEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by FQ.CHINA on 2016/10/24.
 */

@Controller
@RequestMapping("/dic")
public class DicController {

    @Autowired
    private IDicService dicService;

    @RequestMapping
    @ResponseBody
    public List<IACEntry> getSelectBox(@RequestParam String type){
        return dicService.getDicByType(type);
    }
}
