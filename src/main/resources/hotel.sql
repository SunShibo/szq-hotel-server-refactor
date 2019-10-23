/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : hotel

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2019-10-23 16:00:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hm_admin
-- ----------------------------
DROP TABLE IF EXISTS `hm_admin`;
CREATE TABLE `hm_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表',
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `status` varchar(255) DEFAULT NULL COMMENT '状态',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_admin
-- ----------------------------
INSERT INTO `hm_admin` VALUES ('1', '系统', '123456', '1411678a0b9e25ee2f7c8b2f7ac92b6a74b3f9c5', 'normal', '1', null, '2019-07-23 00:00:00', null, null);
INSERT INTO `hm_admin` VALUES ('8', '店长', '123456789', 'f7c3bc1d808e04732adf679965ccc34ca7ae3441', 'normal', '7', null, '2019-08-05 16:31:29', null, '2019-08-05 16:31:29');
INSERT INTO `hm_admin` VALUES ('10', 'test2', '678', '1411678a0b9e25ee2f7c8b2f7ac92b6a74b3f9c5', 'normal', '7', null, '2019-08-06 14:13:34', null, '2019-08-06 14:13:34');

-- ----------------------------
-- Table structure for hm_cashier_summary
-- ----------------------------
DROP TABLE IF EXISTS `hm_cashier_summary`;
CREATE TABLE `hm_cashier_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收银汇总',
  `project` varchar(255) DEFAULT NULL COMMENT '项目',
  `channel` varchar(255) DEFAULT NULL COMMENT '渠道',
  `passenger_source` varchar(255) DEFAULT NULL COMMENT '客源',
  `OTA` varchar(255) DEFAULT NULL COMMENT 'OTA',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房号',
  `room_type_name` varchar(255) DEFAULT NULL COMMENT '房型',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `consumption` double(255,2) DEFAULT NULL COMMENT '消费',
  `settlement` double(255,2) DEFAULT NULL COMMENT '结算',
  `create_time` datetime DEFAULT NULL COMMENT '发生日期',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `type` varchar(255) DEFAULT NULL COMMENT '类型',
  `user_id` int(11) DEFAULT NULL COMMENT '操作员',
  `order_number` varchar(255) DEFAULT NULL COMMENT '单号',
  `hotel_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_cashier_summary
-- ----------------------------
INSERT INTO `hm_cashier_summary` VALUES ('1', '押金', 'directly', '散客', '', '103', '标准间', 'test', '0.00', '500.00', '2019-10-10 12:09:48', '入住支付', 'cash', '1', '631823781778989056', '1');
INSERT INTO `hm_cashier_summary` VALUES ('2', '押金', 'directly', '散客', '', '201', '标准间', 'demo', '0.00', '100.00', '2019-10-10 12:10:24', '入住支付', 'cash', '1', '631825833498288128', '1');
INSERT INTO `hm_cashier_summary` VALUES ('3', '商品', 'directly', '散客', '', '103', '标准间', 'test', '50.00', '0.00', '2019-10-10 12:10:55', '水', null, '1', '631823781778989056', '1');
INSERT INTO `hm_cashier_summary` VALUES ('4', '商品', 'directly', '散客', '', '103', '标准间', 'test', '100.00', '0.00', '2019-10-10 12:11:09', '午饭', null, '1', '631823781778989056', '1');
INSERT INTO `hm_cashier_summary` VALUES ('5', '商品', 'directly', '散客', '', '201', '标准间', 'demo', '500.00', '0.00', '2019-10-10 13:10:31', 'test', null, '1', '631825833498288128', '1');
INSERT INTO `hm_cashier_summary` VALUES ('6', '押金', 'directly', '散客', '', '108', '豪华标间', '108', '0.00', '500.00', '2019-10-10 13:32:34', '入住支付', 'cash', '1', '631846520975826944', '1');
INSERT INTO `hm_cashier_summary` VALUES ('7', '押金', 'directly', '散客', '', '110', '豪华标间', '110', '0.00', '300.00', '2019-10-10 13:33:01', '入住支付', 'cash', '1', '631846631340548096', '1');
INSERT INTO `hm_cashier_summary` VALUES ('8', '商品', 'directly', '散客', '', '110', '豪华标间', '110', '200.00', '0.00', '2019-10-10 13:33:21', 'test', null, '1', '631846631340548096', '1');
INSERT INTO `hm_cashier_summary` VALUES ('9', '房费冲减', 'directly', '散客', '', '110', '豪华标间', '110', '-100.00', '0.00', '2019-10-10 13:33:42', '100', null, '1', '631846631340548096', '1');
INSERT INTO `hm_cashier_summary` VALUES ('10', '储值', null, null, null, null, null, '何小汶', '0.00', '300.00', '2019-10-13 12:22:38', null, '现金', '1', '632916105639231488', '1');
INSERT INTO `hm_cashier_summary` VALUES ('11', '储值', null, null, null, null, null, '何小汶', '0.00', '300.00', '2019-10-13 12:23:06', null, '现金', '1', '632916222890999808', '1');
INSERT INTO `hm_cashier_summary` VALUES ('12', '商品', null, null, null, null, null, null, '1000.00', '0.00', '2019-10-13 12:35:01', '1000', 'stored', '1', '632919223298949120', '1');
INSERT INTO `hm_cashier_summary` VALUES ('13', '商品', null, null, null, null, null, null, '0.00', '1000.00', '2019-10-13 12:35:01', '1000', 'stored', '1', '632919223298949120', '1');

-- ----------------------------
-- Table structure for hm_check_in_person
-- ----------------------------
DROP TABLE IF EXISTS `hm_check_in_person`;
CREATE TABLE `hm_check_in_person` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '入住人表',
  `order_child_id` int(11) DEFAULT NULL COMMENT '子订单id',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `gender` varchar(255) DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `certificate_number` varchar(255) DEFAULT NULL COMMENT '证件号',
  `certificate_type` int(11) DEFAULT NULL COMMENT '证件类型',
  `status` varchar(255) DEFAULT NULL COMMENT '入住人状态 (checkout已离店，checkin在住)',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_check_in_person
-- ----------------------------
INSERT INTO `hm_check_in_person` VALUES ('1', '1', 'test', null, 'test', 'test', '1', 'checkin', '', '2019-10-10 12:02:10', null);
INSERT INTO `hm_check_in_person` VALUES ('2', '2', 'test1', '2', 'test1', 'test1', '1', 'checkin', 'test1', '2019-10-10 12:02:10', null);
INSERT INTO `hm_check_in_person` VALUES ('3', '3', 'demo', null, 'demo', 'demo', '1', 'checkin', '', '2019-10-10 12:10:17', null);
INSERT INTO `hm_check_in_person` VALUES ('4', '4', 'demo1', '2', 'demo1', 'demo1', '1', 'checkin', 'demo1', '2019-10-10 12:10:17', null);
INSERT INTO `hm_check_in_person` VALUES ('5', '5', '108', '2', '108', '108', '1', 'checkin', '', '2019-10-10 13:32:29', null);
INSERT INTO `hm_check_in_person` VALUES ('6', '6', '110', null, '110', '110', '1', 'checkin', '', '2019-10-10 13:32:56', null);

-- ----------------------------
-- Table structure for hm_classes
-- ----------------------------
DROP TABLE IF EXISTS `hm_classes`;
CREATE TABLE `hm_classes` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '班次表',
  `classes_name` varchar(255) DEFAULT NULL COMMENT '班次名称',
  `start_time` time DEFAULT NULL COMMENT '开始时间',
  `end_time` time DEFAULT NULL COMMENT '结束时间',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `hotel_id` int(11) DEFAULT NULL COMMENT '酒店id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_classes
-- ----------------------------
INSERT INTO `hm_classes` VALUES ('1', '凌晨班', '04:00:00', '07:59:59', null, null, '1', '2019-07-31 15:37:47', '1');
INSERT INTO `hm_classes` VALUES ('2', '白班2', '08:00:00', '19:59:59', null, null, '1', '2019-07-10 12:56:27', '1');
INSERT INTO `hm_classes` VALUES ('3', '夜班3', '20:00:00', '03:59:00', null, null, '1', '2019-07-10 12:54:28', '1');

-- ----------------------------
-- Table structure for hm_commodity_transaction
-- ----------------------------
DROP TABLE IF EXISTS `hm_commodity_transaction`;
CREATE TABLE `hm_commodity_transaction` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品交易表',
  `member_id` int(11) DEFAULT NULL COMMENT '会员id',
  `order_number` varchar(255) DEFAULT NULL COMMENT '订单号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `pay_type` varchar(255) DEFAULT NULL COMMENT '支付方式',
  `consume_type` varchar(255) DEFAULT NULL COMMENT '消费方式',
  `money` double DEFAULT NULL COMMENT '交易金额',
  `create_user_id` int(11) DEFAULT NULL COMMENT '操作人id',
  `consumption_details` varchar(255) DEFAULT NULL COMMENT '消费详情',
  `hotel_id` int(11) DEFAULT NULL COMMENT '酒店id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_commodity_transaction
-- ----------------------------
INSERT INTO `hm_commodity_transaction` VALUES ('1', '23', '632919223298949120', '2019-10-13 12:35:01', 'stored', '商品', '1000', '1', '1000', '1');

-- ----------------------------
-- Table structure for hm_dictionaries_key
-- ----------------------------
DROP TABLE IF EXISTS `hm_dictionaries_key`;
CREATE TABLE `hm_dictionaries_key` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典表key',
  `name` varchar(255) DEFAULT NULL COMMENT 'name(key)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_dictionaries_key
-- ----------------------------
INSERT INTO `hm_dictionaries_key` VALUES ('1', '证件类型');
INSERT INTO `hm_dictionaries_key` VALUES ('2', '合作机构');

-- ----------------------------
-- Table structure for hm_dictionaries_value
-- ----------------------------
DROP TABLE IF EXISTS `hm_dictionaries_value`;
CREATE TABLE `hm_dictionaries_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典表value',
  `kid` int(11) DEFAULT NULL COMMENT 'keyid',
  `value` varchar(255) DEFAULT NULL COMMENT 'value',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_dictionaries_value
-- ----------------------------
INSERT INTO `hm_dictionaries_value` VALUES ('1', '1', '身份证');
INSERT INTO `hm_dictionaries_value` VALUES ('2', '1', '驾驶证');
INSERT INTO `hm_dictionaries_value` VALUES ('3', '1', '护照');
INSERT INTO `hm_dictionaries_value` VALUES ('4', '2', '美团');
INSERT INTO `hm_dictionaries_value` VALUES ('5', '2', '凯悦莱');
INSERT INTO `hm_dictionaries_value` VALUES ('6', '2', '刘总');
INSERT INTO `hm_dictionaries_value` VALUES ('7', '1', '军官证');
INSERT INTO `hm_dictionaries_value` VALUES ('13', '2', '希望饲料');

-- ----------------------------
-- Table structure for hm_everyday_room_price
-- ----------------------------
DROP TABLE IF EXISTS `hm_everyday_room_price`;
CREATE TABLE `hm_everyday_room_price` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '每日房价',
  `order_child_id` int(11) DEFAULT NULL COMMENT '子订单id',
  `time` date DEFAULT NULL COMMENT '时间',
  `money` double DEFAULT NULL COMMENT '金额',
  `status` varchar(11) DEFAULT 'no' COMMENT '夜审状态（yes / no）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_everyday_room_price
