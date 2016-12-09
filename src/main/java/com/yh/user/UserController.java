package com.yh.user;

import cn.com.iactive.db.IACEntry;
import com.yh.model.DataModel;
import com.yh.model.LoginInfo;
import com.yh.model.RetVO;
import com.yh.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by FQ.CHINA on 2016/10/12.
 */

@Controller
@RequestMapping("/manage/user")
public class UserController {

    //默认密码
    private static String TEARCH_PASS = "2016";
    static {
        TEARCH_PASS = ResourceBundle.getValue("tearch.password");
    }

    @Autowired
    private IUserService userService;

    @RequestMapping
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("user/user");
    }


    @RequestMapping(value = "tearch")
    public String tearch(){
        return "user/tearchMainList";
    }

    @RequestMapping(value = "getTearchList")
    @ResponseBody
    public HashMap<String,Object> getTearchList(DataModel dataModel) {
        return userService.getUserList(dataModel);
    }

    @RequestMapping(value = "exportTearchTmp")
    public void exportTearchTmp(HttpServletResponse response){
        HSSFWorkbook workbook = userService.exportTemplate();
        try {
                String mimetype = "application/x-msdownload";
                response.setContentType(mimetype);
                String downFileName = "user.xls";
                String inlineType = "attachment"; // 是否内联附件
                response.setHeader("Content-Disposition", inlineType
                        + ";filename=\"" + downFileName + "\"");
                OutputStream out=response.getOutputStream();
                workbook.write(out);
                out.flush();
                out.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "saveTearch")
    @ResponseBody
    public RetVO saveTearch(HttpServletRequest request){
        RetVO ret = new RetVO();
        boolean res = false;
        try {
            HashMap<String,String> user = ParamUtils.getParameters(request);
            user.put("USER_TYPE", AppConstants.USER_TYPE_TEARCH+"");
            user.put("PASSWORD", DES.encrypt(TEARCH_PASS));
            if(StringUtils.isBlank(user.get("ID"))){
                boolean isExist = userService.isUserExist(user.get("USERNAME"));
                if(isExist){
                    ret.setSuccess(false);
                    ret.setMsg(SpringUtil.getMessage("user.isExist"));
                    return ret;
                }
                res = userService.saveUser(user);
            }else{
                res = userService.updateUser(user);
            }
            ret.setSuccess(res);
        }catch (Exception e){
            e.printStackTrace();
            ret.setSuccess(false);
        }
        return ret;
    }


    @RequestMapping(value = "importTeacher")
    @ResponseBody
    public RetVO importTeacher(@RequestParam MultipartFile file,HttpServletRequest request){
        RetVO ret = new RetVO();
        try {
            String extName = FileUtils.getFileExt(file.getOriginalFilename());
            if (!"xls".equals(extName)) {//判断文件格式
                ret.setSuccess(false);
                ret.setMsg(SpringUtil.getMessage("file.format.error"));
                return ret;
            }
            String orgId = request.getParameter("PK_ORG");
            String deptId = request.getParameter("PK_DEPT");
            List<HashMap<String,String>> tList = readTearchXls(file,orgId,deptId);
            for (int i = 0; i < tList.size(); i++) {
                HashMap<String,String> user = tList.get(i);
                boolean isExist = userService.isUserExist(user.get("USERNAME"));
                if(isExist){
                    //TODO 处理已经存在的用户名
                    continue;
                }else{
                    boolean res = userService.saveUser(user);
                    //TODO 处理已经成功(失败)的用户
                }
            }
            ret.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ret.setSuccess(false);
        }
        return ret;
    }

    private List<HashMap<String,String>> readTearchXls(MultipartFile file,String orgId,String deptId)throws Exception{
        List<HashMap<String,String>>  tList = new ArrayList<HashMap<String, String>>();
        ImportExecl poi = new ImportExecl();
        List<List<String>> list = poi.read(file.getInputStream(), true);
        if (list != null) {
            HashMap<String,String> user = null;
            for (int i = 1; i < list.size(); i++) {
                user = new HashMap<String, String>();
                String username = list.get(i).get(0);
                if(StringUtils.isBlank(username))
                    continue;
                user.put("USERNAME",username);
                String truename = list.get(i).get(1);
                if(StringUtils.isBlank(truename))
                    truename = username;
                user.put("TRUENAME",truename);
                String roleText = list.get(i).get(2);
                String role = "0";
                if(StringUtils.isNotBlank(roleText)){
                    role = roleText.split("-")[0];
                }
                user.put("ROLE",role);
                user.put("MPHONE",list.get(i).get(3));
                user.put("USER_TYPE", AppConstants.USER_TYPE_TEARCH+"");
                user.put("PASSWORD", DES.encrypt(TEARCH_PASS));
                user.put("PK_ORG",orgId);
                user.put("PK_DEPT",deptId);
                tList.add(user);
            }
        }
        return tList;
    }

    @RequestMapping(value = "goTearchList")
    public String goTearchList(HttpServletRequest request){
        request.setAttribute("deptId",ParamUtils.getIntParameter(request,"deptId",-1));
        request.setAttribute("orgId",ParamUtils.getIntParameter(request,"orgId",0));
        return "user/list";
    }

    @RequestMapping(value = "setTearchGroup")
    @ResponseBody
    public RetVO setTearchGroup(HttpServletRequest request){
        RetVO ret = new RetVO();
        boolean res = userService.saveUserGroup(ParamUtils.getParameter(request,"gIds",""),
                ParamUtils.getParameter(request,"tIds",""));
        ret.setSuccess(res);
        return ret;
    }

    @RequestMapping(value = "getGroupUserJson")
    @ResponseBody
    public List<IACEntry> getGroupUserJson(HttpServletRequest request){
        return userService.getGroupUser(ParamUtils.getIntParameter(request,"gId",0));
    }

    @RequestMapping(value = "deleteUserGroup")
    @ResponseBody
    public RetVO deleteUserGroup(HttpServletRequest request){
        RetVO ret = new RetVO();
        boolean res = userService.deleteUserGroup(ParamUtils.getIntParameter(request,"uId",0),
                ParamUtils.getIntParameter(request,"gId",0));
        ret.setSuccess(res);
        return ret;
    }

    @RequestMapping(value = "getDeptUserJson")
    @ResponseBody
    public List<IACEntry> getDeptUserJson(HttpServletRequest request){
        return userService.getDeptUser(ParamUtils.getIntParameter(request,"orgId",0),
                ParamUtils.getIntParameter(request,"deptId",0),ParamUtils.getIntParameter(request,"type",0));
    }

    @RequestMapping(value = "editPass")
    public String editPass(){
        return "user/editPass";
    }

    @RequestMapping(value = "doEditPass")
    @ResponseBody
    public int doEditPass(HttpServletRequest request){
        String pass = ParamUtils.getParameter(request,"NEW_PASS","");
        LoginInfo loginInfo = WebUtil.getManagerLoginInfo(request);
        return userService.editUserPass(loginInfo.getUserId(),pass);
    }
}


