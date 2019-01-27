package com.wegoo.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MathUtil {
    /**
     * 个位转换万位，保留一位小数
     */
    public static String changeWan(String units){
        BigDecimal bigDecimal=new BigDecimal(units);
        BigDecimal decimal=bigDecimal.divide(new BigDecimal("10000"));
        // 保留一位小数
        DecimalFormat formater = new DecimalFormat("0.0");
        // 四舍五入
        formater.setRoundingMode(RoundingMode.HALF_UP);
        String formatNum =formater.format(decimal);
        return formatNum;
    }
}
