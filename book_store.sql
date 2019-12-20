/*
 Navicat Premium Data Transfer

 Source Server         : MySQL80
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : book_store

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 20/12/2019 08:52:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority`  (
  `authority_id` int(11) NOT NULL AUTO_INCREMENT,
  `authority_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`authority_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES (1, 'get*/billinformations');
INSERT INTO `authority` VALUES (2, 'get*/allbillinformations');
INSERT INTO `authority` VALUES (4, 'get*/monthlyincome/{number}');
INSERT INTO `authority` VALUES (5, 'get*/topsaillingbook');
INSERT INTO `authority` VALUES (6, 'post*/books');
INSERT INTO `authority` VALUES (7, 'put*/books');
INSERT INTO `authority` VALUES (8, 'delete*/books/{bookId}');
INSERT INTO `authority` VALUES (9, 'patch*/books/{bookId}/{number}');
INSERT INTO `authority` VALUES (10, 'get*/books/recommendedBooks');
INSERT INTO `authority` VALUES (11, 'patch*/transaction/{id}');
INSERT INTO `authority` VALUES (12, 'post*/transaction');

-- ----------------------------
-- Table structure for bill
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill`  (
  `bill_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `bill_price` int(11) NULL DEFAULT NULL,
  `bill_time` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`bill_id`) USING BTREE,
  INDEX `34`(`user_id`) USING BTREE,
  CONSTRAINT `34` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bill
-- ----------------------------
INSERT INTO `bill` VALUES (21, 1, 265, 1575431174);
INSERT INTO `bill` VALUES (22, 1, 549, 1575782586);
INSERT INTO `bill` VALUES (23, 1, 284, 1575858455);
INSERT INTO `bill` VALUES (24, 1, 284, 1575863748);
INSERT INTO `bill` VALUES (25, 1, 173, 1575863999);
INSERT INTO `bill` VALUES (26, 1, 255, 1575864040);
INSERT INTO `bill` VALUES (27, 1, 162, 1575878647);
INSERT INTO `bill` VALUES (28, 1, 284, 1575882594);
INSERT INTO `bill` VALUES (29, 1, 192, 1575885139);

-- ----------------------------
-- Table structure for bill_book_relation
-- ----------------------------
DROP TABLE IF EXISTS `bill_book_relation`;
CREATE TABLE `bill_book_relation`  (
  `bill_book_id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_id` int(11) NULL DEFAULT NULL,
  `book_id` int(11) NULL DEFAULT NULL,
  `flag` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`bill_book_id`) USING BTREE,
  INDEX `3`(`bill_id`) USING BTREE,
  INDEX `4`(`book_id`) USING BTREE,
  CONSTRAINT `3` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`bill_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `4` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 92 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bill_book_relation
-- ----------------------------
INSERT INTO `bill_book_relation` VALUES (63, 21, 1, b'0');
INSERT INTO `bill_book_relation` VALUES (64, 21, 2, b'0');
INSERT INTO `bill_book_relation` VALUES (65, 21, 3, b'0');
INSERT INTO `bill_book_relation` VALUES (66, 22, 1, b'0');
INSERT INTO `bill_book_relation` VALUES (67, 22, 1, b'0');
INSERT INTO `bill_book_relation` VALUES (68, 22, 1, b'0');
INSERT INTO `bill_book_relation` VALUES (69, 22, 2, b'0');
INSERT INTO `bill_book_relation` VALUES (70, 22, 2, b'0');
INSERT INTO `bill_book_relation` VALUES (71, 22, 3, b'0');
INSERT INTO `bill_book_relation` VALUES (72, 23, 1, b'0');
INSERT INTO `bill_book_relation` VALUES (73, 23, 1, b'0');
INSERT INTO `bill_book_relation` VALUES (74, 23, 2, b'0');
INSERT INTO `bill_book_relation` VALUES (75, 24, 1, b'0');
INSERT INTO `bill_book_relation` VALUES (76, 24, 1, b'0');
INSERT INTO `bill_book_relation` VALUES (77, 24, 2, b'0');
INSERT INTO `bill_book_relation` VALUES (78, 25, 1, b'0');
INSERT INTO `bill_book_relation` VALUES (79, 25, 1, b'0');
INSERT INTO `bill_book_relation` VALUES (80, 25, 3, b'0');
INSERT INTO `bill_book_relation` VALUES (81, 26, 3, b'0');
INSERT INTO `bill_book_relation` VALUES (82, 26, 4, b'0');
INSERT INTO `bill_book_relation` VALUES (83, 26, 2, b'0');
INSERT INTO `bill_book_relation` VALUES (84, 27, 5, b'0');
INSERT INTO `bill_book_relation` VALUES (85, 27, 5, b'0');
INSERT INTO `bill_book_relation` VALUES (86, 27, 5, b'0');
INSERT INTO `bill_book_relation` VALUES (87, 28, 1, b'1');
INSERT INTO `bill_book_relation` VALUES (88, 28, 1, b'0');
INSERT INTO `bill_book_relation` VALUES (89, 28, 2, b'0');
INSERT INTO `bill_book_relation` VALUES (90, 29, 1, b'0');
INSERT INTO `bill_book_relation` VALUES (91, 29, 1, b'0');
INSERT INTO `bill_book_relation` VALUES (92, 29, 1, b'0');

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` int(11) NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `book_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `book_price` int(11) NULL DEFAULT NULL,
  `publisher` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`book_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, 67, '丹.西蒙斯', '海伯利安', 64, '机械工业出版社', '科幻');
