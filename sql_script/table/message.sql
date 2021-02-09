CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content_type` int(11) NOT NULL COMMENT '消息类型，1-个人，2-群组',
  `content_from` varchar(32) NOT NULL COMMENT '来源',
  `content_to` varchar(32) NOT NULL COMMENT '到达',
  `content` text COMMENT '发送内容',
  `send_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='消息表'