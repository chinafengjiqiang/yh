package com.yh.lesson;

import cn.com.iactive.db.IACEntry;
import com.yh.model.DataModel;
import com.yh.model.DelModel;
import com.yh.model.RetVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.HashMap;
import java.util.List;

/**
 * Created by FQ.CHINA on 2016/11/28.
 */
public interface ILessonService {
    HashMap<String,Object> getLessonList(DataModel dataModel);

    public HSSFWorkbook exportTemplate();

    long addLesson(HashMap<String,Object> lessson);

    boolean addLessonDetail(HashMap<String, Object> detail);

    RetVO del(String ids);

    boolean deleteDeptLesson(int deptId,int term);

    IACEntry getDeptLesson(int deptId, int term);

    List<IACEntry> getLessonDetail(int lessonId);

    List<HashMap<String,Object>> getLessonDetails(int lessonId);

    List<HashMap<String,Object>> getLessonTable(int lessonId);

    int sendLesson(String ids);

    IACEntry getLesson(int id);

    boolean importPlan(List<List<String>> list,int lessonId,String startTime,String endTime);

    boolean addLessonPlans(List<HashMap<String, Object>> plans);

    void deleteLessonPlans(int lessonId);

    IACEntry getPlan(int lessonId,int week,int lessonNum);

    int sendPlans(String ids);

    List<IACEntry> getLessonPlans(int lessonId);

    HashMap<String,Object> getPreLessonList(DataModel dataModel);

    public HSSFWorkbook exportPreTemplate();

    long addPreLesson(HashMap<String,Object> prelessson);

    boolean addPreLessonDetail(HashMap<String, Object> detail);

    boolean deleteDeptPreLesson(int deptId,String startTime,String endTime);

    RetVO delpre(String ids);

    List<IACEntry> getDeptPreLesson(int deptId,String startTime,String endTime);

    List<IACEntry> getPreLessonDetails(int preId);

    List<HashMap<String,Object>> getPreLessonTable(int preId);

    List<IACEntry> getDeptValidPreLesson(int deptId);
}
