package com.yh.user;

import cn.com.iactive.db.IACDB;
import cn.com.iactive.db.IACEntry;
import com.yh.api.IApi;
import com.yh.dic.IDicService;
import com.yh.model.DataModel;
import com.yh.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

    @Autowired
    private IApi apiService;


    public HashMap<String, Object> getUserList(DataModel dataModel) {
        HashMap<String,Object> params = ListSearchUtil.getSearchMap(dataModel);
        params.put("USER_TYPE", AppConstants.USER_TYPE_TEARCH);
        int orgId = dataModel.getValueAsInt("orgId");
        int deptId = dataModel.getValueAsInt("deptId");
        if(orgId > 0)
            params.put("PK_ORG",orgId);
        if(deptId >= 0)
            params.put("PK_DEPT",deptId);
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

    public boolean saveUserGroup(String gIds, String tIds) {
        try {
            if(StringUtils.isNotBlank(gIds)){
                String[] gIdArr = gIds.split(",");
                if(gIdArr != null){
                    for (String gId : gIdArr){
                        if(StringUtils.isNotBlank(gId)){
                            if(StringUtils.isNotBlank(tIds)){
                                //获取此组中的用户ID
                                List<Integer> uidList = getGroupUserIds(Integer.parseInt(gId));

                                String[] tIdArr = tIds.split(",");
                                if(tIdArr != null){
                                    HashMap<String,Object> ug = null;
                                    for (String tId : tIdArr){
                                        if(StringUtils.isNotBlank(tId)){
                                            //已经存在跳过
                                            if(uidList.contains(Integer.parseInt(tId)))
                                                continue;
                                            ug = new HashMap<String, Object>();
                                            ug.put("PK_GROUP",gId);
                                            ug.put("PK_USER",tId);
                                            iacDB.insertDynamic(DBConstants.TBL_USER_GROUP_NAME,ug);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Integer> getGroupUserIds(int gId) {
        List<Integer> uidList = new ArrayList<Integer>();
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("PK_GROUP",gId);
        List<IACEntry> ugList = iacDB.getIACEntryList("getGroupUserIds",params);
        if(ObjUtils.isNotBlankIACEntryList(ugList)){
            for(IACEntry ug : ugList){
                uidList.add(ug.getValueAsInt("PK_USER"));
            }
        }
        return uidList;
    }

    public List<IACEntry> getGroupUser(int gId) {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("PK_GROUP",gId);
        return iacDB.getIACEntryList("getGroupUsers",params);
    }

    public boolean deleteUserGroup(int uid,int gId) {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("PK_GROUP",gId);
        params.put("PK_USER",uid);
        return iacDB.deleteDynamic(DBConstants.TBL_USER_GROUP_NAME,params);
    }

    public List<IACEntry> getDeptUser(int orgId, int deptId,int userType) {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("PK_ORG",orgId);
        params.put("PK_DEPT",deptId);
        params.put("USER_TYPE",userType);
        return iacDB.getIACEntryList("getDeptUser",params);
    }

    public List<IACEntry> getDeptUser(int deptId, int userType) {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("PK_DEPT",deptId);
        params.put("USER_TYPE",userType);
        return iacDB.getIACEntryList("getDeptUser",params);
    }

    public IACEntry getUserById(int id) {
        return iacDB.getSelectOneIACEntry(DBConstants.TBL_USER_NAME,id);
    }

    public boolean savePushBind(HashMap<String, Object> push) {
        return iacDB.insertDynamic(DBConstants.TBL_PUSH_BIND_NAME,push);
    }

    public IACEntry getUserPushBind(int userId) {
        HashMap<String,Object> params = ObjUtils.getObjMap();
        params.put("USER_ID",userId);
        List<IACEntry> pList = iacDB.getIACEntryList("getUserPushBind",params);
        if(ObjUtils.isNotBlankIACEntryList(pList))
            return pList.get(0);
        return null;
    }

    public List<IACEntry> getOrgUser(int orgId, int userType) {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("PK_ORG",orgId);
        params.put("USER_TYPE",userType);
        return iacDB.getIACEntryList("getDeptUser",params);
    }

    public List<IACEntry> getDeptRoleUser(int deptId, int role) {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("PK_DEPT",deptId);
        params.put("ROLE",role);
        return iacDB.getIACEntryList("getDeptRoleUser",params);
    }

    public List<String> getDeptUserClientId(int deptId) {
        HashMap<String,Object> params = ObjUtils.getObjMap();
        params.put("PK_DEPT",deptId);
        params.put("IS_VALID",1);
        List<IACEntry> retList = iacDB.getIACEntryList("getDeptUserClientId",params);
        List<String> clientList = new ArrayList<String>();
        if (ObjUtils.isNotBlankIACEntryList(retList)) {
            for (IACEntry entry : retList) {
                clientList.add(entry.getValueAsString("CLIENT_ID"));
            }
        }
        return clientList;
    }

    @Override
    public int editUserPass(int userId, String pass) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userId",userId+"");
        params.put("userpass",pass);
        return apiService.editUserPass(params);
    }
}