-- ----------------------------
INSERT INTO `hm_everyday_room_price` VALUES ('1', '1', '2019-10-10', '380', 'no');
INSERT INTO `hm_everyday_room_price` VALUES ('2', '1', '2019-10-11', '380', 'no');
INSERT INTO `hm_everyday_room_price` VALUES ('3', '1', '2019-10-12', '380', 'no');
INSERT INTO `hm_everyday_room_price` VALUES ('4', '2', '2019-10-10', '460', 'no');
INSERT INTO `hm_everyday_room_price` VALUES ('5', '2', '2019-10-11', '460', 'no');
INSERT INTO `hm_everyday_room_price` VALUES ('6', '2', '2019-10-12', '460', 'no');
INSERT INTO `hm_everyday_room_price` VALUES ('7', '3', '2019-10-10', '380', 'no');
INSERT INTO `hm_everyday_room_price` VALUES ('8', '3', '2019-10-11', '380', 'no');
INSERT INTO `hm_everyday_room_price` VALUES ('9', '3', '2019-10-12', '380', 'no');
INSERT INTO `hm_everyday_room_price` VALUES ('10', '4', '2019-10-10', '460', 'no');
INSERT INTO `hm_everyday_room_price` VALUES ('11', '4', '2019-10-11', '460', 'no');
INSERT INTO `hm_everyday_room_price` VALUES ('12', '4', '2019-10-12', '460', 'no');
INSERT INTO `hm_everyday_room_price` VALUES ('13', '5', '2019-10-10', '460', 'no');
INSERT INTO `hm_everyday_room_price` VALUES ('14', '6', '2019-10-10', '460', 'no');

-- ----------------------------
-- Table structure for hm_floor
-- ----------------------------
DROP TABLE IF EXISTS `hm_floor`;
CREATE TABLE `hm_floor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '楼层表',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `sort` int(11) DEFAULT NULL COMMENT '序号',
  `hotel_id` int(11) DEFAULT NULL COMMENT '酒店id',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` varchar(255) DEFAULT NULL COMMENT '状态 yes/no',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_floor
-- ----------------------------
INSERT INTO `hm_floor` VALUES ('1', '一层', '7', '1', '1', '2019-07-03 15:56:10', null, '2019-08-03 21:50:36', 'yes');
INSERT INTO `hm_floor` VALUES ('2', '二层', '6', '1', '1', '2019-07-03 15:56:39', '1', '2019-08-03 21:50:42', 'yes');
INSERT INTO `hm_floor` VALUES ('3', '三层', '5', '1', '1', '2019-07-09 21:12:13', null, '2019-07-10 13:41:42', 'yes');
INSERT INTO `hm_floor` VALUES ('4', '四层', '4', '1', '1', '2019-07-09 21:12:38', null, '2019-08-03 21:50:46', 'yes');

-- ----------------------------
-- Table structure for hm_hotel
-- ----------------------------
DROP TABLE IF EXISTS `hm_hotel`;
CREATE TABLE `hm_hotel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '酒店',
  `name` varchar(255) DEFAULT NULL COMMENT '酒店名称',
  `site` varchar(255) DEFAULT NULL COMMENT '地点',
  `picture` varchar(255) DEFAULT NULL COMMENT '酒店图片',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` varchar(255) DEFAULT NULL COMMENT '状态  yes 正常 no  删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_hotel
-- ----------------------------
INSERT INTO `hm_hotel` VALUES ('1', '上知湫文化驿站(长阳店)', '上知湫文化驿站(长阳店)', 'http://duoyuka.oss-cn-beijing.aliyuncs.com/PcbKy1_1562317360667.png', '010-67652122/010-67632622', '1', '2019-07-03 13:04:25', '1', '2019-08-03 21:43:20', 'yes');

-- ----------------------------
-- Table structure for hm_integral_record
-- ----------------------------
DROP TABLE IF EXISTS `hm_integral_record`;
CREATE TABLE `hm_integral_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '积分记录表',
  `check_in_member_id` int(11) DEFAULT NULL COMMENT '入住会员id',
  `type` varchar(255) DEFAULT NULL COMMENT '类型',
  `odd_numbers` bigint(20) DEFAULT NULL COMMENT '单号',
  `integral_change` double(255,2) DEFAULT NULL COMMENT '积分变动',
  `presenter_money` double(255,2) DEFAULT '0.00' COMMENT '赠送金额',
  `current_balance` double(255,2) DEFAULT NULL COMMENT '当前余额',
  `create_user_id` int(11) DEFAULT NULL COMMENT '操作人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_integral_record
-- ----------------------------
INSERT INTO `hm_integral_record` VALUES ('36', '24', '结账增加', '608380278850715648', '0.20', null, '99997987.66', '1', '2019-08-06 19:26:01', '扑克牌');
INSERT INTO `hm_integral_record` VALUES ('37', '24', '结账增加', '608380398241579008', '3.00', null, '99997990.66', '1', '2019-08-06 19:26:30', '床塌了');
INSERT INTO `hm_integral_record` VALUES ('38', '47', '手动添加', '608687563984601088', '99999999.00', null, '99999999.00', '1', '2019-08-07 15:47:04', '测试');
INSERT INTO `hm_integral_record` VALUES ('39', '47', '结账增加', '608688512572588032', '0.60', null, '99999999.60', '1', '2019-08-07 15:50:50', '测试');
INSERT INTO `hm_integral_record` VALUES ('40', '23', '结账增加', '609029801956605952', '48.59', null, '148.59', '1', '2019-08-08 14:27:00', '结账');
INSERT INTO `hm_integral_record` VALUES ('41', '23', '结账增加', '632919223424778240', '10.00', null, '158.59', '1', '2019-10-13 12:35:01', '1000');

-- ----------------------------
-- Table structure for hm_management_report
-- ----------------------------
DROP TABLE IF EXISTS `hm_management_report`;
CREATE TABLE `hm_management_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理层报表',
  `receivable_sum` double(16,2) DEFAULT NULL COMMENT '应收合计',
  `avg_room_rate` double(16,2) DEFAULT NULL COMMENT '平均房价',
  `avg_consumption_of_room` double(16,2) DEFAULT NULL COMMENT '房平均消费',
  `avg_consumption_of_person` double(16,2) DEFAULT NULL COMMENT '人均消费',
  `indemnity_income` double(16,2) DEFAULT NULL COMMENT '赔偿收入',
  `free_check_in_sum` int(11) DEFAULT NULL COMMENT '免费入住数',
  `room_sum` int(11) DEFAULT NULL COMMENT '房间总数',
  `maintain_room_sum` int(11) DEFAULT NULL COMMENT '维修房数',
  `member_room_sum` int(11) DEFAULT NULL COMMENT '会员房数',
  `member_card_sold_money` double(16,2) DEFAULT NULL COMMENT '会员卡销售金额',
  `room_late_sum` int(11) DEFAULT NULL COMMENT '房晚数',
  `person_late_sum` int(11) DEFAULT NULL COMMENT '人晚数',
  `commodity_revenues` double(16,2) DEFAULT NULL COMMENT '商品收入',
  `room_rate_adjustment` double(16,2) DEFAULT NULL COMMENT '房费调整',
  `occupancy_rate` double(16,4) DEFAULT NULL COMMENT '出租率',
  `REVPAR` double(11,2) DEFAULT NULL COMMENT 'REVPAR = 应收合计 / (总房间数 - 维修房数)',
  `disable_room_sum` int(11) DEFAULT NULL COMMENT '停用房间数',
  `rental_income` double DEFAULT NULL COMMENT '房租收入',
  `empty_room_sum` int(11) DEFAULT NULL COMMENT '空房数',
  `hour_room_late_sum` int(11) DEFAULT NULL COMMENT '钟点房晚数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `hotel_id` int(11) DEFAULT NULL COMMENT '酒店id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_management_report
-- ----------------------------
INSERT INTO `hm_management_report` VALUES ('1', '0.00', '0.00', '0.00', '0.00', '0.00', '0', '73', '0', '0', '0.00', '0', '0', '0.00', '0.00', '0.0000', '0.00', '0', '0', '67', '0', '2019-10-09 15:28:30', '1');

-- ----------------------------
-- Table structure for hm_managerdaily
-- ----------------------------
DROP TABLE IF EXISTS `hm_managerdaily`;
CREATE TABLE `hm_managerdaily` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grossreal_income` double(255,4) DEFAULT NULL COMMENT '总实际收入',
  `total_turnover` double(255,4) DEFAULT NULL COMMENT '总营业额',
  `number_order` double(255,4) DEFAULT NULL COMMENT '预订未到的房数',
  `maintenanceroom_number` double(255,4) DEFAULT NULL COMMENT '维修房数',
  `numberlocked_stores` double(255,4) DEFAULT NULL COMMENT '门店锁房数',
  `numberrooms_availablerent` double(255,4) DEFAULT NULL COMMENT '可出租房数',
  `totalnumber_guestrooms` double(255,4) DEFAULT NULL COMMENT '客房总数',
  `cash_disbursements` double(255,4) DEFAULT NULL COMMENT '现金支出',
  `cash` double(255,4) DEFAULT NULL COMMENT '现金收入',
  `throughout_dayrent` double(255,4) DEFAULT NULL COMMENT '全天日租',
  `rate_adjustment` double(255,4) DEFAULT NULL COMMENT '房费调整',
  `hour_rate` double(255,4) DEFAULT NULL COMMENT '钟点房费',
  `timeout_rate` double(255,4) DEFAULT NULL COMMENT '超时房费',
  `nuclearnight_roomcharge` double(255,4) DEFAULT NULL COMMENT '夜核房费',
  `compensation` double(255,4) DEFAULT NULL COMMENT '赔偿',
  `membership_fee` double(255,4) DEFAULT NULL COMMENT '会员卡费',
  `goods` double(255,4) DEFAULT NULL COMMENT '商品',
  `subtotal` double(255,4) DEFAULT NULL COMMENT '小计',
  `members` double(255,4) DEFAULT NULL COMMENT '会员',
  `agreement_unit` double(255,4) DEFAULT NULL COMMENT '协议单位 中介',
  `app` double(255,4) DEFAULT NULL,
  `micro_letter` double(255,4) DEFAULT NULL COMMENT '微信',
  `individual_traveler` double(255,4) DEFAULT NULL COMMENT '散客',
  `direct_booking` double(255,4) DEFAULT NULL COMMENT '直接预订',
  `enter` double(255,4) DEFAULT NULL COMMENT '步入',
  `daily_type` int(11) DEFAULT NULL COMMENT '1:营业状况统计,2:营业收入明细,3:房费收入分析,4:房晚数分析,5:平均房价分析,6:出租率分析						',
  `hotel_id` int(11) DEFAULT NULL COMMENT '酒店id',
  `date_time` date DEFAULT NULL COMMENT '日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of hm_managerdaily
-- ----------------------------
INSERT INTO `hm_managerdaily` VALUES ('1', '0.0000', '0.0000', '0.0000', '0.0000', '0.0000', '73.0000', '73.0000', '0.0000', '0.0000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '1', '1', '2019-10-09');
INSERT INTO `hm_managerdaily` VALUES ('2', null, null, null, null, null, null, null, null, null, '0.0000', '0.0000', '0.0000', '0.0000', '0.0000', '0.0000', '0.0000', '0.0000', '0.0000', null, null, null, null, null, null, null, '2', '1', '2019-10-09');
INSERT INTO `hm_managerdaily` VALUES ('3', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0.0000', '0.0000', '0.0000', null, null, '0.0000', '0.0000', '0.0000', '3', '1', '2019-10-09');
INSERT INTO `hm_managerdaily` VALUES ('4', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0.0000', '0.0000', '0.0000', null, null, '0.0000', '0.0000', '0.0000', '4', '1', '2019-10-09');
INSERT INTO `hm_managerdaily` VALUES ('5', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0.0000', '0.0000', '0.0000', null, null, '0.0000', '0.0000', '0.0000', '5', '1', '2019-10-09');
INSERT INTO `hm_managerdaily` VALUES ('6', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0.0000', '0.0000', '0.0000', null, null, '0.0000', '0.0000', '0.0000', '6', '1', '2019-10-09');

-- ----------------------------
-- Table structure for hm_manager_daily_one
-- ----------------------------
DROP TABLE IF EXISTS `hm_manager_daily_one`;
CREATE TABLE `hm_manager_daily_one` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '经理日报1',
  `realized_income` double DEFAULT NULL COMMENT '总实际收入',
  `turnover` double DEFAULT NULL COMMENT '总营业额',
  `reserve_noshow_room` int(11) DEFAULT NULL COMMENT '预定未到房数',
  `maintain_room` int(11) DEFAULT NULL COMMENT '维修房数',
  `store_lock_room` int(11) DEFAULT NULL COMMENT '门店锁房数',
  `leasable_room` int(11) DEFAULT NULL COMMENT '可出租房数',
  `guest_rooms` int(11) DEFAULT NULL COMMENT '客房总数',
  `cash_outlay` double DEFAULT NULL COMMENT '现金支出',
  `cash_income` double DEFAULT NULL COMMENT '现金收入',
  `daily_rental` double DEFAULT NULL COMMENT '全天日租',
  `room_rate_adjustment` double DEFAULT NULL COMMENT '房费调整',
  `hour_room_rate` double DEFAULT NULL COMMENT '钟点房费',
  `timeout_room_rate` double DEFAULT NULL COMMENT '超时房费',
  `auditor_room_rate` double DEFAULT NULL COMMENT '夜核房费',
  `compensation` double DEFAULT NULL COMMENT '赔偿',
  `member_card_rate` double DEFAULT NULL COMMENT '会员卡费',
  `commodity` double DEFAULT NULL COMMENT '商品',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_manager_daily_one
-- ----------------------------

-- ----------------------------
-- Table structure for hm_manager_daily_two
-- ----------------------------
DROP TABLE IF EXISTS `hm_manager_daily_two`;
CREATE TABLE `hm_manager_daily_two` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '经理日报2',
  `member` varchar(255) DEFAULT NULL COMMENT '会员',
  `individual_traveler` varchar(255) DEFAULT NULL COMMENT '散客',
  `bargaining_unit` varchar(255) DEFAULT NULL COMMENT '协议单位',
  `app` varchar(255) DEFAULT NULL COMMENT 'app',
  `wechat` varchar(255) DEFAULT NULL COMMENT '微信',
  `step_in` varchar(255) DEFAULT NULL COMMENT '步入',
  `direct_reserve` varchar(255) DEFAULT NULL COMMENT '直接预定',
  `type` varchar(255) DEFAULT NULL COMMENT '分类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_manager_daily_two
-- ----------------------------

-- ----------------------------
-- Table structure for hm_member
-- ----------------------------
DROP TABLE IF EXISTS `hm_member`;
CREATE TABLE `hm_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '入住会员表',
  `member_card_id` int(11) DEFAULT NULL COMMENT '会员卡id',
  `name` varchar(11) DEFAULT NULL COMMENT '会员名字',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `certificate_type` varchar(255) DEFAULT NULL COMMENT '证件类型',
  `certificate_number` varchar(255) DEFAULT NULL COMMENT '证件号',
  `integral` double(11,2) DEFAULT '0.00' COMMENT '积分',
  `stored_value` double(11,2) DEFAULT '0.00' COMMENT '储值',
  `birthday` varchar(255) DEFAULT NULL COMMENT '生日',
  `gender` varchar(255) DEFAULT NULL COMMENT '性别（man/woman）',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `salesman` varchar(255) DEFAULT NULL COMMENT '销售员',
  `state` varchar(255) DEFAULT NULL COMMENT '状态',
  `create_user_id` int(11) DEFAULT NULL COMMENT '操作人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_member
