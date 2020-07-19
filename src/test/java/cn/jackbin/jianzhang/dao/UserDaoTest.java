package cn.jackbin.jianzhang.dao;

import cn.jackbin.jianzhang.model.UserDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.jianzhang.dao
 * @date: 2020/7/19 15:53
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void selectUserById() {
        UserDO userDO = userDao.selectById(1);
        System.out.println(userDO.getUsername());
    }
}
