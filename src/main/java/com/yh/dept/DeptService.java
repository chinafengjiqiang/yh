package com.yh.dept;

import cn.com.iactive.db.IACDB;
import cn.com.iactive.db.IACEntry;
import com.yh.model.DataModel;
import com.yh.model.TreeVO;
import com.yh.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by FQ.CHINA on 2016/10/28.
 */
@Service
public class DeptService implements IDeptService{

    @Autowired
    private IACDB<HashMap<String,Object>> iacDB;

    public HashMap<String, Object> getOrgList(DataModel dataModel) {
        HashMap<String,Object> params = ListSearchUtil.getSearchMap(dataModel);
        return  iacDB.getDataTables("getOrgList",dataModel.getDataTablesModel(),params);
    }

    public boolean editOrg(HashMap<String, String> org) {
        if(StringUtils.isBlank(org.get(DBConstants.TBL_ORG_PK))){
            org.put("IS_VALID",AppConstants.IS_VALID+"");
            return iacDB.insertDynamic(DBConstants.TBL_ORG_NAME,org);
        }else {
           return iacDB.updateDynamic(DBConstants.TBL_ORG_NAME,DBConstants.TBL_ORG_PK,org);
        }
    }

    public List<IACEntry> getOrgListByAid(int aid) {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("AID",aid);
        return iacDB.getIACEntryList("getOrgListByAid",params);
    }

    public List<TreeVO> getOrgTree(int id) {
        List<TreeVO> resList = new ArrayList<TreeVO>();
        TreeVO cTree;
        List<IACEntry> orgList = this.getOrgListByAid(id);
        if(ObjUtils.isNotBlankIACEntryList(orgList)){
            TreeVO rootTree = new TreeVO();
            rootTree.setId(0);
            rootTree.setpId(0);
            rootTree.setName(SpringUtil.getMessage("default.org.name"));
            rootTree.setOpen(true);
            resList.add(rootTree);
            List<TreeVO> cList = new ArrayList<TreeVO>(orgList.size());
            for(IACEntry org : orgList){
                cTree = new TreeVO();
                cTree.setOpen(true);
                cTree.setName(org.getValueAsString("NAME"));
                cTree.setpId(0);
                cTree.setId(org.getValueAsInt("ID"));
                cList.add(cTree);
            }
            rootTree.setChildren(cList);
        }
        return resList;
    }

    public HashMap<String, Object> getDeptList(DataModel dataModel) {
        HashMap<String,Object> params = ListSearchUtil.getSearchMap(dataModel);
        int orgId = dataModel.getValueAsInt("orgId");
        if(orgId > 0)
            params.put("PK_ORG",orgId);
        return  iacDB.getDataTables("getDeptList",dataModel.getDataTablesModel(),params);
    }

    public boolean editDept(HashMap<String, String> dept) {
        if(StringUtils.isBlank(dept.get(DBConstants.TBL_DEPT_PK))){
            return iacDB.insertDynamic(DBConstants.TBL_DEPT_NAME,dept);
        }else {
            return iacDB.updateDynamic(DBConstants.TBL_DEPT_NAME,DBConstants.TBL_DEPT_PK,dept);
        }
    }

    public List<TreeVO> getDeptTree(int id,int pid) {
        if(id == 0){
            return getOrgTree(id);
        }else{
            List<TreeVO> resList = new ArrayList<TreeVO>();
            if(pid == 0){
                List<IACEntry> deptList = getDeptByOrgId(id);
                if(ObjUtils.isNotBlankIACEntryList(deptList)){
                    TreeVO dTree;
                    for (IACEntry dept : deptList){
                        dTree = new TreeVO();
                        dTree.setOpen(true);
                        dTree.setName(dept.getValueAsString("NAME"));
                        dTree.setpId(id);
                        dTree.setId(dept.getValueAsInt("ID"));
                        resList.add(dTree);
                    }
                }
            }
            return resList;
        }
    }

    public List<IACEntry> getDeptByOrgId(int orgId) {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("PK_ORG",orgId);
        return iacDB.getIACEntryList("getDeptList",params);
    }

    public HashMap<String, Object> getGroupList(DataModel dataModel) {
        HashMap<String,Object> params = ListSearchUtil.getSearchMap(dataModel);
        int orgId = dataModel.getValueAsInt("orgId");
        int deptId = dataModel.getValueAsInt("deptId");
        if(orgId > 0)
            params.put("PK_ORG",orgId);
        if(deptId >= 0)
            params.put("PK_DEPT",deptId);
        return  iacDB.getDataTables("getGroupList",dataModel.getDataTablesModel(),params);
    }

    public boolean editGroup(HashMap<String, String> group) {
        if(StringUtils.isBlank(group.get(DBConstants.TBL_GROUP_PK))){
            return iacDB.insertDynamic(DBConstants.TBL_GROUP_NAME,group);
        }else {
            return iacDB.updateDynamic(DBConstants.TBL_GROUP_NAME,DBConstants.TBL_GROUP_PK,group);
        }
    }

    public List<IACEntry> getGroupList(int orgId, int deptId) {
        HashMap<String,Object> params = new HashMap<String, Object>();
        if(orgId > 0)
            params.put("PK_ORG",orgId);
        if(deptId >= 0)
            params.put("PK_DEPT",deptId);
        return iacDB.getIACEntryList("getGroupList",params);
    }

    public List<IACEntry> getUserGroup(int uid) {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("PK_USER",uid);
        return iacDB.getIACEntryList("getUserGroup",params);
    }

    public IACEntry getDeptById(int id) {
        return iacDB.getSelectOneIACEntry(DBConstants.TBL_DEPT_NAME,id);
    }

    public IACEntry getOrgById(int id) {
        return iacDB.getSelectOneIACEntry(DBConstants.TBL_ORG_NAME,id);
    }

    public IACEntry getGroupById(int id) {
        return iacDB.getSelectOneIACEntry(DBConstants.TBL_GROUP_NAME,id);
    }
}
