package org.techpleid.springframework.insights.actions.logging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.techpleid.springframework.insights.actions.ActionDelegator;
import org.techpleid.springframework.insights.actions.ActionObject;
import org.techpleid.springframework.insights.actions.RoverAction;
import org.techpleid.springframework.insights.config.ActionConfiguration;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * TODO
 */
@Component
@Slf4j
@AllArgsConstructor
public class LoggingRoverAction implements RoverAction {

    private final Logger logger = LoggerFactory.getLogger("spring.rover.logger");


    @Override
    public void perform(ActionObject targetActionObject, ActionConfiguration.Config config) {

        if (config.isLogInputParameters()) {
            String logStatement = getLogStatementForInputParameters(targetActionObject);
            logger.info(logStatement);
        }

        if (config.isLogReturnType()) {

        }
    }

    /**
     * This method builds the log statment for the input parameters of given class and method {@See ActionObject}
     *
     * @param actionObject
     * @return
     */
    private String getLogStatementForInputParameters(ActionObject actionObject) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Logging Parameters ");

        String[] parametersName = actionObject.getParametersName();
        for (int i = 0; i < parametersName.length; i++) {

            stringBuilder.append(parametersName)
                    .append(" - ")
                    .append(String.valueOf(actionObject.getParameteresValue()[i]))
                    .append(" ");
        }
        return stringBuilder.toString();
    }

//    public String getLogStatementForReturnedParameters(ActionObject actionObject) {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("Logging returned parameter ")
//    }
}
