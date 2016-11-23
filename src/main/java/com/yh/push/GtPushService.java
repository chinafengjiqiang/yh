package com.yh.push;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.AbstractTemplate;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.base.impl.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FQ.CHINA on 2016/11/18.
 */
public class GtPushService implements IPush{
    public boolean pushToList(String title, String content, String transmissionContent, List<String> targetList, int templateId) {
        if(targetList != null && targetList.size() > 0){
            IGtPush push = new IGtPush(Global.PUSH_HOST,Global.APP_KEY,Global.MASTER);
            AbstractTemplate template = getTemplate(title,content,transmissionContent,"",templateId);
            ListMessage message = new ListMessage();
            message.setData(template);
            //设置消息离线，并设置离线时间
            message.setOffline(true);
            //离线有效时间，单位为毫秒，可选
            message.setOfflineExpireTime(Global.OFFLINE_EXPIRE_TIME*1000*3600);
            //配置推送目标
            List<Target> targets = new ArrayList<Target>();
            Target target = null;
            for(String tgt : targetList){
                target = new Target();
                target.setAppId(Global.APP_ID);
                target.setClientId(tgt);
                targets.add(target);
            }
            //获取taskID
            String taskId = push.getContentId(message);
            //使用taskID对目标进行推送
            IPushResult ret = push.pushMessageToList(taskId, targets);
            if(ret != null){
                String retMsg = (String)ret.getResponse().get("result");
                if("ok".equals(retMsg)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean pushToApp(String title, String content, String transmissionContent, List<String> tagList, int templateId) {
        IGtPush push = new IGtPush(Global.PUSH_HOST,Global.APP_KEY,Global.MASTER);
        AbstractTemplate template = getTemplate(title,content,transmissionContent,"",templateId);
        AppMessage message = new AppMessage();
        message.setData(template);
        //设置消息离线，并设置离线时间
        message.setOffline(true);
        //离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(Global.OFFLINE_EXPIRE_TIME*1000*3600);
        //设置推送目标条件过滤
        List<String> appIdList = new ArrayList<String>();
        appIdList.add(Global.APP_ID);
        message.setAppIdList(appIdList);
        if(tagList != null && tagList.size() > 0){
            message.setTagList(tagList);
        }
        IPushResult ret = push.pushMessageToApp(message);
        if(ret != null){
            String retMsg = (String)ret.getResponse().get("result");
            if("ok".equals(retMsg)){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param title : 消息标题
     * @param content ： 消息内容
     * @param transmissionContent : 透传内容
     * @param url : 设置模板打开网页的地址
     * @param templateId : 模板编号(详见Global中定义)
     * @return
     */
    private AbstractTemplate getTemplate(String title, String content, String transmissionContent, String url, int templateId){
        switch (templateId) {
            case Global.TMPLT_LINK:
                LinkTemplate linkTemplate = new LinkTemplate();
                // 设置APPID与APPKEY
                linkTemplate.setAppId(Global.APP_ID);
                linkTemplate.setAppkey(Global.APP_KEY);
                // 设置通知栏标题与内容
                linkTemplate.setTitle(title);
                linkTemplate.setText(content);
                // 配置通知栏图标
                linkTemplate.setLogo("ic_yh.png");
                // 配置通知栏网络图标
                linkTemplate.setLogoUrl("");
                // 设置通知是否响铃，震动，或者可清除
                linkTemplate.setIsRing(true);
                linkTemplate.setIsVibrate(true);
                linkTemplate.setIsClearable(true);
                // 设置打开的网址地址
                linkTemplate.setUrl(url);
                return linkTemplate;
            case Global.TMPLT_TRANSMISSION:
                TransmissionTemplate transmissionTemplate = new TransmissionTemplate();
                transmissionTemplate.setAppId(Global.APP_ID);
                transmissionTemplate.setAppkey(Global.APP_KEY);
                // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
                transmissionTemplate.setTransmissionType(2);
                transmissionTemplate.setTransmissionContent(transmissionContent);
                return transmissionTemplate;
            default:
                NotificationTemplate template = new NotificationTemplate();
                template.setAppId(Global.APP_ID);
                template.setAppkey(Global.APP_KEY);
                // 设置通知栏标题与内容
                template.setTitle(title);
                template.setText(content);
                // 配置通知栏图标
                template.setLogo("ic_yh.png");
                // 配置通知栏网络图标
                template.setLogoUrl("");
                // 设置通知是否响铃，震动，或者可清除
                template.setIsRing(true);
                template.setIsVibrate(true);
                template.setIsClearable(true);
                // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
                template.setTransmissionType(1);
                template.setTransmissionContent(transmissionContent);

                APNPayload payload = new APNPayload();
                payload.setBadge(1);
                payload.setContentAvailable(1);
                payload.setSound("default");
                payload.setCategory("$由客户端定义");
                payload.setAlertMsg(new APNPayload.SimpleAlertMsg(content));

                template.setAPNInfo(payload);
                return template;
        }
    }
}
