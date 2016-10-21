package com.yh.model;

import cn.com.iactive.db.*;
import com.yh.utils.JacksonMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 * Created by FQ.CHINA on 2016/5/10.
 */
public class DataModel {

    //前端传入的json格式的数据
    private String aoData;

    //DB层需要参数Model
    private DataTablesModel dataTablesModel;

    private List<AoData> dataList = null;

    //存放前端传递过来的所有数据
    private HashMap<String,Object> dataMap = new HashMap<String, Object>();

    private int lErrorInt = 0;
    private long lErrorLong = 0;//0xfffffffe;
    private float fErrorDouble = 0.0f;
    private String strErrorString = "";

    public String getAoData() {
        return aoData;
    }

    public void setAoData(String aoData) {
        this.aoData = aoData;
        ObjectMapper mapper = JacksonMapper.getInstance();
        try {
            dataList = mapper.readValue(aoData, new TypeReference<List<AoData>>() {});
            setDataTablesModel();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void setDataTablesModel()throws Exception{
        dataTablesModel = new DataTablesModel();
        if(dataList != null){
            Field f = null;
            HashMap<String,Field> fieldMap = getDataModelField();
            for(AoData data : dataList){
                if(data == null)
                    continue;
                dataMap.put(data.getName(),data.getValue());
                f = fieldMap.get(data.getName());
                if(f != null){
                    f.setAccessible(true);
                    f.set(dataTablesModel,data.getValue());
                }
            }
        }
    }

    private HashMap<String,Field> getDataModelField(){
        HashMap<String,Field> fieldMap = new HashMap<String, Field>();
        Field[] fields = DataTablesModel.class.getDeclaredFields();
        for (Field field : fields){
            fieldMap.put(field.getName(),field);
        }
        return fieldMap;
    }

    public DataTablesModel getDataTablesModel(){
        return this.dataTablesModel;
    }


    /*
 * 读取一个字符型字段的值
 */
    public String getValueAsString(String key){
        Object objVal = getValueAsObject(key);
        if(objVal == null)
            return strErrorString;
        return objVal.toString();
    }

    /*
 * 读取一个数字型字段的值
 */
    public int getValueAsInt(String key){
        Object objVal = getValueAsObject(key);
        if( objVal == null )
            return lErrorInt;
        // Number
        if( objVal instanceof Number )
        {
            Number a = (Number)objVal;
            return a.intValue();
        }
        return lErrorInt;
    }

    /*
     * 读取一个数字型字段的值
     */
    public long getValueAsLong(String key){
        Object objVal = getValueAsObject(key);
        if( objVal == null )
            return lErrorLong;
        // Number
        if( objVal instanceof Number )
        {
            Number a = (Number)objVal;
            return a.longValue();
        }
        return lErrorLong;
    }


    /*
         * 读取一个浮点型字段值
         */
    public float getValueAsFloat(String key){
        Object objVal = getValueAsObject(key);
        if( objVal == null )
            return fErrorDouble;
        // Number
        if( objVal instanceof Number )
        {
            Number a = (Number)objVal;
            return a.floatValue();
        }
        return fErrorDouble;
    }

    /*
	 * 以Object 方式读取一字段值
	 */
    public Object getValueAsObject(String key)
    {
        return dataMap.get(key);
    }
}
