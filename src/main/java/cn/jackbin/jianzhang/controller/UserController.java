package cn.jackbin.jianzhang.controller;

import cn.jackbin.jianzhang.dao.UserDao;
import cn.jackbin.jianzhang.model.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.jianzhang.controller
 * @date: 2020/7/20 20:59
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDao userDao;

    @GetMapping("/{id}")
    public UserDO getUserById(@PathVariable(value = "id") int id) {
        UserDO userDO = userDao.selectById(id);
        return userDO;
    }
}
