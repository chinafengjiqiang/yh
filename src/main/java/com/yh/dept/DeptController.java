package com.yh.dept;

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
        return deptService.getDeptTree(id);
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

}
