package cn.jackbin.SimpleRecord.utils;

import cn.jackbin.SimpleRecord.constant.Constants;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.utils
 * @date: 2021/4/8 21:36
 **/
@Slf4j
public class AddressUtil {

    // IP地址查询
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    // 未知地址
    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip)
    {
        String address = UNKNOWN;
        // 内网不查询
        if (IpUtil.internalIp(ip))
        {
            return "内网IP";
        }
        try
        {
            Map<String, String> map = new HashMap<>();
            map.put("ip", ip);
            map.put("json", "true");
            String rspStr = HttpUtil.doGet(IP_URL, map);
            if (StringUtil.isEmpty(rspStr))
            {
                log.error("获取地理位置异常 {}", ip);
                return UNKNOWN;
            }
            JSONObject obj = JSONObject.parseObject(rspStr);
            String region = obj.getString("pro");
            String city = obj.getString("city");
            return String.format("%s %s", region, city);
        }
        catch (Exception e)
        {
            log.error("获取地理位置异常 {}", e.getMessage());
        }
        return address;
    }
}
