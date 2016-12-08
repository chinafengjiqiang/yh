package com.yh.utils;

public interface AppConstants {
    /*
        用户类型
     */
    int USER_TYPE_ADMIN = 1;//管理员
    int USER_TYPE_TEARCH = 2;//教师
    int USER_TYPE_COMMON = 3;//普通学员

    /**
     *有效状态定义
     */
     int IS_VALID = 1;//有效
     int NO_VALID = 0;//无效

    /**
     * 消息权限源类型定义
     */
    int MSG_SRC_TYPE_USER = 1;//用户

    int MSG_SRC_TYPE_ROLE = 2;//科目

    int MSG_SRC_TYPE_DEPT = 3;//年级

    int MSG_SRC_TYPE_GROUP = 4;//分组

    int MSG_SRC_TYPE_ORG = 5;//学校

    /**
     * 权限访问方式
     */
    int MSG_RULE_MODE_ALL = 0;//完全

    /**
     * 权限目标定义
     */
    int MSG_OBJECT_TYPE_MSG = 1;//教学通知

    /**
     * 权限对象访问范围
     */
    int MSG_RULE_RANGE_ALL = 0;//全部


    /**
     * 消息推送部分分隔标示
     */
    String PUSH_SPLIT = "#@@#";

    String PUSH_PLAN_SPLIT = "#;#";
    String PUSH_PLAN_INFO_SPLIT = "#,#";
    /**
     * 推送命令号
     */
    int PUSH_CMD_MESSAGE = 1;//消息

    int PUSH_CMD_LESSON = 2;//课程表

    int PUSH_CMD_PLAN = 3;//教学计划

    /**
     *
     */
    String MANAGE_SESSION_KEY = "managerLoginInfo";
}
