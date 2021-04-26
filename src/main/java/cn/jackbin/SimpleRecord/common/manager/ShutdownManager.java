package cn.jackbin.SimpleRecord.common.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.common.manager
 * @date: 2021/4/8 21:31
 **/
@Slf4j
@Component
public class ShutdownManager {

    /**
     * 实例在容器销毁前调用
     */
    @PreDestroy
    public void destroy()
    {
        shutdownAsyncManager();
    }

    /**
     * 停止异步执行任务
     */
    private void shutdownAsyncManager()
    {
        try
        {
            log.info("====关闭后台任务任务线程池====");
            if (AsyncManager.me() != null) {
                AsyncManager.me().shutdown();
            }
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
        }
    }
}
