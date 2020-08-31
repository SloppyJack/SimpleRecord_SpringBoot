package cn.jackbin.SimpleRecord.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Pattern;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.util
 * @date: 2020/8/31 20:20
 **/
public class PasswordUtil {
    private static final Pattern BCRYPT_PATTERN = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static boolean check(String psw) {
        return BCRYPT_PATTERN.matcher(psw).matches();
    }

    public static String encoder(String psw) {
        return encoder.encode(psw);
    }

}
