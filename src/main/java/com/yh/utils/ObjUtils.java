package com.yh.utils;

import cn.com.iactive.db.IACEntry;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author FQ.CHINA
 *
 */
public class ObjUtils {
  
  //判断map中存放的对象是否存在
  public static boolean isNotBlankI(HashMap<String, Object> map,String key){
      if(map == null)
        return false;
      Object value = map.get(key);
      if(value == null)
        return false;
      if((Integer)value <= 0)
        return false;
      return true;
  }
  
  public static boolean isBlankI(HashMap<String, Object> map,String key){
    if(map == null)
      return true;
    Object value = map.get(key);
    if(value == null)
      return true;
    if((Integer)value <= 0)
      return true;
    return false;
}
  
  public static boolean isNotBlankI(HashMap<String, Object> map){
    return isNotBlankI(map, "ID");
  }
  
  public static boolean isBlankI(HashMap<String, Object> map){
    return isBlankI(map, "ID");
  }
  
  //判断map中存放的对象是否存在
  public static boolean isNotBlankL(HashMap<String, Object> map,String key){
      if(map == null)
        return false;
      Object value = map.get(key);
      if(value == null)
        return false;
      if((Long)value <= 0)
        return false;
      return true;
  }
  
  public static boolean isNotBlankL(HashMap<String, Object> map){
    return isNotBlankL(map, "ID");
  }


    public static boolean isBlankList(List<HashMap<String,Object>> list){
        return list == null || list.size() == 0;
    }

    public static boolean isNotBlankList(List<HashMap<String,Object>> list){
        return list != null && list.size() > 0;
    }


    public static boolean isBlankSList(List<HashMap<String,String>> list){
        return list == null || list.size() == 0;
    }

    public static boolean isNotBlankSList(List<HashMap<String,String>> list){
        return list != null && list.size() > 0;
    }


    public static boolean isBlankIACEntryList(List<IACEntry> list){
        return list == null || list.size() == 0;
    }

    public static boolean isNotBlankIACEntryList(List<IACEntry> list){
        return list != null && list.size() > 0;
    }

    public static boolean isNotBlankIACEntry(IACEntry entry){
        return isNotBlankIACEntry(entry,"ID");
    }

    public static boolean isNotBlankIACEntry(IACEntry entry,String key){
        return entry != null && entry.getValueAsObject(key) != null;
    }

    public static boolean isBlankIACEntry(IACEntry entry){
        return isBlankIACEntry(entry,"ID");
    }

    public static boolean isBlankIACEntry(IACEntry entry,String key){
        return entry == null || entry.getValueAsObject(key) == null;
    }


    public static HashMap<String,Object> getObjMap(){
        return new HashMap<String, Object>();
    }

    public static HashMap<String,String> getStringMap(){
        return new HashMap<String, String>();
    }

    public static String[] splitStr(String str,String split){
        if(str != null && str.length() > 0){
            return str.split(split);
        }
        return null;
    }

    public static String[] splitStr(String str){
        return splitStr(str,",");
    }
}
