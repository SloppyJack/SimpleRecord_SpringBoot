package cn.jackbin.SimpleRecord.common;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.common
 * @date: 2020/11/23 20:21
 **/
public class LocalUserId {
    private static final ThreadLocal<Long> local = new ThreadLocal<>();

    /**
     * 得到当前登录用户Id
     *
     * @return userId | null
     */
    public static Long get() {
        return LocalUserId.local.get();
    }

    /**
     * 设置登录用户Id
     */
    public static void set(Long userId) {
        LocalUserId.local.set(userId);
    }

    /**
     * 清理当前用户Id
     */
    public static void clear() {
        LocalUserId.local.remove();
    }
}
