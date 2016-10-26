package com.yh.dic;

import cn.com.iactive.db.IACEntry;
import com.yh.utils.ObjUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
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
    public List<HashMap<String,Object>> getSelectBox(@RequestParam String type){
        List<HashMap<String,Object>> dicList = dicService.getDicByType(type);
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

    @RequestMapping(value = "getDicList")
    @ResponseBody
    public HashMap<Integer,String> getDicList(@RequestParam String type){
        HashMap<Integer,String> dicMap = new HashMap<Integer, String>();
        List<IACEntry> dicList = dicService.getDicListByType(type);
        if(ObjUtils.isNotBlankIACEntryList(dicList)){
            for(IACEntry dic : dicList){
                dicMap.put(dic.getValueAsInt("NID"),dic.getValueAsString("NAME"));
            }
        }
        return dicMap;
    }

}
