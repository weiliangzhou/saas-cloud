package com.wegoo.model.vo;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.wegoo.model.po.OfflineActivity;
import lombok.Data;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivityThemeVo
 * @ProjectName saas_cloud
 * @Description: TODO
 * @date 2018/11/2114:52
 */
@Data
public class OfflineActivityThemeVo {
    @ApiComment(seeClass = OfflineActivity.class)
    private List<OfflineActivity> offlineActivityList;
    @ApiComment(value = "完善信息表单-姓名", sample = "100")
    private Integer realNameShow;
    @ApiComment(value = "完善信息表单-手机", sample = "100")
    private Integer phoneShow;
    @ApiComment(value = "完善信息表单-品牌", sample = "100")
    private Integer ppShow;
    @ApiComment(value = "完善信息表单-职务", sample = "100")
    private Integer zyShow;
    @ApiComment(value = "完善信息表单-身份证", sample = "100")
    private Integer idCardNumShow;
    @ApiComment(value = "完善信息表单-收货地址", sample = "100")
    private Integer addressShow;
}
