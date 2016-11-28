package com.yh.lesson;

import cn.com.iactive.db.IACEntry;
import com.yh.model.DataModel;
import com.yh.model.DelModel;
import com.yh.model.RetVO;
import com.yh.utils.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by FQ.CHINA on 2016/11/28.
 */
@Controller
@RequestMapping("/manage/lesson")
public class LessonController {
    @Autowired
    private ILessonService lessonService;

    @RequestMapping
    public String group(){
        return "lesson/lessonMainList";
    }

    @RequestMapping(value = "goLessonList")
    public String goLessonList(HttpServletRequest request){
        request.setAttribute("deptId", ParamUtils.getIntParameter(request,"deptId",-1));
        return "lesson/lessonList";
    }

    @RequestMapping(value = "getLessonList")
    @ResponseBody
    public HashMap<String,Object> getLessonList(DataModel dataModel) {
        return lessonService.getLessonList(dataModel);
    }

    @RequestMapping(value = "exportLessonTmp")
    public void exportLessonTmp(HttpServletResponse response){
        HSSFWorkbook workbook = lessonService.exportTemplate();
        try {
            String mimetype = "application/x-msdownload";
            response.setContentType(mimetype);
            String downFileName = "lesson.xls";
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


    @RequestMapping(value = "importLesson")
    @ResponseBody
    public RetVO importLesson(@RequestParam MultipartFile file, HttpServletRequest request){
        RetVO ret = new RetVO();
        try {
            String extName = FileUtils.getFileExt(file.getOriginalFilename());
            if (!"xls".equals(extName)) {//判断文件格式
                ret.setSuccess(false);
                ret.setMsg(SpringUtil.getMessage("file.format.error"));
                return ret;
            }
            String deptId = request.getParameter("PK_DEPT");
            String term = request.getParameter("TERM");
            ImportExecl poi = new ImportExecl();
            List<List<String>> list = poi.read(file.getInputStream(), true);
            if (list != null && list.size() > 0) {
                lessonService.deleteDeptLesson(NumUtils.String2Int(deptId),NumUtils.String2Int(term));

                String name;
                List<String> titleList = list.get(0);
                name = titleList.get(0);
                HashMap<String, Object> lesson = new HashMap<String, Object>();
                lesson.put("NAME", name);
                lesson.put("TERM", term);
                lesson.put("DEPT_ID", deptId);
                // 添加
                long lessonId = lessonService.addLesson(lesson);

                if (lessonId > 0) {
                    HashMap<String, Object> detail = null;
                    for (int i = 2; i < list.size(); i++) {
                        if (i != 7) {// 第八行为上下午分割线
                            List<String> infoList = list.get(i);
                            if (infoList != null && infoList.size() == 7) {
                                detail = new HashMap<String, Object>();
                                detail.put("LESSON_ID", lessonId);
                                detail.put("LESSON_NUM", infoList.get(0));
                                detail.put("LESSON_TIME", infoList.get(1));
                                detail.put("WEEK_ONE_LESSON", infoList.get(2));
                                detail.put("WEEK_TWO_LESSON", infoList.get(3));
                                detail.put("WEEK_THREE_LESSON", infoList.get(4));
                                detail.put("WEEK_FOUR_LESSON", infoList.get(5));
                                detail.put("WEEK_FIVE_LESSON", infoList.get(6));
                                lessonService.addLessonDetail(detail);
                            }
                        }
                    }

                }
            }
            ret.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ret.setSuccess(false);
        }
        return ret;
    }

    @RequestMapping(value = "del")
    @ResponseBody
    public RetVO del(DelModel del){
        String ids = del.getIds();
        return lessonService.del(ids);
    }

    @RequestMapping(value = "getLessonDetailJson")
    @ResponseBody
    public List<HashMap<String,Object>> getLessonDetailJson(HttpServletRequest request){
        int lessonId = ParamUtils.getIntParameter(request,"lId",0);
        return lessonService.getLessonTable(lessonId);
    }
}
