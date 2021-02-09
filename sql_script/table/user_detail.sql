CREATE TABLE `user_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `name` varchar(100) DEFAULT NULL COMMENT '注册名称',
  `sex` int(1) NOT NULL DEFAULT '0' COMMENT '性别：1-男，2-女，0-未知',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除标识位',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户注册详情'