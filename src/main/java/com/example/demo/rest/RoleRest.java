package com.example.demo.rest;


import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("role")
public class RoleRest {

    @GetMapping("get")
    public Role get(@RequestParam("id") Long id) {
        Role role = new Role().selectById(id);
        return role;
    }
}
