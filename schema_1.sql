/*
 Navicat MySQL Data Transfer

 Source Server         : 微谷教育线上
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : rm-bp1xh2axa052gg1ifco.mysql.rds.aliyuncs.com:3306
 Source Schema         : new_cloud

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 27/01/2019 10:49:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for saas_banner
-- ----------------------------
DROP TABLE IF EXISTS `saas_banner`;
CREATE TABLE `saas_banner`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  `href_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '跳转路径',
  `href_type` int(1) NULL DEFAULT NULL COMMENT '跳转类型',
  `theme` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'banner主题',
  `queue_number` int(11) NULL DEFAULT NULL COMMENT '排序号',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `available` int(11) NULL DEFAULT NULL,
  `merchant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户号',
  `is_show` int(1) NULL DEFAULT NULL COMMENT '是否发布',
  `port_type` int(1) NULL DEFAULT NULL COMMENT '端口类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_class_category
-- ----------------------------
DROP TABLE IF EXISTS `saas_class_category`;
CREATE TABLE `saas_class_category`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程分类名称',
  `is_show` int(1) NOT NULL DEFAULT 0 COMMENT '该字段 是否发布状态 默认0不发布 1发布',
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属商户id',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_class_info
-- ----------------------------
DROP TABLE IF EXISTS `saas_class_info`;
CREATE TABLE `saas_class_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class_set_id` int(11) NULL DEFAULT NULL COMMENT '所属套课id',
  `category_id` bigint(11) NULL DEFAULT NULL COMMENT '如果节课程没有所属的套课，则存category_id',
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属商户id',
  `audio_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '音频url',
  `logo_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `is_show` int(1) NULL DEFAULT 1 COMMENT '该字段 是否发布状态 默认0不发布 1发布',
  `listen_count` int(11) NULL DEFAULT NULL COMMENT '收听人数',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 0,
  `content_text` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '不带格式的介绍',
  `style` int(1) NULL DEFAULT NULL,
  `is_recommend` int(1) NULL DEFAULT NULL,
  `play_time` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_class_info_comment
-- ----------------------------
DROP TABLE IF EXISTS `saas_class_info_comment`;
CREATE TABLE `saas_class_info_comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `class_info_id` bigint(20) NULL DEFAULT NULL COMMENT '节课id',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL,
  `available` int(1) NULL DEFAULT NULL,
  `merchant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_class_info_statistics
-- ----------------------------
DROP TABLE IF EXISTS `saas_class_info_statistics`;
CREATE TABLE `saas_class_info_statistics`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class_info_id` bigint(20) NULL DEFAULT NULL COMMENT '节课id',
  `listen_count` bigint(20) NULL DEFAULT NULL COMMENT '收听人数',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `节课程id`(`class_info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_class_set
-- ----------------------------
DROP TABLE IF EXISTS `saas_class_set`;
CREATE TABLE `saas_class_set`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '堂标题',
  `banner_url` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '横幅广告图',
  `content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `category_id` int(11) NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属商户id',
  `is_show` tinyint(1) NULL DEFAULT 0 COMMENT '该字段 是否发布状态 默认0不发布 1发布',
  `required_member_level` int(2) NULL DEFAULT NULL COMMENT '观看要求的最低会员等级',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  `content_text` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '不带格式的介绍',
  `style` int(1) NULL DEFAULT NULL,
  `is_recommend` int(1) NULL DEFAULT NULL,
  `front_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '产品id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_class_set_statistics
-- ----------------------------
DROP TABLE IF EXISTS `saas_class_set_statistics`;
CREATE TABLE `saas_class_set_statistics`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class_set_id` bigint(20) NULL DEFAULT NULL COMMENT '套课id',
  `browse_count` int(20) NULL DEFAULT NULL COMMENT '粉丝人数',
  `share_count` int(20) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `套课程id`(`class_set_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 306 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_merchant
-- ----------------------------
DROP TABLE IF EXISTS `saas_merchant`;
CREATE TABLE `saas_merchant`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商户号',
  `merchant_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者id',
  `app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小程序appId',
  `app_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小程序appsecret',
  `wx_pay_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信支付key',
  `gz_app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公众号appId',
  `gz_app_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公众号key',
  `buy_template_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公众号购买模版',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_offline_activity
-- ----------------------------
DROP TABLE IF EXISTS `saas_offline_activity`;
CREATE TABLE `saas_offline_activity`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动地点',
  `apply_start_time` datetime(0) NULL DEFAULT NULL COMMENT '报名开始时间',
  `apply_end_time` datetime(0) NULL DEFAULT NULL COMMENT '报名结束时间',
  `activity_start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `activity_end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `activity_price` int(11) NULL DEFAULT NULL COMMENT '价格',
  `is_retraining` int(11) NULL DEFAULT NULL COMMENT '是否复训 0不是1是',
  `retraining_price` int(11) NULL DEFAULT NULL COMMENT '复训价格',
  `activity_theme_id` int(11) NULL DEFAULT NULL COMMENT '活动主题id',
  `limit_count` int(11) NULL DEFAULT NULL COMMENT '容纳人数',
  `buy_count` int(11) NULL DEFAULT NULL COMMENT '购买人数',
  `is_recommend` int(1) NULL DEFAULT NULL COMMENT '是否推荐，0不推荐，1推荐',
  `is_show` int(1) NULL DEFAULT NULL COMMENT '是否展示，0不展示，1展示',
  `is_rebuy` int(1) NULL DEFAULT NULL COMMENT '是否可用重复购买,0不可用,1可以',
  `is_maid` int(1) NULL DEFAULT NULL COMMENT '是否返佣，0不返佣，1返佣',
  `min_requirement` int(2) NULL DEFAULT NULL COMMENT '购买最低要求',
  `merchant_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户号',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_offline_activity_code
-- ----------------------------
DROP TABLE IF EXISTS `saas_offline_activity_code`;
CREATE TABLE `saas_offline_activity_code`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `activity_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_used` int(1) NULL DEFAULT NULL COMMENT '0未使用1已使用',
  `activity_theme_id` int(11) NULL DEFAULT NULL COMMENT '主题id',
  `activity_id` int(11) NULL DEFAULT NULL COMMENT '活动id',
  `merchant_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT NULL,
  `qr_code_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二维码地址',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unqie_activity_code`(`activity_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2313 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_offline_activity_operator
-- ----------------------------
DROP TABLE IF EXISTS `saas_offline_activity_operator`;
CREATE TABLE `saas_offline_activity_operator`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员手机',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `activity_theme_id` int(11) NULL DEFAULT NULL COMMENT '活动主题id',
  `merchant_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_offline_activity_order
-- ----------------------------
DROP TABLE IF EXISTS `saas_offline_activity_order`;
CREATE TABLE `saas_offline_activity_order`  (
  `order_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `activity_id` int(11) NULL DEFAULT NULL COMMENT '线下活动id',
  `activity_theme_id` int(11) NOT NULL COMMENT '主题id',
  `activity_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动消费码',
  `activity_price` int(11) NULL DEFAULT NULL COMMENT '活动费用',
  `actual_money` int(11) NULL DEFAULT NULL COMMENT '实际支付金额',
  `order_status` int(1) NULL DEFAULT NULL COMMENT '0待支付 1支付成功 2退款成功 -1超时',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` int(1) NULL DEFAULT NULL COMMENT '0男 1女',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在城市',
  `id_card_num` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `payment_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付流水号',
  `payment_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '支付时间',
  `merchant_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户号',
  `is_maid` int(1) NULL DEFAULT NULL COMMENT '是否返佣，0不返佣，1返佣',
  `is_retraining` int(1) NULL DEFAULT NULL COMMENT '是否复训 0不是1是',
  `profession` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职务',
  `referrer` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '推荐人',
  `referrer_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `referrer_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '122334',
  `change_times` int(1) NULL DEFAULT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '活动开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '活动结束时间',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT NULL,
  `brand` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '品牌',
  PRIMARY KEY (`order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_offline_activity_theme
-- ----------------------------
DROP TABLE IF EXISTS `saas_offline_activity_theme`;
CREATE TABLE `saas_offline_activity_theme`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `theme_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主题名称',
  `theme_href_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片地址或者视频地址',
  `theme_type` int(255) NULL DEFAULT NULL COMMENT '类型 0图片 1视频',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `content_text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `buy_count` int(11) NULL DEFAULT NULL COMMENT '收听人数',
  `is_recommend` int(1) NULL DEFAULT NULL COMMENT '是否推荐，0不推荐，1推荐',
  `is_show` int(1) NULL DEFAULT NULL COMMENT '是否展示，0不展示，1展示',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `activity_time` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时长 3天2夜',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT NULL,
  `merchant_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `qr_bg_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二维码背景图',
  `price` int(11) NULL DEFAULT NULL COMMENT '商品价格',
  `limit_count` int(11) NULL DEFAULT NULL COMMENT '限制人数',
  `real_name_show` int(1) NULL DEFAULT NULL COMMENT '0不显示 1显示',
  `phone_show` int(1) NULL DEFAULT NULL COMMENT '0不显示 1显示',
  `id_card_num_show` int(1) NULL DEFAULT NULL COMMENT '0不显示 1显示',
  `address_show` int(1) NULL DEFAULT NULL COMMENT '0不显示 1显示',
  `pp_show` int(1) NULL DEFAULT NULL COMMENT '0不显示 1显示',
  `zy_show` int(1) NULL DEFAULT NULL COMMENT '0不显示 1显示',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_order
-- ----------------------------
DROP TABLE IF EXISTS `saas_order`;
CREATE TABLE `saas_order`  (
  `order_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `product_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `actual_money` int(11) NOT NULL COMMENT '实际支付金额',
  `money` int(11) NULL DEFAULT NULL COMMENT '订单金额按照分为单位存储',
  `maid_percent` int(11) NOT NULL,
  `pay_way` smallint(1) NULL DEFAULT NULL COMMENT '支付方式：目前只支持微信1',
  `level` int(11) NULL DEFAULT NULL,
  `level_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `validity_time` int(11) NULL DEFAULT NULL COMMENT '有效期，天为单位',
  `order_status` smallint(1) NULL DEFAULT 0 COMMENT '订单状态  0待支付 1成功  -1超时关闭',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `merchant_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '下单人',
  `payment_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信支付订单号',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '微信到账成功实践',
  `create_time` datetime(0) NOT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` smallint(1) NULL DEFAULT 1,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `referrer` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推荐人',
  `referrer_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推荐人姓名',
  `referrer_phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推荐人手机',
  PRIMARY KEY (`order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_product
-- ----------------------------
DROP TABLE IF EXISTS `saas_product`;
CREATE TABLE `saas_product`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `level` int(1) NOT NULL COMMENT '会员等级',
  `level_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '等级名称',
  `product_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `maid_percent` int(3) NOT NULL COMMENT '分佣比例 /100',
  `validity_time` int(10) NOT NULL COMMENT '有效期按照天为单位存储',
  `price` int(11) NOT NULL COMMENT '价格',
  `merchant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片url',
  `content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品介绍（带格式）',
  `content_text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品介绍（不带格式）',
  `buy_count` int(11) NULL DEFAULT NULL COMMENT '购买的人数',
  `create_time` datetime(0) NOT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` smallint(1) NOT NULL DEFAULT 1,
  `is_show` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_user
-- ----------------------------
DROP TABLE IF EXISTS `saas_user`;
CREATE TABLE `saas_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `register_from` bigint(1) NULL DEFAULT 1 COMMENT '从什么渠道注册。0:网页 1:微信-H5 2: 微信-小程序 3:线下导入 4:ios 5 :android',
  `register_mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定手机号',
  `register_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `available` int(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_register_mobile`(`register_mobile`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11446 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_user_certification
-- ----------------------------
DROP TABLE IF EXISTS `saas_user_certification`;
CREATE TABLE `saas_user_certification`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `real_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '真实姓名',
  `id_card_num` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份证',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `merchant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户所属id',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `sex` int(4) NOT NULL COMMENT '0男 1女',
  `city` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '城市',
  `profession` int(30) NOT NULL COMMENT '职业 : 0品牌商  1团队长  2创业者 ',
  `create_time` datetime(0) NOT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unqie_id_card_num`(`id_card_num`) USING BTREE COMMENT '身份证号码'
) ENGINE = InnoDB AUTO_INCREMENT = 363 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_user_info
-- ----------------------------
DROP TABLE IF EXISTS `saas_user_info`;
CREATE TABLE `saas_user_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `register_mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第一次注册手机号，用来登录 （与user_name、binding_mobile一致）',
  `real_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `nick_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称，用于小程序显示',
  `logo_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `wechat_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信号码',
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `available` int(1) NOT NULL COMMENT '逻辑删除',
  `id_card_num` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证',
  `consult_phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '咨询手机号',
  `consult_wechat_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '咨询微信号',
  `consult_show_type` int(1) NULL DEFAULT NULL COMMENT '0展示注册手机号 1展示咨询手机号 2展示咨询微信号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11025 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户基本信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_user_member
-- ----------------------------
DROP TABLE IF EXISTS `saas_user_member`;
CREATE TABLE `saas_user_member`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `member_level` int(3) NULL DEFAULT NULL COMMENT '会员等级 1:普通会员 2:认证会员 99:业务员',
  `merchant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商户号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `available` int(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_user_id_merchant_id`(`user_id`, `merchant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11447 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户会员等级关系对应表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_user_openid
-- ----------------------------
DROP TABLE IF EXISTS `saas_user_openid`;
CREATE TABLE `saas_user_openid`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `open_id` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标识符',
  `channel_id` int(3) NULL DEFAULT NULL COMMENT '渠道 0:网页 1:微信-H5 2: 微信-小程序 3:线下导入 4:ios 5 :android',
  `merchant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商户号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `available` int(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_o_c_m`(`open_id`, `channel_id`, `merchant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11023 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户openid对应关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for saas_user_referrer
-- ----------------------------
DROP TABLE IF EXISTS `saas_user_referrer`;
CREATE TABLE `saas_user_referrer`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `referrer` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推荐人',
  `merchant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商户号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `available` int(1) NULL DEFAULT 1,
  `is_buy` int(1) NULL DEFAULT NULL COMMENT '是否锁定关系，0临时1锁定',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_user_id_merchant_id`(`user_id`, `merchant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9442 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户推荐人关系对应表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户操作',
  `time` int(11) NULL DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `params` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求参数',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5515 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_macro
-- ----------------------------
DROP TABLE IF EXISTS `sys_macro`;
CREATE TABLE `sys_macro`  (
  `macro_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type_id` bigint(255) NULL DEFAULT NULL COMMENT '父级id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `value` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态，0：隐藏   1：显示',
  `type` tinyint(20) NULL DEFAULT NULL COMMENT '类型,0:目录，1:参数配置',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`macro_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通用字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 202 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_sign` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色标识',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `user_id_create` bigint(255) NULL DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4067 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色与菜单对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint(255) NULL DEFAULT NULL COMMENT '状态 0:禁用，1:正常',
  `user_id_create` bigint(255) NULL DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户号',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 130 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与角色对应关系' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