-- ----------------------------
INSERT INTO `hm_member` VALUES ('23', '529', '何小汶', '15214440324', '1', '230223', '158.59', '-1000.00', '', 'man', '', '', '系统', 'yes', '1', '2019-08-03 22:26:05', null, null);
INSERT INTO `hm_member` VALUES ('24', '530', '武阳', '18631323023', '3', '123456', '99997990.66', '47983.00', '2013-04-16', 'man', '北京', '', '京睿', 'yes', '1', '2019-08-05 11:10:25', '1', '2019-08-08 10:27:14');
INSERT INTO `hm_member` VALUES ('25', '531', '涂吉祥', '1581110', '1', '362228199003130014', '100.00', '-1840.00', '1990-03-13', 'man', '江西省宜春市上高县敖阳街道学园路410号附202室', 'abcd', '张三', 'yes', '1', '2019-08-05 14:23:29', '1', '2019-08-07 14:48:50');
INSERT INTO `hm_member` VALUES ('26', '430', 'sdsad', 'sadsa', '3', 'asdas', '0.00', '0.00', '', 'man', '', '', '', 'no', '1', '2019-08-05 17:00:29', '1', '2019-08-05 17:01:18');
INSERT INTO `hm_member` VALUES ('27', '532', '王洋', '18731612879', '1', '131022200106291658', '0.00', '-190.00', '', 'woman', '12412', '312312', '3123123', 'yes', '1', '2019-08-06 15:08:57', '1', '2019-08-06 15:11:12');
INSERT INTO `hm_member` VALUES ('28', '533', '111', '111', '2', '111', '-100.00', '123.00', '2019-08-05', 'man', 'sdsd', 'sds', 'sdd', 'yes', '1', '2019-08-06 18:12:03', '1', '2019-08-06 18:12:35');
INSERT INTO `hm_member` VALUES ('37', '637', '汪洋', '15690469182', '1', '123123123124124', '0.00', '-738.00', '', 'man', '4124', '', '', 'yes', '1', '2019-08-06 19:00:18', '1', '2019-08-06 19:00:29');
INSERT INTO `hm_member` VALUES ('46', '638', '小文2', '1231321', '1', '13132', '0.00', '0.00', '', 'man', '', '', '', 'yes', '1', '2019-08-06 19:27:28', null, null);
INSERT INTO `hm_member` VALUES ('47', '639', '丁欣洋', '112233', '1', '110228199609130934', '99999999.60', '10988800.00', '1996-09-13', 'man', '北京市密云区河南寨镇河南寨村中心街327号', '112233', '112', 'yes', '1', '2019-08-07 15:32:14', '1', '2019-08-08 10:26:20');

