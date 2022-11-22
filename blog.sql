
create database blog;
use blog;

CREATE TABLE `blogName` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `usrID` int(11) DEFAULT NULL COMMENT '用户ID',
  `usrName` varchar(20) DEFAULT NULL COMMENT '用户名字',
  `blogName` varchar(100) NOT NULL COMMENT '博客名字',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '博客创建时间,仅插入时纪录',
  `updateDate` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '博客修改时间,记录,数据库必须有改动',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8