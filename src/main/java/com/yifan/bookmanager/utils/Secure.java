package com.yifan.bookmanager.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;

/**
 * 加密
 */
public class Secure {

    /**
     * md5加密
     * @param data 需加密数据
     * @return 加密后的16进制md5字符串
     */
    public static String md5Encryption(String data){
        if(!StrUtil.isBlank(data)){
            return SecureUtil.md5(data);
        }
        throw  new RuntimeException("参数错误");
    }

}
