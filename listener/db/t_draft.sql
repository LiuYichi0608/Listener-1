/*
Navicat MySQL Data Transfer

Source Server         : xzj
Source Server Version : 80022
Source Host           : 47.112.101.204:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2020-11-05 19:34:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_draft
-- ----------------------------
DROP TABLE IF EXISTS `t_draft`;
CREATE TABLE `t_draft` (
  `id` int NOT NULL AUTO_INCREMENT,
  `letter_id` int DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '草稿信中的内容',
  `time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `l_id` (`letter_id`),
  CONSTRAINT `l_id` FOREIGN KEY (`letter_id`) REFERENCES `t_letter` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_draft
-- ----------------------------
