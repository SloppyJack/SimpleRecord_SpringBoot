-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: jianzhang
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `jianzhang`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `jianzhang` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `jianzhang`;

--
-- Table structure for table `tb_common_log`
--

DROP TABLE IF EXISTS `tb_common_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_common_log` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标题',
  `business_type_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '业务类型编码',
  `business_type_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '业务类型名称',
  `method` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '调用方法',
  `request_method` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求方法',
  `request_url` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `request_param` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求参数',
  `oper_id` int DEFAULT NULL COMMENT '操作人Id',
  `oper_ip` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作人IP',
  `oper_address` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作人地址',
  `json_result` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '返回结果',
  `status` int DEFAULT NULL COMMENT '状态(0成功，-1失败)',
  `error_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '失败原因',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_common_log`
--

LOCK TABLES `tb_common_log` WRITE;
/*!40000 ALTER TABLE `tb_common_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_common_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_menu`
--

DROP TABLE IF EXISTS `tb_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_menu` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `menu_title` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单标题',
  `menu_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名',
  `parent_id` int unsigned DEFAULT NULL COMMENT '父Id',
  `order_no` int DEFAULT NULL COMMENT '显示顺序',
  `path` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由地址',
  `component` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组件地址',
  `outer_chain` int DEFAULT '0' COMMENT '是否为外链（0否 1是）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `permission_sign` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
  `icon_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `create_time` datetime(3) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  `delete_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `menu_name` (`menu_name`,`path`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_menu`
--

LOCK TABLES `tb_menu` WRITE;
/*!40000 ALTER TABLE `tb_menu` DISABLE KEYS */;
INSERT INTO `tb_menu` VALUES (1,'系统管理','sysManage',NULL,4,'sys','',0,'M',NULL,'setting',NULL,'2021-03-10 20:45:51.644',NULL),(2,'用户管理','userManage',1,1,'userManage','UserManage',0,'C','','user',NULL,'2021-01-06 20:18:15.567',NULL),(3,'角色管理','roleManage',1,2,'roleManage','RoleManage',0,'C','','team',NULL,'2021-02-01 20:24:20.207',NULL),(4,'菜单管理','menuManage',1,3,'menuManage','MenuManage',0,'C','','menu',NULL,'2021-02-01 20:20:26.813',NULL),(7,'树洞','robot',NULL,2,'robot','robot/Robot',0,'C',NULL,'robot',NULL,'2021-03-02 22:36:44.635',NULL),(8,'首页','home',NULL,1,'home','home/Index',0,'C',NULL,'home',NULL,'2021-02-01 20:26:10.594',NULL),(15,'用户列表','SystemUserView',2,1,NULL,NULL,0,'F','system:user:view',NULL,'2021-03-01 21:04:40.532',NULL,NULL),(16,'用户详细信息','SystemUserDetail',2,2,NULL,NULL,0,'F','system:user:detail',NULL,'2021-03-01 21:05:19.552','2021-03-01 22:13:05.653',NULL),(17,'编辑用户','SystemUserEdit',2,3,NULL,NULL,0,'F','system:user:edit',NULL,'2021-03-01 22:12:53.272',NULL,NULL),(18,'添加用户','SystemUserAdd',2,4,NULL,NULL,0,'F','system:user:add',NULL,'2021-03-01 22:13:36.711',NULL,NULL),(19,'删除用户','SystemUserDel',2,5,NULL,NULL,0,'F','system:user:del',NULL,'2021-03-01 22:14:06.717',NULL,NULL),(20,'恢复用户','SystemUserReset',2,6,NULL,NULL,0,'F','system:user:reset',NULL,'2021-03-01 22:14:39.140',NULL,NULL),(22,'角色列表','SystemRoleView',3,2,NULL,NULL,0,'F','system:role:view',NULL,'2021-03-01 22:20:09.689',NULL,NULL),(23,'菜单添加','SystemMenuAdd',3,3,NULL,NULL,0,'F','system:role:add',NULL,'2021-03-01 22:20:38.093',NULL,NULL),(24,'角色拥有权限','SystemRoleOwnedMenus',3,4,NULL,NULL,0,'F','system:role:ownedMenus',NULL,'2021-03-01 22:21:24.167','2021-03-01 22:21:33.952',NULL),(25,'角色编辑','SystemRoleEdit',3,5,NULL,NULL,0,'F','system:role:edit',NULL,'2021-03-01 22:22:04.659',NULL,NULL),(26,'获取指定权限','SystemMenuDetail',4,1,NULL,NULL,0,'F','system:menu:detail',NULL,'2021-03-02 20:23:29.318',NULL,NULL),(27,'树形权限列表','SystemMenuTreeView',4,2,NULL,NULL,0,'F','system:menu:treeView',NULL,'2021-03-02 20:26:22.802',NULL,NULL),(28,'所有的权限','SystemMenuAll',4,3,NULL,NULL,0,'F','system:menu:all',NULL,'2021-03-02 20:26:58.346',NULL,NULL),(29,'菜单添加','SystemMenuAdd',4,4,NULL,NULL,0,'F','system:menu:add',NULL,'2021-03-02 20:27:32.554',NULL,NULL),(30,'删除菜单','SystemMenuDel',4,5,NULL,NULL,0,'F','system:menu:del',NULL,'2021-03-02 20:28:00.317',NULL,NULL),(31,'还原菜单','SystemMenuReset',4,6,NULL,NULL,0,'F','SystemMenuReset',NULL,'2021-03-02 20:28:36.039',NULL,NULL),(32,'菜单编辑','SystemMenuEdit',4,7,NULL,NULL,0,'F','system:menu:edit',NULL,'2021-03-02 20:29:01.910',NULL,NULL),(33,'删除角色','SystemRoleDel',3,6,NULL,NULL,0,'F','system:role:del',NULL,'2021-03-02 21:18:25.176','2021-03-02 22:09:37.075',NULL),(34,'还原角色','SystemRoleReset',3,7,NULL,NULL,0,'F','system:role:reset',NULL,'2021-03-02 21:19:04.986',NULL,NULL),(35,'百度','baidu',NULL,5,'https://www.baidu.com','null',1,'C',NULL,NULL,'2021-03-02 22:37:51.724','2021-03-10 20:45:58.887',NULL),(36,'账户','acountManage',NULL,6,'account',NULL,0,'M',NULL,'user','2021-03-03 23:11:10.459','2021-03-10 20:46:03.217',NULL),(37,'个人中心','personalCenter',36,1,'center','center',0,'C',NULL,'','2021-03-03 23:14:21.173','2021-03-04 23:21:56.164',NULL),(38,'个人设置','settings',36,2,'settings','settings/Index',0,'C',NULL,NULL,'2021-03-04 22:13:00.379','2021-03-04 23:03:23.997',NULL),(39,'基本设置','baseSettings',38,1,'base','BaseSetting',0,'C',NULL,NULL,'2021-03-04 23:12:30.990','2021-03-04 23:22:05.904',NULL),(40,'安全设置','securitySettings',38,2,'security','Security',0,'C',NULL,NULL,'2021-03-04 23:12:59.238','2021-03-04 23:22:11.871',NULL),(41,'个性化设置','customSettings',38,3,'custom','Custom',0,'C',NULL,NULL,'2021-03-04 23:13:16.202','2021-03-04 23:22:39.400',NULL),(42,'账户绑定','bindingSettings',38,4,'binding','Binding',0,'C',NULL,NULL,'2021-03-04 23:13:48.257','2021-03-04 23:23:58.684',NULL),(43,'新消息通知','notificationSettings',38,5,'notification','Notification',0,'C',NULL,NULL,'2021-03-04 23:14:22.276','2021-03-04 23:23:44.761',NULL),(44,'记一笔','recordAdd',46,1,'create','Create',0,'C',NULL,'edit','2021-03-10 20:45:46.938','2021-03-11 20:19:18.466',NULL),(46,'账单','record',NULL,3,'record','Create',0,'M',NULL,'form','2021-03-11 20:15:50.641','2021-03-15 22:44:33.898',NULL),(47,'月账单','monthRecordList',46,2,'monthList','MonthList',0,'C',NULL,'unordered-list','2021-03-11 20:19:08.385','2021-03-11 20:31:01.806',NULL),(48,'图标分析','charAnalysis',46,3,'analysis','Analysis',0,'C',NULL,'dot-chart','2021-03-23 21:29:01.332',NULL,NULL),(49,'获取某年所有消费类别的总额','recordAnalysisSpendCategoryTotal',48,1,NULL,NULL,0,'F','record:analysis:spendCategoryTotal',NULL,'2021-03-23 22:27:13.083','2021-03-23 22:27:44.541',NULL),(50,'获取最近六个月的支出和收入','recordAnalysisLatestSixMonthList',48,2,NULL,NULL,0,'F','record:analysis:latestSixMonthList',NULL,'2021-03-23 22:28:09.471',NULL,NULL);
/*!40000 ALTER TABLE `tb_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_record_detail`
--

DROP TABLE IF EXISTS `tb_record_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_record_detail` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int unsigned NOT NULL COMMENT '记账人Id',
  `spend_category_id` int unsigned NOT NULL COMMENT '花费类别,类别表的主键',
  `amount` decimal(12,4) DEFAULT NULL,
  `remarks` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `occur_time` datetime DEFAULT NULL COMMENT '发生时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `spend_category` (`spend_category_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  CONSTRAINT `tb_record_detail_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `tb_record_detail_ibfk_2` FOREIGN KEY (`spend_category_id`) REFERENCES `tb_spend_category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_record_detail`
--

LOCK TABLES `tb_record_detail` WRITE;
/*!40000 ALTER TABLE `tb_record_detail` DISABLE KEYS */;
INSERT INTO `tb_record_detail` VALUES (25,1,2,123.0000,'123\nasdf\n测试添加','2021-03-03 00:00:00','2021-03-10 23:17:47','2021-03-11 23:47:46',NULL),(26,1,2,11.0000,'asdf','2021-03-10 00:00:00','2021-03-10 23:18:45','2021-03-12 00:05:39',NULL),(27,1,3,11.0000,'test','2021-03-11 00:00:00','2021-03-11 20:03:55','2021-03-11 23:51:52',NULL),(28,1,3,11.0000,'test1','2021-03-09 00:00:00','2021-03-11 20:04:07','2021-03-11 23:48:49',NULL),(29,1,1,11.0000,'test','2021-03-11 00:00:00','2021-03-11 20:04:29',NULL,'2021-03-11 21:41:05'),(30,1,5,10.0000,'上网','2021-03-17 00:00:00','2021-03-24 21:13:05',NULL,NULL),(31,1,3,20.0000,'打车','2021-04-12 00:00:00','2021-04-12 21:02:57',NULL,NULL);
/*!40000 ALTER TABLE `tb_record_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_record_type`
--

DROP TABLE IF EXISTS `tb_record_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_record_type` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '名字',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `order_no` int DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_record_type`
--

LOCK TABLES `tb_record_type` WRITE;
/*!40000 ALTER TABLE `tb_record_type` DISABLE KEYS */;
INSERT INTO `tb_record_type` VALUES (1,'incomeType','收入',NULL,1,'2020-06-08 22:37:12',NULL,NULL),(2,'expendType','支出',NULL,2,'2020-06-08 22:38:02',NULL,NULL);
/*!40000 ALTER TABLE `tb_record_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role`
--

DROP TABLE IF EXISTS `tb_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_role` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分组名称，例如：搬砖者',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组信息：例如：搬砖的人',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name_del` (`name`,`delete_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role`
--

LOCK TABLES `tb_role` WRITE;
/*!40000 ALTER TABLE `tb_role` DISABLE KEYS */;
INSERT INTO `tb_role` VALUES (1,'root','超级用户','2020-05-30 09:52:00.240','2021-03-25 22:20:45.361',NULL),(2,'manage','管理人员','2020-05-30 09:52:00.241','2021-03-02 22:00:38.368',NULL),(3,'developer','开发人员','2020-08-19 20:50:57.706','2021-03-25 22:23:06.324',NULL),(4,'test','测试人员(for test)','2020-08-19 20:51:04.510','2021-03-02 22:06:28.184',NULL),(6,'guest','游客人员','2020-12-10 20:18:09.569','2021-03-02 22:09:52.141',NULL),(7,'default','默认角色','2021-03-02 22:21:53.609','2021-03-25 22:12:29.796',NULL);
/*!40000 ALTER TABLE `tb_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role_menu`
--

DROP TABLE IF EXISTS `tb_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_role_menu` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `role_id` int unsigned NOT NULL COMMENT '角色id',
  `menu_id` int unsigned NOT NULL COMMENT '菜单id',
  `create_time` datetime(3) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  `delete_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `permission_id` (`menu_id`) USING BTREE,
  KEY `group_id_permission_id` (`role_id`,`menu_id`) USING BTREE COMMENT '联合索引',
  CONSTRAINT `tb_role_menu_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`id`),
  CONSTRAINT `tb_role_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `tb_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role_menu`
--

LOCK TABLES `tb_role_menu` WRITE;
/*!40000 ALTER TABLE `tb_role_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_spend_category`
--

DROP TABLE IF EXISTS `tb_spend_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_spend_category` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '花费类别编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '花费类别名称',
  `record_type_id` int unsigned NOT NULL COMMENT '外键：记录类型',
  `order_no` int DEFAULT NULL COMMENT '花费类别排序',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_code` (`code`) USING BTREE,
  KEY `record_type` (`record_type_id`) USING BTREE,
  CONSTRAINT `tb_spend_category_ibfk_1` FOREIGN KEY (`record_type_id`) REFERENCES `tb_record_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_spend_category`
--

LOCK TABLES `tb_spend_category` WRITE;
/*!40000 ALTER TABLE `tb_spend_category` DISABLE KEYS */;
INSERT INTO `tb_spend_category` VALUES (1,'CY','餐饮',2,NULL,NULL,'2020-06-08 22:42:55',NULL,NULL),(2,'ZS','住宿',2,NULL,NULL,'2020-06-08 22:43:23',NULL,NULL),(3,'CXJT','出行交通',2,NULL,NULL,'2020-09-23 23:20:44',NULL,NULL),(4,'GW','购物',2,NULL,NULL,'2020-09-23 23:21:07',NULL,NULL),(5,'XXYL','休闲娱乐',2,NULL,NULL,'2020-09-23 23:21:22',NULL,NULL),(6,'GZ','工资',1,NULL,NULL,'2020-10-12 19:41:22',NULL,NULL),(7,'JZ','兼职',1,NULL,NULL,'2020-10-12 19:41:52',NULL,NULL),(8,'LC','理财',1,NULL,NULL,'2020-10-12 19:42:08',NULL,NULL),(9,'QT','其它',1,NULL,NULL,'2020-10-12 19:42:32',NULL,NULL);
/*!40000 ALTER TABLE `tb_spend_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名，唯一',
  `credential` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
  `nickname` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户昵称',
  `sex` int NOT NULL COMMENT '性别',
  `avatar_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像url',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE COMMENT '用户名唯一'
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` VALUES (1,'root','$2a$10$uUDbJqzpGGHNIAx0pGpiS.6JROmf8xwlBZcx3019IfhSWRidL21pO','root',1,'','root@qq.com','2020-05-30 09:52:00.235','2021-05-08 08:51:21.202',NULL),(3,'test','$2a$10$lHlz4Q5N5Cj1HzTeGP.zbe5k63S6B0nxx66e8g75sYHksRPaTiDf2','测试用户',1,NULL,'itsbintnt@qq.com','2020-09-09 20:14:56.721','2020-09-09 20:15:06.306',NULL);
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_role`
--

DROP TABLE IF EXISTS `tb_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user_role` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int unsigned NOT NULL COMMENT '用户id',
  `role_id` int unsigned NOT NULL COMMENT '分组id',
  `create_time` datetime(3) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  `delete_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id_group_id` (`user_id`,`role_id`) USING BTREE COMMENT '联合索引',
  KEY `group_id` (`role_id`) USING BTREE,
  CONSTRAINT `tb_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `tb_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_role`
--

LOCK TABLES `tb_user_role` WRITE;
/*!40000 ALTER TABLE `tb_user_role` DISABLE KEYS */;
INSERT INTO `tb_user_role` VALUES (7,1,1,'2021-02-27 15:21:17.358',NULL,NULL);
/*!40000 ALTER TABLE `tb_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_wechat_user`
--

DROP TABLE IF EXISTS `tb_wechat_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_wechat_user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `open_id` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_id` int unsigned NOT NULL,
  `create_time` datetime(3) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  `delete_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tb_wechat_user_ibfk_1` (`user_id`),
  CONSTRAINT `tb_wechat_user_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_wechat_user`
--

LOCK TABLES `tb_wechat_user` WRITE;
/*!40000 ALTER TABLE `tb_wechat_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_wechat_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-08  9:02:37