-- ----------------------------
-- Table structure for hm_member_card
-- ----------------------------
DROP TABLE IF EXISTS `hm_member_card`;
CREATE TABLE `hm_member_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员卡表',
  `member_level_id` int(11) DEFAULT NULL COMMENT '会员id',
  `card_number` varchar(255) DEFAULT '' COMMENT '卡号',
  `money` double DEFAULT NULL COMMENT '金额',
  `state` varchar(255) DEFAULT NULL COMMENT '状态（未售出unsold/使用中use/冻结freeze/删除no）',
  `selling_time` datetime DEFAULT NULL COMMENT '出售时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=669 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_member_card
-- ----------------------------
INSERT INTO `hm_member_card` VALUES ('429', '8', '00000010000', '100', 'no', null);
INSERT INTO `hm_member_card` VALUES ('430', '8', '00000010001', '100', 'use', '2019-08-05 17:00:29');
INSERT INTO `hm_member_card` VALUES ('431', '8', '00000010002', '100', 'freeze', null);
INSERT INTO `hm_member_card` VALUES ('432', '8', '00000010003', '100', 'freeze', null);
INSERT INTO `hm_member_card` VALUES ('433', '9', '00000010004', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('434', '8', '00000010005', '100', 'use', '2019-08-06 19:00:18');
INSERT INTO `hm_member_card` VALUES ('435', '8', '00000010006', '100', 'use', '2019-08-07 15:32:14');
INSERT INTO `hm_member_card` VALUES ('436', '8', '00000010007', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('437', '8', '00000010008', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('438', '8', '00000010009', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('439', '8', '00000010010', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('440', '8', '00000010011', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('441', '8', '00000010012', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('442', '8', '00000010013', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('443', '8', '00000010014', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('444', '8', '00000010015', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('445', '8', '00000010016', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('446', '8', '00000010017', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('447', '8', '00000010018', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('448', '8', '00000010019', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('449', '8', '00000010020', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('450', '8', '00000010021', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('451', '8', '00000010022', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('452', '8', '00000010023', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('453', '8', '00000010024', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('454', '8', '00000010025', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('455', '8', '00000010026', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('456', '8', '00000010027', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('457', '8', '00000010028', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('458', '8', '00000010029', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('459', '8', '00000010030', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('460', '8', '00000010031', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('461', '8', '00000010032', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('462', '8', '00000010033', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('463', '8', '00000010034', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('464', '8', '00000010035', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('465', '8', '00000010036', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('466', '8', '00000010037', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('467', '8', '00000010038', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('468', '8', '00000010039', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('469', '8', '00000010040', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('470', '8', '00000010041', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('471', '8', '00000010042', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('472', '8', '00000010043', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('473', '8', '00000010044', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('474', '8', '00000010045', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('475', '8', '00000010046', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('476', '8', '00000010047', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('477', '8', '00000010048', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('478', '8', '00000010049', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('479', '8', '00000010050', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('480', '8', '00000010051', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('481', '8', '00000010052', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('482', '8', '00000010053', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('483', '8', '00000010054', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('484', '8', '00000010055', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('485', '8', '00000010056', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('486', '8', '00000010057', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('487', '8', '00000010058', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('488', '8', '00000010059', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('489', '8', '00000010060', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('490', '8', '00000010061', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('491', '8', '00000010062', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('492', '8', '00000010063', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('493', '8', '00000010064', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('494', '8', '00000010065', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('495', '8', '00000010066', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('496', '8', '00000010067', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('497', '8', '00000010068', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('498', '8', '00000010069', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('499', '8', '00000010070', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('500', '8', '00000010071', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('501', '8', '00000010072', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('502', '8', '00000010073', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('503', '8', '00000010074', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('504', '8', '00000010075', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('505', '8', '00000010076', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('506', '8', '00000010077', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('507', '8', '00000010078', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('508', '8', '00000010079', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('509', '8', '00000010080', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('510', '8', '00000010081', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('511', '8', '00000010082', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('512', '8', '00000010083', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('513', '8', '00000010084', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('514', '8', '00000010085', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('515', '8', '00000010086', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('516', '8', '00000010087', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('517', '8', '00000010088', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('518', '8', '00000010089', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('519', '8', '00000010090', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('520', '8', '00000010091', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('521', '8', '00000010092', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('522', '8', '00000010093', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('523', '8', '00000010094', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('524', '8', '00000010095', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('525', '8', '00000010096', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('526', '8', '00000010097', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('527', '8', '00000010098', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('528', '8', '00000010099', '100', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('529', '9', '00000020000', '120', 'use', '2019-08-03 22:26:05');
INSERT INTO `hm_member_card` VALUES ('530', '9', '00000020001', '120', 'use', '2019-08-05 11:10:25');
INSERT INTO `hm_member_card` VALUES ('531', '9', '00000020002', '120', 'use', '2019-08-05 14:23:29');
INSERT INTO `hm_member_card` VALUES ('532', '9', '00000020003', '120', 'use', '2019-08-06 15:08:57');
INSERT INTO `hm_member_card` VALUES ('533', '9', '00000020004', '120', 'use', '2019-08-06 18:12:03');
INSERT INTO `hm_member_card` VALUES ('534', '9', '00000020005', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('535', '9', '00000020006', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('536', '9', '00000020007', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('537', '9', '00000020008', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('538', '9', '00000020009', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('539', '9', '00000020010', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('540', '9', '00000020011', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('541', '9', '00000020012', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('542', '9', '00000020013', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('543', '9', '00000020014', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('544', '9', '00000020015', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('545', '9', '00000020016', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('546', '9', '00000020017', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('547', '9', '00000020018', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('548', '9', '00000020019', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('549', '9', '00000020020', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('550', '9', '00000020021', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('551', '9', '00000020022', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('552', '9', '00000020023', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('553', '9', '00000020024', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('554', '9', '00000020025', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('555', '9', '00000020026', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('556', '9', '00000020027', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('557', '9', '00000020028', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('558', '9', '00000020029', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('559', '9', '00000020030', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('560', '9', '00000020031', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('561', '9', '00000020032', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('562', '9', '00000020033', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('563', '9', '00000020034', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('564', '9', '00000020035', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('565', '9', '00000020036', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('566', '9', '00000020037', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('567', '9', '00000020038', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('568', '9', '00000020039', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('569', '9', '00000020040', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('570', '9', '00000020041', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('571', '9', '00000020042', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('572', '9', '00000020043', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('573', '9', '00000020044', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('574', '9', '00000020045', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('575', '9', '00000020046', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('576', '9', '00000020047', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('577', '9', '00000020048', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('578', '9', '00000020049', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('579', '9', '00000020050', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('580', '9', '00000020051', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('581', '9', '00000020052', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('582', '9', '00000020053', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('583', '9', '00000020054', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('584', '9', '00000020055', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('585', '9', '00000020056', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('586', '9', '00000020057', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('587', '9', '00000020058', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('588', '9', '00000020059', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('589', '9', '00000020060', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('590', '9', '00000020061', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('591', '9', '00000020062', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('592', '9', '00000020063', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('593', '9', '00000020064', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('594', '9', '00000020065', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('595', '9', '00000020066', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('596', '9', '00000020067', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('597', '9', '00000020068', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('598', '9', '00000020069', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('599', '9', '00000020070', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('600', '9', '00000020071', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('601', '9', '00000020072', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('602', '9', '00000020073', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('603', '9', '00000020074', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('604', '9', '00000020075', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('605', '9', '00000020076', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('606', '9', '00000020077', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('607', '9', '00000020078', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('608', '9', '00000020079', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('609', '9', '00000020080', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('610', '9', '00000020081', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('611', '9', '00000020082', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('612', '9', '00000020083', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('613', '9', '00000020084', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('614', '9', '00000020085', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('615', '9', '00000020086', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('616', '9', '00000020087', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('617', '9', '00000020088', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('618', '9', '00000020089', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('619', '9', '00000020090', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('620', '9', '00000020091', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('621', '9', '00000020092', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('622', '9', '00000020093', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('623', '9', '00000020094', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('624', '9', '00000020095', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('625', '9', '00000020096', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('626', '9', '00000020097', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('627', '9', '00000020098', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('628', '9', '00000020099', '120', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('629', null, '00088800022', '190', 'use', '2018-11-10 08:00:00');
INSERT INTO `hm_member_card` VALUES ('630', null, '00088800023', '190', 'use', '2018-11-10 08:00:01');
INSERT INTO `hm_member_card` VALUES ('631', null, '00088800024', '190', 'use', '2018-11-10 08:00:02');
INSERT INTO `hm_member_card` VALUES ('632', null, '00088800025', '190', 'use', '2018-11-10 08:00:03');
INSERT INTO `hm_member_card` VALUES ('633', null, '00088800026', '190', 'use', '2018-11-10 08:00:04');
INSERT INTO `hm_member_card` VALUES ('634', null, '00088800027', '190', 'use', '2018-11-10 08:00:05');
INSERT INTO `hm_member_card` VALUES ('635', null, '00088800028', '190', 'use', '2018-11-10 08:00:06');
INSERT INTO `hm_member_card` VALUES ('636', null, '00088800029', '190', 'use', '2018-11-10 08:00:07');
INSERT INTO `hm_member_card` VALUES ('637', '9', '00000000567', '588', 'use', '2019-08-06 19:00:29');
INSERT INTO `hm_member_card` VALUES ('638', '9', '00000000568', '588', 'use', '2019-08-06 19:27:28');
INSERT INTO `hm_member_card` VALUES ('639', '9', '00000000569', '588', 'use', '2019-08-07 15:46:03');
INSERT INTO `hm_member_card` VALUES ('640', '9', '00000000570', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('641', '9', '00000000571', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('642', '9', '00000000572', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('643', '9', '00000000573', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('644', '9', '00000000574', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('645', '9', '00000000575', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('646', '9', '00000000576', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('647', '9', '00000000577', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('648', '9', '00000000578', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('649', '9', '00000000579', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('650', '9', '00000000580', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('651', '9', '00000000581', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('652', '9', '00000000582', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('653', '9', '00000000583', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('654', '9', '00000000584', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('655', '9', '00000000585', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('656', '9', '00000000586', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('657', '9', '00000000587', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('658', '9', '00000000588', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('659', '9', '00000000589', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('660', '9', '00000000590', '588', 'unsold', null);
INSERT INTO `hm_member_card` VALUES ('661', '8', '00088800030', '190', 'use', '2018-11-10 08:00:00');
INSERT INTO `hm_member_card` VALUES ('662', '8', '00088800031', '190', 'use', '2018-11-10 08:00:01');
INSERT INTO `hm_member_card` VALUES ('663', '8', '00088800032', '190', 'use', '2018-11-10 08:00:02');
INSERT INTO `hm_member_card` VALUES ('664', '9', '00088800033', '190', 'use', '2018-11-10 08:00:03');
INSERT INTO `hm_member_card` VALUES ('665', '9', '00088800034', '190', 'use', '2018-11-10 08:00:04');
INSERT INTO `hm_member_card` VALUES ('666', '9', '00088800035', '190', 'use', '2018-11-10 08:00:05');
INSERT INTO `hm_member_card` VALUES ('667', '9', '00088800036', '190', 'use', '2018-11-10 08:00:06');
INSERT INTO `hm_member_card` VALUES ('668', '9', '00088800037', '190', 'use', '2018-11-10 08:00:07');

-- ----------------------------
-- Table structure for hm_member_level
-- ----------------------------
DROP TABLE IF EXISTS `hm_member_level`;
CREATE TABLE `hm_member_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员级别表',
  `name` varchar(255) DEFAULT NULL COMMENT '会员名称',
  `state` varchar(255) DEFAULT NULL COMMENT '状态（yes/no物理删除）',
  `consume_get_integral` double DEFAULT '0' COMMENT '消费1元获得多少积分',
  `discount` double DEFAULT NULL COMMENT '折扣',
  `type` varchar(255) DEFAULT NULL COMMENT '能否储值（yes/no）',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_member_level
-- ----------------------------
INSERT INTO `hm_member_level` VALUES ('8', '会员卡', 'yes', '0', '1', 'no', '1', '2019-08-03 22:22:00', '1', '2019-08-03 22:22:05');
INSERT INTO `hm_member_level` VALUES ('9', '储值卡', 'yes', '0.01', '0.8', 'yes', '1', '2019-08-03 22:22:16', '1', '2019-08-03 22:22:59');
INSERT INTO `hm_member_level` VALUES ('10', '钻石会员', 'yes', '0.02', '0.75', 'yes', '1', '2019-08-06 18:29:49', '1', '2019-08-06 18:30:09');

-- ----------------------------
-- Table structure for hm_menu
-- ----------------------------
DROP TABLE IF EXISTS `hm_menu`;
CREATE TABLE `hm_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单表',
  `menu_name` varchar(500) DEFAULT NULL COMMENT '菜单名称',
  `p_id` int(11) DEFAULT NULL COMMENT '父id',
  `url` varchar(500) DEFAULT NULL COMMENT 'url',
  `path` varchar(2555) DEFAULT NULL COMMENT '前端跳转路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_menu
-- ----------------------------
INSERT INTO `hm_menu` VALUES ('1', '首页', '0', '/home/homeCount,/order/getRoomInfoById,/order/getReservationInfo,/order/getRemainingLease,/order/getRemainingLease,/order/changeRoom,/order/changeRoom,/order/addAlRoom,/order/updAlRoom,/order/getAlRoom,/order/updCheckInInfo,/order/getCheckInInfo,/home/home,/room/queryIndexRoomState,/roomRecord/selectRoomRecord,/room/getRoomMessage,/room/updateRoomMaintain,/room/updateRoomRemark,/room/updateRoomMaintain,/admin/changePassword', 'home.html');
INSERT INTO `hm_menu` VALUES ('2', '预订中心', '0', '/order/reservationRoom,', 'appoint.html');
INSERT INTO `hm_menu` VALUES ('3', '预约入住', '2', '/order/getReservationRoomInfo,/order/reservationRoom,/order/pay,/order/getPayInfo', 'appoint.html');
INSERT INTO `hm_menu` VALUES ('4', '房间预定', '2', 'url', 'schedule.html');
INSERT INTO `hm_menu` VALUES ('5', '统计图表', '2', '/room/querySc,/room/querySs,', 'chart.html');
INSERT INTO `hm_menu` VALUES ('6', '直接入住', '0', '/order/pay,/order/reservationRoom,/order/getPayInfo,', 'checkIn.html');
INSERT INTO `hm_menu` VALUES ('7', '会员管理', '0', 'url', 'member.html');
INSERT INTO `hm_menu` VALUES ('8', '会员信息', '7', '/member/selectMember,/memberLevel/selectmemberLevel,/member/addMember,/member/importMember,/member/exportMember,/memberCard/importMemberCard,/member/updateMember,/member/deleteMember,/member/getMemberInfo,/member/integralChange,/member/storedValueChange,/member/getConsumptionRecord,/integralRecord/getIntegralRecord,/storedValueRecord/getStoredValueRecord,/member/updateGetMemberCardNumber,/member/getMemberInfo,/member/getMemberCardNumber,', 'member.html');
INSERT INTO `hm_menu` VALUES ('9', '会员卡信息', '7', '/memberLevel/selectmemberLevel,/memberCard/selectMemberCard,/memberCard/updateMemberCard,/memberCard/deleteMemberCard,/memberCard/addMemberCard,/memberCard/exportMemberCard,', 'membershipCard.html');
INSERT INTO `hm_menu` VALUES ('10', '酒店管理', '0', 'url', 'roomType.html');
INSERT INTO `hm_menu` VALUES ('11', '房型管理', '10', '/roomType/queryRoomType,/roomType/insertRoomType,/roomType/updateRoomType,/roomType/deleteRoomType,', 'roomType.html');
INSERT INTO `hm_menu` VALUES ('12', '客房管理', '10', '/room/queryRoom,/room/selectRoom,/room/updatelockRoomState,/room/updatelockRoomOpen,/room/deleteByPrimaryKey,/room/updateByPrimaryKeySelective,/room/insertSelective,/order/closeOrder,', 'room.html');
INSERT INTO `hm_menu` VALUES ('13', '订单管理', '0', 'url', 'order.html');
INSERT INTO `hm_menu` VALUES ('14', '客房订单管理', '13', '/order/getOrderInfoById,/order/queryOrderList,/chilOrder/addCashPledge,/chilOrder/recorded,/chilOrder/free,/chilOrder/queryAccounts,/chilOrder/queryChildleAccounts,/chilOrder/transferAccounts,/chilOrder/alonePrint,/chilOrder/print,/order/getReservationRoomInfo,/order/getPayInfo,/order/checkOut,/order/getCheckOutInfo,/chilOrder/childleAccounts,/chilOrder/accounts,/order/checkOutRollback,/order/getSubscribeOrderChild,/order/getOrderChildPayInfo,', 'order.html');
INSERT INTO `hm_menu` VALUES ('15', '现金订单管理', '13', '/commodity/queryCommodiry,', 'cashOrder.html');
INSERT INTO `hm_menu` VALUES ('16', '报表', '0', 'url', 'residentStatement.html');
INSERT INTO `hm_menu` VALUES ('17', '在住报表', '16', '/order/getCheckInReport,', 'residentStatement.html');
INSERT INTO `hm_menu` VALUES ('18', '预离店报表', '16', '/order/getCheckOutReport,', 'pre-check-out-report.html');
INSERT INTO `hm_menu` VALUES ('19', '管理层报表', '16', '/managementReport/getManagementReport,', 'management_layer.html');
INSERT INTO `hm_menu` VALUES ('20', '收银入账信息报表', '16', '/statement/queryCashierSummary,/FormAccountDetailController/FormAccountDetail', 'cashier_account.html');
INSERT INTO `hm_menu` VALUES ('23', '营业收入报表', '16', '/income/getIncome,', 'income.html');
INSERT INTO `hm_menu` VALUES ('25', '经理日报表', '16', '/managerDaliy/queryTest,/managerDaliy/insertTest,/managerDaliy/selectManagerDaliy,', 'managerReport.html');
INSERT INTO `hm_menu` VALUES ('26', '充值日报表', '16', '/rechargeDaily/getRechargeDaily,', 'daily_recharge.html');
INSERT INTO `hm_menu` VALUES ('27', '设置', '0', 'url', 'hotelManage.html');
INSERT INTO `hm_menu` VALUES ('28', '角色设置', '27', '/admin/addRoleGrantAuthority,/admin/getAllRoleMenu,/admin/grantAuthority,/admin/getRoleList,/admin/delRoleByIds,/admin/getPermissionsMenu', 'role.html');
INSERT INTO `hm_menu` VALUES ('29', '班次设置', '27', '/classes/addClasses,/classes/updateClasses,/classes/deleteClasses,/classes/queryClasses,', 'scheduleSetting.html');
INSERT INTO `hm_menu` VALUES ('31', '会员折扣设置', '27', '/memberLevel/updateMemberLevel,/memberLevel/addMemberLevel,/memberLevel/selectmemberLevel,/memberRoomType/selectMemberRoomType,/memberRoomType/updateMemberRoomType', 'member_discount.html');
INSERT INTO `hm_menu` VALUES ('34', '交班', '0', '/shiftRecords/shifRecord,', 'javascript:showConfirm(1)');
INSERT INTO `hm_menu` VALUES ('35', '预交班', '34', '/shiftRecords/shifRecord,', 'javascript:showConfirm(1)');
INSERT INTO `hm_menu` VALUES ('36', '交班结账', '34', 'url', 'javascript:showConfirm(2)');
INSERT INTO `hm_menu` VALUES ('37', '交班记录', '34', '/shiftRecords/queryDetails,/shiftRecords/queryShiftRecordList,', 'handoverList.html');
INSERT INTO `hm_menu` VALUES ('38', '商品交易', '0', '/commodity/addCommodity,/commodity/queryCommodiry,/commodity/querySuspend,/commodity/suspend,/commodity/queryCommodiryById', 'javascript:shopAdd()');
INSERT INTO `hm_menu` VALUES ('39', '在住报表', '0', 'url', 'residentStatement.html');
INSERT INTO `hm_menu` VALUES ('40', '预离店报表', '0', 'url', 'pre-check-out-report.html');
INSERT INTO `hm_menu` VALUES ('41', '酒店设置', '27', '/hotel/addHotel,/hotel/deleteHotel,/hotel/updateHotel,/floor/addFloor,/floor/deleteFloor,/floor/updateFloor,/floor/queryFloor,', 'hotelManage.html');
INSERT INTO `hm_menu` VALUES ('42', '账户管理', '27', '/admin/adminLogin,/admin/adminRegister,/admin/getAdminById,/admin/delAdmins,/admin/changePassword,/admin/updateAdminUser,/admin/exitLogin,', 'admin.html');
INSERT INTO `hm_menu` VALUES ('43', '字典设置', '27', '/Dictionary/getDictionaryBOs,/Dictionary/addDic,/Dictionary/updDic,', 'setting.html');

-- ----------------------------
-- Table structure for hm_night_audit
-- ----------------------------
DROP TABLE IF EXISTS `hm_night_audit`;
CREATE TABLE `hm_night_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `child_id` int(11) DEFAULT NULL COMMENT '子订单id',
  `hotel_id` int(11) DEFAULT NULL COMMENT '酒店di',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `person_num` int(11) DEFAULT NULL COMMENT '在住人的数量',
  `source` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_night_audit
