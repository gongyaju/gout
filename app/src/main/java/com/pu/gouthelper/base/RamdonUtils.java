package com.pu.gouthelper.base;

import java.util.Random;

/**
 * Created by Administrator on 2016/2/2.
 */
public class RamdonUtils {
    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERCHAR = "0123456789";

    /**
     * 11.     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     * 12.     *
     * 13.     * @param length
     * 14.     *            随机字符串长度
     * 15.     * @return 随机字符串
     * 16.
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

}
