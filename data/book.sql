/*
 Navicat MySQL Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 11/12/2022 22:34:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `isbn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `genre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `numberOfPage` int NULL DEFAULT NULL,
  `inStoreNumber` int NULL DEFAULT NULL,
  `previousSale` int NULL DEFAULT 0,
  `price` float NULL DEFAULT NULL,
  `percentage` float NULL DEFAULT NULL,
  `publisher_id` bigint NOT NULL,
  `bookstore_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (8, 'Harry Potter and the Sorcerer\'s Stone', 'J.K. Rowling', '9780590353427', 'Magic words', 320, 44, 6, 10.99, 0.5, 7, 0);
INSERT INTO `book` VALUES (9, 'Harry Potter and the Deathly Hallows', 'J.K. Rowling', '9780545029377', 'Magic words', 784, 46, 4, 65, 0.5, 8, 0);
INSERT INTO `book` VALUES (10, 'Harry Potter and the Cursed Child', 'J.K. Rowling', '9781338099133', 'Magic words', 320, 50, 0, 29.99, 0.5, 8, 0);
INSERT INTO `book` VALUES (11, 'Harry Potter and the Chamber of Secrets', 'J.K. Rowling', '9780545791328', 'Magic words', 259, 50, 0, 39.99, 0.5, 8, 0);
INSERT INTO `book` VALUES (12, 'Harry Potter and the Prisoner of Azkaban', 'J.K. Rowling', '9787807475460', 'Magic words', 320, 50, 0, 67.73, 0.5, 9, 0);

SET FOREIGN_KEY_CHECKS = 1;
