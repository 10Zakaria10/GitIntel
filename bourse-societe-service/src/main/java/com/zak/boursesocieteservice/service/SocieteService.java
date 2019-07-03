package com.zak.boursesocieteservice.service;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocieteService {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${me}")
    private String message;

    @RequestMapping("/messages")
    public String tellMe(){

        return message +discoveryClient.getOrder();
    }
}

