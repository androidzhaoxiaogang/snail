package com.snail.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5 {

    /**
     * 把字符串进行MD5加密
     *
     * @param string 字符串
     * @return MD5加密后的字符串
     */
    public static String defaultEncode(String string) {
        StringBuilder sb = new StringBuilder(32);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashValue = md.digest(string.getBytes());
            for (int i = 0; i < hashValue.length; i++) {
                sb.append(Integer.toHexString((hashValue[i] & 0xf0) >> 4));
                sb.append(Integer.toHexString(hashValue[i] & 0x0f));
            }
        } catch (NoSuchAlgorithmException e) {
        }
        return sb.toString();
    }

}
