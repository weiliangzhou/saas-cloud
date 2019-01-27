package com.wegoo.tokenManager;


/**
 * @author 二师兄超级帅
 * @Title: TokenManager
 * @ProjectName parent
 * @Description: TODO
 */
public interface TokenManager {

    /**
     * 创建一个token关联上指定用户
     *
     * @param name 指定用户的id
     * @return 生成的token
     */
    public TokenModel createToken(String name);

    /**
     * 检查token是否有效
     *
     * @param model token
     * @return 是否有效
     */
    public boolean checkToken(TokenModel model);

    /**
     * 从字符串中解析token
     *
     * @param authentication 加密后的字符串
     * @return
     */
    public TokenModel getToken(String authentication);

    /**
     * 清除token
     *
     * @param name 登录用户的id
     */
    public void deleteToken(String name);
}