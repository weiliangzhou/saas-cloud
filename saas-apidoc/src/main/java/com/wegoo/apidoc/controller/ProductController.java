package com.wegoo.apidoc.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.wegoo.model.vo.WxPayVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api2Doc(name = "商品管理")
@RestController
public class ProductController {
    @ApiComment("用户购买商品")
    @RequestMapping(name = "用户购买商品",
            value = "/qudao-member/saas/product/auth/newH5Buy", method = RequestMethod.POST)
    public WxPayVo loginAndRegister(@ApiComment("商户号") String merchantId,
                                    @ApiComment("商品编号") Long id) {
        return new WxPayVo();
    }
}
