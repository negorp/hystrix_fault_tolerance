package com.example.test.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class TestService {
    public static final String COMMAND_KEY = "TestCommandKey";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(commandKey = COMMAND_KEY, fallbackMethod = "processPageFallback")
    public String processPage(String url) {

        String response = restTemplate.exchange(url
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<String>() {
                }).getBody();

        System.out.println("Response Received as " + response + " -  " + new Date());

        return "All ok we are in " + url;
    }

    public String processPageFallback(String url) {
        url = "www.yahoo.com";
        System.out.println("Falling back to " + url);
        String response = restTemplate.exchange("http://www.yahoo.com"
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<String>() {
                }).getBody();

        System.out.println("Response Received as " + response + " -  " + new Date());

        return "Failed falling back to " + url;
    }


}