-- ----------------------------

-- ----------------------------
-- Table structure for hm_night_audit_copy
-- ----------------------------
DROP TABLE IF EXISTS `hm_night_audit_copy`;
CREATE TABLE `hm_night_audit_copy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `child_id` int(11) DEFAULT NULL COMMENT '子订单id',
  `hotel_id` int(11) DEFAULT NULL COMMENT '酒店di',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `person_num` int(11) DEFAULT NULL COMMENT '在住人的数量',
  `source` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_night_audit_copy
-- ----------------------------

-- ----------------------------
-- Table structure for hm_operating_income
-- ----------------------------
DROP TABLE IF EXISTS `hm_operating_income`;
CREATE TABLE `hm_operating_income` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '营业收入报表',
  `room_rate` decimal(20,2) DEFAULT '0.00' COMMENT '房费',
  `timeout_room_rate` decimal(20,2) DEFAULT '0.00' COMMENT '超时房费',
  `room_rate_adjustment` decimal(20,2) DEFAULT '0.00' COMMENT '房费调整',
  `other_rate` decimal(20,2) DEFAULT '0.00' COMMENT '其他费用',
  `commodity` decimal(20,2) DEFAULT '0.00' COMMENT '商品',
  `compensation` decimal(20,2) DEFAULT '0.00' COMMENT '赔偿',
  `member_card_rate` decimal(20,2) DEFAULT '0.00' COMMENT '会员卡收入',
  `debt_sum` decimal(20,2) DEFAULT '0.00' COMMENT '借方总计',
  `cash` decimal(20,2) DEFAULT '0.00' COMMENT '现金',
  `bank_card` decimal(20,2) DEFAULT '0.00' COMMENT '银行卡',
  `wechat` decimal(20,2) DEFAULT '0.00' COMMENT '微信',
  `alipay` decimal(20,2) DEFAULT '0.00' COMMENT '支付宝',
  `stored_pay` decimal(20,2) DEFAULT '0.00' COMMENT '储值支付',
  `credit_sum` decimal(20,2) DEFAULT '0.00' COMMENT '贷方总计',
  `night_auditor_time` date DEFAULT NULL COMMENT '夜审时间',
  `hotel_id` int(11) DEFAULT NULL COMMENT '酒店id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_operating_income
-- ----------------------------
INSERT INTO `hm_operating_income` VALUES ('1', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '2019-10-09', '1');

-- ----------------------------
-- Table structure for hm_order
-- ----------------------------
DROP TABLE IF EXISTS `hm_order`;
CREATE TABLE `hm_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单表',
  `order_placer` varchar(255) DEFAULT NULL COMMENT '下单人名称',
  `order_number` varchar(255) DEFAULT NULL COMMENT '单号',
  `check_type` varchar(255) DEFAULT NULL COMMENT '入住类型(day全天房、hour钟点房、free免费房)',
  `check_time` datetime DEFAULT NULL COMMENT '入住时间',
  `check_out_time` datetime DEFAULT NULL COMMENT '离店时间',
  `day_count` int(11) DEFAULT NULL COMMENT '入住天数',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `ID_number` varchar(255) DEFAULT NULL COMMENT '证件号码',
  `ID_number_type` int(11) DEFAULT NULL COMMENT '证件类型id',
  `order_type` varchar(255) DEFAULT NULL COMMENT '订单类型（subscribe预约入住,directly直接入住）',
  `clerk_ordering_name` varchar(255) DEFAULT NULL COMMENT '接单员姓名',
  `clerk_ordering_id` int(11) DEFAULT NULL COMMENT '接单员id',
  `members_id` int(11) DEFAULT NULL COMMENT '会员id',
  `memberId_or_organizationId` int(11) DEFAULT NULL COMMENT '合作机构id',
  `total_price` double DEFAULT NULL COMMENT '总房价',
  `hotel_id` int(11) DEFAULT NULL COMMENT '酒店id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `OTA` varchar(255) DEFAULT NULL,
  `channel` varchar(255) DEFAULT NULL COMMENT '客源',
  `subscribe_remark` varchar(1000) DEFAULT NULL COMMENT '预约备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_order
-- ----------------------------
INSERT INTO `hm_order` VALUES ('1', 'test', '631823781778989056', 'day', '2019-10-10 12:02:06', '2019-10-13 14:00:00', '3', 'test', 'test', '1', 'directly', '系统', '1', null, null, '2520', '1', '2019-10-10 12:02:10', '2019-10-10 12:02:10', '', '散客', null);
INSERT INTO `hm_order` VALUES ('2', 'demo', '631825833498288128', 'day', '2019-10-10 12:10:16', '2019-10-13 14:00:00', '3', 'demo', 'demo', '1', 'directly', '系统', '1', null, null, '2520', '1', '2019-10-10 12:10:17', '2019-10-10 12:10:17', '', '散客', null);
INSERT INTO `hm_order` VALUES ('3', '108', '631846520975826944', 'day', '2019-10-10 13:32:29', '2019-10-11 14:00:00', '1', '108', '108', '1', 'directly', '系统', '1', null, null, '460', '1', '2019-10-10 13:32:29', '2019-10-10 13:32:29', '', '散客', null);
INSERT INTO `hm_order` VALUES ('4', '110', '631846631340548096', 'day', '2019-10-10 13:32:55', '2019-10-11 14:00:00', '1', '110', '110', '1', 'directly', '系统', '1', null, null, '460', '1', '2019-10-10 13:32:56', '2019-10-10 13:32:56', '', '散客', null);

