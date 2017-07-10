-- MySQL dump 10.13  Distrib 5.7.15, for Win64 (x86_64)
--
-- Host: 192.168.0.156    Database: spider
-- ------------------------------------------------------
-- Server version	5.6.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `spider`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `spider` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `spider`;

--
-- Table structure for table `process`
--

DROP TABLE IF EXISTS `process`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `process` (
  `id` bigint(32) NOT NULL COMMENT '流程编号',
  `site_id` bigint(32) DEFAULT NULL COMMENT '来源编号',
  `begin_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `execute_time` bigint(32) DEFAULT NULL COMMENT '执行时间(统计字段)',
  `status` int(4) DEFAULT '1' COMMENT '状态码表(1：待执行，2：执行中，3：执行成功，4：执行失败，5：执行超时)',
  `message` text COMMENT '记录信息',
  PRIMARY KEY (`id`),
  KEY `site_id` (`site_id`) USING BTREE,
  KEY `end_time` (`end_time`),
  KEY `status` (`status`),
  KEY `begin_time` (`begin_time`),
  KEY `begin_time_status` (`begin_time`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='执行流程';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `site`
--

DROP TABLE IF EXISTS `site`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site` (
  `id` bigint(32) NOT NULL COMMENT '来源编号',
  `title` varchar(128) DEFAULT NULL COMMENT '来源名称',
  `context` varchar(128) NOT NULL COMMENT '来源地址(起始页面)',
  `file_content` varchar(2048) DEFAULT NULL COMMENT '文件内容',
  `file_path` varchar(128) DEFAULT NULL COMMENT '文件路径',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标志',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置站点';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `id` bigint(32) NOT NULL COMMENT '任务编号',
  `process_id` bigint(32) DEFAULT NULL COMMENT '流程编号',
  `site_id` bigint(32) DEFAULT NULL COMMENT '来源编号',
  `url` varchar(512) DEFAULT NULL COMMENT '抓取地址',
  `url_md5` varchar(32) DEFAULT NULL COMMENT '抓取地址md5值，考虑url可能过长，此字段索引用来查询',
  `title` varchar(1024) DEFAULT NULL COMMENT '文章标题',
  `title_md5` varchar(32) DEFAULT NULL COMMENT '标题md5值，考虑url可能过长，此字段索引用来查询',
  `begin_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `execute_time` bigint(32) DEFAULT NULL COMMENT '执行时间',
  `snapshot_path` varchar(256) DEFAULT NULL COMMENT '快照路径',
  `status` int(4) DEFAULT '5' COMMENT '状态码表(1：待执行，2：执行中，3：执行成功，4：执行失败，5：执行超时)',
  `message` text COMMENT '记录信息',
  PRIMARY KEY (`id`),
  KEY `process_id` (`process_id`),
  KEY `urlmd5` (`url_md5`) USING BTREE,
  KEY `titlemd5` (`title_md5`) USING BTREE,
  KEY `site_id` (`site_id`),
  KEY `end_time` (`end_time`),
  KEY `status` (`status`),
  KEY `begin_time_status` (`begin_time`,`status`) USING BTREE,
  KEY `begin_time` (`begin_time`),
  CONSTRAINT `task_ibfk_2` FOREIGN KEY (`process_id`) REFERENCES `process` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='执行任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `task_push`
--

DROP TABLE IF EXISTS `task_push`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_push` (
  `id` bigint(32) NOT NULL COMMENT '推送编号',
  `task_id` bigint(32) DEFAULT NULL COMMENT '任务编号',
  `site_id` bigint(32) DEFAULT NULL COMMENT '来源编号',
  `begin_time` datetime DEFAULT NULL COMMENT '最后推送时间',
  `end_time` datetime DEFAULT NULL,
  `execute_time` bigint(32) DEFAULT NULL,
  `status` int(4) DEFAULT '1' COMMENT '状态码表(1：待执行，2：执行中，3：执行成功，4：执行失败)',
  `message` text COMMENT '记录信息',
  PRIMARY KEY (`id`),
  KEY `task_id` (`task_id`),
  KEY `site_id` (`site_id`),
  KEY `end_time` (`end_time`),
  KEY `status` (`status`),
  KEY `begin_time` (`begin_time`),
  KEY `begin_time_status` (`begin_time`,`status`),
  CONSTRAINT `task_push_ibfk_1` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务推送';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-17 13:07:43
