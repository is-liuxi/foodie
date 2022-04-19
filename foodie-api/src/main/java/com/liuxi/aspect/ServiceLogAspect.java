package com.liuxi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * <p>
 * service 层日志监控时间
 * </P>
 * @author liu xi
 * @date 2022/4/18 1:22
 */
@Component
@Aspect
@Slf4j
public class ServiceLogAspect {

    /**
     * AOP通知：
     * 1、前置通知：在方法调用之前执行
     * 2、后置通知：在方法正常调用之后执行
     * 3、环绕通知：在方法调用之前和之后，都分别执行
     * 4、异常通知：如果方法调用过程中发生异常，通知
     * 5、最终通知：在方法调用之后执行
     */
    /**
     * * 返回类型，返回值
     * com.liuxi.service.impl..*：impl及子包下所有的类
     * .*(..)：类中所有方法，任意参数
     */
    @Pointcut("execution(* com.liuxi.service.impl..*.*(..)))")
    public void pointcut() {

    }

    /**
     * 环绕通知，输出 service 中的方法耗时
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("===========开始执行：{}.{}===========",
                // 类名
                joinPoint.getTarget().getClass(),
                // 方法名
                joinPoint.getSignature().getName());
        // 开始时间
        long startTime = System.currentTimeMillis();
        // 执行目标方法
        Object proceed = joinPoint.proceed();
        long runTime = System.currentTimeMillis() - startTime;

        // 根据执行耗时，输出不同日志级别的日志
        if (runTime > 3000) {
            log.error("===========执行 {}.{} 耗时：{}===========", joinPoint.getTarget().getClass(),
                    joinPoint.getSignature().getName(), runTime);
        } else if (runTime > 2000) {
            log.warn("===========执行 {}.{} 耗时：{}===========", joinPoint.getTarget().getClass(),
                    joinPoint.getSignature().getName(), runTime);
        } else {
            log.info("===========执行 {}.{} 耗时：{}===========", joinPoint.getTarget().getClass(),
                    joinPoint.getSignature().getName(), runTime);
        }
        return proceed;
    }
}
