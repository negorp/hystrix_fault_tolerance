package com.example.test;

import com.example.test.service.TestService;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixCommandProperties;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class TestServiceApplicationTests {

    @Autowired
    private TestService myService;

    @Before
    public void setup() {
        warmUpCircuitBreaker();
    }

    @Test
    public void shouldHaveCustomTimeout() {
        assertTrue(getCircuitBreakerCommandProperties().executionTimeoutInMilliseconds().get() == 2016);
    }

    private void warmUpCircuitBreaker() {
        myService.processPage("");
    }

    public static HystrixCommandProperties getCircuitBreakerCommandProperties() {
        return HystrixCommandMetrics.getInstance(getCommandKey()).getProperties();
    }

    private static HystrixCommandKey getCommandKey() {
        return HystrixCommandKey.Factory.asKey(TestService.COMMAND_KEY);
    }

}
