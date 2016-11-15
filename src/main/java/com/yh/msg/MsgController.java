package com.yh.msg;

import cn.com.iactive.db.IACEntry;
import com.yh.model.DataModel;
import com.yh.model.RetVO;
import com.yh.utils.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created by FQ.CHINA on 2016/11/3.
 */

@Controller
@RequestMapping("/manage/msg")
public class MsgController {

    @Autowired
    private IMsgService msgService;

    @RequestMapping
    public String msg(){
        return "msg/list";
    }

    @RequestMapping(value = "getMsgList")
    @ResponseBody
    public HashMap<String,Object> getMsgList(DataModel dataModel) {
        return msgService.getMsgList(dataModel);
    }

    @RequestMapping(value = "saveMsg")
    @ResponseBody
    public RetVO saveMsg(HttpServletRequest request){
        RetVO ret = new RetVO();
        boolean res = false;
        try {
            HashMap<String,String> msg = ParamUtils.getParameters(request);
            res = msgService.editMsg(msg);
            ret.setSuccess(res);
        }catch (Exception e){
            e.printStackTrace();
            ret.setSuccess(false);
        }
        return ret;
    }

    @RequestMapping(value = "saveSend")
    @ResponseBody
    public RetVO saveSend(HttpServletRequest request){
        RetVO ret = new RetVO();
        boolean res = false;
        try {
            HashMap<String,String> sendInfo = ParamUtils.getParameters(request);
            res = msgService.processSend(sendInfo);
            ret.setSuccess(res);
        }catch (Exception e){
            e.printStackTrace();
            ret.setSuccess(false);
        }
        return ret;
    }

    @RequestMapping(value = "getMsgRuleJson")
    @ResponseBody
    public List<IACEntry> getMsgRuleJson(@RequestParam int msgId){
        return msgService.getMsgRuleList(msgId);
    }

}
