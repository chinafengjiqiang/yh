package com.yh.user;

import com.yh.model.DataModel;
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
@RequestMapping("/user")
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
}

