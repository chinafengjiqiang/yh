package com.yh.lesson;

import cn.com.iactive.db.IACDB;
import cn.com.iactive.db.IACEntry;
import com.yh.model.DataModel;
import com.yh.model.DelModel;
import com.yh.model.RetVO;
import com.yh.push.Global;
import com.yh.push.GtPushService;
import com.yh.push.IPush;
import com.yh.user.IUserService;
import com.yh.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by FQ.CHINA on 2016/11/28.
 */
@Service
public class LessonService implements ILessonService{
    private final static String[] LESSON_NUM_TIME = {"7:30-8:10","8:20-9:00","9:10-9:50","10:30-11:10","11:20-12:00","15:00-15:40","15:50-16:30","16:40-17:20"};
    private final static String[] PRELESSON_NUM_TIME = {"7:30-8:10","8:20-9:00","9:10-9:50","10:30-11:10","11:20-12:00","14:30-15:10","15:20-16:00","16:10-16:50","17:00-17:40"};
    private final static int LESSON_NUM = 8;
    private final static int PRELESSON_NUM = 9;
    @Autowired
    private IACDB<HashMap<String,Object>> iacDB;
    @Autowired
    private IUserService userService;

    public HashMap<String, Object> getLessonList(DataModel dataModel) {
        HashMap<String,Object> params = ListSearchUtil.getSearchMap(dataModel);
        int deptId = dataModel.getValueAsInt("deptId");
        if(deptId >= 0)
            params.put("DEPT_ID",deptId);
        return  iacDB.getDataTables("getLessonList",dataModel.getDataTablesModel(),params);
    }

