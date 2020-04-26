package com.hustcaid.myshoppingmanagement.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/******************************************************************************
 *  @author Caid Wang
 *  @date 2020/3/28   
 *
 ******************************************************************************/
@Slf4j
@Aspect
@Component // 重要 也要注册成bean
public class LogAspect {
    @Pointcut("execution(* com.hustcaid.myshoppingmanagement.dao.*.*(..))")
    public void visitDao() {

    }

    @Pointcut("execution(* com.hustcaid.myshoppingmanagement.controller.*.*(..))")
    public void visitPage() { // 不是bean 并不起作用

    }

    @Before(value = "visitDao() || visitPage()")
    public void doLog(JoinPoint joinPoint) {
        log.debug("visit " + joinPoint.getSignature().getName());
    }
}
