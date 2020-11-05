/*
Navicat MySQL Data Transfer

Source Server         : xzj
Source Server Version : 80022
Source Host           : 47.112.101.204:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2020-11-05 19:34:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `age` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '张三', '21');
INSERT INTO `t_user` VALUES ('2', '李四', '22');
INSERT INTO `t_user` VALUES ('3', '王五', '23');
INSERT INTO `t_user` VALUES ('4', 'Tom', '24');
INSERT INTO `t_user` VALUES ('5', 'Jerry', '25');
INSERT INTO `t_user` VALUES ('6', 'Lucy', '26');
INSERT INTO `t_user` VALUES ('7', 'Bob', '22');
INSERT INTO `t_user` VALUES ('8', '张四', '23');
INSERT INTO `t_user` VALUES ('9', '赵大', '24');
