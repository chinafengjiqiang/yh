package com.yh.utils;
/**
 * 2009-6-12
 * @author <a href="mailto:chinafengqiang@gmail.com">feng jiqiang</a>
 * <p>Title:获取资源文件中属性的值<p>
 * <p>Description:<p>
 */
public class ResourceBundle {
	private static java.util.ResourceBundle  bundle = null;
	public static String CONF_FILE_NAME = "app";//文件目录
	
	public static void getResourseBundle(){
		try {
			bundle = java.util.ResourceBundle.getBundle(CONF_FILE_NAME);
	     } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getResourseBundle(String fileName){
		try {
			bundle = java.util.ResourceBundle.getBundle(fileName);
	     } catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取指定key的值
	 * @param key
	 * @return
	 */
	public static String getValue(String key){
		try {
			if(bundle == null)
				getResourseBundle();
			return bundle.getString(key);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static int getIntValue(String key){
		String tempValue = getValue(key);
		if(tempValue!=null&&!"".equals(tempValue))
			return Integer.parseInt(tempValue);
		return 0;
	}
	
	public static Object getObjectValue(String key){
		if(bundle == null)
			getResourseBundle();	
		return bundle.getObject(key);
	}
	
	public static String getValue(String fileName,String key){
		ResourceBundle bud = new ResourceBundle();
		bud.getResourseBundle(fileName);
		String str = bundle.getString(key);
		if(!CONF_FILE_NAME.equals(fileName))
			bundle = null;
		return str;
	}
	
}
