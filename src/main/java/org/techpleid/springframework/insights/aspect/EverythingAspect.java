package org.techpleid.springframework.insights.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.techpleid.springframework.insights.actions.ActionDelegator;
import org.techpleid.springframework.insights.actions.RoverAction;

/**
 * TODO
 *
 * @author Dhruv
 */
//@Aspect
//@EnableAspectJAutoProxy(proxyTargetClass = true)
@Aspect
@Component
@Slf4j
public class EverythingAspect {

    @Autowired
    private RoverAction roverAction;
    @Autowired
    private ActionDelegator actionDelegator;

    @Around("anyProxyExecution()")
    public void aroundAdvice(final JoinPoint joinpoint) {
        actionDelegator.execute(joinpoint);
    }


    @Pointcut("execution(public * *(..)) && " +
            "!execution(* org.springframework.context.annotation.*.*(..)) && " +
            "!within(is(FinalType)) && " +
            "!execution(* org.springframework.boot.autoconfigure.task.*.*(..)) &&" +
            "!execution(* org.techpleid.springframework.insights.aspect.EverythingAspect.*(..)) &&" +
            "!execution(* org.techpleid.springframework.insights.actions.*.*(..)) &&" +
            "!execution(* org.techpleid.springframework.insights.config.*.*(..)) &&" +

            //TODO: actions and all the sub packages
            "!execution(* org.techpleid.springframework.insights.actions.logging.*.*(..)) &&" +
            "!execution(* org.springframework.aop.*.*(..)) +" +
            // "!execution(* (@Configuration *).*(..)) &&" +
            "!execution(* (@Aspect *).*(..)) &&" +
            "!adviceexecution()")
//    @Pointcut("within(org.techpleid.springframework..*)")
//    @Pointcut(
//            "execution(* *..*(..)) && " +
//                    "!within(is(EnumType)) && " +
//                    "!within(is(FinalType)) &&"
//    )
    public void anyProxyExecution() {

    }
}
