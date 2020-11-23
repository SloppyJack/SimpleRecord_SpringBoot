package cn.jackbin.SimpleRecord.common;

import cn.jackbin.SimpleRecord.entity.UserDO;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 线程安全的当前登录用户，如果用户为登录，则得到 null
 * @date: 2020/7/28 21:17
 **/
public class LocalUser {
    private static final ThreadLocal<UserDO> local = new ThreadLocal<>();

    /**
     * 得到当前登录用户
     *
     * @return user | null
     */
    public static UserDO get() {
        return LocalUser.local.get();
    }

    /**
     * 设置登录用户
     */
    public static void set(UserDO user) {
        LocalUser.local.set(user);
    }

    /**
     * 清理当前用户
     */
    public static void clear() {
        LocalUser.local.remove();
    }
}
