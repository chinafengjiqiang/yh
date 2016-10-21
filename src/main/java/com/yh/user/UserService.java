package com.yh.user;

import cn.com.iactive.db.IACDB;
import cn.com.iactive.db.IACEntry;
import com.yh.dic.IDicService;
import com.yh.model.DataModel;
import com.yh.utils.AppConstants;
import com.yh.utils.SpringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle row_0_style = wb.createCellStyle();
        row_0_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont row_0_font = wb.createFont();
        row_0_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        row_0_style.setFont(row_0_font);
        HSSFCell cell0 = row.createCell(0);
        HSSFCell cell1 = row.createCell(1);
        HSSFCell cell2 = row.createCell(2);
        HSSFCell cell3 = row.createCell(3);
        cell0.setCellStyle(row_0_style);
        cell1.setCellStyle(row_0_style);
        cell2.setCellStyle(row_0_style);
        cell3.setCellStyle(row_0_style);
        cell0.setCellValue(SpringUtil.getMessage("user.username"));
        cell1.setCellValue(SpringUtil.getMessage("user.truename"));
        cell2.setCellValue(SpringUtil.getMessage("user.role"));
        cell3.setCellValue(SpringUtil.getMessage("user.mohone"));

        List<IACEntry> roleList = dicService.getDicByType(TEARCH_ROLE);
        return wb;
    }
}
