package com.yh.user;

import com.yh.model.DataModel;
import com.yh.model.RetVO;
import com.yh.utils.AppConstants;
import com.yh.utils.ParamUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by FQ.CHINA on 2016/10/12.
 */

@Controller
@RequestMapping("/manage/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("user/user");
    }


    @RequestMapping(value = "list")
    public String list(){
        return "user/list";
    }

    @RequestMapping(value = "getUserList")
    @ResponseBody
    public HashMap<String,Object> getUserList(DataModel dataModel) {
        return userService.getUserList(dataModel);
    }

    @RequestMapping(value = "export")
    public void exportUserTemplate(HttpServletResponse response){
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


    @RequestMapping(value = "save")
    @ResponseBody
    public RetVO saveUser(HttpServletRequest request){
        RetVO ret = new RetVO();
        boolean res = false;
        try {
            HashMap<String,String> user = ParamUtils.getParameters(request);
            user.put("USER_TYPE", AppConstants.USER_TYPE_TEARCH+"");
            if(StringUtils.isBlank(user.get("id"))){
                res = userService.saveUser(user);
            }else{

            }
            ret.setSuccess(res);
        }catch (Exception e){
            e.printStackTrace();
            ret.setSuccess(false);
        }
        return ret;
    }
}


