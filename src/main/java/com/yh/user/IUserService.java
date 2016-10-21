package com.yh.user;

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
}
