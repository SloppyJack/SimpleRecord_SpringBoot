package cn.jackbin.SimpleRecord.utils;

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

    /**
     * 生成随机的8为字符（含大小写及数字）
     */
    public static String randomPsw() {
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < 8; j++) {
            double rand = Math.random();
            double randTri = Math.random() * 3;
            if (randTri >= 0 && randTri < 1) {
                result.append((char) (rand * ('9' - '0') + '0'));
            } else if (randTri >= 1 && randTri < 2) {
                result.append((char) (rand * ('Z' - 'A') + 'A'));
            } else {
                result.append((char) (rand * ('z' - 'a') + 'a'));
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(PasswordUtil.randomPsw());
    }

}
