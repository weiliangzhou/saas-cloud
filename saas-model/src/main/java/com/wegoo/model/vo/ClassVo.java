package com.wegoo.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.wegoo.model.BigDecimalUtil.BigDecimalUtil;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 套课和单独的节课程列表vo
 */
@Data
public class ClassVo {
    @ApiComment(value = "套或节课程id", sample = "1")
    private Long id;
    @ApiComment(value = "所属课程分类id", sample = "1")
    private Long categoryId;
    @ApiComment(value = "所属套课程id", sample = "1")
    private Long classSetId;
    @ApiComment(value = "套或节课程标题", sample = "新人创业培训")
    private String title;
    @ApiComment(value = "所属的课程分类名称", sample = "朋友圈学习")
    private String categoryTitle;
    @ApiComment(value = "套或节课程新建时间", sample = "2018-7-19 14:05:24")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiComment(value = "套或节课程更新时间", sample = "2018-7-19 14:05:24")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
    @ApiComment(value = "商户id", sample = "0571XUDONGYAO")
    private String merchantId;
    @ApiComment(value = "1-套，2-节", sample = "1")
    private Integer classType;
    @ApiComment(value = "浏览人数", sample = "858")
    private Long browseCount;
    @ApiComment(value = "浏览人数描述", sample = "858/1.4万")
    private String browseCountDesc;
    @ApiComment(value = "浏览人数描述2", sample = "人收听")
    private String browseCountDesc2;
    @ApiComment(value = "图片", sample = "858")
//    如果是堂，logo是节的可配置优先级），
//    按照发布时间倒序
    private String logoUrl;
    @ApiComment(value = "音频url", sample = "www.test.com")
    private String audioUrl;
    //如果是套，返回下属的节课程
    private List<ClassVo> children;
    @ApiComment(value = "套课观看要求最低会员等级", sample = "1")
    private Integer requiredMemberLevel;
    @ApiComment(value = "套课或者节课简介", sample = "<P>反正就是带标签哈哈哈哈")
    private String content;
    @ApiComment(value = "套课或者节课不带格式简介", sample = "不该格式哈哈哈哈")
    private String contentText;
    @ApiComment(value = "套课下属节课程数", sample = "3")
    private Integer childrenSize;
    @ApiComment(value = "课程时长", sample = "6分10秒")
    private String playTimeDesc;
    @ApiComment(value = "课程类型", sample = "0音频/1视频/2未选择")
    private Integer style;
    @ApiComment(value = "观后感类型", sample = "0课后感 1已读")
    private Integer commentType;
    @ApiComment(value = "是否推荐", sample = "0不推荐/1推荐")
    private Integer isRecommend;
    @ApiComment(value = "封面", sample = "www.baidu.com")
    private String frontCover;
    @ApiComment(value = "是否展示", sample = "0 1")
    private Integer isShow;
    @ApiComment(value = "是否有权限听课", sample = "0 1")
    private Integer isAllow;
    @ApiComment(value = "价格", sample = "100")
    private Integer price;
    @ApiComment(value = "价格", sample = "1元")
    private String priceDesc;
    @ApiComment(value = "商品id", sample = "103")
    private Long productId;

    public String getPriceDesc() {
        return String.valueOf(BigDecimalUtil.div(100, this.price==null ? 0 : this.price, 2));
    }
}
