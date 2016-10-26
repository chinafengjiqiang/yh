package com.yh.user;

import cn.com.iactive.db.IACDB;
import cn.com.iactive.db.IACEntry;
import com.yh.dic.IDicService;
import com.yh.model.DataModel;
import com.yh.utils.AppConstants;
import com.yh.utils.DBConstants;
import com.yh.utils.ObjUtils;
import com.yh.utils.SpringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by FQ.CHINA on 2016/10/18.
 */

@Service
public class UserService implements IUserService{

    public static final String TEARCH_ROLE = "TEARCH_ROLE";

    @Autowired
    private IACDB<HashMap<String,Object>> iacDB;

    @Autowired
    private IDicService dicService;


    public HashMap<String, Object> getUserList(DataModel dataModel) {
        String search = dataModel.getValueAsString("search");
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("USER_TYPE", AppConstants.USER_TYPE_TEARCH);
        if(StringUtils.isNotBlank(search))
            params.put("SEARCH","%"+search+"%");
        return  iacDB.getDataTables("getUserList",dataModel.getDataTablesModel(),params);
    }

    public HSSFWorkbook exportTemplate() {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("new sheet");
        sheet.autoSizeColumn(1, true);
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle row_0_style = wb.createCellStyle();
        row_0_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont row_0_font = wb.createFont();
        row_0_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        row_0_font.setFontHeightInPoints((short)14);
        row_0_style.setFont(row_0_font);
        HSSFCell cell0 = row.createCell(0);
        HSSFCell cell1 = row.createCell(1);
        HSSFCell cell2 = row.createCell(2);
        HSSFCell cell3 = row.createCell(3);

        cell0.setCellStyle(row_0_style);
        cell1.setCellStyle(row_0_style);
        cell2.setCellStyle(row_0_style);
        cell3.setCellStyle(row_0_style);
        String text0 = SpringUtil.getMessage("user.username");
        String text1= SpringUtil.getMessage("user.truename");
        String text2 = SpringUtil.getMessage("user.role");
        String text3 = SpringUtil.getMessage("user.mohone");
        cell0.setCellValue(text0);
        cell1.setCellValue(text1);
        cell2.setCellValue(text2);
        cell3.setCellValue(text3);

        sheet.setColumnWidth(0, text0.getBytes().length*2*256);
        sheet.setColumnWidth(1, text1.getBytes().length*2*256);
        sheet.setColumnWidth(2, text2.getBytes().length*2*256);
        sheet.setColumnWidth(3, text3.getBytes().length*2*256);

        //生成下拉表
        String[] textList = getRoleTextList();
        CellRangeAddressList regions = new CellRangeAddressList(1, 65535, 2, 2);
        // 生成下拉框内容
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(textList);
        // 绑定下拉框和作用区域
        HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint);
        // 对sheet页生效
        sheet.addValidationData(data_validation);
        return wb;
    }

    private String[] getRoleTextList(){
        String[] textArr = new String[0];
        List<IACEntry> roleList = dicService.getDicListByType(TEARCH_ROLE);
        int size = roleList.size();
        if(roleList != null && size > 0){
            textArr = new String[size];
            for (int i = 0; i < size; i++) {
                IACEntry role = roleList.get(i);
                String text = role.getValueAsInt("NID")+"-"+role.getValueAsString("NAME");
                textArr[i] = text;
            }
        }
        return textArr;
    }

    public boolean saveUser(HashMap<String, String> user) {
        return iacDB.insertDynamic(DBConstants.TBL_USER_NAME,user);
    }

    public boolean updateUser(HashMap<String, String> user) {
       return iacDB.updateDynamic(DBConstants.TBL_USER_NAME,DBConstants.TBL_USER_PK,user);
    }

    public boolean isUserExist(String username) {
        IACEntry user = getUserByUsername(username);
        if(ObjUtils.isNotBlankIACEntry(user))
            return true;
        return false;
    }

    public IACEntry getUserByUsername(String username) {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("USERNAME",username);
        List<IACEntry> userList = iacDB.getIACEntryList("getUserByUsername",params);
        if(ObjUtils.isNotBlankIACEntryList(userList)){
            return userList.get(0);
        }
        return null;
    }
}
