package com.wegoo.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author 二师兄超级帅
 * @Title: HttpKit
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/920:20
 */
@Slf4j
public class HttpKit {

    public static String readData(HttpServletRequest request) {
        BufferedReader br = null;

        String var4;
        try {
            br = request.getReader();
            String line = br.readLine();
            if (line != null) {
                StringBuilder ret = new StringBuilder();
                ret.append(line);

                while ((line = br.readLine()) != null) {
                    ret.append('\n').append(line);
                }

                var4 = ret.toString();
                return var4;
            }

            var4 = "";
        } catch (IOException var14) {
            throw new RuntimeException(var14);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException var13) {
                    log.error(var13.getMessage(), var13);
                }
            }

        }

        return var4;
    }
}
