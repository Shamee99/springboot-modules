package org.shamee.demo.controller;

import org.shamee.demo.entity.User;
import org.shamee.demo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/demo/user")
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 查询全部
     * @return
     */
    @GetMapping("listall")
    public List<User> listall(){
        return userService.selectAll();
    }

    /**
     * 按userId查询
     * @return
     */
    @GetMapping("listById")
    public User listById(){
        return userService.listById("zhangsan");
    }

    /**
     * 新增
     * @return
     */
    @GetMapping("insert")
    public Boolean insert(){
//        User user = User.builder().userId("zhangsan1").userName("张三1").atk(100).battleNum(200).isUse(false).build();
//        userService.insert(user);
//        User user2 = User.builder().userId("zhangsan2").userName("张三2").atk(100).battleNum(200).isUse(true).build();
//        userService.insert(user2);
//        User user3 = User.builder().userId("zhangsan3").userName("张三3").atk(100).battleNum(200).isUse(true).build();
        User user = new User();
        user.setUserName("阿三发射点");
        userService.save(user);
        return Boolean.TRUE;
    }

    /**
     * 更新
     * @return
     */
    @GetMapping("update")
    public Boolean update(){
        userService.updateRow("zhangsan", "张三三");
        return Boolean.TRUE;
    }

    /**
     * 删除
     * @return
     */
    @GetMapping("delete")
    public Boolean delete(){
        userService.deleteByWrapper("张三三");
        return Boolean.TRUE;
    }
}
