package com.yh.api;

import java.util.HashMap;
/**
 * 
 * @author FQ.CHINA
 * 错误返回码工厂
 *
 */
public class ErrorsFactory {
  private static HashMap<Integer,String> errors = new HashMap<Integer, String>();
  
  public static final int Request_Success = 0;
  
  public static final int Request_Params_Not_Valid = 1001;
  public static final int Request_Sign_Is_Null = 1002;
  public static final int Request_Time_Expires_Timeout = 1003;
  public static final int Request_Sign_Verify_Fail = 1004;
  public static final int Request_Sp_Or_Org_Name_Error = 1005;
  public static final int Request_URLDecoder_Error = 1006;

  /*
   * 其他错误码定义
   */
  public static final int Request_Params_ERROR = 4001;
  public static final int OBJECT_NOT_EXIST = 4002;
  public static final int ERROR_CODE_A = 4003;
  public static final int ERROR_CODE_B = 4004;
  public static final int ERROR_CODE_C = 4005;
  public static final int ERROR_CODE_D = 4006;
  public static final int ERROR_CODE_E = 4007;
  public static final int ERROR_CODE_F = 4008;
  public static final int ERROR_CODE_G = 4009;



  /*
   * 服务器内部错误
   */
   
  public static final int Server_Exception = 5001;
  
  public static final int Request_Fail = 5002;
  
  static{
    /*
     * 系统级通用错误返回码
     */
    errors.put(Request_Success, "Request Success");//请求成功
    errors.put(Request_Params_Not_Valid, "Request Params Not Valid");//请求参数非法
    errors.put(Request_Sign_Is_Null, "Request Sign Is Null");//请求无签名
    errors.put(Request_Time_Expires_Timeout, "Request Time Expires Timeout");//请求已超时
    errors.put(Request_Sign_Verify_Fail, "Request Sign Verify Fail");//签名参数sign校验失败
    errors.put(Request_Sp_Or_Org_Name_Error, "Request Sp Or Org UserName Error");//渠道或单位管理员账号错误
    errors.put(Request_URLDecoder_Error, "Request URLDecoder Error");//URLDecoder出现错误
   
    
    /*
     * 系统级服务器内部错误返回码
     */
    errors.put(Server_Exception, "Api Server Throws Exception");//服务器出现异常
    errors.put(Request_Fail, "Request Fail");//请求失败
    
    /*
     * 应用级错误返回码
     */

     
    /*
     * 应用级各具体api错误返回码
     */
    
  }
  
  public synchronized static String getErrorMsg(int code){
    return errors.get(code);
  }
}
