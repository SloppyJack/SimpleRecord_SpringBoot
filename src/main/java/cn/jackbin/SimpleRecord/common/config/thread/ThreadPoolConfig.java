package cn.jackbin.SimpleRecord.common.config.thread;

import cn.jackbin.SimpleRecord.utils.ThreadUtil;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.common.config.thread
 * @date: 2021/4/12 20:45
 **/
@Configuration
public class ThreadPoolConfig {
    // 核心线程池大小
    private final int corePoolSize = 50;

    // 最大可创建的线程数
    private final int maxPoolSize = 200;

    // 队列最大长度
    private final int queueCapacity = 1000;

    // 线程池维护线程所允许的空闲时间
    private final int keepAliveSeconds = 60;

    @Bean(name = "threadPoolExecutor")
    public ThreadPoolExecutor threadPoolExecutor()
    {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveSeconds,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(queueCapacity), new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    /**
     * 执行周期性或定时任务
     */
    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduledExecutorService()
    {
        return new ScheduledThreadPoolExecutor(corePoolSize,
                new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build())
        {
            @Override
            protected void afterExecute(Runnable r, Throwable t)
            {
                super.afterExecute(r, t);
                ThreadUtil.printException(r, t);
            }
        };
    }
}
