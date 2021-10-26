package cn.jackbin.SimpleRecord.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.service
 * @date: 2021/10/8 21:09
 **/
@Component
public class RecordDetailFactory {

    static Map<String, RecordDetailHandler> map = new HashMap<>();    // 容器池子

    /**
     * 获取记账的处理类
     */
    public RecordDetailHandler getHandler(String code) {
        return map.get(code);
    }

    public void addHandler(String code, RecordDetailHandler handler) {
        map.put(code, handler);
    }
}
