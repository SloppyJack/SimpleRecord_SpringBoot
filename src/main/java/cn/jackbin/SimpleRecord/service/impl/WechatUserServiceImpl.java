package cn.jackbin.SimpleRecord.service.impl;

import cn.jackbin.SimpleRecord.entity.UserDO;
import cn.jackbin.SimpleRecord.entity.WechatUserDO;
import cn.jackbin.SimpleRecord.mapper.WechatUserMapper;
import cn.jackbin.SimpleRecord.service.WechatUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service.impl
 * @date: 2020/11/3 21:34
 **/
@Service
public class WechatUserServiceImpl extends ServiceImpl<WechatUserMapper, WechatUserDO> implements WechatUserService {
    @Autowired
    private WechatUserMapper wechatUserMapper;

    @Override
    public WechatUserDO getByOpenId(String openId) {
        QueryWrapper<WechatUserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id",openId);
        return wechatUserMapper.selectOne(queryWrapper);
    }
}
