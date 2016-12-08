package com.yh.filter;
import com.yh.model.LoginInfo;
import com.yh.utils.AppConstants;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by FQ.CHINA on 2016/12/8.
 */
public class ManageFilter extends HttpServlet implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestUrl = request.getRequestURI();
        HttpSession session = request.getSession();
        if (requestUrl.contains("/manage/")) {
            LoginInfo info = (LoginInfo) session
                    .getAttribute(AppConstants.MANAGE_SESSION_KEY);
            if (info == null) {
                response.sendRedirect(request.getContextPath());
                return;
            } else {
                chain.doFilter(request, response);
                return;
            }
        } else {
            chain.doFilter(request, response);
            return;
        }
    }
}
