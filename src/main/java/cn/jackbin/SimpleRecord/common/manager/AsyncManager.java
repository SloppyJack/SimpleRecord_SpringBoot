package cn.jackbin.SimpleRecord.common.manager;

import cn.jackbin.SimpleRecord.utils.SpringContextUtil;
import cn.jackbin.SimpleRecord.utils.ThreadUtil;

import java.util.TimerTask;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 异步任务管理器
 * @date: 2021/4/8 21:25
 **/
public class AsyncManager {
    /**
     * 操作延迟10毫秒
     */
    private final int OPERATE_DELAY_TIME = 10;

    /**
     * 异步延时任务调度线程池
     */
    private final ScheduledExecutorService scheduledExecutorService = SpringContextUtil.getBean("scheduledExecutorService");

    private final ThreadPoolExecutor poolExecutor = SpringContextUtil.getBean("threadPoolExecutor");

    /**
     * 单例模式
     */
    private AsyncManager(){}

    private static final AsyncManager me = new AsyncManager();

    public static AsyncManager me()
    {
        return me;
    }

    /**
     * 执行延时任务
     *
     * @param task 任务
     */
    public void schedule(TimerTask task)
    {
        scheduledExecutorService.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 执行延时任务
     *
     * @param task 任务
     */
    public void schedule(TimerTask task, long delay)
    {
        scheduledExecutorService.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 异步执行任务
     */
    public void execute(Runnable task) {
        poolExecutor.execute(task);
    }

    /**
     * 异步提交任务
     */
    public Future<?> submit(Runnable task) {
        return poolExecutor.submit(task);
    }

    /**
     * 停止任务线程池
     */
    public void shutdown()
    {
        ThreadUtil.shutdownAndAwaitTermination(scheduledExecutorService);
        ThreadUtil.shutdownAndAwaitTermination(poolExecutor);
    }
}