-- ----------------------------
-- Table structure for hm_order_child
-- ----------------------------
DROP TABLE IF EXISTS `hm_order_child`;
CREATE TABLE `hm_order_child` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '子订单',
  `order_id` int(11) DEFAULT NULL COMMENT '订单id',
  `start_time` datetime DEFAULT NULL COMMENT '入住时间',
  `end_time` datetime DEFAULT NULL COMMENT '离店时间',
  `pay_cash_num` double(255,2) DEFAULT '0.00' COMMENT '支付现金金额',
  `other_pay_num` double(255,2) DEFAULT '0.00' COMMENT '其他支付金额',
  `room_rate` double(255,2) DEFAULT '0.00' COMMENT '房费',
  `other_rate` double(255,2) DEFAULT '0.00' COMMENT '其他费用',
  `timeout_rate` double(255,2) DEFAULT '0.00' COMMENT '超时费用(无用字段)',
  `free_rate_num` double(255,2) DEFAULT '0.00' COMMENT '免单金额',
  `order_state` varchar(255) DEFAULT NULL COMMENT '订单状态(1.预约中2.入住待支付3.入住中4.退房待结账5.以结账6.已取消)',
  `practical_departure_time` datetime DEFAULT NULL COMMENT '实际离店时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `room_id` int(11) DEFAULT NULL COMMENT '房间id',
  `room_type_id` int(11) DEFAULT NULL COMMENT '房型id',
  `al_room_code` varchar(255) DEFAULT NULL COMMENT '联房识别码',
  `night_auditor_state` varchar(255) DEFAULT NULL COMMENT '夜审状态',
  `print_state` varchar(255) DEFAULT NULL COMMENT '打印状态（备用字段）',
  `main` varchar(255) DEFAULT NULL COMMENT '主账房 yes/no',
  `update_time` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`),
  KEY `index_name` (`start_time`),
  KEY `start_time` (`start_time`),
  KEY `end_time` (`end_time`),
  KEY `room_id` (`room_id`),
  KEY `order_state` (`order_state`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_order_child
-- ----------------------------
INSERT INTO `hm_order_child` VALUES ('1', '1', '2019-10-10 12:02:06', '2019-10-13 14:00:00', '500.00', '0.00', '0.00', '150.00', '0.00', '0.00', 'admissions', null, '', '254', '20', 'aa728349-f6b0-4881-8949-2bcfabb3bbf3', null, null, 'no', '2019-10-10 12:09:48');
INSERT INTO `hm_order_child` VALUES ('2', '1', '2019-10-10 12:02:06', '2019-10-13 14:00:00', '-500.00', '0.00', '0.00', '-150.00', '0.00', '0.00', 'admissions', null, 'test1', '255', '22', 'aa728349-f6b0-4881-8949-2bcfabb3bbf3', null, null, 'yes', '2019-10-10 12:12:39');
INSERT INTO `hm_order_child` VALUES ('3', '2', '2019-10-10 12:10:16', '2019-10-13 14:00:00', '600.00', '0.00', '0.00', '650.00', '0.00', '0.00', 'admissions', null, '', '265', '20', '24a0f5d4-64f6-468f-8ca2-15b34d0e1722', null, null, 'yes', '2019-10-10 12:10:24');
INSERT INTO `hm_order_child` VALUES ('4', '2', '2019-10-10 12:10:16', '2019-10-13 14:00:00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'admissions', null, 'demo1', '266', '22', '24a0f5d4-64f6-468f-8ca2-15b34d0e1722', null, null, null, '2019-10-10 12:10:24');
INSERT INTO `hm_order_child` VALUES ('5', '3', '2019-10-10 13:32:29', '2019-10-11 14:00:00', '800.00', '0.00', '0.00', '200.00', '0.00', '-100.00', 'admissions', null, '', '256', '22', '686192bd-0fd8-4280-a688-9a93ba194b4d', null, null, 'yes', '2019-10-10 13:32:34');
INSERT INTO `hm_order_child` VALUES ('6', '4', '2019-10-10 13:32:55', '2019-10-11 14:00:00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'admissions', null, '', '257', '22', 'd0a9d7c2-48c2-4663-ae99-091b6a8935d3', null, null, 'yes', '2019-10-10 13:33:01');

-- ----------------------------
-- Table structure for hm_order_child_backup
-- ----------------------------
DROP TABLE IF EXISTS `hm_order_child_backup`;
CREATE TABLE `hm_order_child_backup` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '子订单',
  `room_rate` double(255,2) DEFAULT '0.00' COMMENT '房费',
  `room_rate_record_id` int(255) DEFAULT NULL COMMENT '房费那条记录的id',
  `other_rate` double(255,2) DEFAULT '0.00' COMMENT '其他费用 (超时费)',
  `other_rate_record_id` int(255) DEFAULT NULL COMMENT '其他费用那条记录的id',
  `timeout_rate` double(255,2) DEFAULT '0.00' COMMENT '超时减免费',
  `timeout_rate_record_id` int(11) DEFAULT NULL COMMENT '超时减免费记录id',
  `free_rate_num` double(255,2) DEFAULT '0.00' COMMENT '免单金额',
  `order_state` varchar(255) DEFAULT NULL COMMENT '订单状态(1.预约中2.入住待支付3.入住中4.退房待结账5.以结账6.已取消)',
  `practical_departure_time` datetime DEFAULT NULL COMMENT '实际离店时间',
  `room_major_state` varchar(255) DEFAULT NULL COMMENT '房态',
  `end_time` datetime DEFAULT NULL COMMENT '预离店',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_order_child_backup
-- ----------------------------

-- ----------------------------
-- Table structure for hm_order_record
-- ----------------------------
DROP TABLE IF EXISTS `hm_order_record`;
CREATE TABLE `hm_order_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单记录表',
  `order_child_id` int(11) DEFAULT NULL COMMENT '子订单id',
  `info` varchar(500) DEFAULT NULL COMMENT '详细信息',
  `pay_type` varchar(11) DEFAULT NULL COMMENT '支付方式',
  `money` double DEFAULT NULL COMMENT '金额',
  `state` varchar(255) DEFAULT NULL COMMENT '状态',
  `project` varchar(255) DEFAULT NULL COMMENT '项目',
  `number` varchar(255) DEFAULT NULL COMMENT '数量',
  `create_user_id` int(11) DEFAULT NULL COMMENT '操作人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_order_record
-- ----------------------------
INSERT INTO `hm_order_record` VALUES ('1', '3', '入住押金(103转入)(106转入)(103转入)(106转入)', 'cash', '500', 'no', '押金', null, '1', '2019-10-10 12:09:48');
INSERT INTO `hm_order_record` VALUES ('2', '3', '入住押金(201转入)', 'cash', '100', 'no', '押金', null, '1', '2019-10-10 12:10:24');
INSERT INTO `hm_order_record` VALUES ('3', '3', '水(103转入)(106转入)(103转入)(106转入)', null, '-50', 'no', '商品', null, '1', '2019-10-10 12:10:55');
INSERT INTO `hm_order_record` VALUES ('4', '3', '午饭(103转入)(106转入)(103转入)(106转入)', null, '-100', 'no', '商品', null, '1', '2019-10-10 12:11:09');
INSERT INTO `hm_order_record` VALUES ('5', '3', 'test(201转入)', null, '-500', 'no', '商品', null, '1', '2019-10-10 13:10:31');
INSERT INTO `hm_order_record` VALUES ('6', '5', '入住押金', 'cash', '500', 'no', '押金', null, '1', '2019-10-10 13:32:34');
INSERT INTO `hm_order_record` VALUES ('7', '5', '入住押金(110转入)', 'cash', '300', 'no', '押金', null, '1', '2019-10-10 13:33:01');
INSERT INTO `hm_order_record` VALUES ('8', '5', 'test(110转入)', null, '-200', 'no', '商品', null, '1', '2019-10-10 13:33:21');
INSERT INTO `hm_order_record` VALUES ('9', '5', '100(110转入)', null, '100', 'no', '房费冲减', null, '1', '2019-10-10 13:33:42');

-- ----------------------------
-- Table structure for hm_organization
-- ----------------------------
DROP TABLE IF EXISTS `hm_organization`;
CREATE TABLE `hm_organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '机构表',
  `name` varchar(255) DEFAULT NULL COMMENT '机构名称',
  `customers_type` varchar(255) DEFAULT NULL COMMENT '客源类型',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_organization
-- ----------------------------

-- ----------------------------
-- Table structure for hm_organization_room_type
-- ----------------------------
DROP TABLE IF EXISTS `hm_organization_room_type`;
CREATE TABLE `hm_organization_room_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '机构房型中间表',
  `organization_id` int(11) DEFAULT NULL COMMENT '机构id',
  `room_type_id` int(11) DEFAULT NULL COMMENT '房型id',
  `price` double(10,0) DEFAULT NULL COMMENT '价格',
  `discount` double(255,0) DEFAULT NULL COMMENT '折扣',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_organization_room_type
-- ----------------------------

-- ----------------------------
-- Table structure for hm_recharge_daily
-- ----------------------------
DROP TABLE IF EXISTS `hm_recharge_daily`;
CREATE TABLE `hm_recharge_daily` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '充值日报表',
  `hotel_id` int(11) DEFAULT NULL COMMENT '酒店id',
  `member_card_number` varchar(255) DEFAULT NULL COMMENT '卡号',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `member_id` int(11) DEFAULT NULL COMMENT '会员id',
  `pay_type` varchar(255) DEFAULT NULL COMMENT '支付方式',
  `recharge_money` double(11,2) DEFAULT NULL COMMENT '充值金额',
  `presenter_money` double(11,2) DEFAULT NULL COMMENT '赠送金额',
  `create_user_id` int(11) DEFAULT NULL COMMENT '操作人id',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_recharge_daily
-- ----------------------------
INSERT INTO `hm_recharge_daily` VALUES ('3', '1', '00000020001', '武阳', '24', '现金', '50000.00', '2000.00', '1', '2019-08-05 11:11:28');
INSERT INTO `hm_recharge_daily` VALUES ('4', '1', '00000020003', '王洋', '27', '现金', '200.00', '10.00', '1', '2019-08-06 15:41:23');
INSERT INTO `hm_recharge_daily` VALUES ('5', '1', '00000020004', '111', '28', '支付宝', '111.00', '12.00', '1', '2019-08-06 18:13:30');
INSERT INTO `hm_recharge_daily` VALUES ('6', '1', '00000000569', '丁欣洋', '47', '现金', '1111.00', '1111.00', '1', '2019-08-07 15:47:42');
INSERT INTO `hm_recharge_daily` VALUES ('7', '1', '00000000569', '丁欣洋', '47', '现金', '9999999.00', '999999.00', '1', '2019-08-07 15:49:37');
INSERT INTO `hm_recharge_daily` VALUES ('8', '1', '00000020000', '何小汶', '23', '现金', '300.00', '0.00', '1', '2019-10-13 12:22:38');
INSERT INTO `hm_recharge_daily` VALUES ('9', '1', '00000020000', '何小汶', '23', '现金', '300.00', '0.00', '1', '2019-10-13 12:23:06');

