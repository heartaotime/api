//package com.open.custom.api.config;
//
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//@WebFilter(urlPatterns = "/*", filterName = "authFilter")  //这里的“/*” 表示的是需要拦截的请求路径
//public class CrossOriginFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
//        httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
//        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
//        httpResponse.addHeader("Access-Control-Allow-Origin", "*");
//        filterChain.doFilter(servletRequest, httpResponse);
//    }
//
//}
