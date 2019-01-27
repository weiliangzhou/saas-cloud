package com.wegoo.utils;


import java.io.UnsupportedEncodingException;
import java.util.TreeMap;

public class Tools {

    public static String encode(String url) {
        String val = null;
        try {
            val = java.net.URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return val;
    }

    /**
     * 方法描述：生成签名<br/>
     *
     * @param map
     * @return String
     */
    public static String createSign(TreeMap<String, String> map) {
        StringBuffer sb = new StringBuffer();
        for (String key : map.keySet()) {
            if (sb.length() < 1) {
                sb.append(key + "=" + map.get(key));
            } else {
                sb.append("&" + key + "=" + map.get(key));
            }
        }
        System.out.println("sign加密前" + sb.toString());
        System.out.println("sign加密后" + EncryptUtil.md5(sb.toString()));
        return EncryptUtil.md5(sb.toString());
    }


//	public static void main(String[] args){
//		System.out.println(Des3.encryptModeWithUrlEncode("2016-12-27", Constants.appKey));
//
//		TreeMap<String, String> map = new TreeMap<String, String>();
//		map.put("startTime","2016-12-27");
//		map.put("endTime","2016-12-27");
//		String newSign=Tools.createSign(map);
//		System.out.println(newSign);
//
//		System.out.println(Des3.decryptMode("EH2NxGFxL+6y1ZAHVYXbH9jkZd9HP0ex+sR/HoCZjORiJ+GRTpKfn+XXxHqkKzeE/JKS8EnKA7JHoK2GhEENfsNGSfHj3kCnAsXgCHo7xnPw+tSI5VdLEVJ9GrFaOAb43R9p0/KEMIuZte3nPm3mNVfBRAMb62D7GN3gwnum72vTB7+mtvlSPhpz0vdkX1OAubMuLpBsPpZ00cAUowSey9eS6aM8AulosvUX84GrJJy3mgHtJYJ6vpnFSdbju4xVw4NC/ZRRwpavowp7S7zGfA==",Constants.appKey));
//	}

}
