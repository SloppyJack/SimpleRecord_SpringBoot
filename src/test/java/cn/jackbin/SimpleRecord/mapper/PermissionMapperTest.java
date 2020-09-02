package cn.jackbin.SimpleRecord.mapper;

import cn.jackbin.SimpleRecord.entity.PermissionDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.mapper
 * @date: 2020/8/19 21:36
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class PermissionMapperTest {
    @Autowired
    private PermissionMapper mapper;

    @Test
    public void queryPermissionByUserId() {
        List<PermissionDO> list = mapper.queryPermissionByUserId(2L);
        Assert.assertEquals(5,list.size());
    }
}