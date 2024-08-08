
create database blog;
use blog;

CREATE TABLE `blogName` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `usrID` varchar(20) DEFAULT NULL COMMENT '用户id',
  `usrName` varchar(20) DEFAULT NULL COMMENT '用户名字',
  `blogName` varchar(100) NOT NULL COMMENT '博客名字',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '博客创建时间,仅插入时纪录',
  `updateDate` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '博客修改时间,记录,数据库必须有改动',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

alter table blogName add `categoryName` varchar(100) NOT NULL COMMENT '分组名字';



CREATE TABLE `usr` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `usrID` varchar(20) DEFAULT NULL COMMENT '用户id',
  `usr`  varchar(20) DEFAULT NULL COMMENT '用户名字',
  `pwd`   varchar(50) DEFAULT NULL COMMENT '用户名字',
  `mail`  varchar(50) DEFAULT NULL COMMENT '用户邮件',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '博客创建时间,仅插入时纪录',
  `updateDate` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '博客修改时间,记录,数据库必须有改动',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

alter table blogName modify column usrID varchar(20) DEFAULT NULL COMMENT '用 户id'


CREATE TABLE `blogCategory` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `usrID` varchar(20) DEFAULT NULL COMMENT '用 户id',
  `categoryName` varchar(100) NOT NULL COMMENT '分组名字',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '分组创建时间,仅插入时纪录',
  `updateDate` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '分组修改时间,记录,数据库必须有改动',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8




/**
 链表查询 blog

有三表

第一个 usr 表,         包含字段 usrID
第二个 blogCategory 表,包含字段 usrID,categoryName
第三个 blogName 表,    包含字段 categoryName,blogName,first,等字段

第一步 从第一个表中获取所有的 usrID,
第二步 从第二个表中根据 usrID,获取所有的 categoryName
第三步 从第三个表中根据 categoryName,分组数据 输出 blogName 表 一行一行数据
 */

SELECT
    bn.*
FROM
    usr u
JOIN
    blogCategory bc ON u.usrID = bc.usrID
JOIN
    blogName bn ON bc.categoryName = bn.categoryName
WHERE
    bn.usrID = "20221128231019"
ORDER BY
    bn.categoryName, bn.createDate DESC,bc.createDate DESC;



/**
 跟新 blogName 如果包含gl 的categoryName =  OpenGL
 */

UPDATE blogName
SET categoryName = 'OpenGL'
WHERE LOWER(blogName) LIKE '%gl%';


/**
 跟新 blogName 如果不包含gl 的categoryName =  Other
 */

UPDATE blogName
SET categoryName = 'Other'
WHERE LOWER(blogName) NOT LIKE '%gl%';



/**
 插入数据 blogCategory 
 */

insert blogCategory(usrID,categoryName) values('20221126221656','Other');