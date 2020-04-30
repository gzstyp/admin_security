/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.66
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : 192.168.1.66:3306
 Source Schema         : admin_security

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 30/04/2020 11:44:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `KID` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ROLE_FLAG` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ROLE_NAME` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`KID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('ffffffff95beb47dffffffffad7e6abe', 'ROLE_ADMIN', '管理员');
INSERT INTO `sys_role` VALUES ('ffffffffab9fc98dffffffff8b642b39', 'ROLE_USER', '普通用户');
INSERT INTO `sys_role` VALUES ('ffffffffbd471a55ffffffff976c6d1b', 'ROLE_VIP', 'VIP用户');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `KID` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `USERNAME` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `PASSWORD` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`KID`) USING BTREE,
  UNIQUE INDEX `index_username_unique`(`USERNAME`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('0000000035fbca03000000003a33d6b7', 'admin', 'cfd861abb53a23830725d2167a305ea340d1cbc6');
INSERT INTO `sys_user` VALUES ('000000003a56d4fb0000000037016444', 'typ', 'cfd861abb53a23830725d2167a305ea340d1cbc6');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `USER_ID` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ROLE_ID` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE INDEX `INDEX_USER_ROLE`(`USER_ID`, `ROLE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('0000000035fbca03000000003a33d6b7', 'ffffffff95beb47dffffffffad7e6abe');
INSERT INTO `sys_user_role` VALUES ('0000000035fbca03000000003a33d6b7', 'ffffffffab9fc98dffffffff8b642b39');
INSERT INTO `sys_user_role` VALUES ('0000000035fbca03000000003a33d6b7', 'ffffffffbd471a55ffffffff976c6d1b');

SET FOREIGN_KEY_CHECKS = 1;