-- ----------------------------
-- Table structure for hm_role
-- ----------------------------
DROP TABLE IF EXISTS `hm_role`;
CREATE TABLE `hm_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色表',
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `alternate_field` varchar(255) DEFAULT NULL COMMENT '备用字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_role
-- ----------------------------
INSERT INTO `hm_role` VALUES ('1', '系统', '1', null, null, null, null);
INSERT INTO `hm_role` VALUES ('7', '店长', null, '2019-07-10 16:01:47', null, '2019-08-05 16:36:25', null);
INSERT INTO `hm_role` VALUES ('13', '店员', null, '2019-08-08 11:16:29', null, '2019-08-08 11:16:29', null);

-- ----------------------------
-- Table structure for hm_role_hotel
-- ----------------------------
DROP TABLE IF EXISTS `hm_role_hotel`;
CREATE TABLE `hm_role_hotel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色酒店',
  `role_id` int(11) DEFAULT NULL,
  `hotel_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_role_hotel
-- ----------------------------
INSERT INTO `hm_role_hotel` VALUES ('5', '1', '1');
INSERT INTO `hm_role_hotel` VALUES ('27', '7', '1');
INSERT INTO `hm_role_hotel` VALUES ('28', '13', '1');

-- ----------------------------
-- Table structure for hm_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `hm_role_menu`;
CREATE TABLE `hm_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色菜单',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=396 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_role_menu
-- ----------------------------
INSERT INTO `hm_role_menu` VALUES ('1', '1', '1');
INSERT INTO `hm_role_menu` VALUES ('2', '1', '2');
INSERT INTO `hm_role_menu` VALUES ('3', '1', '3');
INSERT INTO `hm_role_menu` VALUES ('4', '1', '4');
INSERT INTO `hm_role_menu` VALUES ('5', '1', '5');
INSERT INTO `hm_role_menu` VALUES ('6', '1', '6');
INSERT INTO `hm_role_menu` VALUES ('7', '1', '7');
INSERT INTO `hm_role_menu` VALUES ('29', '1', '8');
INSERT INTO `hm_role_menu` VALUES ('30', '1', '9');
INSERT INTO `hm_role_menu` VALUES ('31', '1', '10');
INSERT INTO `hm_role_menu` VALUES ('32', '1', '11');
INSERT INTO `hm_role_menu` VALUES ('33', '1', '12');
INSERT INTO `hm_role_menu` VALUES ('34', '1', '13');
INSERT INTO `hm_role_menu` VALUES ('35', '1', '14');
INSERT INTO `hm_role_menu` VALUES ('36', '1', '15');
INSERT INTO `hm_role_menu` VALUES ('37', '1', '16');
INSERT INTO `hm_role_menu` VALUES ('38', '1', '17');
INSERT INTO `hm_role_menu` VALUES ('39', '1', '18');
INSERT INTO `hm_role_menu` VALUES ('40', '1', '19');
INSERT INTO `hm_role_menu` VALUES ('41', '1', '20');
INSERT INTO `hm_role_menu` VALUES ('44', '1', '23');
INSERT INTO `hm_role_menu` VALUES ('46', '1', '25');
INSERT INTO `hm_role_menu` VALUES ('47', '1', '26');
INSERT INTO `hm_role_menu` VALUES ('48', '1', '27');
INSERT INTO `hm_role_menu` VALUES ('50', '1', '28');
INSERT INTO `hm_role_menu` VALUES ('51', '1', '29');
INSERT INTO `hm_role_menu` VALUES ('53', '1', '31');
INSERT INTO `hm_role_menu` VALUES ('56', '1', '34');
INSERT INTO `hm_role_menu` VALUES ('57', '1', '35');
INSERT INTO `hm_role_menu` VALUES ('58', '1', '36');
INSERT INTO `hm_role_menu` VALUES ('59', '1', '37');
INSERT INTO `hm_role_menu` VALUES ('60', '1', '38');
INSERT INTO `hm_role_menu` VALUES ('61', '1', '39');
INSERT INTO `hm_role_menu` VALUES ('62', '1', '40');
INSERT INTO `hm_role_menu` VALUES ('163', '1', '41');
INSERT INTO `hm_role_menu` VALUES ('164', '1', '42');
INSERT INTO `hm_role_menu` VALUES ('165', '1', '43');
INSERT INTO `hm_role_menu` VALUES ('336', '7', '1');
INSERT INTO `hm_role_menu` VALUES ('337', '7', '2');
INSERT INTO `hm_role_menu` VALUES ('338', '7', '3');
INSERT INTO `hm_role_menu` VALUES ('339', '7', '4');
INSERT INTO `hm_role_menu` VALUES ('340', '7', '5');
INSERT INTO `hm_role_menu` VALUES ('341', '7', '6');
INSERT INTO `hm_role_menu` VALUES ('342', '7', '7');
INSERT INTO `hm_role_menu` VALUES ('343', '7', '8');
INSERT INTO `hm_role_menu` VALUES ('344', '7', '9');
INSERT INTO `hm_role_menu` VALUES ('345', '7', '10');
INSERT INTO `hm_role_menu` VALUES ('346', '7', '11');
INSERT INTO `hm_role_menu` VALUES ('347', '7', '12');
INSERT INTO `hm_role_menu` VALUES ('348', '7', '13');
INSERT INTO `hm_role_menu` VALUES ('349', '7', '14');
INSERT INTO `hm_role_menu` VALUES ('350', '7', '15');
INSERT INTO `hm_role_menu` VALUES ('351', '7', '16');
INSERT INTO `hm_role_menu` VALUES ('352', '7', '17');
INSERT INTO `hm_role_menu` VALUES ('353', '7', '18');
INSERT INTO `hm_role_menu` VALUES ('354', '7', '19');
INSERT INTO `hm_role_menu` VALUES ('355', '7', '20');
INSERT INTO `hm_role_menu` VALUES ('356', '7', '23');
INSERT INTO `hm_role_menu` VALUES ('357', '7', '25');
INSERT INTO `hm_role_menu` VALUES ('358', '7', '26');
INSERT INTO `hm_role_menu` VALUES ('359', '7', '27');
INSERT INTO `hm_role_menu` VALUES ('360', '7', '28');
INSERT INTO `hm_role_menu` VALUES ('361', '7', '29');
INSERT INTO `hm_role_menu` VALUES ('362', '7', '31');
INSERT INTO `hm_role_menu` VALUES ('363', '7', '41');
INSERT INTO `hm_role_menu` VALUES ('364', '7', '42');
INSERT INTO `hm_role_menu` VALUES ('365', '7', '43');
INSERT INTO `hm_role_menu` VALUES ('366', '7', '34');
INSERT INTO `hm_role_menu` VALUES ('367', '7', '35');
INSERT INTO `hm_role_menu` VALUES ('368', '7', '36');
INSERT INTO `hm_role_menu` VALUES ('369', '7', '37');
INSERT INTO `hm_role_menu` VALUES ('370', '7', '38');
INSERT INTO `hm_role_menu` VALUES ('371', '7', '39');
INSERT INTO `hm_role_menu` VALUES ('372', '7', '40');
INSERT INTO `hm_role_menu` VALUES ('374', '13', '1');
INSERT INTO `hm_role_menu` VALUES ('375', '13', '2');
INSERT INTO `hm_role_menu` VALUES ('376', '13', '3');
INSERT INTO `hm_role_menu` VALUES ('377', '13', '4');
INSERT INTO `hm_role_menu` VALUES ('378', '13', '5');
INSERT INTO `hm_role_menu` VALUES ('379', '13', '6');
INSERT INTO `hm_role_menu` VALUES ('380', '13', '7');
INSERT INTO `hm_role_menu` VALUES ('381', '13', '8');
INSERT INTO `hm_role_menu` VALUES ('382', '13', '13');
INSERT INTO `hm_role_menu` VALUES ('383', '13', '14');
INSERT INTO `hm_role_menu` VALUES ('384', '13', '15');
INSERT INTO `hm_role_menu` VALUES ('385', '13', '16');
INSERT INTO `hm_role_menu` VALUES ('386', '13', '17');
INSERT INTO `hm_role_menu` VALUES ('387', '13', '18');
INSERT INTO `hm_role_menu` VALUES ('388', '13', '20');
INSERT INTO `hm_role_menu` VALUES ('389', '13', '34');
INSERT INTO `hm_role_menu` VALUES ('390', '13', '35');
INSERT INTO `hm_role_menu` VALUES ('391', '13', '36');
INSERT INTO `hm_role_menu` VALUES ('392', '13', '37');
INSERT INTO `hm_role_menu` VALUES ('393', '13', '38');
INSERT INTO `hm_role_menu` VALUES ('394', '13', '39');
INSERT INTO `hm_role_menu` VALUES ('395', '13', '40');

-- ----------------------------
-- Table structure for hm_room
-- ----------------------------
DROP TABLE IF EXISTS `hm_room`;
CREATE TABLE `hm_room` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '房间',
  `room_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '房间名称',
  `room_type_id` int(11) DEFAULT NULL COMMENT '房型id',
  `room_major_state` varchar(255) CHARACTER SET utf8 DEFAULT 'vacant' COMMENT '房间主状态 空房,入住中,超时,脏房',
  `room_state` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '房间状态 是否维修/yes/no',
  `remark` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  `lock_room_state` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '是否锁房 yes|no',
  `lock_room_start_time` datetime DEFAULT NULL COMMENT '锁房开始时间',
  `lock_room_end_time` datetime DEFAULT NULL COMMENT '锁房结束时间',
  `room_auxiliary_status` varchar(255) CHARACTER SET utf8 DEFAULT 'no' COMMENT '是否是钟点房 yes|no',
  `room_auxiliary_status_stand` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '是否是免费房 yes|no',
  `setting` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '设置',
  `character` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '特性',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `floor_id` int(11) DEFAULT NULL COMMENT '楼层id',
  `hotel_id` int(11) DEFAULT NULL COMMENT '酒店id',
  `show` varchar(15) CHARACTER SET utf8 DEFAULT 'yes' COMMENT '是否显示 yes|no',
  `network` varchar(15) COLLATE utf8_general_mysql500_ci DEFAULT NULL COMMENT '网络上下架 yes|no',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=329 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

-- ----------------------------
-- Records of hm_room
-- ----------------------------
INSERT INTO `hm_room` VALUES ('252', '101', '20', 'vacant', 'no', '1212', 'no', null, null, 'no', 'yes', '', '', null, null, null, null, '1', '1', 'no', null);
INSERT INTO `hm_room` VALUES ('253', '102', '22', 'vacant', 'no', '54645', 'no', null, null, 'yes', 'yes', '', '', null, null, null, null, '1', '1', 'no', null);
INSERT INTO `hm_room` VALUES ('254', '103', '20', 'inthe', 'no', '4545', 'no', null, null, 'no', 'yes', '', '', null, null, null, null, '1', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('255', '106', '22', 'inthe', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '1', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('256', '108', '22', 'timeout', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '1', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('257', '110', '22', 'timeout', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '1', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('258', '112', '22', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '1', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('259', '116', '22', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '1', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('260', '118', '22', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '1', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('261', '120', '22', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '1', '1', 'no', null);
INSERT INTO `hm_room` VALUES ('262', '122', '22', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '1', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('263', '126', '22', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '1', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('264', '128', '22', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '1', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('265', '201', '20', 'inthe', 'no', 'cd', null, null, null, 'yes', 'yes', 'a', 'bc', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('266', '202', '22', 'inthe', 'no', 'test', 'no', null, null, 'yes', 'yes', 'a', 'aa', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('267', '203', '20', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('268', '205', '20', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('269', '206', '22', 'vacant', 'no', '测试报表', 'no', null, null, 'yes', 'yes', '', '', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('270', '207', '20', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('272', '208', '22', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('273', '209', '20', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('274', '210', '22', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('275', '211', '20', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('276', '212', '22', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('277', '213', '20', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('278', '216', '22', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('279', '218', '22', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('280', '220', '22', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('281', '222', '22', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('282', '226', '22', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('283', '228', '22', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('284', '301', '22', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('285', '302', '23', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('286', '303', '23', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('287', '305', '23', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('288', '306', '23', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('289', '307', '23', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('290', '308', '23', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('291', '309', '23', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('292', '310', '23', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('293', '311', '23', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('294', '312', '23', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('295', '313', '23', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('296', '315', '23', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('297', '316', '23', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('298', '317', '23', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('299', '318', '23', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('300', '320', '23', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('301', '322', '23', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('302', '326', '23', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('303', '328', '23', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '3', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('304', '401', '21', 'vacant', 'no', '', null, null, null, 'yes', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('305', '402', '22', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('306', '403', '21', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('307', '405', '21', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('308', '406', '22', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('309', '407', '21', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('310', '408', '22', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('311', '409', '21', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('312', '410', '22', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('313', '411', '21', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('314', '412', '22', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('315', '413', '21', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('316', '415', '21', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('317', '416', '22', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('318', '417', '21', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('319', '418', '22', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('320', '420', '22', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('321', '422', '22', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('322', '426', '22', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('323', '428', '22', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '4', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('324', '666', '25', 'vacant', 'no', 'as', 'no', null, null, 'no', 'yes', '', '', null, null, null, null, '1', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('325', '888', '24', 'vacant', 'no', '', null, null, null, 'no', 'yes', '', '', null, null, null, null, '2', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('326', '800', '26', 'vacant', 'no', '1', 'no', null, null, 'no', 'no', '', '', null, null, null, null, '1', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('327', '801', '26', 'vacant', 'no', '1', 'no', null, null, 'no', 'no', '', '', null, null, null, null, '1', '1', 'yes', null);
INSERT INTO `hm_room` VALUES ('328', '802', '26', 'vacant', 'no', '', null, null, null, 'no', 'no', '', '', null, null, null, null, '1', '1', 'yes', null);

-- ----------------------------
-- Table structure for hm_room_record
-- ----------------------------
DROP TABLE IF EXISTS `hm_room_record`;
CREATE TABLE `hm_room_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '房间记录表',
  `room_id` int(11) DEFAULT NULL COMMENT '房间id',
  `virgin_state` varchar(255) DEFAULT NULL COMMENT '原状态',
  `new_state` varchar(255) DEFAULT NULL COMMENT '新状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_room_record
-- ----------------------------
INSERT INTO `hm_room_record` VALUES ('1', '254', 'vacant', 'inthe', '直接入住', '1', '2019-10-10 12:02:08');
INSERT INTO `hm_room_record` VALUES ('2', '255', 'vacant', 'inthe', '直接入住', '1', '2019-10-10 12:02:08');
INSERT INTO `hm_room_record` VALUES ('3', '265', 'vacant', 'inthe', '直接入住', '1', '2019-10-10 12:10:17');
INSERT INTO `hm_room_record` VALUES ('4', '266', 'vacant', 'inthe', '直接入住', '1', '2019-10-10 12:10:17');
INSERT INTO `hm_room_record` VALUES ('5', '256', 'vacant', 'inthe', '直接入住', '1', '2019-10-10 13:32:29');
INSERT INTO `hm_room_record` VALUES ('6', '257', 'vacant', 'inthe', '直接入住', '1', '2019-10-10 13:32:56');
INSERT INTO `hm_room_record` VALUES ('7', '256', 'inthe', 'timeout', '入住超时', '1', '2019-10-13 12:20:00');
INSERT INTO `hm_room_record` VALUES ('8', '257', 'inthe', 'timeout', '入住超时', '1', '2019-10-13 12:20:00');

