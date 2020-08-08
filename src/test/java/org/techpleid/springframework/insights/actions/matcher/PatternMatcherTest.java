package org.techpleid.springframework.insights.actions.matcher;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.techpleid.springframework.insights.actions.ActionObject;
import org.techpleid.springframework.insights.config.ActionConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PatternMatcherTest {

    @Mock
    private ActionConfiguration configuration;
    @Mock
    private ActionObject actionObject;

    @InjectMocks
    private PatternMatcher patternMatcher;

}