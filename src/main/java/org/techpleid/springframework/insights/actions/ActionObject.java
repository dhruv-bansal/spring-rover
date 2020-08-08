package org.techpleid.springframework.insights.actions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * TODO add intent
 */
@Getter
@Slf4j
public class ActionObject {

    private String clazz;
    private String method;
    private String[] parametersName;
    Object[] parameteresValue;

    ActionObject(JoinPoint joinPoint) {

        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        log.debug("Target Method Signature {} ", signature);

        final Method method = signature.getMethod();

        this.clazz = method.getDeclaringClass().getName();
        this.method = method.getName();
        parametersName = signature.getParameterNames();
        this.parameteresValue = joinPoint.getArgs();
    }

}