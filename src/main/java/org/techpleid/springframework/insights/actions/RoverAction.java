package org.techpleid.springframework.insights.actions;

import org.aspectj.lang.JoinPoint;
import org.techpleid.springframework.insights.config.ActionConfiguration;

public interface RoverAction {

    void perform(ActionObject targetActionObject, ActionConfiguration.Config config);
}
