package com.yh.msg;

import cn.com.iactive.db.IACDB;
import cn.com.iactive.db.IACEntry;
import com.yh.dept.IDeptService;
import com.yh.dic.IDicService;
import com.yh.model.DataModel;
import com.yh.push.Global;
import com.yh.push.GtPushService;
import com.yh.push.IPush;
import com.yh.user.IUserService;
import com.yh.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by FQ.CHINA on 2016/11/3.
 */
@Service
public class MsgService implements IMsgService{

    @Autowired
    private IACDB<HashMap<String,Object>> iacDB;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IDicService dicService;

    public HashMap<String, Object> getMsgList(DataModel dataModel) {
        HashMap<String,Object> params = ListSearchUtil.getSearchMap(dataModel);
        return  iacDB.getDataTables("getMsgList",dataModel.getDataTablesModel(),params);
    }

    public boolean editMsg(HashMap<String, String> msg) {
        msg.put("CREATE_TIME", DateUtils.dateTime2String(new Date()));
        if(StringUtils.isBlank(msg.get(DBConstants.TBL_MSG_PK))){
            return iacDB.insertDynamic(DBConstants.TBL_MSG_NAME,msg);
        }else {
            return iacDB.updateDynamic(DBConstants.TBL_MSG_NAME,DBConstants.TBL_MSG_PK,msg);
        }
    }

