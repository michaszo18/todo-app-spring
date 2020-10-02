package com.example.todoapp.controller;

import com.example.todoapp.TaskConfigurationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    private DataSourceProperties dataSource;
    private TaskConfigurationProperties myProp;

    @Value("${spring.profiles.active}")
    private String activeProfile;


    public InfoController(DataSourceProperties dataSource, TaskConfigurationProperties myProp) {
        this.dataSource = dataSource;
        this.myProp = myProp;
    }

    @GetMapping("/info/mode")
    String mode() {
        return activeProfile;
    }

    @GetMapping("/info/url")
    String url() {
        return dataSource.getUrl();
    }

    @GetMapping("/info/prop")
    boolean myProp() {
        return myProp.getTemplate().isAllowMultipleTasks();
    }

}
