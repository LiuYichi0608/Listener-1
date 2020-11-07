/*
Navicat MySQL Data Transfer

Source Server         : xzj
Source Server Version : 80022
Source Host           : 47.112.101.204:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2020-11-05 19:34:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_envelope
-- ----------------------------
DROP TABLE IF EXISTS `t_envelope`;
CREATE TABLE `t_envelope` (
  `id` int NOT NULL AUTO_INCREMENT,
  `url_envelope` varchar(255) DEFAULT NULL,
  `url_paper` varchar(255) DEFAULT NULL,
  `letter_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `letter_id` (`letter_id`),
  CONSTRAINT `letter_id` FOREIGN KEY (`letter_id`) REFERENCES `t_letter` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_envelope
-- ----------------------------
