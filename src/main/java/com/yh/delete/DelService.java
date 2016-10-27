package com.yh.delete;

import cn.com.iactive.db.IACDB;

import com.yh.model.DelModel;
import com.yh.model.RetVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@Service
public class DelService implements IDelService {

    @Autowired
    private IACDB<HashMap<String,Object>> iacDB;

    public RetVO del(DelModel del) {
        RetVO ret = new RetVO();
        try {
            String ids = del.getIds();
            if(StringUtils.isNotBlank(ids)){
                List<String> idList = Arrays.asList(ids.split(","));
                iacDB.deleteBatchDynamic(del.getTableName(),del.getPk(),idList);
            }
        }catch (Exception e){
            e.printStackTrace();
            ret.setSuccess(false);
        }
        return ret;
    }
}
