package org.techpleid.springframework.insights.actions.integrationtest.testpackage1.beans;

import org.springframework.stereotype.Component;

@Component
public class TestSimpleBean2 {

    public SimpleBeanOutput simpleBehaviour(SimpleBeanInput beanInput) {
        beanInput.increamentCounter();
        beanInput.setSimpleString("modifiedString");
        SimpleBeanOutput simpleBeanOutput = SimpleBeanOutput.builder()
                .simpleString(beanInput.getSimpleString())
                .count(beanInput.getCount())
                .build();
        return simpleBeanOutput;
    }
}
