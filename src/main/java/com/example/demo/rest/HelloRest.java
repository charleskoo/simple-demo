package com.example.demo.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloRest {

    @Value("${application.name}")
    private String name;

    @GetMapping("hello")
    public  String sayHello(@RequestParam(required = false, name = "who") String who) {
        who = StringUtils.isEmpty(who) ? "World" : who;
        log.info("hello, {}", who);
        return String.format("Hello, %s", who);
    }

    @GetMapping("application-info")
    public  String getInfo() {
        return name;
    }

    @GetMapping("test-error")
    public  String error() {
        throw new NullPointerException();
    }
}
