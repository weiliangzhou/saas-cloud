package com.wegoo.model.wxPayUtils;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author 二师兄超级帅
 * @Title: PaymentKit
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/917:58
 */
@Slf4j
public class PaymentKit {
    public static String toXml(Map<String, String> params) {
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>");
        Iterator var2 = params.entrySet().iterator();

        while (var2.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) var2.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (!StrKit.isBlank(value)) {
                xml.append("<").append(key).append(">");
                xml.append(entry.getValue());
                xml.append("</").append(key).append(">");
            }
        }

        xml.append("</xml>");
        return xml.toString();
    }

    public static Map<String, String> xmlToMap(String xmlStr) {
        XmlHelper xmlHelper = XmlHelper.of(xmlStr);
        return xmlHelper.toMap();
    }

    public static boolean codeIsOK(String return_code) {
        return StrKit.notBlank(return_code) && "SUCCESS".equals(return_code);
    }

    public static boolean verifyNotify(Map<String, String> params, String paternerKey) {
        String sign = params.get("sign");
        String localSign = createSign(params, paternerKey);
        return sign.equals(localSign);
    }

    public static String createSign(Map<String, String> params, String partnerKey) {
        params.remove("sign");
        String stringA = packageSign(params, false);
        String stringSignTemp = stringA + "&key=" + partnerKey;
        return HashKit.md5(stringSignTemp).toUpperCase();
    }

    public static String packageSign(Map<String, String> params, boolean urlEncoder) {
        TreeMap<String, String> sortedParams = new TreeMap(params);
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        Iterator var5 = sortedParams.entrySet().iterator();

        while (var5.hasNext()) {
            Map.Entry<String, String> param = (Map.Entry) var5.next();
            String value = param.getValue();
            if (!StrKit.isBlank(value)) {
                if (first) {
                    first = false;
                } else {
                    sb.append("&");
                }

                sb.append(param.getKey()).append("=");
                if (urlEncoder) {
                    try {
                        value = urlEncode(value);
                    } catch (UnsupportedEncodingException var9) {
                        ;
                    }
                }

                sb.append(value);
            }
        }

        return sb.toString();
    }

    public static String urlEncode(String src) throws UnsupportedEncodingException {
        return URLEncoder.encode(src, "UTF-8").replace("+", "%20");
    }


    public static boolean isWechatSign(Map<String, String> params, String wxPayKey) {
        String sign = params.get("sign").toUpperCase();
        log.info("微信回调签名------------->"+sign);
        params.remove("sign");
        String localSign = createSign(params, wxPayKey);
        log.info("本地签名------------->"+localSign);
        return localSign.equals(sign);
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("1", "111");
        map.put("2", "222");
        map.remove("1");
        System.out.println(map);
    }
}
