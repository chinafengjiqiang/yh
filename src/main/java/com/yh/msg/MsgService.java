package com.yh.msg;

import cn.com.iactive.db.IACDB;
import com.yh.model.DataModel;
import com.yh.utils.AppConstants;
import com.yh.utils.DBConstants;
import com.yh.utils.DateUtils;
import com.yh.utils.ListSearchUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by FQ.CHINA on 2016/11/3.
 */
@Service
public class MsgService implements IMsgService{

    @Autowired
    private IACDB<HashMap<String,Object>> iacDB;
    public HashMap<String, Object> getMsgList(DataModel dataModel) {
        HashMap<String,Object> params = ListSearchUtil.getSearchMap(dataModel);
        return  iacDB.getDataTables("getMsgList",dataModel.getDataTablesModel(),params);
    }

    public boolean editMsg(HashMap<String, String> msg) {
        msg.put("CREATE_TIME", DateUtils.dateTime2String(new Date()));
        if(StringUtils.isBlank(msg.get(DBConstants.TBL_MSG_PK))){
            return iacDB.insertDynamic(DBConstants.TBL_MSG_NAME,msg);
        }else {
            return iacDB.updateDynamic(DBConstants.TBL_MSG_NAME,DBConstants.TBL_MSG_PK,msg);
        }
    }
}
