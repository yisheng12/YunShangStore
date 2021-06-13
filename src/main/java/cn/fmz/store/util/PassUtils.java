package cn.fmz.store.util;

import org.springframework.util.DigestUtils;

/**
 * 密码加密工具
 *
 * @data 2021/06/13
 */
public class PassUtils {

    /**
     * 执行md5加密
     *
     * @param password 原密码
     * @param salt     盐值
     * @return 加密后的结果
     */
    public static String getMd5Password(String password, String salt) {
        // 加密规则：
        // 在密码的左右两侧各拼接一次盐值
        // 执行5次加密
        String str = salt + password + salt;
        for (int i = 0; i < 5; i++) {
            str = DigestUtils
                    .md5DigestAsHex(str.getBytes())
                    .toUpperCase();
        }
        return str;
    }

}
