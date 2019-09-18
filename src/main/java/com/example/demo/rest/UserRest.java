package com.example.demo.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.LegacyHashing;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.Query;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("user")
public class UserRest {

    @Autowired
    private UserService userService;

    @GetMapping("get")
    @Cacheable(value = "user", key = "#id")
    public User get(@RequestParam("id") Long id) {
        log.info("查询用户【id】= {}", id);
        User user = userService.getById(id);
        return user;
    }

    @GetMapping("list")
    public List<User> list() {
        List<User> userList = userService.list(new QueryWrapper<>());
        return userList;
    }

    @GetMapping("page")
    public List<User> list(@RequestParam("id") Integer id, @RequestParam("page") Integer page, @RequestParam("page_size") Integer page_size) {
        Page<User> userPage = new Page<>(page, page_size);
        userPage.setAsc("id");

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name", "user_2").or().eq("id", id);

        IPage<User> result = userService.page(userPage, wrapper);
        return result.getRecords();
    }

    @PutMapping("insert")
    public User insert(@RequestBody User user) {
        Objects.nonNull(user.getName());
        Objects.nonNull(user.getPassword());
        Objects.nonNull(user.getEmail());

        String salt = UUID.randomUUID().toString();
        String passwordWithSalt = user.getPassword() + salt;
        String passwordmd5 = LegacyHashing.md5().hashBytes(passwordWithSalt.getBytes()).toString();

        user.setSalt(salt);
        user.setPassword(passwordmd5);
        user.setStatus(1);

        boolean save = userService.save(user);
        return get(user.getId());
    }

    @PostMapping("update")
    public User editUserName(@RequestBody User user) {
        boolean result = userService.updateById(user);
        return get(user.getId());
    }

    @DeleteMapping("delete")
    public String deleteById(@RequestBody User user) {
        boolean result = userService.removeById(user);
        return "Success";
    }
}
