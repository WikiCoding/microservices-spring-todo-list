package com.wikicoding.todo_list_service_discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TodoListServiceDiscovery {
    public static void main(String[] args) {
        SpringApplication.run(TodoListServiceDiscovery.class, args);
    }
}