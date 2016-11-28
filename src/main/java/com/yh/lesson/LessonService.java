package com.yh.lesson;

import cn.com.iactive.db.IACDB;
import cn.com.iactive.db.IACEntry;
import com.yh.model.DataModel;
import com.yh.model.DelModel;
import com.yh.model.RetVO;
import com.yh.utils.DBConstants;
import com.yh.utils.ListSearchUtil;
import com.yh.utils.ObjUtils;
import com.yh.utils.SpringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by FQ.CHINA on 2016/11/28.
 */
@Service
public class LessonService implements ILessonService{
    private final static String[] LESSON_NUM_TIME = {"7:30-8:10","8:20-9:00","9:10-9:50","10:30-11:10","11:20-12:00","15:00-15:40","15:50-16:30","16:40-17:20"};
    private final static int LESSON_NUM = 8;
    @Autowired
    private IACDB<HashMap<String,Object>> iacDB;

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
}
