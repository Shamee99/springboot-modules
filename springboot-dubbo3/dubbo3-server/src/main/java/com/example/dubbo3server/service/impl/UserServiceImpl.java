package com.example.dubbo3server.service.impl;

import com.example.dubbo3api.service.IUserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Service;

@DubboService
@Service
public class UserServiceImpl implements IUserService {

    @Override
    public String sayHello(String name){
        return name + "say hello, my bro.";
    }
}
