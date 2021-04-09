package cn.jackbin.SimpleRecord.common.manager.factory;

import cn.jackbin.SimpleRecord.entity.CommonLogDO;
import cn.jackbin.SimpleRecord.service.CommonLogService;
import cn.jackbin.SimpleRecord.utils.AddressUtil;
import cn.jackbin.SimpleRecord.utils.SpringContextUtil;

import java.util.TimerTask;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 异步工厂类
 * @date: 2021/4/8 21:33
 **/
public class AsyncFactory {

    /**
     * 操作日志记录
     * @param logDO 日志信息
     * @return 任务task
     */
    public static TimerTask recordCommonLog(final CommonLogDO logDO) {
        return new TimerTask() {
            @Override
            public void run() {
                // 远程查询操作地点
                logDO.setOperAddress(AddressUtil.getRealAddressByIP(logDO.getOperIp()));
                SpringContextUtil.getBean(CommonLogService.class).save(logDO);
            }
        };
    }
}
