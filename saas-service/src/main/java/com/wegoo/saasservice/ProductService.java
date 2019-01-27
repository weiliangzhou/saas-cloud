package com.wegoo.saasservice;

import com.wegoo.model.po.Product;
import com.wegoo.model.vo.BuyResult;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: ProductService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/515:04
 */
public interface ProductService {

    List<Product> getProductList(String merchantId);

    /**
     * 下单购买商品
     *
     * @param product 商品
     * @return 下单后的信息
     */
    BuyResult newH5Buy(Product product);

    /**
     * 修改商品购买数量
     *
     * @param productId  商品编号
     * @param merchantId 商户号
     */
    int updateBuyCountById(Long productId, String merchantId);

    /**
     * 根据商品id查询商品价格
     * @param id
     * @return
     */
    Integer getProductPriceByProductId(Long id);
}
