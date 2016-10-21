package com.yh.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class JsonUtil {
	 /**
     * 从一个JSON 对象字符格式中得到一个java对象
     * @param jsonString
     * @param pojoCalss
     * @return
     */
    public static Object getObject4JsonString(String jsonString,Class pojoCalss){
        Object pojo;
        JSONObject jsonObject = JSONObject.fromObject( jsonString );  
        pojo = JSONObject.toBean(jsonObject,pojoCalss);
        return pojo;
    }
    
    
    
    /**
     * 从json HASH表达式中获取一个map，改map支持嵌套功能
     * @param jsonString
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static Map<String,Object> getMap4Json(String jsonString){
        JSONObject jsonObject = JSONObject.fromObject( jsonString );  
        Iterator  keyIter = jsonObject.keys();
        String key;
        Object value;
        Map<String,Object> valueMap = new HashMap<String,Object>();

        while( keyIter.hasNext())
        {
            key = (String)keyIter.next();
            value = jsonObject.get(key); 
            valueMap.put(key, value);
        }
        
        return valueMap;
    }
    
    
    /**
     * 从json数组中得到相应java数组
     * @param jsonString
     * @return
     */
    public static Object[] getObjectArray4Json(String jsonString){
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        return jsonArray.toArray();
    }
    
    
    /**
     * 从json对象集合表达式中得到一个java对象列表
     * @param jsonString
     * @param pojoClass
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getList4Json(String jsonString, Class pojoClass){
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        JSONObject jsonObject;
        Object pojoValue;
        
        List list = new ArrayList();
        for ( int i = 0 ; i<jsonArray.size(); i++){
            jsonObject = jsonArray.getJSONObject(i);
            pojoValue = JSONObject.toBean(jsonObject,pojoClass);
            list.add(pojoValue);
        }
        return list;

    }
    
    /**
     * 从json数组中解析出java字符串数组
     * @param jsonString
     * @return
     */
    public static String[] getStringArray4Json(String jsonString){
        
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        String[] stringArray = new String[jsonArray.size()];
        for( int i = 0 ; i<jsonArray.size() ; i++ ){
            stringArray[i] = jsonArray.getString(i);
        }
        
        return stringArray;
    }
    
    /**
     * 从json数组中解析出javaLong型对象数组
     * @param jsonString
     * @return
     */
    public static Long[] getLongArray4Json(String jsonString){
        
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        Long[] longArray = new Long[jsonArray.size()];
        for( int i = 0 ; i<jsonArray.size() ; i++ ){
            longArray[i] = jsonArray.getLong(i);
        }
        return longArray;
    }
    
    /**
     * 从json数组中解析出java Integer型对象数组
     * @param jsonString
     * @return
     */
    public static Integer[] getIntegerArray4Json(String jsonString){
        
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        Integer[] integerArray = new Integer[jsonArray.size()];
        for( int i = 0 ; i<jsonArray.size() ; i++ ){
            integerArray[i] = jsonArray.getInt(i);
        }
        return integerArray;
    }
    

    
    /**
     * 从json数组中解析出java Integer型对象数组
     * @param jsonString
     * @return
     */
    public static Double[] getDoubleArray4Json(String jsonString){
        
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        Double[] doubleArray = new Double[jsonArray.size()];
        for( int i = 0 ; i<jsonArray.size() ; i++ ){
            doubleArray[i] = jsonArray.getDouble(i);
            
        }
        return doubleArray;
    }
    
    
    /**
     * 将java对象转换成json字符串
     * @param javaObj
     * @return
     */
    public static String getJsonString4JavaPOJO(Object javaObj){
        
        JSONObject json;
        json = JSONObject.fromObject(javaObj);
        return json.toString();
    }


    
    public static String getJsonValueByJsonKey(String jsonString,String key){
    	try {
        	JSONObject jsonObject = JSONObject.fromObject(jsonString);
        	return jsonObject.getString(key);
		} catch (Exception e) {
			return "";
		}
    }
    
    public static int getJsonIntValueByJsonKey(String jsonString,String key){
    	try {
        	JSONObject jsonObject = JSONObject.fromObject(jsonString);
        	return jsonObject.getInt(key);
		} catch (Exception e) {
			return 0;
		}
    }
    
    public static long getJsonLongValueByJsonKey(String jsonString,String key){
    	try {
        	JSONObject jsonObject = JSONObject.fromObject(jsonString);
        	return jsonObject.getLong(key);
		} catch (Exception e) {
			return 0;
		}
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
    	//String jsonString = "{method:2001,body:[{name:\"feng\",value:\"qiang\"}]}";
    	//Map<String,Object> map = getMap4Json(jsonString);
    	/*Set<Entry<String,Object>> set = map.entrySet();
    	Iterator<Entry<String,Object>> iter = set.iterator();
    	while(iter.hasNext()){
    		Entry<String,Object> entry = iter.next();
    		String key = entry.getKey();
    		Object valueObject = entry.getValue();
    		System.out.println(key+" : "+valueObject);
    		
    	}*/
    	//Object bodys = map.get("body");
    	//System.out.println(bodys);
    	/*String bodys = getJsonValueByJsonKey(jsonString,"body");
    	String[] strArr = getStringArray4Json(bodys);
    	System.out.println(strArr.length);
    	Map cmap = getMap4Json(strArr[0]);
    	String name = (String)cmap.get("name");
    	System.out.println(name);*/
    	/*String arrRect1 = "[{\"rectID\":1,\"userID\":12345},{\"rectID\":2,\"userID\":234}]";
    	String arrStream1 = "[{\"streamID\":2,\"encMod\":0},{\"streamID\":1,\"encMod\":0},{\"streamID\":0,\"encMod\":0}]";
    	String arrRect2 = "[{\"rectID\":0,\"userID\":123},{\"rectID\":1,\"userID\":234}]";
    	String arrStream2 = "[{\"streamID\":1,\"encMod\":0},{\"streamID\":0,\"encMod\":0}]";
    	String arrModuld = "[{\"mouldid\":0,\"autoLoopTime\":30,\"arr_videoRect\":"+arrRect1+",\"arr_stream\":"+arrStream1+"},{\"mouldid\":1,\"autoLoopTime\":30,\"arr_videoRect\":"+arrRect2+",\"arr_stream\":"+arrStream2+"}]";
    	String jsonString = "{\"method\": 10030,\"body\":{\"roomID\":24,\"SpeechExcitation\":0,\"arr_mould\":"+arrModuld+"}}";
    	
    	System.out.println(jsonString);
    	
    	System.out.println("------------------------------");
    	
    	String reString = getJsonValueByJsonKey(jsonString, "body");
    	String roomId = getJsonValueByJsonKey(reString, "roomID");
    	System.out.println(reString);
    	System.out.println(roomId);
    	String arrModuldString = getJsonValueByJsonKey(reString,"arr_mould");
    	System.out.println(arrModuldString);
    	Object[] arrObjects = getObjectArray4Json(arrModuldString);
    	System.out.println(arrObjects[0]);
    	String moduid = getJsonValueByJsonKey(arrObjects[0].toString(), "mouldid");
    	System.out.println(moduid);
    	String arrStreamString = getJsonValueByJsonKey(arrObjects[0].toString(), "arr_stream");
    	System.out.println(arrStreamString);*/
    	/*String str = "{\"uname\":'\u7ba1\u7406\u5458',\"gender\":'\u7537'}";
    	Map map =  getMap4Json(str);
    	System.out.println(map.toString());*/
    	//String jsonString = "{\"arrMouldUID\":[12,23,34]}";
    	//Integer[] jsonArr = getIntegerArray4Json(getJsonValueByJsonKey(str, "arrMouldUID"));
    	//System.out.println(jsonArr[0]);
      String params = "[{\"pid\":'P1',\"startTime\":'2016-07-18 12:20'}]";
      String[] arrs = getStringArray4Json(params);
      for(String str : arrs){
        String users = getJsonValueByJsonKey(str, "pid");
        System.out.println(users);
      }
    }	
    
    /**
     * 
     * @param status : y : 成功、n ： 失败
     * @param info ： 提示信息
     * @return
     */
    public static String buildValidFormAjaxJson(char status,String info){
    	return "{\"info\":\""+info+"\",\"status\":\""+status+"\"}";
    }

}
