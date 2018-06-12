package com.baizhi.util;

import java.util.Random;

public class SaltUtil {

    private static String source = "abcdefghijklmnopqrestuvwxyzABCDEFGHIGKLMNOPQRETUVWXYZ-+*/<>?~!@#$%^&*()-=";

    public static String getSalt() {
        String salt = "";
        char[] ch = source.toCharArray();
        for (int i = 0; i < 4; i++) {
            int index = new Random().nextInt(ch.length);
            salt += ch[index];
        }
        return salt;

    }

}