-- ----------------------------
-- Table structure for hm_room_type
-- ----------------------------
DROP TABLE IF EXISTS `hm_room_type`;
CREATE TABLE `hm_room_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '房型表',
  `room_type_name` varchar(255) DEFAULT NULL COMMENT '房型名称',
  `room_type_picture` varchar(255) DEFAULT NULL COMMENT '房型图片',
  `basic_price` double(10,0) DEFAULT NULL COMMENT '基础价格',
  `hour_room_price` double(10,0) DEFAULT '0' COMMENT '钟点房价格',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `show` varchar(15) DEFAULT 'yes' COMMENT '是否显示 yes|no',
  `hotel_id` int(11) DEFAULT NULL COMMENT '酒店id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_room_type
-- ----------------------------
INSERT INTO `hm_room_type` VALUES ('20', '标准间', null, '380', '150', '1', '2019-08-03 21:40:52', null, null, 'yes', '1');
INSERT INTO `hm_room_type` VALUES ('21', '大床房', null, '380', '150', '1', '2019-08-03 21:41:09', null, null, 'yes', '1');
INSERT INTO `hm_room_type` VALUES ('22', '豪华标间', null, '460', '150', '1', '2019-08-03 21:41:23', null, null, 'yes', '1');
INSERT INTO `hm_room_type` VALUES ('23', '豪华大床房', null, '460', '150', '1', '2019-08-03 21:41:33', null, null, 'yes', '1');
INSERT INTO `hm_room_type` VALUES ('24', '套间(大床)', null, '520', '240', '1', '2019-08-03 21:41:54', null, null, 'yes', '1');
INSERT INTO `hm_room_type` VALUES ('25', '套房(标间)', null, '580', '240', '1', '2019-08-03 21:42:19', null, null, 'yes', '1');
INSERT INTO `hm_room_type` VALUES ('26', '测试日期冲突', null, '1', '1', '1', '2019-08-04 10:09:04', null, null, 'yes', '1');

-- ----------------------------
-- Table structure for hm_shift_records
-- ----------------------------
DROP TABLE IF EXISTS `hm_shift_records`;
CREATE TABLE `hm_shift_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '交班记录表',
  `classes_id` int(11) DEFAULT NULL COMMENT '班次id',
  `admin_id` int(11) DEFAULT NULL COMMENT '操作员id',
  `general_income` double(255,2) DEFAULT NULL COMMENT '总收入',
  `cash_income` double(255,2) DEFAULT NULL COMMENT '现金收',
  `cash_back` double(255,2) DEFAULT NULL COMMENT '现金退',
  `cash_amount` double(255,2) DEFAULT NULL COMMENT '现金合计',
  `bank_card_income` double(255,2) DEFAULT NULL COMMENT '银行卡收',
  `bank_card_back` double(255,2) DEFAULT NULL COMMENT '银行卡退',
  `bank_card_amount` double(255,2) DEFAULT NULL COMMENT '银行卡合计',
  `wechat_income` double(255,2) DEFAULT NULL COMMENT '微信收',
  `wechat_back` double(255,2) DEFAULT NULL COMMENT '微信退',
  `wechat_amount` double(255,2) DEFAULT NULL COMMENT '微信合计',
  `alipay_income` double(255,2) DEFAULT NULL COMMENT '支付宝收',
  `alipay_back` double(255,2) DEFAULT NULL COMMENT '支付宝退',
  `alipay_amount` double(255,2) DEFAULT NULL COMMENT '支付宝合计',
  `integral_income` double(255,2) DEFAULT NULL COMMENT '积分进',
  `integral_back` double(255,2) DEFAULT NULL COMMENT '积分出',
  `integral_amount` double(255,2) DEFAULT NULL COMMENT '积分合计',
  `stored_value_income` double(255,2) DEFAULT NULL COMMENT '储值收',
  `stored_value_back` double(255,2) DEFAULT NULL COMMENT '储值退',
  `stored_value_amount` double(255,2) DEFAULT NULL COMMENT '储值合计',
  `member_card_sell_count` int(11) DEFAULT NULL COMMENT '会员卡售出数量',
  `attendance_time` datetime DEFAULT NULL COMMENT '上班时间',
  `closing_time` datetime DEFAULT NULL COMMENT '下班时间',
  `other_income` double(255,2) DEFAULT NULL COMMENT '其他收入',
  `other_back` double(255,2) DEFAULT NULL COMMENT '其他支出',
  `other_amount` double(255,2) DEFAULT NULL COMMENT '其他总计',
  `hotel_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_shift_records
-- ----------------------------
INSERT INTO `hm_shift_records` VALUES ('4', '1', '1', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0', '2019-08-08 17:00:27', '2019-09-24 18:38:45', '0.00', '0.00', '0.00', '1');
INSERT INTO `hm_shift_records` VALUES ('5', null, '1', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0', '2019-09-25 10:14:53', null, '0.00', '0.00', '0.00', '1');

-- ----------------------------
-- Table structure for hm_stored_value_record
-- ----------------------------
DROP TABLE IF EXISTS `hm_stored_value_record`;
CREATE TABLE `hm_stored_value_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '储值记录表',
  `check_in_member_id` int(11) DEFAULT NULL COMMENT '入住会员id',
  `type` varchar(255) DEFAULT NULL COMMENT '类型',
  `odd_numbers` bigint(20) DEFAULT NULL COMMENT '单号',
  `stored_value_money` double(11,2) DEFAULT NULL COMMENT '储值金额',
  `presenter_money` double(11,2) DEFAULT '0.00' COMMENT '赠送金额',
  `current_balance` double(11,2) DEFAULT NULL COMMENT '当前余额',
  `create_user_id` int(11) DEFAULT NULL COMMENT '操作人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_stored_value_record
-- ----------------------------
INSERT INTO `hm_stored_value_record` VALUES ('36', '23', '房费', '607339822033403904', '100.00', null, '-100.00', '1', '2019-08-03 22:31:36', '入住支付');
INSERT INTO `hm_stored_value_record` VALUES ('37', '24', '现金', '607893430688808960', '50000.00', '2000.00', '52000.00', '1', '2019-08-05 11:11:27', '飒飒');
INSERT INTO `hm_stored_value_record` VALUES ('38', '24', '房费', '607893787045265408', '1000.00', null, '51000.00', '1', '2019-08-05 11:12:52', '入住支付');
INSERT INTO `hm_stored_value_record` VALUES ('39', '24', '结账退款', '607894252675923968', '516.00', '0.00', '51516.00', '1', '2019-08-05 11:14:43', '结账');
INSERT INTO `hm_stored_value_record` VALUES ('40', '24', '储值支付', '607895588578525184', '200.00', '200.00', '51316.00', '1', '2019-08-05 11:20:02', '阿萨');
INSERT INTO `hm_stored_value_record` VALUES ('41', '24', '房费', '607897667439165440', '600.00', null, '50716.00', '1', '2019-08-05 11:28:18', '入住支付');
INSERT INTO `hm_stored_value_record` VALUES ('42', '27', '现金', '609048379703427072', '200.00', '10.00', '210.00', '1', '2019-08-06 15:41:23', '1');
INSERT INTO `hm_stored_value_record` VALUES ('43', '27', '房费', '609052000969031680', '400.00', null, '-190.00', '1', '2019-08-06 15:55:46', '入住支付');
INSERT INTO `hm_stored_value_record` VALUES ('44', '24', '储值支付', '608347765029732352', '300.00', '0.00', '50416.00', '1', '2019-08-06 17:16:49', '入住押金');
INSERT INTO `hm_stored_value_record` VALUES ('45', '28', '支付宝', '608362027794038784', '111.00', '12.00', '123.00', '1', '2019-08-06 18:13:30', 'we');
INSERT INTO `hm_stored_value_record` VALUES ('46', '24', '储值支付', '608364293548670976', '13.00', '13.00', '50403.00', '1', '2019-08-06 18:22:30', 'sdsd');
INSERT INTO `hm_stored_value_record` VALUES ('47', '24', '储值支付', '608364789986492416', '100.00', '100.00', '50303.00', '1', '2019-08-06 18:24:28', '水');
INSERT INTO `hm_stored_value_record` VALUES ('48', '37', '押金', '608374028998017024', '738.00', null, '-738.00', '1', '2019-08-06 19:01:12', '入住支付');
INSERT INTO `hm_stored_value_record` VALUES ('49', '24', '房费', '608379680159956992', '2000.00', null, '48303.00', '1', '2019-08-06 19:23:38', '入住支付');
INSERT INTO `hm_stored_value_record` VALUES ('50', '24', '储值支付', '608380278808772608', '20.00', '20.00', '48283.00', '1', '2019-08-06 19:26:01', '扑克牌');
INSERT INTO `hm_stored_value_record` VALUES ('51', '24', '储值支付', '608380398195441664', '300.00', '300.00', '47983.00', '1', '2019-08-06 19:26:30', '床塌了');
INSERT INTO `hm_stored_value_record` VALUES ('52', '23', '房费', '608406429690232832', '500.00', null, '-600.00', '1', '2019-08-06 21:09:56', '入住支付');
INSERT INTO `hm_stored_value_record` VALUES ('53', '47', '现金', '608687724496420864', '1111.00', '1111.00', '2222.00', '1', '2019-08-07 15:47:42', '111');
INSERT INTO `hm_stored_value_record` VALUES ('54', '47', '现金', '608688206191263744', '9999999.00', '999999.00', '11002220.00', '1', '2019-08-07 15:49:37', '测试');
INSERT INTO `hm_stored_value_record` VALUES ('55', '47', '储值支付', '608688512413204480', '60.00', '0.00', '11002160.00', '1', '2019-08-07 15:50:50', '测试');
INSERT INTO `hm_stored_value_record` VALUES ('56', '47', '房费', '609034715596324864', '1840.00', '0.00', '11000320.00', '1', '2019-08-08 14:46:32', '入住支付');
INSERT INTO `hm_stored_value_record` VALUES ('57', '47', '储值支付', '609035677568335872', '10000.00', '0.00', '10990320.00', '1', '2019-08-08 14:50:21', '入住押金');
INSERT INTO `hm_stored_value_record` VALUES ('58', '47', '房费', '609045487399469056', '1520.00', '0.00', '10988800.00', '1', '2019-08-08 15:29:20', '入住支付');
INSERT INTO `hm_stored_value_record` VALUES ('59', '25', '房费', '609045688843501568', '1840.00', '0.00', '-1840.00', '1', '2019-08-08 15:30:08', '入住支付');
INSERT INTO `hm_stored_value_record` VALUES ('60', '23', '现金', '632916105614065664', '300.00', '0.00', '-300.00', '1', '2019-10-13 12:22:38', '300');
INSERT INTO `hm_stored_value_record` VALUES ('61', '23', '现金', '632916222874222592', '300.00', '0.00', '0.00', '1', '2019-10-13 12:23:06', '0');
INSERT INTO `hm_stored_value_record` VALUES ('62', '23', '储值支付', '632919223391223808', '1000.00', '0.00', '-1000.00', '1', '2019-10-13 12:35:01', '1000');

-- ----------------------------
-- Table structure for hm_test
-- ----------------------------
DROP TABLE IF EXISTS `hm_test`;
CREATE TABLE `hm_test` (
  `id` int(11) NOT NULL,
  `num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_test
-- ----------------------------
INSERT INTO `hm_test` VALUES ('1', '100');
INSERT INTO `hm_test` VALUES ('2', '300');

-- ----------------------------
-- Table structure for hm_update_price
-- ----------------------------
DROP TABLE IF EXISTS `hm_update_price`;
CREATE TABLE `hm_update_price` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `time` date DEFAULT NULL,
  `room_type_id` int(11) DEFAULT NULL,
  `money` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hm_update_price
-- ----------------------------
