package com.infoac.cas.util;

import com.infoac.cas.dto.SystemLog;
import com.infoac.cas.dto.UserDTO;
import com.infoac.cas.entity.Client;
import com.infoac.cas.service.SystemLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class SystemLogAspect {
    @Autowired
    private SystemLogService systemLogService;

    private final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    //定义一个方法作为切入点id
    @Pointcut("@annotation(com.infoac.cas.util.Log)")
    private void allMethod() {
    }

    @After("allMethod()")
    public void myBeforeAdivice(JoinPoint joinPoint) throws Throwable {
        //HttpServletRequest request  = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        //获取session中的用户
        HttpSession session = ContextHolderUtils.getSession();
        Client clinet = ClientManager.getInstance().getClient(session.getId());
        if (clinet != null) {
            UserDTO user = clinet.getUser();
            //获取方法签名
            MethodSignature methodName = (MethodSignature) joinPoint.getSignature();
            //获取方法
            Method method = methodName.getMethod();
            //获取方法的注解
            Log log = method.getAnnotation(Log.class);
            //获取操作的描述属性
            String operatetype = log.operateType();
            SystemLog systemLog = new SystemLog();
            //创建一个日志对象(保存日志)
            systemLog.setOperatetype(operatetype);//操作属性
            systemLog.setOperateor(user.getUsername());//操作用户
            systemLog.setId(user.getId());
            //设置操作日期
            //Date date = new Date();
            //时间格式化
            //SimpleDateFormat dateFormat =   new SimpleDateFormat("yyyy-MM-dd");
            String date = DateUtil.timestampFormat(new Date());
            systemLog.setOperatedate(date);
            systemLogService.insert(systemLog);
        }

    }
}
