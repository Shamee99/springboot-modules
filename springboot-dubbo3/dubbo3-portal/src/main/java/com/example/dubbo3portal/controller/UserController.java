package com.example.dubbo3portal.controller;


import com.example.dubbo3api.service.IUserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @DubboReference
    private IUserService userService;


    @GetMapping("sayHello/{name}")
    public String sayHello(@PathVariable String name){
        return userService.sayHello(name);
    }


}
