package com.wegoo.saas.member;

import com.alibaba.fastjson.JSON;
import com.wegoo.baseservice.BaseService;
import com.wegoo.constants.user.Channel;
import com.wegoo.currentUser.CurrentUserInfoContext;
import com.wegoo.model.groups.H5Buy;
import com.wegoo.model.po.Merchant;
import com.wegoo.model.po.Product;
import com.wegoo.model.po.User;
import com.wegoo.model.po.UserOpenId;
import com.wegoo.model.vo.BuyResult;
import com.wegoo.model.vo.WxPayVo;
import com.wegoo.model.wxPayUtils.StrKit;
import com.wegoo.saasservice.*;
import com.wegoo.utils.IpKit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 商品对外提供接口
 */
@RestController
@RequestMapping("/qudao-member/saas/product")
public class ProductApiController extends BaseService {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserOpenIdService userOpenIdService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WxPayService wxPayService;

    @PostMapping("/auth/newH5Buy")
    public String newH5Buy( @Validated(H5Buy.class) @RequestBody Product product) {
        product.setUserId(CurrentUserInfoContext.getUserID());
        BuyResult buyResult = productService.newH5Buy(product);
        String orderNo = buyResult.getOrderNo();
        Integer totalFee = buyResult.getTotalFee();
        String merchantId = buyResult.getMerchantId();
        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
        String gzhAppId = merchant.getGzAppId();
        String userId_local = product.getUserId();
        User user = userService.getUserByUserId(userId_local);
        String wxPayKey = merchant.getWxPayKey();
//        String realIp = IpKit.getRealIp(request);
//        if (StringUtils.isBlank(realIp)) {
//            realIp = "127.0.0.1";
//        }
        UserOpenId userOpenId = userOpenIdService.getUserOpenIdByUserIdAndMerchantIdAndChannel(user.getUserId(),merchantId , Channel.H5);
        WxPayVo wxPayVo = wxPayService.pay("127.0.0.1", userOpenId.getOpenId(), orderNo, totalFee.toString(), gzhAppId, merchantId, wxPayKey);
        return setSuccessResult(wxPayVo);
    }
}
