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
        List<IACEntry> dicList = dicService.getDicByType(type);
        return dicList;
    }

    @RequestMapping(value = "getDicText")
    @ResponseBody
    public String getDicText(@RequestParam String type,@RequestParam int nid){
        String text = "";
        IACEntry entry = dicService.getDicText(type,nid);
        if(entry != null){
            text = entry.getValueAsString("NAME");
        }
        return text;
    }
}
