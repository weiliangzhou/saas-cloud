package com.wegoo.utils;

/**
 * @author 二师兄超级帅
 * @Title: 字典
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/3011:43
 */
public class DictUtil {
    public static String getMemberLevelStr(Integer memberLevel) {
        switch (memberLevel) {
            case 99:
                return "校长";
            case 6:
                return "院长";
            case 5:
                return "班长";
            case 4:
                return "VIP学员";
            case 1:
                return "学员";
            default:
                return "会员";
        }
    }

    public static String getSexStr(Integer sex) {
        switch (sex) {
            case 0:
                return "男";
            default:
                return "女";

        }
    }

}
