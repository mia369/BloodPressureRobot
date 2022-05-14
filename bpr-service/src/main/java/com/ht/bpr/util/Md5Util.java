package com.ht.bpr.util;

import com.ht.bpr.common.BprResultStatus;
import com.ht.bpr.exception.BprException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/11 10:35
 * @description
 */
public class Md5Util {
    public static String md5(String code) {
        byte[] codeBytes = null;
        try {
            codeBytes = MessageDigest.getInstance("md5").digest(code.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new BprException(BprResultStatus.ILLEGAL_MD5_ALGORITHM);
        }
        StringBuilder md5code = new StringBuilder(new BigInteger(1, codeBytes).toString(16));
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code.insert(0, "0");
        }
        return md5code.toString();
    }
}