    public boolean processSend(HashMap<String, String> msg) {
        try {

            //存放保存成功的发送权限
            List<HashMap<String,Object>> ruleList = new ArrayList<HashMap<String, Object>>();

            //保存通知发送记录
            saveMsgSendInfo(msg,ruleList);

            //进行发送
            sendPush(ruleList);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private void saveMsgSendInfo(HashMap<String,String> msg,List<HashMap<String,Object>> ruleList)throws Exception{
        String msgIds = msg.get("mIds");
        if(StringUtils.isNotBlank(msgIds)){
            String[] msgIdArr = msgIds.split(",");
            if(msgIdArr.length > 0){
                for (String msgId : msgIdArr){
                    if(StringUtils.isNotBlank(msgId)){
                        String srcType = msg.get("REC_TYPE");
                        String sendType = msg.get("SEND_TYPE");
                        String srcIds = msg.get("REC_OBJ");
                        String srcRangStr = msg.get("SRC_RANGE");
                        int srcRange = 0;
                        if(StringUtils.isNotBlank(srcRangStr)){
                            srcRange = Integer.parseInt(srcRangStr);
                        }
                        if(StringUtils.isNotBlank(srcIds)){
                            String[] srcIdArr = srcIds.split(",");
                            if(srcIdArr.length > 0){
                                HashMap<String,Object> rule = null;
                                for(String srcId : srcIdArr){
                                    if(StringUtils.isNotBlank(srcId)){
                                        rule = new HashMap<String, Object>();
                                        rule.put("SRC_TYPE", NumUtils.String2Int(srcType));
                                        rule.put("SRC",NumUtils.String2Int(srcId));
                                        rule.put("SRC_RANGE",srcRange);
                                        rule.put("MODE",AppConstants.MSG_RULE_MODE_ALL);
                                        rule.put("OBJ_TYPE",AppConstants.MSG_OBJECT_TYPE_MSG);
                                        rule.put("OBJ_ID",NumUtils.String2Int(msgId));
                                        rule.put("OBJ_RANGE",NumUtils.String2Int(sendType));
                                        rule.put("CREATE_TIME",new Date());
                                        boolean ret = iacDB.insertDynamic(DBConstants.TBL_MSG_RULE_NAME,rule);
                                        if(ret){
                                            ruleList.add(rule);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void sendPush(List<HashMap<String,Object>> ruleList)throws Exception{
        Map<Integer,IACEntry> msgMap = new HashMap<Integer,IACEntry>();
        for (HashMap<String, Object> rule : ruleList) {
            int msgId = (Integer)rule.get("OBJ_ID");
            int msgRange = (Integer)rule.get("OBJ_RANGE");

            //加载消息内容
            IACEntry msg = msgMap.get(msgId);
            if (ObjUtils.isBlankIACEntry(msg)) {
                msg = getMsgById(msgId);
                if (ObjUtils.isNotBlankIACEntry(msg)) {
                    msg.getIacMap().put("MSG_RANGE",msgRange);
                    msgMap.put(msgId,msg);
                }
            }
            //处理
            if (ObjUtils.isNotBlankIACEntry(msg)) {
                List<Integer> userIdList = new ArrayList<Integer>();
                this.getRuleUser(rule,userIdList);
                if(userIdList.size() > 0){
                    String title = msg.getValueAsString("TITLE");
                    String content = msg.getValueAsString("CONTENT");
                    String transmissionContent = AppConstants.PUSH_CMD_MESSAGE+AppConstants.PUSH_SPLIT+title+AppConstants.PUSH_SPLIT+content;
                    List<String> clientIdList = getPushClientByUsers(userIdList);
                    if (clientIdList != null && clientIdList.size() > 0) {
                        IPush push = new GtPushService();
                        boolean ret = push.pushToList(title,content,transmissionContent,clientIdList, Global.TMPLT_TRANSMISSION);
                    }
                }
            }
        }
    }

    public List<IACEntry> getMsgRuleList(int msgId) {
        HashMap<String,Object> params = ObjUtils.getObjMap();
        params.put("OBJ_TYPE",AppConstants.MSG_OBJECT_TYPE_MSG);
        params.put("OBJ_ID",msgId);
        List<IACEntry> ruleList = iacDB.getIACEntryList("getMsgRuleList",params);
        if(ObjUtils.isNotBlankIACEntryList(ruleList)){
            for (IACEntry rule : ruleList) {
                initRuleText(rule);
            }
        }
        return ruleList;
    }

    private IACEntry initRuleText(IACEntry rule){
        int srcType = rule.getValueAsInt("SRC_TYPE");
        int src = rule.getValueAsInt("SRC");
        int srcRange = rule.getValueAsInt("SRC_RANGE");
        int objRange = rule.getValueAsInt("OBJ_RANGE");
        IACEntry obj = null;
        String srcName = "";
        IACEntry srcTypeDic = dicService.getDicText("REC_MSG_TYPE",srcType);
        rule.getIacMap().put("SRC_TYPE_NAME",srcTypeDic.getValueAsString("NAME"));
        IACEntry objRangeDic = dicService.getDicText("MSG_SEND_TYPE",objRange);
        rule.getIacMap().put("OBJ_RANGE_NAME",objRangeDic.getValueAsString("NAME"));
        switch (srcType){
            case AppConstants.MSG_SRC_TYPE_USER:
                obj = userService.getUserById(src);
                if(obj != null){
                    srcName = obj.getValueAsString("TRUENAME");
                }
                break;
            case AppConstants.MSG_SRC_TYPE_ROLE:
                obj = dicService.getDicText("TEARCH_ROLE",src);
                if(obj != null){
                    IACEntry dept = deptService.getDeptById(srcRange);
                    srcName = obj.getValueAsString("NAME")+"(";
                    srcName += dept.getValueAsString("NAME")+")";
                }
                break;
            case AppConstants.MSG_SRC_TYPE_DEPT:
                obj = deptService.getDeptById(src);
                if(obj != null){
                    srcName = obj.getValueAsString("NAME");
                }
                break;
            case AppConstants.MSG_SRC_TYPE_GROUP:
                obj = deptService.getGroupById(src);
                if(obj != null){
                    srcName = obj.getValueAsString("NAME");
                }
                break;
            case AppConstants.MSG_SRC_TYPE_ORG:
                obj = deptService.getOrgById(src);
                if(obj != null){
                    srcName = obj.getValueAsString("NAME");
                }
                break;
        }
        rule.getIacMap().put("SRC_NAME",srcName);
        return rule;
    }

    public IACEntry getMsgById(int msgId) {
        return iacDB.getSelectOneIACEntry(DBConstants.TBL_MSG_NAME,msgId);
    }

    private void getRuleUser(HashMap<String,Object> rule, List<Integer> userIdList){
        int srcType = (Integer)rule.get("SRC_TYPE");
        int src = (Integer)rule.get("SRC");
        int srcRange = (Integer)rule.get("SRC_RANGE");
        switch (srcType){
            case AppConstants.MSG_SRC_TYPE_USER:
                IACEntry user = userService.getUserById(src);
                addUser(user,userIdList);
                break;
            case AppConstants.MSG_SRC_TYPE_ROLE:
                List<IACEntry> userList = userService.getDeptRoleUser(srcRange,src);
                addUser(userList,userIdList);
                break;
            case AppConstants.MSG_SRC_TYPE_DEPT:
                List<IACEntry> userdList = userService.getDeptUser(src,AppConstants.USER_TYPE_TEARCH);
                addUser(userdList,userIdList);
                break;
            case AppConstants.MSG_SRC_TYPE_GROUP:
                List<IACEntry> usergList = userService.getGroupUser(src);
                addUser(usergList,userIdList);
                break;
            case AppConstants.MSG_SRC_TYPE_ORG:
                List<IACEntry> useroList = userService.getOrgUser(src,AppConstants.USER_TYPE_TEARCH);
                addUser(useroList,userIdList);
                break;
        }
    }

    private void addUser(IACEntry user,List<Integer> userIdList){
        if (ObjUtils.isNotBlankIACEntry(user)) {
            userIdList.add(user.getValueAsInt("ID"));
        }
    }

    private void addUser(List<IACEntry> userList,List<Integer> userIdList){
        if (ObjUtils.isNotBlankIACEntryList(userList)) {
            for(IACEntry user : userList){
                addUser(user,userIdList);
            }
        }
    }

    public List<String> getPushClientByUsers(List<Integer> userIdList) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("userIdList",userIdList);
        List<HashMap<String,Object>> pushList = iacDB.getList("getPushClientByUsers",params);
        if(pushList != null && pushList.size() > 0){
            List<String> clientIdList = new ArrayList<String>(pushList.size());
            for (HashMap<String,Object> push : pushList){
                clientIdList.add((String)push.get("CLIENT_ID"));
            }
            return clientIdList;
        }
        return null;
    }
}