    public HSSFWorkbook exportTemplate() {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("new sheet");
        sheet.autoSizeColumn(1, true);

        //合并单元格
        CellRangeAddress cra=new CellRangeAddress(0, 0, 0, 6);
        sheet.addMergedRegion(cra);

        CellRangeAddress cra_row_8=new CellRangeAddress(7, 7, 0, 6);
        sheet.addMergedRegion(cra_row_8);

        HSSFRow row = sheet.createRow(0);

        HSSFCellStyle row_0_style = wb.createCellStyle();
        row_0_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont row_0_font = wb.createFont();
        row_0_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        row_0_font.setFontHeightInPoints((short)14);
        row_0_style.setFont(row_0_font);

        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(row_0_style);
        cell.setCellValue(SpringUtil.getMessage("lesson.title"));

        HSSFRow row1 = sheet.createRow(1);
        HSSFCell cell0 = row1.createCell(0);
        HSSFCell cell1 = row1.createCell(1);
        HSSFCell cell2 = row1.createCell(2);
        HSSFCell cell3 = row1.createCell(3);
        HSSFCell cell4 = row1.createCell(4);
        HSSFCell cell5 = row1.createCell(5);
        HSSFCell cell6 = row1.createCell(6);

        cell0.setCellStyle(row_0_style);
        cell1.setCellStyle(row_0_style);
        cell2.setCellStyle(row_0_style);
        cell3.setCellStyle(row_0_style);
        cell4.setCellStyle(row_0_style);
        cell5.setCellStyle(row_0_style);
        cell6.setCellStyle(row_0_style);

        String text0 = SpringUtil.getMessage("lesson.num");
        String text1= SpringUtil.getMessage("lesson.time");
        String text2 = SpringUtil.getMessage("lesson.one");
        String text3 = SpringUtil.getMessage("lesson.two");
        String text4 = SpringUtil.getMessage("lesson.three");
        String text5 = SpringUtil.getMessage("lesson.four");
        String text6 = SpringUtil.getMessage("lesson.five");

        cell0.setCellValue(text0);
        cell1.setCellValue(text1);
        cell2.setCellValue(text2);
        cell3.setCellValue(text3);
        cell4.setCellValue(text4);
        cell5.setCellValue(text5);
        cell6.setCellValue(text6);

        sheet.setColumnWidth(0, text0.getBytes().length*2*256);
        sheet.setColumnWidth(1, text1.getBytes().length*2*256);
        sheet.setColumnWidth(2, text2.getBytes().length*2*256);
        sheet.setColumnWidth(3, text3.getBytes().length*2*256);
        sheet.setColumnWidth(4, text3.getBytes().length*2*256);
        sheet.setColumnWidth(5, text3.getBytes().length*2*256);
        sheet.setColumnWidth(6, text3.getBytes().length*2*256);

        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = wb.createFont();
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short)12);
        style.setFont(font);

        int num = 0;
        for(int row_num = 2;row_num < 11;row_num++){
            HSSFRow rown = sheet.createRow(row_num);
            if(row_num != 7){
                HSSFCell rown_0 = rown.createCell(0);
                HSSFCell rown_1 = rown.createCell(1);
                rown_0.setCellStyle(style);
                rown_1.setCellStyle(style);
                rown_0.setCellValue(num+1);
                rown_1.setCellValue(LESSON_NUM_TIME[num]);
                num++;
            }
        }

        return wb;
    }


    public long addLesson(HashMap<String, Object> lessson) {
        return iacDB.insertDynamicRInt(DBConstants.TBL_LESSON_NAME,lessson);
    }

    public boolean addLessonDetail(HashMap<String, Object> detail) {
        return iacDB.insertDynamic(DBConstants.TBL_LESSON_DETAIL_NAME,detail);
    }

    public RetVO del(String ids) {
        RetVO ret = new RetVO();
        try {
            if(StringUtils.isNotBlank(ids)){
                List<String> idList = Arrays.asList(ids.split(","));
                iacDB.deleteBatchDynamic(DBConstants.TBL_LESSON_DETAIL_NAME,
                        DBConstants.TBL_LESSON_DETAIL_FPK,idList);
                iacDB.deleteBatchDynamic(DBConstants.TBL_LESSON_NAME,
                        DBConstants.TBL_LESSON_PK,idList);
            }
        }catch (Exception e){
            e.printStackTrace();
            ret.setSuccess(false);
        }
        return ret;
    }

    public boolean deleteDeptLesson(int deptId, int term) {
        try {
            IACEntry lesson = getDeptLesson(deptId,term);
            if(lesson != null){
                del(lesson.getValueAsInt("ID")+"");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public IACEntry getDeptLesson(int deptId, int term) {
        HashMap<String,Object> params = ObjUtils.getObjMap();
        params.put("DEPT_ID",deptId);
        params.put("TERM",term);
        List<IACEntry> retList = iacDB.getIACEntryList("getDeptLesson",params);
        if(ObjUtils.isNotBlankIACEntryList(retList))
            return retList.get(0);
        return null;
    }

    public List<IACEntry> getLessonDetail(int lessonId) {
        HashMap<String,Object> params = ObjUtils.getObjMap();
        params.put("LESSON_ID",lessonId);
        return iacDB.getIACEntryList("getLessonDetail",lessonId);
    }

    public List<HashMap<String, Object>> getLessonDetails(int lessonId) {
        HashMap<String,Object> params = ObjUtils.getObjMap();
        params.put("LESSON_ID",lessonId);
        return iacDB.getList("getLessonDetail",params);
    }

    HashMap<Integer,IACEntry> ldMap = null;
    public List<HashMap<String,Object>> getLessonTable(int lessonId) {
        List<IACEntry> ldList = getLessonDetail(lessonId);
        List<HashMap<String,Object>> retList = new ArrayList<HashMap<String, Object>>(LESSON_NUM);
        initLessonDetail(ldList);
        HashMap<String,Object> ldtMap = null;
        for(int i = 1;i<=LESSON_NUM;i++){
            ldtMap = new HashMap<String, Object>();
            IACEntry ld = ldMap.get(i);
            ldtMap.put("LESSON_ID",lessonId);
            ldtMap.put("LESSON_NUM",i);
            if(ld != null){
                ldtMap.put("LESSON_TIME",ld.getValueAsString("LESSON_TIME"));
                ldtMap.put("WEEK_ONE_LESSON",ld.getValueAsString("WEEK_ONE_LESSON"));
                ldtMap.put("WEEK_TWO_LESSON",ld.getValueAsString("WEEK_TWO_LESSON"));
                ldtMap.put("WEEK_THREE_LESSON",ld.getValueAsString("WEEK_THREE_LESSON"));
                ldtMap.put("WEEK_FOUR_LESSON",ld.getValueAsString("WEEK_FOUR_LESSON"));
                ldtMap.put("WEEK_FIVE_LESSON",ld.getValueAsString("WEEK_FIVE_LESSON"));
            }
            retList.add(ldtMap);
        }
        return retList;
    }

    private void initLessonDetail(List<IACEntry> ldList){
        if (ldMap != null) {
            ldMap.clear();
        }else {
            ldMap = new HashMap<Integer, IACEntry>();
        }
        if (ObjUtils.isNotBlankIACEntryList(ldList)) {
            for(IACEntry ld : ldList){
                int num = ld.getValueAsInt("LESSON_NUM");
                ldMap.put(num,ld);
            }

        }
    }

    public int sendLesson(String ids) {
        try {
            String[] idArr = ObjUtils.splitStr(ids);
            if(idArr != null){
                for (String s : idArr) {
                    if (StringUtils.isNotBlank(s)) {
                        int lessonId = Integer.parseInt(s);
                        IACEntry lesson = getLesson(lessonId);
                        if(ObjUtils.isBlankIACEntry(lesson))
                            continue;
                        int deptId = lesson.getValueAsInt("DEPT_ID");
                        List<IACEntry> dList = this.getLessonDetail(lessonId);
                        if(ObjUtils.isNotBlankIACEntryList(dList)){
                            String strHead = AppConstants.PUSH_CMD_LESSON + AppConstants.PUSH_SPLIT;
                                    strHead += lessonId+AppConstants.PUSH_SPLIT;
                            StringBuilder details = new StringBuilder();
                            for (IACEntry entry : dList) {
                                int num = entry.getValueAsInt("LESSON_NUM");
                                String time = entry.getValueAsString("LESSON_TIME");
                                String one = entry.getValueAsString("WEEK_ONE_LESSON");
                                if(StringUtils.isBlank(one)) one = " ";
                                String tow = entry.getValueAsString("WEEK_TWO_LESSON");
                                if(StringUtils.isBlank(tow)) tow = " ";
                                String three = entry.getValueAsString("WEEK_THREE_LESSON");
                                if(StringUtils.isBlank(three)) three = " ";
                                String four = entry.getValueAsString("WEEK_FOUR_LESSON");
                                if(StringUtils.isBlank(four)) four = " ";
                                String five = entry.getValueAsString("WEEK_FIVE_LESSON");
                                if(StringUtils.isBlank(five)) five = " ";
                                details.append(num).append(",").append(time).append(",");
                                details.append(one).append(",").append(tow).append(",");
                                details.append(three).append(",").append(four).append(",");
                                details.append(five).append(";");
                            }
                            String transmissionContent = strHead+details;
                            //得到部门下的用户clientId
                            List<String> clientIds = userService.getDeptUserClientId(deptId);
                            if(clientIds.size() > 0){
                                IPush push = new GtPushService();
                                boolean ret = push.pushToList(SpringUtil.getMessage("lesson.push.title"),SpringUtil.getMessage("lesson.push.content")
                                        ,transmissionContent,clientIds, Global.TMPLT_TRANSMISSION);
                            }
                        }
                    }
                }
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public IACEntry getLesson(int id) {
        return iacDB.getSelectOneIACEntry(DBConstants.TBL_LESSON_NAME,id);
    }

    public boolean importPlan(List<List<String>> list, int lessonId, String startTime, String endTime) {
        HashMap<String,HashMap<String,List<String>>> resPlanMap = new HashMap<String,HashMap<String,List<String>>>();
        if(list!=null&&list.size()>0){
            for(int i=2;i<list.size();i++){
                List<String> resList = list.get(i);
                if(resList !=null&&resList.size()>0){
                    String week = getWeek(resList.get(0));
                    HashMap<String,List<String>> weekPanMap = resPlanMap.get(week);
                    if(weekPanMap == null){
                        weekPanMap = new HashMap<String,List<String>>();
                        resPlanMap.put(week, weekPanMap);
                    }
                    for(int j = 1;j<resList.size();j++){
                        String category = list.get(1).get(j).trim();
                        List<String> categoryList = weekPanMap.get(category);
                        if(categoryList == null){
                            categoryList = new ArrayList<String>();
                            weekPanMap.put(category,categoryList);
                        }
                        String content = resList.get(j);
                        if(StringUtils.isNotBlank(content))
                            categoryList.add(content);
                    }
                }
            }
        }

        List<HashMap<String,Object>> allPlans = new ArrayList<HashMap<String,Object>>();

        List<HashMap<String,Object>> lessonList = getLessonDetails(lessonId);
        HashMap<String,Object> planMap= null;
        if(lessonList != null && lessonList.size() > 0){
            for(HashMap<String,Object> lesson : lessonList){
                int lessonNum = (Integer)lesson.get("LESSON_NUM");
                String oneLesson = (String)lesson.get("WEEK_ONE_LESSON");
                HashMap<String,List<String>> cateoryMap =  resPlanMap.get("WEEK_ONE_LESSON");
                if(cateoryMap != null){
                    List<String> planList = cateoryMap.get(oneLesson);
                    if(planList!=null&&planList.size()>0){
                        planMap = new HashMap<String,Object>();
                        planMap.put("LESSON_ID",lessonId);
                        planMap.put("LESSON_NUM",lessonNum);
                        planMap.put("LESSON_WEEK",1);
                        planMap.put("LESSON_NAME",oneLesson);
                        planMap.put("LESSON_CONTENT",planList.get(0));
                        planMap.put("START_DATE",DateUtils.stringsToDate(startTime));
                        planMap.put("END_DATE",DateUtils.stringsToDate(endTime));
                        planList.remove(0);
                        allPlans.add(planMap);
                    }
                }
                String twoLesson = (String)lesson.get("WEEK_TWO_LESSON");
                HashMap<String,List<String>> cateory2Map =  resPlanMap.get("WEEK_TWO_LESSON");
                if(cateory2Map != null){
                    List<String> planList = cateory2Map.get(twoLesson);
                    if(planList!=null&&planList.size()>0){
                        planMap = new HashMap<String,Object>();
                        planMap.put("LESSON_ID",lessonId);
                        planMap.put("LESSON_NUM",lessonNum);
                        planMap.put("LESSON_WEEK",2);
                        planMap.put("LESSON_NAME",twoLesson);
                        planMap.put("LESSON_CONTENT",planList.get(0));
                        planMap.put("START_DATE",DateUtils.stringsToDate(startTime));
                        planMap.put("END_DATE",DateUtils.stringsToDate(endTime));
                        planList.remove(0);
                        allPlans.add(planMap);
                    }
                }
                String threeLesson = (String)lesson.get("WEEK_THREE_LESSON");
                HashMap<String,List<String>> cateory3Map =  resPlanMap.get("WEEK_THREE_LESSON");
                if(cateory3Map != null){
                    List<String> planList = cateory3Map.get(threeLesson);
                    if(planList!=null&&planList.size()>0){
                        planMap = new HashMap<String,Object>();
                        planMap.put("LESSON_ID",lessonId);
                        planMap.put("LESSON_NUM",lessonNum);
                        planMap.put("LESSON_WEEK",3);
                        planMap.put("LESSON_NAME",threeLesson);
                        planMap.put("LESSON_CONTENT",planList.get(0));
                        planMap.put("START_DATE",DateUtils.stringsToDate(startTime));
                        planMap.put("END_DATE",DateUtils.stringsToDate(endTime));
                        planList.remove(0);
                        allPlans.add(planMap);
                    }
                }
                String fourLesson = (String)lesson.get("WEEK_FOUR_LESSON");
                HashMap<String,List<String>> cateory4Map =  resPlanMap.get("WEEK_FOUR_LESSON");
                if(cateory4Map != null){
                    List<String> planList = cateory4Map.get(fourLesson);
                    if(planList!=null&&planList.size()>0){
                        planMap = new HashMap<String,Object>();
                        planMap.put("LESSON_ID",lessonId);
                        planMap.put("LESSON_NUM",lessonNum);
                        planMap.put("LESSON_WEEK",4);
                        planMap.put("LESSON_NAME",fourLesson);
                        planMap.put("LESSON_CONTENT",planList.get(0));
                        planMap.put("START_DATE",DateUtils.stringsToDate(startTime));
                        planMap.put("END_DATE",DateUtils.stringsToDate(endTime));
                        planList.remove(0);
                        allPlans.add(planMap);
                    }
                }
                String fiveLesson = (String)lesson.get("WEEK_FIVE_LESSON");
                HashMap<String,List<String>> cateory5Map =  resPlanMap.get("WEEK_FIVE_LESSON");
                if(cateory5Map != null){
                    List<String> planList = cateory5Map.get(fiveLesson);
                    if(planList!=null&&planList.size()>0){
                        planMap = new HashMap<String,Object>();
                        planMap.put("LESSON_ID",lessonId);
                        planMap.put("LESSON_NUM",lessonNum);
                        planMap.put("LESSON_WEEK",5);
                        planMap.put("LESSON_NAME",fiveLesson);
                        planMap.put("LESSON_CONTENT",planList.get(0));
                        planMap.put("START_DATE",DateUtils.stringsToDate(startTime));
                        planMap.put("END_DATE",DateUtils.stringsToDate(endTime));
                        planList.remove(0);
                        allPlans.add(planMap);
                    }
                }
                String sixLesson = (String)lesson.get("WEEK_SIX_LESSON");
                HashMap<String,List<String>> cateory6Map =  resPlanMap.get("WEEK_SIX_LESSON");
                if(cateory6Map != null){
                    List<String> planList = cateory6Map.get(fiveLesson);
                    if(planList!=null&&planList.size()>0){
                        planMap = new HashMap<String,Object>();
                        planMap.put("LESSON_ID",lessonId);
                        planMap.put("LESSON_NUM",lessonNum);
                        planMap.put("LESSON_WEEK",6);
                        planMap.put("LESSON_NAME",sixLesson);
                        planMap.put("LESSON_CONTENT",planList.get(0));
                        planMap.put("START_DATE",DateUtils.stringsToDate(startTime));
                        planMap.put("END_DATE",DateUtils.stringsToDate(endTime));
                        planList.remove(0);
                        allPlans.add(planMap);
                    }
                }
                String sevenLesson = (String)lesson.get("WEEK_SEVEN_LESSON");
                HashMap<String,List<String>> cateory7Map =  resPlanMap.get("WEEK_SEVEN_LESSON");
                if(cateory7Map != null){
                    List<String> planList = cateory7Map.get(fiveLesson);
                    if(planList!=null&&planList.size()>0){
                        planMap = new HashMap<String,Object>();
                        planMap.put("LESSON_ID",lessonId);
                        planMap.put("LESSON_NUM",lessonNum);
                        planMap.put("LESSON_WEEK",7);
                        planMap.put("LESSON_NAME",sevenLesson);
                        planMap.put("LESSON_CONTENT",planList.get(0));
                        planMap.put("START_DATE",DateUtils.stringsToDate(startTime));
                        planMap.put("END_DATE",DateUtils.stringsToDate(endTime));
                        planList.remove(0);
                        allPlans.add(planMap);
                    }
                }
            }
        }
        this.addLessonPlans(allPlans);
        return true;
    }


    private String getWeek(String date){
        String res = "";
        switch (date) {
            case "一":
                res = "WEEK_ONE_LESSON";
                break;
            case "二":
                res = "WEEK_TWO_LESSON";
                break;
            case "三":
                res = "WEEK_THREE_LESSON";
                break;
            case "四":
                res = "WEEK_FOUR_LESSON";
                break;
            case "五":
                res = "WEEK_FIVE_LESSON";
                break;
            case "六":
                res = "WEEK_SIX_LESSON";
                break;
            case "七":
                res = "WEEK_SEVEN_LESSON";
                break;
            default:
                break;
        }
        return res;
    }

    @Override
    public boolean addLessonPlans(List<HashMap<String, Object>> plans) {
        if(plans != null){
            for(HashMap<String,Object> plan : plans){
                if(plan != null){
                    iacDB.insertDynamic(DBConstants.TBL_LESSON_PLAN_NAME,plan);
                }
            }
        }
        return true;
    }

    public void deleteLessonPlans(int lessonId) {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("lessonId",lessonId);
        params.put("date",new Date());
        iacDB.delete("deleteLessonPlans", params);
    }

    @Override
    public IACEntry getPlan(int lessonId, int week, int lessonNum) {
        HashMap<String,Object> params = ObjUtils.getObjMap();
        params.put("LESSON_ID",lessonId);
        params.put("LESSON_WEEK",week);
        params.put("LESSON_NUM",lessonNum);
        List<IACEntry> retList = iacDB.getIACEntryList("getPlanDetail",params);
        if(ObjUtils.isNotBlankIACEntryList(retList))
            return retList.get(0);
        return null;
    }

    @Override
    public int sendPlans(String ids) {
        try {
            String[] idArr = ObjUtils.splitStr(ids);
            if(idArr != null) {
                for (String s : idArr) {
                    if (StringUtils.isNotBlank(s)) {
                        int lessonId = Integer.parseInt(s);
                        List<IACEntry> planList = getLessonPlans(lessonId);
                        if (ObjUtils.isNotBlankIACEntryList(planList)) {
                            IACEntry lesson = getLesson(lessonId);
                            if(ObjUtils.isBlankIACEntry(lesson))
                                continue;
                            int deptId = lesson.getValueAsInt("DEPT_ID");

                            String strHead = AppConstants.PUSH_CMD_PLAN + AppConstants.PUSH_SPLIT;
                            StringBuilder details = new StringBuilder();
                            strHead += lessonId+AppConstants.PUSH_SPLIT;
                            for (IACEntry plan : planList) {
                                int week = plan.getValueAsInt("LESSON_WEEK");
                                int num = plan.getValueAsInt("LESSON_NUM");
                                String name = plan.getValueAsString("LESSON_NAME");
                                String content = plan.getValueAsString("LESSON_CONTENT");
                                String startTime = DateUtils.dateTime2String((Date)plan.getValueAsObject("START_DATE"));
                                String endTime = DateUtils.dateTime2String((Date)plan.getValueAsObject("END_DATE"));
                            }
                        }
                    }
                }
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<IACEntry> getLessonPlans(int lessonId) {
        HashMap<String,Object> params = ObjUtils.getObjMap();
        params.put("lessonId",lessonId);
        params.put("date",new Date());
        return iacDB.getIACEntryList("getLessonPlans",params);
    }

    @Override
    public HashMap<String, Object> getPreLessonList(DataModel dataModel) {
        HashMap<String,Object> params = ListSearchUtil.getSearchMap(dataModel);
        int deptId = dataModel.getValueAsInt("deptId");
        if(deptId >= 0)
            params.put("DEPT_ID",deptId);
        return  iacDB.getDataTables("getPreLessonList",dataModel.getDataTablesModel(),params);
    }

    public HSSFWorkbook exportPreTemplate() {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("new sheet");
        sheet.autoSizeColumn(1, true);

        //合并单元格
        CellRangeAddress cra=new CellRangeAddress(0, 0, 0, 6);
        sheet.addMergedRegion(cra);

        CellRangeAddress cra_row_8=new CellRangeAddress(7, 7, 0, 6);
        sheet.addMergedRegion(cra_row_8);

        HSSFRow row = sheet.createRow(0);

        HSSFCellStyle row_0_style = wb.createCellStyle();
        row_0_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont row_0_font = wb.createFont();
        row_0_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        row_0_font.setFontHeightInPoints((short)14);
        row_0_style.setFont(row_0_font);

        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(row_0_style);
        cell.setCellValue(SpringUtil.getMessage("prelesson.title"));

        HSSFRow row1 = sheet.createRow(1);
        HSSFCell cell0 = row1.createCell(0);
        HSSFCell cell1 = row1.createCell(1);
        HSSFCell cell2 = row1.createCell(2);
        HSSFCell cell3 = row1.createCell(3);
        HSSFCell cell4 = row1.createCell(4);
        HSSFCell cell5 = row1.createCell(5);
        HSSFCell cell6 = row1.createCell(6);

        cell0.setCellStyle(row_0_style);
        cell1.setCellStyle(row_0_style);
        cell2.setCellStyle(row_0_style);
        cell3.setCellStyle(row_0_style);
        cell4.setCellStyle(row_0_style);
        cell5.setCellStyle(row_0_style);
        cell6.setCellStyle(row_0_style);

        String text0 = SpringUtil.getMessage("lesson.num");
        String text1= SpringUtil.getMessage("lesson.time");
        String text2 = SpringUtil.getMessage("lesson.one");
        String text3 = SpringUtil.getMessage("lesson.two");
        String text4 = SpringUtil.getMessage("lesson.three");
        String text5 = SpringUtil.getMessage("lesson.four");
        String text6 = SpringUtil.getMessage("lesson.five");

        cell0.setCellValue(text0);
        cell1.setCellValue(text1);
        cell2.setCellValue(text2);
        cell3.setCellValue(text3);
        cell4.setCellValue(text4);
        cell5.setCellValue(text5);
        cell6.setCellValue(text6);

        sheet.setColumnWidth(0, text0.getBytes().length*2*256);
        sheet.setColumnWidth(1, text1.getBytes().length*2*256);
        sheet.setColumnWidth(2, text2.getBytes().length*2*256);
        sheet.setColumnWidth(3, text3.getBytes().length*2*256);
        sheet.setColumnWidth(4, text3.getBytes().length*2*256);
        sheet.setColumnWidth(5, text3.getBytes().length*2*256);
        sheet.setColumnWidth(6, text3.getBytes().length*2*256);

        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = wb.createFont();
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short)12);
        style.setFont(font);

        int num = 0;
        for(int row_num = 2;row_num < 12;row_num++){
            HSSFRow rown = sheet.createRow(row_num);
            if(row_num != 7){
                HSSFCell rown_0 = rown.createCell(0);
                HSSFCell rown_1 = rown.createCell(1);
                rown_0.setCellStyle(style);
                rown_1.setCellStyle(style);
                rown_0.setCellValue(num+1);
                rown_1.setCellValue(PRELESSON_NUM_TIME[num]);
                num++;
            }
        }

        return wb;
    }

    @Override
    public long addPreLesson(HashMap<String, Object> prelessson) {
        return iacDB.insertDynamicRInt(DBConstants.TBL_PRELESSON_NAME,prelessson);
    }

    @Override
    public boolean addPreLessonDetail(HashMap<String, Object> detail) {
        return iacDB.insertDynamic(DBConstants.TBL_PRELESSON_DETAIL_NAME,detail);
    }

    @Override
    public boolean deleteDeptPreLesson(int deptId, String startTime, String endTime) {
        try {
            List<IACEntry> retList = getDeptPreLesson(deptId,startTime,endTime);
            if(ObjUtils.isNotBlankIACEntryList(retList)){
                for (IACEntry pre : retList) {
                    if(pre != null){
                        delpre(pre.getValueAsInt("ID")+"");
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public List<IACEntry> getDeptPreLesson(int deptId, String startTime, String endTime) {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("DEPT_ID",deptId);
        params.put("START_DATE",startTime);
        params.put("END_DATE",endTime);
        return iacDB.getIACEntryList("getDeptPreLesson", params);
    }

    @Override
    public RetVO delpre(String ids) {
        RetVO ret = new RetVO();
        try {
            if(StringUtils.isNotBlank(ids)){
                List<String> idList = Arrays.asList(ids.split(","));
                iacDB.deleteBatchDynamic(DBConstants.TBL_PRELESSON_DETAIL_NAME,
                        DBConstants.TBL_PRELESSON_DETAIL_FPK,idList);
                iacDB.deleteBatchDynamic(DBConstants.TBL_PRELESSON_NAME,
                        DBConstants.TBL_PRELESSON_PK,idList);
            }
        }catch (Exception e){
            e.printStackTrace();
            ret.setSuccess(false);
        }
        return ret;
    }

    @Override
    public List<IACEntry> getPreLessonDetails(int preId) {
        HashMap<String,Object> params = ObjUtils.getObjMap();
        params.put("PRE_ID",preId);
        return iacDB.getIACEntryList("getPreLessonDetail",params);
    }

    HashMap<Integer, IACEntry> pldMap = null;
    @Override
    public List<HashMap<String, Object>> getPreLessonTable(int preId) {
        List<IACEntry> ldList = getPreLessonDetails(preId);
        List<HashMap<String,Object>> retList = new ArrayList<HashMap<String, Object>>(PRELESSON_NUM);
        initPreLessonDetail(ldList);
        HashMap<String,Object> pldtMap = null;
        for(int i = 1;i<=PRELESSON_NUM;i++){
            pldtMap = new HashMap<String, Object>();
            IACEntry ld = pldMap.get(i);
            pldtMap.put("PRE_ID",preId);
            pldtMap.put("PRE_NUM",i);
            if(ld != null){
                pldtMap.put("PRE_TIME",ld.getValueAsString("PRE_TIME"));
                pldtMap.put("WEEK_ONE_PRE",ld.getValueAsString("WEEK_ONE_PRE"));
                pldtMap.put("WEEK_TWO_PRE",ld.getValueAsString("WEEK_TWO_PRE"));
                pldtMap.put("WEEK_THREE_PRE",ld.getValueAsString("WEEK_THREE_PRE"));
                pldtMap.put("WEEK_FOUR_PRE",ld.getValueAsString("WEEK_FOUR_PRE"));
                pldtMap.put("WEEK_FIVE_PRE",ld.getValueAsString("WEEK_FIVE_PRE"));
            }
            retList.add(pldtMap);
        }
        return retList;
    }

    private void initPreLessonDetail(List<IACEntry> ldList){
        if (pldMap != null) {
            pldMap.clear();
        }else {
            pldMap = new HashMap<Integer, IACEntry>();
        }
        if (ObjUtils.isNotBlankIACEntryList(ldList)) {
            for(IACEntry ld : ldList){
                int num = ld.getValueAsInt("PRE_NUM");
                pldMap.put(num,ld);
            }

        }
    }

    @Override
    public List<IACEntry> getDeptValidPreLesson(int deptId) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("DEPT_ID",deptId);
        params.put("NOW_DATE",new Date());
        return iacDB.getIACEntryList("getDeptValidPreLesson",params);
    }
}
