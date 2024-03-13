package com.example.springbootmapstruct.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.example.springbootmapstruct.convert.UserConvert;
import com.example.springbootmapstruct.entity.User;
import com.example.springbootmapstruct.vo.UserVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestUserController {

    @GetMapping("test")
    public UserVo test(){
        User user = new User();
        user.setName("张三");
        user.setAge(20);
        user.setPassword("123456");
        user.setCreateTime(DateUtil.date());

        List<String> memoList = new ArrayList<>();
        memoList.add("好人1");
        memoList.add("好人2");
        memoList.add("好人3");
        user.setMemo(JSONUtil.toJsonStr(memoList));

        return UserConvert.INSTANCE.entity2Vo(user);

    }

}
