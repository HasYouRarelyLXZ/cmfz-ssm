package com.baizhi.aop;

import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Log;
import com.baizhi.entity.TAdmin;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.omg.PortableServer.ServantActivatorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Around implements MethodInterceptor {
    @Autowired
    private LogMapper logMapper;

    @Override
    public Object invoke(MethodInvocation invocation) throws ParseException {
        //什么人
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = requestAttributes.getRequest().getSession();
        //什么人
        TAdmin Tuser = (TAdmin) session.getAttribute("user");
        String userName = Tuser.getName();
        //什么时间
        long time = new Date().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fmt = format.format(time);
        Date dateFormat = format.parse(fmt);
        //执行了什么操作
        Method method = invocation.getMethod();
        LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);
        String transaction = annotation.name();
        boolean flag = false;
        //原始方法执行之后的结果  返回值
        Object proceed = null;
        try {
            proceed = invocation.proceed();

            System.out.println(userName + "在" + dateFormat + "执行了" + transaction + "操作" + ",结果是" + proceed);
            flag = true;
        } catch (Throwable throwable) {
            flag = false;
        }
        if (flag) {
            logMapper.insertSelective(new Log(UUID.randomUUID().toString().replaceAll("-", ""), userName, dateFormat, transaction, "成功"));
        } else {
            logMapper.insertSelective(new Log(UUID.randomUUID().toString().replaceAll("-", ""), userName, dateFormat, transaction, "失败"));
        }
        return proceed;
    }
}
