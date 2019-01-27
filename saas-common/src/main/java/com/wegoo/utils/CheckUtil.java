package com.wegoo.utils;


import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验参数工具类
 */
public class CheckUtil {
    static String phonePaternStr = "^(110|13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$";// 手机号的正则表达式
    static Pattern phonePattern = Pattern.compile(phonePaternStr);
    static Pattern operationPwdPattern = Pattern.compile("^\\d{6}$");
    static Pattern operatorCodePattern = Pattern.compile("^\\w{1,16}$");
    static Pattern charatorAndNumPattern = Pattern.compile("^[\\w\\d]+$");
    static Pattern numberpatternStr = Pattern.compile("[0-9]*");//判断字符串是否为数字的正则表达式
    /**
     * 判断字符串是否为空
     *
     * @param param
     *            要检查的字符串
     * @return 如果字符串为空或者“”时，返回true
     */
    public static boolean isEmpty(String param) {
        if (null == param || param.trim().length()==0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串是否不为空
     *
     * @param param
     *            要检查的字符串
     * @return 如果字符串不为空并且不为“”时，返回true
     */
    public static boolean isNotEmpty(String param) {
        return !isEmpty(param);
    }

    /**
     * 检查一个集合类是不是空的
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection){
        if (null==collection || collection.size()==0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 检查一个集合类是否非空
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection collection){
        return !isEmpty(collection);
    }

    /**
     * 检查指定的字符串列表是否不为空。
     */
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    /**
     * 判断提供手机号是否是合法的手机号
     *
     * @param phone
     * @return
     */
    public static boolean isValidePhoneNum(String phone) {

        Matcher ma = phonePattern.matcher(phone);
        if (ma.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证字符长度
     * @param str
     * @param length
     * @return
     */
    public static boolean isValideLength(String str, int length){
        if (str.length()>length){
            return false;
        }
        return true;
    }

    /**
     * 验证字符最小长度
     * @param str
     * @param length
     * @return
     */
    public static boolean isValideMinLength(String str, int length){
        if (str.length()<length){
            return false;
        }
        return true;
    }


    /**
     * 验证操作密码
     *
     * @param operationPwd
     * @return
     */
    public static boolean isValideOperationPwd(String operationPwd) {

        Matcher ma = operationPwdPattern.matcher(operationPwd);
        if (ma.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查是否只有字符和数字
     * @param target
     * @return
     */
    public static boolean isOnlyCharatorAndNum(String target){
        Matcher ma = charatorAndNumPattern.matcher(target);
        if (ma.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证收银员编码
     *
     * @param operatorCode
     * @return
     */
    public static boolean isValideOperatorCode(String operatorCode) {

        Matcher ma = operatorCodePattern.matcher(operatorCode);
        if (ma.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 比较两个字符串是否相等
     * @param str1
     * @param str2
     * @return
     */
    public static boolean compareString(String str1, String str2) {
        if(null == str1 && null == str2){
            return true;
        }
        if((null != str1 && null == str2) || (null == str1 && null != str2)){
            return false;
        }
        return str1.equals(str2);
    }

    /**
     * 比较两个数字是否相等
     * @param integer1
     * @param integer2
     * @return
     */
    public static boolean compareInteger(Integer integer1, Integer integer2) {
        if(null == integer1 && null == integer2){
            return true;
        }
        if((null != integer1 && null == integer2) || (null == integer1 && null != integer2)){
            return false;
        }
        return integer1.equals(integer2);
    }
    /**
     * 判断字符串是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Matcher isNum = numberpatternStr.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isOnlyCharatorAndNum("ab@12"));
    }
}
