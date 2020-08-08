package org.techpleid.springframework.insights.actions;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.techpleid.springframework.insights.actions.matcher.ActionTargetMatcher;
import org.techpleid.springframework.insights.config.ActionConfiguration;

import java.util.List;

@Component
@Slf4j
public class ActionDelegator {

    @Autowired
    private ActionConfiguration configuration;


    // client can implement any implementation of {@link RoverAction}
    @Autowired
    private List<RoverAction> roverAction;

    @Autowired
    private ActionTargetMatcher actionTargetMatcher;

    public void execute(JoinPoint joinPoint) {

        log.trace("Creating action object");
        ActionObject targetActionObject = new ActionObject(joinPoint);

        boolean isMatched = actionTargetMatcher.match(targetActionObject);
        if (isMatched) {
            ActionConfiguration.Config config = actionTargetMatcher.getTargetedConfiguration()
                    .get(targetActionObject.getClazz()).get(targetActionObject.getMethod());

            for (RoverAction action : roverAction) {
                action.perform(targetActionObject, config);
            }
        }

    }
}
