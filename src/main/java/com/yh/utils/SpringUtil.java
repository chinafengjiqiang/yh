package com.yh.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by FQ.CHINA on 2016/10/20.
 */

@Component
public class SpringUtil implements ApplicationContextAware{

    private static ApplicationContext applicationContext; // Spring应用上下文环境

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            SpringUtil.applicationContext = applicationContext;
    }


    /**
     * 获取applicationContext
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获得ApplicationContext中的bean
     * @param name
     * @param <T>
     * @return
     * @throws BeansException
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 获取国际化值
     * @param key
     * @return
     */
    public static String getMessage(String key) {
        return SpringUtil.getMessage(key,null,null);
    }

    /**
     * 获取国际化值
     * @param key
     * @param objects
     * @param locale
     * @return
     */
    public static String getMessage(String key, Object[] objects, Locale locale){
        return SpringUtil.getApplicationContext().getMessage(key,objects,locale);
    }
}
