package com.yh.dic;

import cn.com.iactive.db.IACDB;
import cn.com.iactive.db.IACEntry;
import com.yh.utils.DBConstants;
import com.yh.utils.ObjUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by FQ.CHINA on 2016/10/21.
 */

@Service
public class DicService implements IDicService {

    @Autowired
    private IACDB<HashMap<String,Object>> iacDB;

    public List<HashMap<String,Object>> getDicByType(String type) {
        HashMap<String,Object> params = new HashMap<String, Object>(1);
        params.put("DIC_TYPE",type);
        return iacDB.getListDynamic(DBConstants.TBL_DIC_NAME,params);
    }

    public IACEntry getDicText(String type, int nid) {
        HashMap<String,Object> params = new HashMap<String, Object>(1);
        params.put("DIC_TYPE",type);
        params.put("NID",nid);
        List<IACEntry> resList = iacDB.getListIACEntryDynamic(DBConstants.TBL_DIC_NAME,params);
        if(ObjUtils.isNotBlankIACEntryList(resList)){
            return resList.get(0);
        }
        return null;
    }

    public List<IACEntry> getDicListByType(String type) {
        HashMap<String,Object> params = new HashMap<String, Object>(1);
        params.put("DIC_TYPE",type);
        return iacDB.getListIACEntryDynamic(DBConstants.TBL_DIC_NAME,params);
    }
}
