package com.yh.dept;

import cn.com.iactive.db.IACEntry;
import com.yh.model.DataModel;
import com.yh.model.TreeVO;

import java.util.HashMap;
import java.util.List;

/**
 * Created by FQ.CHINA on 2016/10/28.
 */
public interface IDeptService {
    HashMap<String,Object> getOrgList(DataModel dataModel);

    boolean editOrg(HashMap<String,String> org);

    List<IACEntry> getOrgListByAid(int aid);

    List<TreeVO> getOrgTree(int id);

    HashMap<String,Object> getDeptList(DataModel dataModel);

    boolean editDept(HashMap<String,String> dept);

    List<TreeVO> getDeptTree(int id,int pid);

    List<IACEntry> getDeptByOrgId(int orgId);

    HashMap<String,Object> getGroupList(DataModel dataModel);

    boolean editGroup(HashMap<String,String> dept);

    List<IACEntry> getGroupList(int orgId,int deptId);

    List<IACEntry> getUserGroup(int uid);

    IACEntry getDeptById(int id);

    IACEntry getOrgById(int id);

    IACEntry getGroupById(int id);
}
