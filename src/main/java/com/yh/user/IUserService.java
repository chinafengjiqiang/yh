package com.yh.user;

import cn.com.iactive.db.IACEntry;
import com.yh.model.DataModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by FQ.CHINA on 2016/10/18.
 */
public interface IUserService {
    HashMap<String,Object> getUserList(DataModel dataModel);

    HSSFWorkbook exportTemplate();

    boolean saveUser(HashMap<String,String> user);

    boolean updateUser(HashMap<String,String> user);

    boolean isUserExist(String username);

    IACEntry getUserByUsername(String username);

    boolean saveUserGroup(String gIds,String tIds);

    List<Integer> getGroupUserIds(int gId);

    List<IACEntry> getGroupUser(int gId);

    boolean deleteUserGroup(int uid,int gId);

    List<IACEntry> getDeptUser(int orgId,int deptId,int userType);

    List<IACEntry> getDeptUser(int deptId,int userType);

    IACEntry getUserById(int id);

    boolean savePushBind(HashMap<String,Object> push);

    IACEntry getUserPushBind(int userId);

    List<IACEntry> getOrgUser(int orgId,int userType);

    List<IACEntry> getDeptRoleUser(int deptId,int role);

    List<String> getDeptUserClientId(int deptId);

    int editUserPass(int userId,String pass);
}
