package com.yh.utils;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yh.model.LoginInfo;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;



public class WebUtil {
	 /***
     * 获取URI的路径,如路径为http://www.iactive.com/action/post.htm?method=add, 得到的值为"/action/post.htm"
     * @param request
     * @return
     */
    public static String getRequestURI(HttpServletRequest request){     
        return request.getRequestURI();
    }
    /**
     * 获取完整请求路径(含内容路径及请求参数)
     * @param request
     * @return
     */
    public static String getRequestURIWithParam(HttpServletRequest request){     
        return getRequestURI(request) + (request.getQueryString() == null ? "" : "?"+ request.getQueryString());
    }
    
    
    /**
     * 得到登陆用户信息
     * @param request
     * @return
     */
    public static LoginInfo getLoginInfo(HttpServletRequest request){
    	return (LoginInfo)request.getSession().getAttribute("userLoginInfo");
    }
    
    /**
     * 得到登陆管理员信息
     * @param request
     * @return
     */
    public static LoginInfo getManagerLoginInfo(HttpServletRequest request){
        return (LoginInfo)request.getSession().getAttribute(AppConstants.MANAGE_SESSION_KEY);
    }

    public static void setManagerLoginInfo(HttpServletRequest request,LoginInfo loginInfo){
        request.getSession().setAttribute(AppConstants.MANAGE_SESSION_KEY,loginInfo);
    }
    
    
    /**
     * 删除登陆用户信息
     * @param request
     */
    public static void removeLoginInfo(HttpServletRequest request){
    	HttpSession thisSession = request.getSession();
    	thisSession.removeAttribute("userLoginInfo");
    	if(thisSession != null)
    		thisSession.invalidate();//是该session失效
    }
    
    /**
     * 删除登陆管理员信息
     * @param request
     */
    public static void removeManagerLoginInfo(HttpServletRequest request){
        //request.getSession().removeAttribute("managerLoginInfo");
        HttpSession thisSession = request.getSession();
        thisSession.removeAttribute(AppConstants.MANAGE_SESSION_KEY);
        if(thisSession != null)
            thisSession.invalidate();//是该session失效
    }
    
    /**
     * Base64加密传输参数值 
     * @param value
     * @return
     */
    public static String encodeRequestParam(String value){
    	return new String(Base64.encodeBase64(value.getBytes()));
    }
    
    public static String decodeRequestParam(String value){
    	return new String(Base64.decodeBase64(value.getBytes()));
    }
    
    /**
     * DES加密传输参数值 
     * @param value
     * @return
     */
    public static String encodeRequestParamDES(String value){
    	try {
    		return DES.encrypt(value,ResourceBundle.getValue("des.secret.key"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return "";
    }
    
    public static String decodeRequestParamDES(String value){
    	try {
			return DES.decrypt(value, ResourceBundle.getValue("des.secret.key"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return value;
    }
    
    /**
     * 去除html代码
     * @param inputString
     * @return
     */
    public static String HtmltoText(String inputString) {
        String htmlStr = inputString; //含html标签的字符串
        String textStr ="";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;          
        Pattern p_ba;
        java.util.regex.Matcher m_ba;
        
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
            String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
            String patternStr = "\\s+";
            
            p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); //过滤script标签

            p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); //过滤style标签
         
            p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); //过滤html标签
            
            p_ba = Pattern.compile(patternStr,Pattern.CASE_INSENSITIVE);
            m_ba = p_ba.matcher(htmlStr);
            htmlStr = m_ba.replaceAll(""); //过滤空格
         
         textStr = htmlStr;
         
        }catch(Exception e) {
                    System.err.println("Html2Text: " + e.getMessage());
        }          
        return textStr;//返回文本字符串
     }
    
    
	//把用户浏览器语言标示存到session
	public  static void setLanguageLocal(HttpServletRequest request){
		/*
		 * 当前使用语言的地方代码，当用户未选择语言进入界面时，locale默认为用户浏览器请求的语言标示
		 * 当用户选择了语言时，则locale为选择的语言标示 如：简体中文 zh、繁体中文 tw
		 */
		String locale = "en";

		// 用户选择语言时带入的参数
		String lang = request.getParameter("lang");

		String sessionLocal = (String) request.getSession().getAttribute(
				"LANLOCAL");
		if (lang == null && sessionLocal != null) {
			locale = sessionLocal;
		} else {
			if (lang != null) {
				locale = lang;
			} else {
				locale = request.getLocale().getLanguage();
			}
			request.getSession().setAttribute("LANLOCAL", locale);
		}
	}
	
	public static String getLang(HttpServletRequest request)
	{
		// language
		String lang = request.getParameter("lang");
        if (lang == null || lang.length() == 0)
	        lang = request.getHeader("Accept-Language");  // eg. zh-cn;
        if (lang == null || lang.length() == 0)
        	return "zh-cn";
        
        lang = lang.split("\\;")[0]; //只取首选语言 zh-cn,zh-tw;en;en-us;
        lang = lang.split("\\,")[0];
        if (lang.indexOf("zh")<0) // non-zh language, such as "en-us", must only get "en" !
        	lang = lang.split("\\-")[0];
        
        if (lang.equalsIgnoreCase("zh") || lang.equalsIgnoreCase("zh-cn") || lang.equalsIgnoreCase("zh-sg"))
        	lang="zh-cn";
        else if (lang.equalsIgnoreCase("zh-tw")||lang.equalsIgnoreCase("zh-hk")||lang.equalsIgnoreCase("zh-mo")||lang.equalsIgnoreCase("tw")||lang.equalsIgnoreCase("big5"))
        	lang="zh-tw";
        	
        return lang;
	}
	
	public static String getServerBasePath(HttpServletRequest request)
	{
		String basePath = request.getScheme() + "://" + request.getServerName();
		if (request.getServerPort() != 80) 
			basePath = basePath + ":" + request.getServerPort();
		basePath = basePath + request.getContextPath() + "/";
		return basePath;
	}
	
	public static String getServerPath(){
		return ResourceBundle.getValue("APP.URL");
	}

}
