package com.yh.api;

import cn.com.iactive.db.IACEntry;
import com.yh.user.IUserService;
import com.yh.utils.NumUtils;
import com.yh.utils.ObjUtils;
import com.yh.utils.ParamUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.management.HotspotMemoryMBean;

import java.util.HashMap;

/**
 * Created by FQ.CHINA on 2016/11/15.
 */

@Service
public class ApiService implements IApi{

    @Autowired
    private IUserService userService;
    public int login(HashMap<String,String> params,HashMap<String, Object> retMap) {
        try{
            if (!ParamsCheck.checkLogin(params)) {
                return ErrorsFactory.Request_Params_ERROR;
            }
            String username = params.get("username");
            String userpass = params.get("userpass");
            int userType = NumUtils.String2Int(params.get("userType"));
            IACEntry user = userService.getUserByUsername(username);
            if (user == null) {//用户不存在
                return ErrorsFactory.OBJECT_NOT_EXIST;
            }
            int userId = user.getValueAsInt("ID");
            if (userId <= 0) {//用户不存在
                return ErrorsFactory.OBJECT_NOT_EXIST;
            }
            String truename = user.getValueAsString("TRUENAME");
            String password = user.getValueAsString("PASSWORD");
            int type = user.getValueAsInt("USER_TYPE");
            if (!userpass.equals(password)) {//
                return ErrorsFactory.ERROR_CODE_A;//用户密码错误
            }
            if (userType != type) {
                return ErrorsFactory.ERROR_CODE_B;//此用户类型无权限
            }
            int org = user.getValueAsInt("PK_ORG");
            int dept = user.getValueAsInt("PK_DEPT");
            int role = user.getValueAsInt("ROLE");
            String mphone = user.getValueAsString("MPHONE");
            retMap.put("userId",userId);
            retMap.put("username",username);
            retMap.put("userpass",password);
            retMap.put("truename",truename);
            retMap.put("userType",userType);
            retMap.put("userOrg",org);
            retMap.put("userDept",dept);
            retMap.put("userRole",role);
            retMap.put("mphone",mphone);
            return ErrorsFactory.Request_Success;
        }catch (Exception e){
            e.printStackTrace();
            return ErrorsFactory.Server_Exception;
        }
    }

    public int pushBind(HashMap<String, String> params) {
       try {
           if (!ParamsCheck.checkPushBind(params)) {
               return ErrorsFactory.Request_Params_ERROR;
           }
           HashMap<String,Object> push = ObjUtils.getObjMap();
           int userId = NumUtils.String2Int(params.get("userId"));
           String clientId = params.get("clientId");
           IACEntry bind = userService.getUserPushBind(userId);
           if(ObjUtils.isNotBlankIACEntry(bind)){
               String clientIde = bind.getValueAsString("CLIENT_ID");
               if(clientId.equals(clientIde))
                    return ErrorsFactory.Request_Success;
           }
           push.put("USER_ID",userId);
           push.put("CLIENT_ID",clientId);
           push.put("ORG_ID",NumUtils.String2Int(params.get("orgId")));
           push.put("DEPT_ID",NumUtils.String2Int(params.get("deptId")));
           boolean res = userService.savePushBind(push);
           return res?ErrorsFactory.Request_Success:ErrorsFactory.Request_Fail;
       }catch (Exception e){
            e.printStackTrace();
            return ErrorsFactory.Server_Exception;
       }
    }

    public int editUserPass(HashMap<String, String> params) {
        try {
            if (!ParamsCheck.checkEditUserPass(params)) {
                return ErrorsFactory.Request_Params_ERROR;
            }

            String newPass = params.get("userpass");
            boolean isValid = ParamUtils.isValidPass(newPass);
            if (!isValid) {
                return ErrorsFactory.ERROR_CODE_A;
            }
            int userId = NumUtils.String2Int(params.get("userId"));
            IACEntry user = userService.getUserById(userId);
            if (user == null || user.getValueAsInt("ID") == 0) {//用户不存在
                return ErrorsFactory.OBJECT_NOT_EXIST;
            }
            params.clear();
            params.put("ID",userId+"");
            params.put("PASSWORD",newPass);
            boolean ret = userService.updateUser(params);
            if(ret)
                return ErrorsFactory.Request_Success;
            return ErrorsFactory.Request_Fail;
        }catch (Exception e){
            e.printStackTrace();
            return ErrorsFactory.Server_Exception;
        }
    }

    public int editUser(HashMap<String, String> params) {
        try {
            int userId = NumUtils.String2Int(params.get("userId"));
            HashMap<String,String> user = new HashMap<String, String>();
            user.put("ID",userId+"");
            String mphone = params.get("mphone");
            if(StringUtils.isNotBlank(mphone)){
                user.put("MPHONE",mphone);
            }
            boolean ret = userService.updateUser(user);
            if(ret)
                return ErrorsFactory.Request_Success;
            return ErrorsFactory.Request_Fail;
        }catch (Exception e){
            e.printStackTrace();
            return ErrorsFactory.Server_Exception;
        }
    }

    public int getUserInfo(HashMap<String, String> params, HashMap<String, Object> retMap) {
        try {
            if (!ParamsCheck.checkGetUserInfo(params)) {
                return ErrorsFactory.Request_Params_ERROR;
            }
            int userId = NumUtils.String2Int(params.get("userId"));
            IACEntry user = userService.getUserById(userId);
            if(user != null){
                retMap.put("userId",user.getValueAsInt("ID"));
                retMap.put("usernane",user.getValueAsString("USERNAME"));
                retMap.put("truename",user.getValueAsString("TRUENAME"));
                retMap.put("userType",user.getValueAsInt("USER_TYPE"));
                retMap.put("userOrg",user.getValueAsInt("PK_ORG"));
                retMap.put("userDept",user.getValueAsInt("PK_DEPT"));
                retMap.put("userRole",user.getValueAsInt("ROLE"));
                retMap.put("mphone",user.getValueAsString("MPHONE"));
            }
            return ErrorsFactory.Request_Success;
        }catch (Exception e){
            e.printStackTrace();
            return ErrorsFactory.Server_Exception;
        }
    }
}
