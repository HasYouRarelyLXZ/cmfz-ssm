package com.baizhi.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    /**
     * md5
     *
     * @param password
     * @return
     */
    public static String getMd5Code(String password, String salt) {
        try {
            // java.secutiry
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            String pass = password + salt;

            byte[] digest = messageDigest.digest(pass.getBytes());
            StringBuilder sb = new StringBuilder();
            // byte -128~~~127 0~~255
            for (byte b : digest) { // 0x0-0 0x1-1 0xa-10 0xf 15 0x10-16 0x11-17
                int c = b & 0xff;
                if (c < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(c));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;

    }
}