INSERT INTO `book` VALUES (2, 37, '尤瓦尔·赫拉利', '人类简史', 156, '机械工业出版社', '科普');
INSERT INTO `book` VALUES (3, 86, 'Zweig.S.', '人类群星闪耀时', 45, '机械工业出版社', '文学');
INSERT INTO `book` VALUES (4, 119, '丹尼尔·卡尼曼', '思考，快与慢', 54, '机械工业出版社', '经济学');
INSERT INTO `book` VALUES (5, 79, '吴军', '浪潮之巅', 54, '机械工业出版社', '互联网');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '普通用户', '普通用户');
INSERT INTO `role` VALUES (2, '管理员', '管理员');

-- ----------------------------
-- Table structure for role_authority_relation
-- ----------------------------
DROP TABLE IF EXISTS `role_authority_relation`;
CREATE TABLE `role_authority_relation`  (
  `role_authority_id` int(11) NOT NULL AUTO_INCREMENT,
  `authority_id` int(11) NULL DEFAULT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`role_authority_id`) USING BTREE,
  INDEX `654`(`authority_id`) USING BTREE,
  INDEX `65`(`role_id`) USING BTREE,
  CONSTRAINT `65` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `654` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`authority_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_authority_relation
-- ----------------------------
INSERT INTO `role_authority_relation` VALUES (1, 1, 1);
INSERT INTO `role_authority_relation` VALUES (2, 2, 2);
INSERT INTO `role_authority_relation` VALUES (3, 4, 2);
INSERT INTO `role_authority_relation` VALUES (4, 5, 2);
INSERT INTO `role_authority_relation` VALUES (5, 6, 2);
INSERT INTO `role_authority_relation` VALUES (6, 7, 2);
INSERT INTO `role_authority_relation` VALUES (7, 8, 2);
INSERT INTO `role_authority_relation` VALUES (8, 9, 2);
INSERT INTO `role_authority_relation` VALUES (9, 10, 2);
INSERT INTO `role_authority_relation` VALUES (10, 11, 1);
INSERT INTO `role_authority_relation` VALUES (11, 12, 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '$2a$10$WXrn5YN4/PcloMQH86hM..iYhroQh3wMrFOZfSRrE0bhJOtsd9zVW', 'user');
INSERT INTO `user` VALUES (2, '$2a$10$gxCNv0R8azxhZ3FnRohcUue37/90BhX66mUPlKGi9yc0bSZVQ1kqC', 'admin');

-- ----------------------------
-- Table structure for user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_role_relation`;
CREATE TABLE `user_role_relation`  (
  `user_role_relation_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`user_role_relation_id`) USING BTREE,
  INDEX `1`(`role_id`) USING BTREE,
  INDEX `2`(`user_id`) USING BTREE,
  CONSTRAINT `1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role_relation
-- ----------------------------
INSERT INTO `user_role_relation` VALUES (1, 1, 1);
INSERT INTO `user_role_relation` VALUES (2, 2, 2);

SET FOREIGN_KEY_CHECKS = 1;
