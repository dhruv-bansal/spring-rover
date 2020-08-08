package org.techpleid.springframework.insights.actions.integrationtest.testpackage1.beans;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SimpleBeanInput {
    private Integer count;
    private String simpleString;

    public SimpleBeanInput() {
        count = 0;
        simpleString = "somestring";
    }

    public Integer increamentCounter() {
        return this.count++;
    }

    public void setSimpleString(String simpleString) {
        this.simpleString = simpleString;
    }
}
