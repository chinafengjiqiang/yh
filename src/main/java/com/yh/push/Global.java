package com.yh.push;

public interface Global {

   ////离线有效时间，单位为小时
   int OFFLINE_EXPIRE_TIME = 48;

   /*
    * gt yh
    */
   String APP_ID = "EWnQffAgXp6efqJhmwVJh4";
   String APP_KEY = "8bWI4ZwrOl91UGD8mCdA5";
   String MASTER = "vh1Rb928O560AuNYtB7u8";
   String PUSH_HOST = "http://sdk.open.api.igexin.com/apiex.htm";
   
   /*
    * 推送信息模板
    */
   int TMPLT_NOTIFICATION = 1;//点击通知打开应用模板
   int TMPLT_LINK = 2;//点击通知打开网页模板
   int TMPLT_NOTY_LOAD = 3;//点击通知弹窗下载模板
   int TMPLT_TRANSMISSION = 4;//透传模板
}
