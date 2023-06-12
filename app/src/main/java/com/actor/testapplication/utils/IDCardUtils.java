package com.actor.testapplication.utils;

/**
 * 身份证工具类
 * TODO 计算年龄, 提取生日, 判断性别, 判断地区
 */
public class IDCardUtils {

    //系数x17
    protected static final short[] COEFFICIENTS = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    //最后1位x11, 0~10, 10就是X
    protected static final short[] LAST_NUMS = {1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    /**
     * 传入17/18位身份证号, 返回计算的最后1位
     * @param idCardNum 17/18位身份证号
     * @return 计算的最后1位, 0~10, 10就是X
     */
    public static short calcLastNum(String idCardNum) {
        int count = 0;
        if (idCardNum == null || idCardNum.length() < COEFFICIENTS.length) return -1;
        for (int i = 0; i < COEFFICIENTS.length; i++) {
            int charI = idCardNum.charAt(i) - '0';
            if (charI < 0 || charI > 9) return -1;
            count += charI * COEFFICIENTS[i];
        }
        count %= LAST_NUMS.length;
        return LAST_NUMS[count];
    }
}
