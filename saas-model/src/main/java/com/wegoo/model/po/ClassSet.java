package com.wegoo.model.po;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

import java.util.Date;

/**
 * 套课程
 */
@Data
public class ClassSet {
    private Long id;

    private String title;

    private String bannerUrl;

    private Integer categoryId;

    private String merchantId;

    private Integer isShow;

    private Integer requiredMemberLevel;

    private Date createTime;

    private Date modifyTime;

    private Integer available = 1;

    private String content;
    @ApiComment(value = "不带格式介绍", sample = "不带格式介绍")
    private String contentText;

    @ApiComment(value = "套课类型", sample = "0音频 1视频")
    private Integer style;

    @ApiComment(value = "是否推荐", sample = "0不推荐 1推荐")
    private Integer isRecommend;

    @ApiComment(value = "封面", sample = "www.baidu.com")
    private String frontCover;
    @ApiComment(value = "商品id", sample = "103")
    private Long productId;
}