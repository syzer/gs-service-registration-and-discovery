package com.example.serviceregistrationanddiscoveryclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
public class ServiceRegistrationAndDiscoveryClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceRegistrationAndDiscoveryClientApplication.class, args);
    }
}

class Example {
    private String color;
    private String type;
}

@RestController
class ServiceInstanceRestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String json = "{ \"color\" : \"Red\", \"type\" : \"CacheEvent\" }";
            Example example = objectMapper.readValue(json, Example.class);

            String jsonRepresentation = objectMapper.writeValueAsString(example);
            System.out.println(jsonRepresentation);
        }
        catch(Exception e) {
            //  Block of code to handle errors
        }

        System.out.println("=====================");

        return this.discoveryClient.getInstances(applicationName);
    }
}
