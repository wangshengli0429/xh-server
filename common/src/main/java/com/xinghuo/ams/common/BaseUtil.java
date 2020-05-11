package com.xinghuo.ams.common;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName BaseUtil.java
 * @Description 请填写注释...
 * @author Libin.Wei
 * @Date 2018年10月31日 下午5:12:49
 * @version 1.0.0
 * @Copyright 2016 www.jumapeisong.com Inc. All rights reserved.
 */

public class BaseUtil {

    private final static Pattern MOBILEPHONE = Pattern.compile("^1[3456789]\\d{9}$");

    /**
     * 
     * 生成编码(毫秒级时间戳 + 租户ID +6位随机数，然后MD5加密)
     * 
     * @author Libin.Wei
     * @Date 2018年10月31日 下午5:14:05
     * @return
     */
    public static String generationCoding(Integer tenantId) {
        long millis = System.currentTimeMillis();
        Integer random = ThreadLocalRandom.current().nextInt(10000, 99999);
        return DigestUtils.md5Hex(millis + tenantId + random + "");
    }

    /**
     * 
     * @Description 验证手机号格式
     * @author Libin.Wei
     * @Date 2017年1月16日 下午1:31:18
     * @param phone
     *            手机号码
     * @return 手机号为空的时候返回false
     */
    public static boolean checkMobilePhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return false;
        }

        Matcher matcher = MOBILEPHONE.matcher(phone);
        return matcher.matches();
    }
}
