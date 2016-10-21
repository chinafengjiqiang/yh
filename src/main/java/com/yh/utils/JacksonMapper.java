package com.yh.utils;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by FQ.CHINA on 2016/5/9.
 */
public class JacksonMapper {
    private JacksonMapper(){

    }

    private static class JacksonMapperHolder{
        private static final ObjectMapper mapper = new ObjectMapper();
    }

    public static final ObjectMapper getInstance(){
        return JacksonMapperHolder.mapper;
    }
}
