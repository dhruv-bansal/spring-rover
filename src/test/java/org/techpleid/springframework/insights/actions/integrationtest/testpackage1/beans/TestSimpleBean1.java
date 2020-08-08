package org.techpleid.springframework.insights.actions.integrationtest.testpackage1.beans;

import org.springframework.stereotype.Component;

@Component
public class TestSimpleBean1 {

    /**
     * This is the simple test behaviour to increment counter in some input spring bean.
     *
     * @param beanInput
     * @return Incremented value
     */
    public Integer incrementCounter(SimpleBeanInput beanInput) {
        return beanInput.increamentCounter();
    }
}
