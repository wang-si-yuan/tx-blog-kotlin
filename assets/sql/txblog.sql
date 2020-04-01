/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云测试
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : rm-2zevkr8602v12ubuxao.mysql.rds.aliyuncs.com:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 02/04/2020 00:20:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pdman_db_version
-- ----------------------------
DROP TABLE IF EXISTS `pdman_db_version`;
CREATE TABLE `pdman_db_version` (
  `DB_VERSION` varchar(256) DEFAULT NULL,
  `VERSION_DESC` varchar(1024) DEFAULT NULL,
  `CREATED_TIME` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `content` text,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test
-- ----------------------------
BEGIN;
INSERT INTO `test` VALUES (1, '云', '你好', NULL);
INSERT INTO `test` VALUES (2, '国', '在干嘛呀？', NULL);
INSERT INTO `test` VALUES (3, '小明', '中学', '学生');
INSERT INTO `test` VALUES (4, '小红', '小学', '红色');
INSERT INTO `test` VALUES (5, '晓东', '青年', '承认');
COMMIT;

-- ----------------------------
-- Table structure for test1
-- ----------------------------
DROP TABLE IF EXISTS `test1`;
CREATE TABLE `test1` (
  `id` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `content` text,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test1
-- ----------------------------
BEGIN;
INSERT INTO `test1` VALUES (1, '云', '你好', NULL);
INSERT INTO `test1` VALUES (2, '国', '在干嘛呀？', NULL);
INSERT INTO `test1` VALUES (3, '小明', '中学', '学生');
INSERT INTO `test1` VALUES (4, '小红', '小学', '红色');
INSERT INTO `test1` VALUES (5, '晓东', '青年', '承认');
COMMIT;

-- ----------------------------
-- Table structure for tx_category
-- ----------------------------
DROP TABLE IF EXISTS `tx_category`;
CREATE TABLE `tx_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `category` varchar(64) NOT NULL COMMENT '分类名称',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `created_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间戳',
  `updated_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `category` (`category`),
  UNIQUE KEY `category_2` (`category`),
  UNIQUE KEY `category_3` (`category`),
  UNIQUE KEY `category_4` (`category`),
  UNIQUE KEY `category_5` (`category`),
  UNIQUE KEY `category_6` (`category`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='分类';

-- ----------------------------
-- Records of tx_category
-- ----------------------------
BEGIN;
INSERT INTO `tx_category` VALUES (1, '教程', 1, 0, 1585311134130, 1585539606351);
INSERT INTO `tx_category` VALUES (2, 'JVM 语言&生态', 1, 0, 1585532182432, 0);
INSERT INTO `tx_category` VALUES (3, 'SpringBoot', 1, 0, 1585532219779, 0);
INSERT INTO `tx_category` VALUES (4, 'Gradle', 1, 0, 1585532254673, 0);
COMMIT;

-- ----------------------------
-- Table structure for tx_comment
-- ----------------------------
DROP TABLE IF EXISTS `tx_comment`;
CREATE TABLE `tx_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content` varchar(500) NOT NULL COMMENT '内容',
  `name` varchar(64) DEFAULT NULL,
  `email` varchar(128) NOT NULL COMMENT '邮箱',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父级ID',
  `root_id` int(11) NOT NULL DEFAULT '0' COMMENT '根ID',
  `post_id` int(11) NOT NULL COMMENT '文章ID',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `created_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间戳',
  `updated_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间戳',
  `level` int(255) DEFAULT '0' COMMENT '等级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='评论';

-- ----------------------------
-- Records of tx_comment
-- ----------------------------
BEGIN;
INSERT INTO `tx_comment` VALUES (1, '评论测试哟', 'archx', 'admin@qq.com', 0, 0, 11, 1, 1585654191680, 0, 0);
INSERT INTO `tx_comment` VALUES (2, '评论测试回复', 'archx', 'admin@qq.com', 1, 1, 11, 1, 1585654491680, 0, 1);
INSERT INTO `tx_comment` VALUES (3, '回复的回复', 'archx', 'admin@qq.com', 2, 1, 11, 1, 1585754491680, 1585751807328, 2);
INSERT INTO `tx_comment` VALUES (4, '再来一条', 'admin', 'admin@qq.com', 0, 0, 11, 1, 1585654491680, 0, 0);
INSERT INTO `tx_comment` VALUES (5, '不回复你哟', 'archx', 'admin@qq.com', 4, 4, 11, 1, 1585654591680, 0, 1);
INSERT INTO `tx_comment` VALUES (8, 'bbb', 'aaa', '', 0, 0, 11, 1, 1585749879343, 1585751213764, 0);
INSERT INTO `tx_comment` VALUES (9, 'ccc', 'ccc', '', 8, 8, 11, 1, 1585751484694, 1585752027832, 1);
COMMIT;

-- ----------------------------
-- Table structure for tx_draft_tags
-- ----------------------------
DROP TABLE IF EXISTS `tx_draft_tags`;
CREATE TABLE `tx_draft_tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `draft_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '草稿ID',
  `tag_id` int(11) NOT NULL DEFAULT '0' COMMENT '标签ID',
  `created_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间戳',
  `updated_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间戳',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='草稿文章标签';

-- ----------------------------
-- Records of tx_draft_tags
-- ----------------------------
BEGIN;
INSERT INTO `tx_draft_tags` VALUES (18, 15, 5, 0, 0);
INSERT INTO `tx_draft_tags` VALUES (19, 15, 6, 0, 0);
INSERT INTO `tx_draft_tags` VALUES (20, 15, 9, 0, 0);
INSERT INTO `tx_draft_tags` VALUES (21, 16, 1, 0, 0);
INSERT INTO `tx_draft_tags` VALUES (22, 16, 10, 0, 0);
INSERT INTO `tx_draft_tags` VALUES (23, 17, 1, 0, 0);
INSERT INTO `tx_draft_tags` VALUES (24, 17, 10, 0, 0);
INSERT INTO `tx_draft_tags` VALUES (25, 18, 1, 0, 0);
INSERT INTO `tx_draft_tags` VALUES (26, 18, 10, 0, 0);
INSERT INTO `tx_draft_tags` VALUES (27, 19, 1, 0, 0);
INSERT INTO `tx_draft_tags` VALUES (28, 19, 10, 0, 0);
INSERT INTO `tx_draft_tags` VALUES (29, 20, 1, 0, 0);
INSERT INTO `tx_draft_tags` VALUES (30, 20, 10, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for tx_drafts
-- ----------------------------
DROP TABLE IF EXISTS `tx_drafts`;
CREATE TABLE `tx_drafts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `post_id` int(11) NOT NULL DEFAULT '0' COMMENT '文章ID',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `brief` varchar(500) NOT NULL COMMENT '摘要',
  `content` text NOT NULL COMMENT '内容',
  `category_id` int(11) NOT NULL COMMENT '分类',
  `short_link` varchar(32) NOT NULL COMMENT '短连接',
  `pin` int(11) NOT NULL DEFAULT '0' COMMENT '置顶（1：置顶，0：不置顶）',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `created_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间戳',
  `updated_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间戳',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='文章草稿箱';

-- ----------------------------
-- Records of tx_drafts
-- ----------------------------
BEGIN;
INSERT INTO `tx_drafts` VALUES (14, 12, '请输入标题', '请输入简介', '', 1, '1244950029223936002', 0, 0, 1585654195032, 0);
INSERT INTO `tx_drafts` VALUES (15, 12, '导入导出测试', '这是导入导出测试', '## 项目概要\n\n> 定位服务旨在通过CB以及Beacon结合室内地图坐标数据提供定位功能，定位目标载体可以是手机、TAG或者其他主动扫描和被动广播蓝牙信号的设备。\n\n定位服务提供主动和被动两种模式的定位功能实现； 其中主动定位是指定位目标载体通过扫描周边Beacon设备上报至定位服务（Location Server)， 定位服务通过Beacon信号数据计算出定位结果； 被动定位是指场景内的CB或其他类似智能网关接收定位目标载体广播的iBeacon信号， 通过CB点位数据和信号数据计算出定位结果。\n\n## 服务架构\n\n> 定位服务将采用`Golang`实现，Go is an open source programming language that makes it easy to build simple, reliable, and efficient software.\n\n**定位实现**\n\n通过将广播的蓝牙信号强度`RSSI`转换为距离（米）再集合CB或Beacon的点位数据通过`三角定位`算法计算定位，有效精度最高可达`3 - 5` 米。\n\n![](http://q7vu0v5rm.bkt.clouddn.com/Flef01z78_ayZZZZ7RxrMeXeXunu)\n\n**三角定位算法示意图**\n\n> 设位置节点 D（x,y），已知 A、B、C 三点的坐标为（x1,y1），（x2,y2），（x3,y3）。它们到 D 的距离分别是 d1、d2、d3. 则 D 的位置可以通过下列方程中的任意两个进行求解。\n\n![](http://q7vu0v5rm.bkt.clouddn.com/FklN5_DYRPcTzwwEbaw3qOPsDf8C)\n\n## 程序实现\n\n\n## HTTP JSON_RPC 接口设计\n\n```javascript\n# 扫描开关\n\n{\n	\"version\": \"1.0\"\n	\"method\": \"mth_scan\",\n	\"params\": [\"CB_ID\", 1]\n}\n\n# 蓝牙设置\n{\n	\"version\": \"1.0\"\n	\"method\": \"mth_bluetooth\",\n	\"params\": [\"CB_ID\", {\n		\"uuid\": \"E2C56DB5DFFB48D2B060D0F5A71096E0\",\n		\"major\": 10187,\n		\"minor\": 10001,\n		\"rssi\": 1,\n		\"dbm\": -4\n		\"interval\": 852\n	}]\n}\n\n# 上报设置\n\n{\n	\"version\": \"1.0\"\n	\"method\": \"mth_report\",\n	\"params\": [\"CB_ID\",{\n		\"interval\": 10,\n		\"http\": \"http://t.cn/httpd\",\n		\"tcp\": \"127.0.0.1:55837\",\n		\"mqtt\": \"127.0.0.1:1883/MQTT2\"\n	}]\n}\n\n# 重启\n\n{\n	\"version\": \"1.0\"\n	\"method\": \"mth_reboot\",\n	\"params\": [\"CB_ID\"]\n}\n\n# 升级\n\n{\n	\"version\": \"1.0\"\n	\"method\": \"mth_upgrage\",\n	\"params\": [\"CB_ID\", \"firmware\",\"http://t.cn/1qZw24B8o\"]\n}\n```', 1, '1244950029223936002', 0, 0, 1585656193038, 0);
INSERT INTO `tx_drafts` VALUES (16, 11, 'SpinrgBoot + Kotlin 构建TxBlog程序', '这是使用该程序提供写作模块编写的文章，有一种很奇怪的感觉。 emmm.....', 'emmm...\n\n', 1, 'spring-boot-kotlin-txblog', 0, 0, 1585752802540, 0);
INSERT INTO `tx_drafts` VALUES (17, 11, 'SpinrgBoot + Kotlin 构建TxBlog程序', '这是使用该程序提供写作模块编写的文章，有一种很奇怪的感觉。 emmm.....', '一直以来都想写个Blog程序，这是自成为程序员开始就想做的事情。\n后来的工作中每天都面对各种CRUD编程、加班、怼产品，渐渐的也就没有什么激情了，这事也就算了。\n\n恰逢疫情在家办公，又做起了7x24小时的操蛋客服，就想起了最初的这个小愿望，于是乎就有现在看到的这个小东西。\n\n刚好今年家里迎来了一位新成员，是时候好好的补英语和数学了\n\n**项目框架**\n\n- `SpringBoot2`\n- `Mybatis`\n- `Mybatis-Plus`\n\n', 1, 'spring-boot-kotlin-txblog', 0, 0, 1585753377394, 0);
INSERT INTO `tx_drafts` VALUES (18, 11, 'SpinrgBoot + Kotlin 构建TxBlog程序', '这是使用该程序提供写作模块编写的文章，有一种很奇怪的感觉。 emmm.....', '一直以来都想写个Blog程序，这是自成为程序员开始就想做的事情。\n后来的工作中每天都面对各种CRUD编程、加班、怼产品，渐渐的也就没有什么激情了，这事也就算了。\n\n恰逢疫情在家办公，又做起了7x24小时的操蛋客服，就想起了最初的这个小愿望，于是乎就有现在看到的这个小东西。\n\n刚好今年家里迎来了一位新成员，是时候好好的补英语和数学了，少敲代码整那些有的没的，不然以后都无法辅导吞金兽作业了。\n\n## 是时候使用Kotlin了\n\n额，就随便一说\n\n在安卓中用得挺爽的\n\n## 关于项目\n\n没有什么复杂的东西，更多的是为了使用`Kotlin`。\n\n项目使用前后分离的模式\n\n前端包含在了`springboot`包中，使用内嵌容器，后台管理端使用`vue`,`iview ui`构建\n\n**支持的功能**\n\n- 导航管理\n- 分类管理\n- 标签管理\n- 评论管理\n- 文章管理\n\n写作模块是参考 [miketech.it](https://miketech.it) 同学的文章实现的，你能从这个项目看到很多相似的地方\n\n**项目框架**\n\n- `SpringBoot2`\n- `Mybatis`\n- `Mybatis-Plus`\n\n## 效果预览图\n\n', 1, 'spring-boot-kotlin-txblog', 0, 0, 1585754056015, 0);
INSERT INTO `tx_drafts` VALUES (19, 11, 'SpinrgBoot + Kotlin 构建TxBlog程序', '这是使用该程序提供写作模块编写的文章，有一种很奇怪的感觉。 emmm.....', '一直以来都想写个Blog程序，这是自成为程序员开始就想做的事情。\n后来的工作中每天都面对各种CRUD编程、加班、怼产品，渐渐的也就没有什么激情了，这事也就算了。\n\n恰逢疫情在家办公，又做起了7x24小时的操蛋客服，就想起了最初的这个小愿望，于是乎就有现在看到的这个小东西。\n\n刚好今年家里迎来了一位新成员，是时候好好的补英语和数学了，少敲代码整那些有的没的，不然以后都无法辅导吞金兽作业了。\n\n## 是时候使用Kotlin了\n\n额，就随便一说\n\n在安卓中用得挺爽的\n\n## 关于项目\n\n没有什么复杂的东西，更多的是为了使用`Kotlin`。\n\n项目使用前后分离的模式\n\n前端包含在了`springboot`包中，使用内嵌容器，后台管理端使用`vue`,`iview ui`构建\n\n**支持的功能**\n\n- 导航管理\n- 分类管理\n- 标签管理\n- 评论管理\n- 文章管理\n\n写作模块是参考 [miketech.it](https://miketech.it) 同学的文章实现的，你能从这个项目看到很多相似的地方\n\n**项目框架**\n\n- `SpringBoot2`\n- `Mybatis`\n- `Mybatis-Plus`\n\n## 效果预览图\n\n**写作模块**\n\n> 可导入/导出Markdown, 草稿恢复等\n\n![k01.png](http://q7vu0v5rm.bkt.clouddn.com/FpwKTbKjkhJNy2jCBlWQvNDAx-sK)', 1, 'spring-boot-kotlin-txblog', 0, 0, 1585754640780, 0);
INSERT INTO `tx_drafts` VALUES (20, 11, 'SpinrgBoot + Kotlin 构建TxBlog程序', '这是使用该程序提供写作模块编写的文章，有一种很奇怪的感觉。 emmm.....', '一直以来都想写个Blog程序，这是自成为程序员开始就想做的事情。\n后来的工作中每天都面对各种CRUD编程、加班、怼产品，渐渐的也就没有什么激情了，这事也就算了。\n\n恰逢疫情在家办公，又做起了7x24小时的操蛋客服，就想起了最初的这个小愿望，于是乎就有现在看到的这个小东西。\n\n刚好今年家里迎来了一位新成员，是时候好好的补英语和数学了，少敲代码整那些有的没的，不然以后都无法辅导吞金兽作业了。\n\n## 是时候使用Kotlin了\n\n额，就随便一说\n\n在安卓中用得挺爽的\n\n## 关于项目\n\n没有什么复杂的东西，更多的是为了使用`Kotlin`。\n\n项目使用前后分离的模式\n\n前端包含在了`springboot`包中，使用内嵌容器，后台管理端使用`vue`,`iview ui`构建\n\n**支持的功能**\n\n- 导航管理\n- 分类管理\n- 标签管理\n- 评论管理\n- 文章管理\n\n写作模块是参考 [miketech.it](https://miketech.it) 同学的文章实现的，你能从这个项目看到很多相似的地方\n\n**项目框架**\n\n- `SpringBoot2`\n- `Mybatis`\n- `Mybatis-Plus`\n\n## 效果预览图\n\n**写作模块**\n\n> 可导入/导出Markdown, 草稿恢复等\n\n![k01.png](http://q7vu0v5rm.bkt.clouddn.com/FpwKTbKjkhJNy2jCBlWQvNDAx-sK)\n\n*全屏写作*\n\n![k03.png](http://q7vu0v5rm.bkt.clouddn.com/FtilaQmoTpYpY6eTetDCR8JiKz3Y)\n\n**前端文章呈现**\n\n![k02.png](http://q7vu0v5rm.bkt.clouddn.com/Fhs_pDPPmAXHUeyAKFnZp1mkVAYa)\n', 1, 'spring-boot-kotlin-txblog', 0, 0, 1585755537065, 0);
INSERT INTO `tx_drafts` VALUES (21, 32, '请输入标题', '请输入简介', '', 1, '1245378350072066050', 0, 0, 1585756311576, 0);
COMMIT;

-- ----------------------------
-- Table structure for tx_nav
-- ----------------------------
DROP TABLE IF EXISTS `tx_nav`;
CREATE TABLE `tx_nav` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `link` varchar(255) NOT NULL COMMENT '链接',
  `priority` int(11) NOT NULL DEFAULT '0' COMMENT '优先级',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `created_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间戳',
  `updated_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间戳',
  `icon` varchar(32) NOT NULL DEFAULT '' COMMENT '图标',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `name_2` (`name`),
  UNIQUE KEY `name_3` (`name`),
  UNIQUE KEY `name_4` (`name`),
  UNIQUE KEY `name_5` (`name`),
  UNIQUE KEY `name_6` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='导航栏';

-- ----------------------------
-- Records of tx_nav
-- ----------------------------
BEGIN;
INSERT INTO `tx_nav` VALUES (1, '教程', '/category/1', 0, 0, 1585318300070, 1585547869466, 'fas fa-book');
INSERT INTO `tx_nav` VALUES (2, '关于', '/s/abount', 1, 0, 1585537526922, 1585547978408, 'fas fa-info');
INSERT INTO `tx_nav` VALUES (20, 'Github', 'https://github.com/archx', 2, 0, 15855375269220, 0, 'fab fa-github');
COMMIT;

-- ----------------------------
-- Table structure for tx_post
-- ----------------------------
DROP TABLE IF EXISTS `tx_post`;
CREATE TABLE `tx_post` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `brief` varchar(500) NOT NULL COMMENT '摘要',
  `content` text NOT NULL COMMENT '内容',
  `short_link` varchar(32) NOT NULL COMMENT '短连接',
  `pin` int(11) NOT NULL DEFAULT '0' COMMENT '置顶（1：置顶，0：不置顶）',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `created_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间戳',
  `updated_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间戳',
  `category_id` int(11) NOT NULL COMMENT '分类',
  PRIMARY KEY (`id`),
  UNIQUE KEY `short_link` (`short_link`),
  UNIQUE KEY `short_link_2` (`short_link`),
  UNIQUE KEY `short_link_3` (`short_link`),
  UNIQUE KEY `short_link_4` (`short_link`),
  UNIQUE KEY `short_link_5` (`short_link`),
  UNIQUE KEY `short_link_6` (`short_link`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='文章';

-- ----------------------------
-- Records of tx_post
-- ----------------------------
BEGIN;
INSERT INTO `tx_post` VALUES (11, 'SpinrgBoot + Kotlin 构建TxBlog程序', '这是使用该程序提供写作模块编写的文章，有一种很奇怪的感觉。 emmm.....', '一直以来都想写个Blog程序，这是自成为程序员开始就想做的事情。\n后来的工作中每天都面对各种CRUD编程、加班、怼产品，渐渐的也就没有什么激情了，这事也就算了。\n\n恰逢疫情在家办公，又做起了7x24小时的操蛋客服，就想起了最初的这个小愿望，于是乎就有现在看到的这个小东西。\n\n刚好今年家里迎来了一位新成员，是时候好好的补英语和数学了，少敲代码整那些有的没的，不然以后都无法辅导吞金兽作业了。\n\n## 是时候使用Kotlin了\n\n额，就随便一说\n\n在安卓中用得挺爽的\n\n## 关于项目\n\n没有什么复杂的东西，更多的是为了使用`Kotlin`。\n\n项目使用前后分离的模式\n\n前端包含在了`springboot`包中，使用内嵌容器，后台管理端使用`vue`,`iview ui`构建\n\n**支持的功能**\n\n- 导航管理\n- 分类管理\n- 标签管理\n- 评论管理\n- 文章管理\n\n写作模块是参考 [miketech.it](https://miketech.it) 同学的文章实现的，你能从这个项目看到很多相似的地方\n\n**项目框架**\n\n- `SpringBoot2`\n- `Mybatis`\n- `Mybatis-Plus`\n\n## 效果预览图\n\n**写作模块**\n\n> 可导入/导出Markdown, 草稿恢复等\n\n![k01.png](http://q7vu0v5rm.bkt.clouddn.com/FpwKTbKjkhJNy2jCBlWQvNDAx-sK)', 'spring-boot-kotlin-txblog', 0, 1, 1585642507844, 1585754641279, 1);
INSERT INTO `tx_post` VALUES (12, '导入导出测试', '这是导入导出测试', '## 项目概要\n\n> 定位服务旨在通过CB以及Beacon结合室内地图坐标数据提供定位功能，定位目标载体可以是手机、TAG或者其他主动扫描和被动广播蓝牙信号的设备。\n\n定位服务提供主动和被动两种模式的定位功能实现； 其中主动定位是指定位目标载体通过扫描周边Beacon设备上报至定位服务（Location Server)， 定位服务通过Beacon信号数据计算出定位结果； 被动定位是指场景内的CB或其他类似智能网关接收定位目标载体广播的iBeacon信号， 通过CB点位数据和信号数据计算出定位结果。\n\n## 服务架构\n\n> 定位服务将采用`Golang`实现，Go is an open source programming language that makes it easy to build simple, reliable, and efficient software.\n\n**定位实现**\n\n通过将广播的蓝牙信号强度`RSSI`转换为距离（米）再集合CB或Beacon的点位数据通过`三角定位`算法计算定位，有效精度最高可达`3 - 5` 米。\n\n![](http://q7vu0v5rm.bkt.clouddn.com/Flef01z78_ayZZZZ7RxrMeXeXunu)\n\n**三角定位算法示意图**\n\n> 设位置节点 D（x,y），已知 A、B、C 三点的坐标为（x1,y1），（x2,y2），（x3,y3）。它们到 D 的距离分别是 d1、d2、d3. 则 D 的位置可以通过下列方程中的任意两个进行求解。\n\n![](http://q7vu0v5rm.bkt.clouddn.com/FklN5_DYRPcTzwwEbaw3qOPsDf8C)\n\n## 程序实现\n\n\n## HTTP JSON_RPC 接口设计\n\n```javascript\n# 扫描开关\n\n{\n	\"version\": \"1.0\"\n	\"method\": \"mth_scan\",\n	\"params\": [\"CB_ID\", 1]\n}\n\n# 蓝牙设置\n{\n	\"version\": \"1.0\"\n	\"method\": \"mth_bluetooth\",\n	\"params\": [\"CB_ID\", {\n		\"uuid\": \"E2C56DB5DFFB48D2B060D0F5A71096E0\",\n		\"major\": 10187,\n		\"minor\": 10001,\n		\"rssi\": 1,\n		\"dbm\": -4\n		\"interval\": 852\n	}]\n}\n\n# 上报设置\n\n{\n	\"version\": \"1.0\"\n	\"method\": \"mth_report\",\n	\"params\": [\"CB_ID\",{\n		\"interval\": 10,\n		\"http\": \"http://t.cn/httpd\",\n		\"tcp\": \"127.0.0.1:55837\",\n		\"mqtt\": \"127.0.0.1:1883/MQTT2\"\n	}]\n}\n\n# 重启\n\n{\n	\"version\": \"1.0\"\n	\"method\": \"mth_reboot\",\n	\"params\": [\"CB_ID\"]\n}\n\n# 升级\n\n{\n	\"version\": \"1.0\"\n	\"method\": \"mth_upgrage\",\n	\"params\": [\"CB_ID\", \"firmware\",\"http://t.cn/1qZw24B8o\"]\n}\n```', '1244950029223936002', 0, 1, 1585654191680, 1585656193650, 1);
INSERT INTO `tx_post` VALUES (32, '请输入标题', '请输入简介', '', '1245378350072066050', 0, 0, 1585756311327, 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for tx_post_tags
-- ----------------------------
DROP TABLE IF EXISTS `tx_post_tags`;
CREATE TABLE `tx_post_tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `post_id` int(11) NOT NULL DEFAULT '0' COMMENT '文章ID',
  `tag_id` int(11) NOT NULL DEFAULT '0' COMMENT '标签ID',
  `created_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间戳',
  `updated_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间戳',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='文章标签';

-- ----------------------------
-- Records of tx_post_tags
-- ----------------------------
BEGIN;
INSERT INTO `tx_post_tags` VALUES (5, 12, 5, 0, 0);
INSERT INTO `tx_post_tags` VALUES (6, 12, 6, 0, 0);
INSERT INTO `tx_post_tags` VALUES (7, 12, 9, 0, 0);
INSERT INTO `tx_post_tags` VALUES (8, 11, 1, 0, 0);
INSERT INTO `tx_post_tags` VALUES (9, 11, 10, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for tx_tag
-- ----------------------------
DROP TABLE IF EXISTS `tx_tag`;
CREATE TABLE `tx_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `created_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间戳',
  `updated_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `name_2` (`name`),
  UNIQUE KEY `name_3` (`name`),
  UNIQUE KEY `name_4` (`name`),
  UNIQUE KEY `name_5` (`name`),
  UNIQUE KEY `name_6` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='标签';

-- ----------------------------
-- Records of tx_tag
-- ----------------------------
BEGIN;
INSERT INTO `tx_tag` VALUES (1, 'Kotlin', 1, 0, 1585309713859, 0);
INSERT INTO `tx_tag` VALUES (3, 'Java', 1, 0, 1585309970165, 0);
INSERT INTO `tx_tag` VALUES (5, 'Golang', 1, 0, 1585539616536, 0);
INSERT INTO `tx_tag` VALUES (6, 'IoT', 1, 0, 1585539621487, 0);
INSERT INTO `tx_tag` VALUES (7, 'Scala', 1, 0, 1585587731787, 0);
INSERT INTO `tx_tag` VALUES (8, 'Linux', 1, 0, 1585590278755, 0);
INSERT INTO `tx_tag` VALUES (9, 'mqtt', 1, 0, 1585590352252, 0);
INSERT INTO `tx_tag` VALUES (10, 'SpringBoot', 1, 0, 1585752651326, 0);
COMMIT;

-- ----------------------------
-- Table structure for tx_user
-- ----------------------------
DROP TABLE IF EXISTS `tx_user`;
CREATE TABLE `tx_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '用户密码',
  `salt` varchar(64) NOT NULL COMMENT '盐',
  `email` varchar(120) NOT NULL COMMENT '邮箱',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `created_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间戳',
  `updated_at` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新时间戳',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表\n';

-- ----------------------------
-- Records of tx_user
-- ----------------------------
BEGIN;
INSERT INTO `tx_user` VALUES (1, 'admin', '221b8906a089b56e5ec64d698b96234a', 'Pb6QPFvQ2ukr3YiVCpWo7w==', 'admin@example.com', 0, 1585305349878, 1585310788054);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
