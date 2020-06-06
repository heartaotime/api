package com.open.custom.api.aspect;


import com.google.gson.Gson;
import com.open.custom.api.app.model.OpenAppInfo;
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
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

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
        HttpServletRequest request = sra.getRequest();

        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        String classMethod = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();
        logger.info("CLASS_METHOD : " + classMethod);

        if (!nConvert2Json.contains(classMethod)) {
            logger.info("REQUEST ARGS : " + gson.toJson(pjp.getArgs()));
        } else {
            logger.info("REQUEST ARGS : " + Arrays.toString(pjp.getArgs()));
            Enumeration<String> enu = request.getParameterNames();
            int i = 1;
            while (enu.hasMoreElements()) {
                String paraName = (String) enu.nextElement();
                String parameter = request.getParameter(paraName);
                logger.info("REQUEST ARGS " + i + " : " + paraName + "=" + request.getParameter(paraName));
                i++;
            }
        }

        long startTime = System.currentTimeMillis();
        Object response = null;
        try {
            response = pjp.proceed();
        } catch (BusiException e) {
            response = new CommonResponse(e.getCode(), e.getMessage());
        } catch (Throwable e) {
            logger.error("RESPONSE ERROR : {}", Arrays.toString(e.getStackTrace()));
            response = new CommonResponse("999", "未知异常：" + e.getMessage());
        } finally {
            // 3.出参打印
            logger.info("RESPONSE : {}", response != null ? gson.toJson(response) : "");

            long endTime = System.currentTimeMillis();
            logger.info("SPEND TIME : {}ms\n", (endTime - startTime));
        }
        return response;
    }

    @Before(value = "userControllerAspect()")
    public void beforeAdvice(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (args == null || args[0] == null) {
            throw new BusiException("请求参数不能为空");
        }

        String argsStr = gson.toJson(args[0]);

        CommonRequest commonRequest = gson.fromJson(argsStr, CommonRequest.class);
        if (commonRequest == null) {
            throw new BusiException("请求参数不能为空");
        }
        String appCode = commonRequest.getAppCode();
        if (StringUtils.isEmpty(appCode)) {
            throw new BusiException("appCode不能为空");
        }

        OpenAppInfo openAppInfo = iOpenAppInfoService.assertAppCode(appCode);

//        Integer count = iOpenAppInfoService.checkAppExist(appCode);
//        if (count == null || count < 1) {
//            throw new BusiException("appCode不存在");
//        }

    }
}
