package com.yh.dept;

import cn.com.iactive.db.IACEntry;
import com.yh.model.DataModel;
import com.yh.model.RetVO;
import com.yh.model.TreeVO;
import com.yh.utils.AppConstants;
import com.yh.utils.ParamUtils;
import com.yh.utils.SpringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created by FQ.CHINA on 2016/10/28.
 */

@Controller
@RequestMapping("/manage/dept")
public class DeptController {

    @Autowired
    private IDeptService deptService;

    @RequestMapping(value = "org")
    public String org(){
        return "dept/orgList";
    }

    @RequestMapping(value = "getOrgList")
    @ResponseBody
    public HashMap<String,Object> getOrgList(DataModel dataModel) {
        return deptService.getOrgList(dataModel);
    }

    @RequestMapping(value = "saveOrg")
    @ResponseBody
    public RetVO saveOrg(HttpServletRequest request){
        RetVO ret = new RetVO();
        boolean res = false;
        try {
            HashMap<String,String> org = ParamUtils.getParameters(request);
            res = deptService.editOrg(org);
            ret.setSuccess(res);
        }catch (Exception e){
            e.printStackTrace();
            ret.setSuccess(false);
        }
        return ret;
    }

    @RequestMapping(value = "dept")
    public String dept(){
        return "dept/deptMainList";
    }

    @RequestMapping(value = "goDeptList")
    public String goDeptList(HttpServletRequest request){
        request.setAttribute("orgId",ParamUtils.getIntParameter(request,"id",0));
        return "dept/deptList";
    }

    @RequestMapping(value = "getDeptList")
    @ResponseBody
    public HashMap<String,Object> getDeptList(DataModel dataModel) {
        return deptService.getDeptList(dataModel);
    }


    @RequestMapping(value = "getOrgTree")
    @ResponseBody
    public List<TreeVO> getOrgTree(HttpServletRequest request){
        int id = ParamUtils.getIntParameter(request,"id",0);
        return deptService.getOrgTree(id);
    }

    @RequestMapping(value = "saveDept")
    @ResponseBody
    public RetVO saveDept(HttpServletRequest request){
        RetVO ret = new RetVO();
        boolean res = false;
        try {
            HashMap<String,String> dept = ParamUtils.getParameters(request);
            res = deptService.editDept(dept);
            ret.setSuccess(res);
        }catch (Exception e){
            e.printStackTrace();
            ret.setSuccess(false);
        }
        return ret;
    }

    @RequestMapping(value = "group")
    public String group(){
        return "dept/groupMainList";
    }


    @RequestMapping(value = "getDeptTree")
    @ResponseBody
    public List<TreeVO> getDeptTree(HttpServletRequest request){
        int id = ParamUtils.getIntParameter(request,"id",0);
        int pid = ParamUtils.getIntParameter(request,"pId",0);
        return deptService.getDeptTree(id,pid);
    }

    @RequestMapping(value = "goGroupList")
    public String goGroupList(HttpServletRequest request){
        request.setAttribute("deptId",ParamUtils.getIntParameter(request,"deptId",-1));
        request.setAttribute("orgId",ParamUtils.getIntParameter(request,"orgId",0));
        return "dept/groupList";
    }

    @RequestMapping(value = "getGroupList")
    @ResponseBody
    public HashMap<String,Object> getGroupList(DataModel dataModel) {
        return deptService.getGroupList(dataModel);
    }

    @RequestMapping(value = "saveGroup")
    @ResponseBody
    public RetVO saveGroup(HttpServletRequest request){
        RetVO ret = new RetVO();
        boolean res = false;
        try {
            HashMap<String,String> group = ParamUtils.getParameters(request);
            res = deptService.editGroup(group);
            ret.setSuccess(res);
        }catch (Exception e){
            e.printStackTrace();
            ret.setSuccess(false);
        }
        return ret;
    }

    @RequestMapping(value = "getGroupJson")
    @ResponseBody
    public List<IACEntry> getGroupJson(HttpServletRequest request){
        int orgId = ParamUtils.getIntParameter(request,"orgId",0);
        int deptId = ParamUtils.getIntParameter(request,"deptId",-1);
        return deptService.getGroupList(orgId,deptId);
    }

    @RequestMapping(value = "getUsergroupJson")
    @ResponseBody
    public List<IACEntry> getUsergroupJson(HttpServletRequest request){
        int uid = ParamUtils.getIntParameter(request,"uId",0);
        return deptService.getUserGroup(uid);
    }

    @RequestMapping(value = "getDeptJson")
    @ResponseBody
    public List<IACEntry> getDeptJson(HttpServletRequest request){
        return deptService.getDeptByOrgId(ParamUtils.getIntParameter(request,"orgId",0));
    }

}
