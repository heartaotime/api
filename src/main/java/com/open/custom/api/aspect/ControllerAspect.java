package com.open.custom.api.aspect;


import com.google.gson.Gson;
import com.open.custom.api.app.model.OpenApiAccessWithBLOBs;
import com.open.custom.api.app.model.OpenAppInfo;
import com.open.custom.api.app.sevice.IOpenApiAccessService;
import com.open.custom.api.app.sevice.IOpenAppInfoService;
import com.open.custom.api.bean.CommonRequest;
import com.open.custom.api.bean.CommonResponse;
import com.open.custom.api.exception.BusiException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Description: 日志拦截器
 * Author: huxintao
 * Date: 2020-05-15
 */

@Aspect
@Component
public class ControllerAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Gson gson = new Gson();

    private static final Set<String> nConvert2Json = new HashSet<>();

    {
        nConvert2Json.add("com.open.custom.api.common.control.CommonRestController.upload");
    }

    @Autowired
    private IOpenAppInfoService iOpenAppInfoService;


    @Autowired
    private IOpenApiAccessService iOpenApiAccessService;


    /**
     * Controller aspect.
     */
    @Pointcut("execution(public * com.open.custom.api.*.control.*Controller.*(..))")
    public void controllerAspect() {
    }

    @Pointcut("execution(public * com.open.custom.api.user.control.*Controller.*(..))")
    public void userControllerAspect() {
    }

    /**
     * Around 手动控制调用核心业务逻辑，以及调用前和调用后的处理,
     * <p>
     * 注意：当核心业务抛异常后，立即退出，转向AfterAdvice 执行完AfterAdvice，再转到ThrowingAdvice
     *
     * @param pjp the pjp
     * @return object
     * @throws Throwable the throwable
     */
    @Around(value = "controllerAspect()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        //防止不是http请求的方法，例如：scheduled
        if (ra == null || sra == null) {
            return pjp.proceed();
        }

        long startTime = System.currentTimeMillis();
        Object response = null;

        String requestStr = "";
        String responseStr = "";

        String requestURL = null;
        String httpMethod = null;
        String remoteAddr = null;
        String classMethod = null;
        String appCode = null;
        String userCode = null;

        try {

            HttpServletRequest request = sra.getRequest();

            requestURL = request.getRequestURL().toString();
            httpMethod = request.getMethod();
            remoteAddr = request.getRemoteAddr();
            classMethod = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();

            // String appCode = request.getHeader("Authorization");

            logger.info("URL : " + requestURL);
            logger.info("HTTP_METHOD : " + httpMethod);
            logger.info("IP : " + remoteAddr);
            logger.info("CLASS_METHOD : " + classMethod);

            if (!nConvert2Json.contains(classMethod)) {
                Object[] args = pjp.getArgs();
                requestStr = gson.toJson(pjp.getArgs());
                logger.info("REQUEST ARGS : " + requestStr);

                Object arg = args[0];
                if (arg instanceof CommonRequest) {
                    // 获取 appCode userCode
                    CommonRequest commonRequest = (CommonRequest) arg;
                    appCode = commonRequest.getAppCode();
                    Object param = commonRequest.getParam();
                    if (param != null) {
                        Map map = gson.fromJson(gson.toJson(param), Map.class);
                        if (!CollectionUtils.isEmpty(map)) {
                            Object userCodeObj = map.get("userCode");
                            userCode = userCodeObj == null ? null : userCodeObj.toString();
                        }
                    }
                }
            } else {
                requestStr = Arrays.toString(pjp.getArgs());
                logger.info("REQUEST ARGS : " + requestStr);
                Enumeration<String> enu = request.getParameterNames();
                int i = 1;
                while (enu.hasMoreElements()) {
                    String paraName = (String) enu.nextElement();
                    String paraValue = request.getParameter(paraName);
                    logger.info("REQUEST ARGS " + i + " : " + paraName + "=" + paraValue);
                    i++;

                    // 获取 appCode userCode
                    if ("appCode".equals(paraName)) {
                        appCode = paraValue;
                    }
                    if ("userCode".equals("paraName")) {
                        userCode = paraValue;
                    }
                }
            }

            response = pjp.proceed();
        } catch (BusiException e) {
            response = new CommonResponse(e.getCode(), e.getMessage());
        } catch (Throwable e) {
            logger.error("RESPONSE ERROR : {}", Arrays.toString(e.getStackTrace()));
            response = new CommonResponse("999", "未知异常：" + e.getMessage());
        } finally {
            // 3.出参打印
            responseStr = response != null ? gson.toJson(response) : null;
            logger.info("RESPONSE : {}", responseStr);

            long endTime = System.currentTimeMillis();
            logger.info("SPEND TIME : {}ms\n", (endTime - startTime));

            // 记录接口日志
            saveAccess(httpMethod, remoteAddr, classMethod, requestStr, responseStr, appCode, userCode);
        }
        return response;
    }

    private void saveAccess(String httpMethod, String remoteAddr, String classMethod, String requestStr, String responseStr, String appCode, String userCode) {
        try {
            OpenApiAccessWithBLOBs record = new OpenApiAccessWithBLOBs();
            record.setApiMethod(classMethod.substring(classMethod.lastIndexOf(".") + 1));
            record.setApiMethodClass(classMethod);
            record.setApiRequest(requestStr);
            record.setApiResponse(responseStr);
            record.setClientIp(remoteAddr);
            record.setHttpMethod(httpMethod);
            record.setAccessDate(new Date());
            record.setAppCode(appCode);
            record.setUserCode(userCode);
            iOpenApiAccessService.insertSelective(record);
        } catch (Exception e) {
            logger.error("saveAccess catch Exception, {}", e);
        }
    }

    @Before(value = "userControllerAspect()")
    public void beforeAdvice(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (args == null || args[0] == null) {
            throw new BusiException("请求参数不能为空");
        }

        Object arg = args[0];
        if (arg instanceof CommonRequest) {
            CommonRequest commonRequest = (CommonRequest) arg;
            if (commonRequest == null) {
                throw new BusiException("请求参数不能为空");
            }
            String appCode = commonRequest.getAppCode();
            if (StringUtils.isEmpty(appCode)) {
                throw new BusiException("appCode不能为空");
            }
            iOpenAppInfoService.assertAppCode(appCode);
        }

//        String argsStr = gson.toJson(args[0]);

//        CommonRequest commonRequest = gson.fromJson(argsStr, CommonRequest.class);
//        if (commonRequest == null) {
//            throw new BusiException("请求参数不能为空");
//        }
//        String appCode = commonRequest.getAppCode();
//        if (StringUtils.isEmpty(appCode)) {
//            throw new BusiException("appCode不能为空");
//        }
//
//        OpenAppInfo openAppInfo = iOpenAppInfoService.assertAppCode(appCode);

//        Integer count = iOpenAppInfoService.checkAppExist(appCode);
//        if (count == null || count < 1) {
//            throw new BusiException("appCode不存在");
//        }

    }
}
