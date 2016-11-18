package com.yh.push;

import java.util.List;

/**
 * Created by FQ.CHINA on 2016/11/18.
 */
public interface IPush {
    /**
     * 批量发送消息
     * @param title
     * @param content
     * @param transmissionContent
     * @param targetList
     * @param templateId
     * @return
     */
    boolean pushToList(String title, String content, String transmissionContent, List<String> targetList, int templateId);

    /**
     * 单个发送消息
     * @param title
     * @param content
     * @param transmissionContent
     * @param tagList
     * @param templateId
     * @return
     */
    boolean pushToApp(String title,String content,String transmissionContent,List<String> tagList,int templateId);
}
