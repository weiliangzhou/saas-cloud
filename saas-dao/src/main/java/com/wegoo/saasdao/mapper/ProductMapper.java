package com.wegoo.saasdao.mapper;

import com.wegoo.model.po.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProductMapper {

    List<Product> getProductList(String merchantId);

    Product selectByPrimaryKey(@Param("id") Long productId);

    @Update("update saas_product set buy_count = buy_count+1 where id = #{productId} and merchant_id=#{merchantId}")
    int updateBuyCountById(@Param("productId") Long productId, @Param("merchantId") String merchantId);

    @Select("select price from saas_product where id = #{id}")
    Integer getProductPriceByProductId(Long id);
}