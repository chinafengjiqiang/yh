package com.yh.delete;


import com.yh.model.DelModel;
import com.yh.model.RetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/del")
public class DelController {

    @Autowired
    private IDelService delService;

    @RequestMapping
    @ResponseBody
    public RetVO delLabel(DelModel del){
        return delService.del(del);
    }
}
