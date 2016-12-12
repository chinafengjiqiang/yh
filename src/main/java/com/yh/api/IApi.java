package com.yh.api;

import cn.com.iactive.db.IACEntry;

import java.util.HashMap;
import java.util.List;

/**
 * Created by FQ.CHINA on 2016/11/15.
 */
public interface IApi {
    int login(HashMap<String, String> params,HashMap<String, Object> retMap);

    int pushBind(HashMap<String, String> params);

    int editUserPass(HashMap<String,String> params);

    int editUser(HashMap<String,String> params);

    int getUserInfo(HashMap<String,String> params,HashMap<String, Object> retMap);

    HashMap<String,Object> getLessonPlan(int lessonId, int week, int lessonNum);

    HashMap<String,Object> getPreLesson(int userId);
}
