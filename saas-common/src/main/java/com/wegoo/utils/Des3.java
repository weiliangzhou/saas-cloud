package com.wegoo.utils;


import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Slf4j
public class Des3 {

    private static final String Algorithm = "DESede"; // 定义 加密算法,可用
    private static final String KEY = "123456788765432112345678";


    // src为被加密的数据缓冲区（源）
    public static String encryptMode(String src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(KEY.getBytes(), Algorithm);
            // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            // 开始加密运算
            byte[] encryptedByteArray = c1.doFinal(src.getBytes());
            // 加密运算之后 将byte[]转化为base64的String
            return MyBase64.encodeToStr(encryptedByteArray);
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }

    /**
     * 方法描述：3DES加密
     *
     * @param src
     * @param appKey 密钥
     * @return String
     */
    public static String encryptMode(String src, String appKey) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(appKey.getBytes(), Algorithm);
            // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            // 开始加密运算
            byte[] encryptedByteArray = c1.doFinal(src.getBytes());
            // 加密运算之后 将byte[]转化为base64的String
            return MyBase64.encodeToStr(encryptedByteArray);
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }

    public static String encryptModeWithUrlEncode(String src, String appKey) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(appKey.getBytes(), Algorithm);
            // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            // 开始加密运算
            byte[] encryptedByteArray = c1.doFinal(src.getBytes());
            // 加密运算之后 将byte[]转化为base64的String
            return Tools.encode(MyBase64.encodeToStr(encryptedByteArray));
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }


    // keybyte为加密密钥，长度为24字节
    // src为加密后的缓冲区
    public static String decryptMode(String src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(KEY.getBytes(), Algorithm);
            // 解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            // 解密运算之前
            byte[] encryptedByteArray = MyBase64.decodeStr(src);
            // 解密运算 将base64的String转化为byte[]
            return new String(c1.doFinal(encryptedByteArray), "utf-8");
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }

    /**
     * 方法描述：3des解密<br/>
     *
     * @param src
     * @param appKey
     * @return String
     */
    public static String decryptMode(String src, String appKey) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(appKey.getBytes(), Algorithm);
            // 解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            // 解密运算之前
            byte[] encryptedByteArray = MyBase64.decodeStr(src);
            // 解密运算 将base64的String转化为byte[]
            return new String(c1.doFinal(encryptedByteArray), "utf-8");
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }


    public static void main(String[] args) throws Exception {
        //System.out.println(Des3.encryptMode("hello world!",Constants.appKey));

//        System.out.println(Des3.decryptMode("zwlN6kF2dWXFbAJF1CoUPr9BYlIc5JE36HkdSYbomFfHenm9PAfKbFBGnXV9TzF/HIC3J0/x+1ROmT64f9SmILX6a1cfmzmv", "123456788765432112345678"));

        System.out.println(encryptMode("29c22c9c66224a43a3645e99e69f9582_99b04d511d4b4445b40c15ff6deefc94", "123456788765432112345678"));

//        String res = decryptMode("P4I96HQuN8D2eYb8QjXRPc2rrsqYwcWc2bXPYZiTAEhM5qR8RvXthyxF8TkgGgdOFtzp80iVgiKfkHtRUblLLxbc6fNIlYIizq2qjLakrID3fY2YVllu/g==", "123456788765432112345678");
//        System.out.println(res);
//		System.out.println(Des3.decryptMode("YlGA1Cq/c1w0SbK9pV5YIr9EZyBO+6IvtJNX0va5fz6fYQhb7oDP9+NbwqgO HBPoOo8aBjPSn5bmRMQk9VBxc+NbwqgOHBPo8dDj2b6vvgDAkNwUFVzeApyf P2CFjGIoDNdwqXCoEeYiqRTbfpmTnNwRtvt63FZMSnP1SIyCVVAVHU+B7Xnu epSBBVMXweY9mVmna3L8P1EKPj3yUSTQyPcBx+v4H2SRKxRiehfW4sfqCF72 w2fPfF4idDMn4ck7GUIWhUW7vDzt4476zIAKZBbvT7SRyHrpO1vGjtjwQAb9 dYuwhD7jbUjz98opNAqNCtpu+V8O47LoXufM/Tg/68WA8JXNHPamrKXN3z2n fqIX8I0mQihtoLQzfn7hGl4zAzGG52I1rmtI8/fKKTQKjQHk3SlJxGrPDBYX BQ+gOhzY6r5aJu5h6mW5JqSHEEDNK1f2v2KkTbWmUeoJ5GmBB6FQk6hpdIyJ JOHlbDYK+tmfYQhb7oDP9/ZVbTusJ1S5caOujOUa+9dxE94y2vnt+Qd/E2Zg YIpP/6Vy6dYT3KweHvMMLTPHgnJsfmBNPWsQt04P2MesfBMOhj9QnN/UmONO snHiplnwFXLUxoCaWsRALzy/ipOZNopRPLsG6udr3ddYTS+6pCVvGLHgASrx ixnPvwHKIyaom9p7hXAtd97iJzUGzVjbTqR9PbfO3tmfq7JTKH/YBAdIDQH4 pXwrG06RpRUobumNtWhzdW+bIOCee/e5jy56tNvdgS5g4Usmto59BgxrJkMz uNsK94UmoflqWvQiPumsmwP72dIJdS+3vNQDuv3VcOeBX5z2V2xgBVa3j1Ac 7y4=",Constants.appKey));
//        System.out.println(encryptMode("2016-01-01", "gxd_123456"));
//		String result = "{\"currentPage\":1,\"pageSize\":20,\"totalSize\":100,\"userList\":[{\"uuid\":\"\",\"platUserId\":\"1000111\",\"platUserPhone\":\"13111111111\",\"regTime\":\"2017-11-23 09:10:15\"}]}";
//		String encryptData=Des3.encryptMode(result,Constants.appKey);
//		System.out.println(encryptData);
    }


}
