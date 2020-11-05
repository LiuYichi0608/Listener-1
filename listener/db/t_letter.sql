/*
Navicat MySQL Data Transfer

Source Server         : xzj
Source Server Version : 80022
Source Host           : 47.112.101.204:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2020-11-05 19:34:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_letter
-- ----------------------------
DROP TABLE IF EXISTS `t_letter`;
CREATE TABLE `t_letter` (
  `id` int NOT NULL AUTO_INCREMENT,
  `send_id` int DEFAULT NULL COMMENT '发信人的id',
  `receive_id` int DEFAULT NULL COMMENT '收信人id',
  `time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '收发信时间',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '信件内容',
  PRIMARY KEY (`id`),
  KEY `u_id` (`send_id`),
  CONSTRAINT `u_id` FOREIGN KEY (`send_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_letter
-- ----------------------------
