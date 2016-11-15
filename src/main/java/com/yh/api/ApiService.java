package com.yh.api;

import com.yh.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by FQ.CHINA on 2016/11/15.
 */
public class ApiService implements IApi{

    @Autowired
    private IUserService userService;
    public int login(String username, String pass, int userType) {
        return 0;
    }
}
