package com.wegoo.model.po;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

import java.util.Date;

/**
 * 节课程
 */
@Data
public class ClassInfo {
    @ApiComment(value = "节课程id", sample = "1")
    private Long id;
    @ApiComment(value = "所属套课程id", sample = "1")
    private Long classSetId;
    @ApiComment(value = "所属课程分类id", sample = "1")
    private Long categoryId;
    @ApiComment(value = "商户id", sample = "0571XUDONGYAO")
    private String merchantId;
    @ApiComment(value = "音频url", sample = "www.test.com")
    private String audioUrl;
    @ApiComment(value = "图片url", sample = "www.test.com")
    private String logoUrl;
    @ApiComment(value = "节课程标题", sample = "布娃娃微商创业分享新手教程")
    private String title;
    @ApiComment(value = "用户端是否显示", sample = "1")
    private Integer isShow;
    @ApiComment(value = "节课程内容", sample = "一段带个格式的内容，反正很长就是了")
    private String content;
    @ApiComment(value = "节课程收听人数", sample = "563")
    private Long listenCount;
    @ApiComment(value = "创建时间", sample = "2018-7-19 14:30:41")
    private Date createTime;
    @ApiComment(value = "修改时间", sample = "2018-7-19 14:30:50")
    private Date modifyTime;
    @ApiComment(value = "逻辑删除位", sample = "1")
    private Integer available = 1;
    @ApiComment(value = "不带格式介绍", sample = "不带格式介绍")
    private String contentText;
    @ApiComment(value = "节课类型", sample = "0音频 1视频")
    private Integer style;
    @ApiComment(value = "是否推荐", sample = "0不推荐，1推荐")
    private Integer isRecommend;
    @ApiComment(value = "课程时长", sample = "10")
    private Integer playTime;
}