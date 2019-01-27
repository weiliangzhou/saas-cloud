package com.wegoo.saas.member;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wegoo.api.member.ClassSetApiService;
import com.wegoo.baseservice.BaseService;
import com.wegoo.constants.BaseResultConstants;
import com.wegoo.constants.MemberLevelConstants;
import com.wegoo.currentUser.CurrentUserInfoContext;
import com.wegoo.model.po.ClassSet;
import com.wegoo.model.po.ClassSetStatistics;
import com.wegoo.model.po.Order;
import com.wegoo.model.po.UserMember;
import com.wegoo.model.vo.ClassVo;
import com.wegoo.model.vo.PageClassVo;
import com.wegoo.saasservice.*;
import com.wegoo.utils.MathUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/qudao-member/saas/classset")
public class ClassSetApiServiceImpl extends BaseService implements ClassSetApiService {
    @Autowired
    private ClassSetService classSetService;
    @Autowired
    private ClassSetStatisticsService classSetStatisticsService;
    private static final long CONSTANT_WAN = 10000L;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserMemberService userMemberService;

    @Override
    @PostMapping("/getClassSetList")
    @Cacheable(cacheNames = "getClassSetList", keyGenerator = "zwlKey")
    public String getClassSetList(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer queryType = jsonObject.getInteger("queryType");
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<ClassVo> list = classSetService.getClassSetList(merchantId, queryType);
        for (ClassVo classVo : list) {
            //浏览人数
            ClassSetStatistics classSetStatistics = classSetStatisticsService.getByClassSetId(classVo.getId());
            if (classSetStatistics != null) {
                Long browseCount = classSetStatistics.getBrowseCount() == null ? 0L : classSetStatistics.getBrowseCount();
                classVo.setBrowseCount(browseCount);
                String classListenCountDesc = String.valueOf(browseCount);
                if (browseCount >= CONSTANT_WAN) {
                    classListenCountDesc = MathUtil.changeWan(classListenCountDesc) + "万";
                }
                classVo.setBrowseCountDesc(classListenCountDesc);
                classVo.setBrowseCountDesc2("人收听");
            } else {
                classVo.setBrowseCountDesc("0");
                classVo.setBrowseCountDesc2("人收听");
            }
        }
        PageClassVo pageClassVo = new PageClassVo();
        pageClassVo.setPageNum(pageNum);
        pageClassVo.setTotalPage(page.getTotal());
        pageClassVo.setList(list);
        return setSuccessResult(pageClassVo);
    }

    @Override
    @PostMapping("/getClassSetDetailByClassSetId")
    public String getClassSetByClassSetId(@RequestBody JSONObject jsonObject) {
        Long id = jsonObject.getLong("id");
        String userId = CurrentUserInfoContext.getUserID();
        String merchantId = jsonObject.getString("merchantId");
        addClassSetBrowseCount(id);
        ClassSet classSet = classSetService.getClassSetByClassSetId(id);
        if (classSet == null) {
            return setFailResult(BaseResultConstants.HTTP_CODE_500, "查无此套课，请确认套课id传入正确");
        }
        Long productId = classSet.getProductId();
        ClassVo classVo = new ClassVo();
        classVo.setId(classSet.getId());
        classVo.setTitle(classSet.getTitle());
        classVo.setContent(classSet.getContent());
        classVo.setContentText(classSet.getContentText());
        classVo.setLogoUrl(classSet.getBannerUrl());
        classVo.setProductId(productId);
        //浏览人数
        ClassSetStatistics classSetStatistics = classSetStatisticsService.getByClassSetId(classSet.getId());
        if (classSetStatistics != null) {
            Long browseCount = classSetStatistics.getBrowseCount() == null ? 0L : classSetStatistics.getBrowseCount();
            classVo.setBrowseCount(browseCount);
            String classListenCountDesc = String.valueOf(browseCount);
            if (browseCount >= CONSTANT_WAN) {
                classListenCountDesc = MathUtil.changeWan(classListenCountDesc) + "万";
            }
            classVo.setBrowseCountDesc(classListenCountDesc);
            classVo.setBrowseCountDesc2("人收听");
        } else {
            classVo.setBrowseCountDesc("0");
            classVo.setBrowseCountDesc2("人收听");
        }
        //通过查询saas_order表  order_status=1 userId  productId merchantId
        //如果存在 则返回1已购买 否则 0未购买（前端需要调到productId对应的购买页）
        classVo.setIsAllow(0);
        if (StringUtils.isNotBlank(userId)) {
            Order order = orderService.getOrderByUserIdAndProductId(userId, productId, merchantId);
            if (null != order) {
                classVo.setIsAllow(1);
            }
            //如果是业务员不购买也可以听课
            UserMember userMember = userMemberService.getUserMemberByUserIdAndMerchatId(userId, merchantId);
            if (userMember != null) {
                Integer memberLevel = userMember.getMemberLevel();
                if (MemberLevelConstants.YWY == memberLevel) {
                    classVo.setIsAllow(1);
                }
            }
        }
        Integer price = productService.getProductPriceByProductId(productId);
        classVo.setPrice(price == null ? 0 : price);
        return setSuccessResult(classVo);
    }

    /**
     * 浏览人数+1
     */
    private void addClassSetBrowseCount(Long classSetId) {
        ClassSetStatistics classSetStatistics = classSetStatisticsService.getByClassSetId(classSetId);
        if (null == classSetStatistics) {
            ClassSetStatistics temp = new ClassSetStatistics();
            temp.setClassSetId(classSetId);
            temp.setBrowseCount(1L);
            classSetStatisticsService.add(temp);
        } else {
            classSetStatisticsService.setpAddBrowseCount(classSetId);
        }
    }
}
