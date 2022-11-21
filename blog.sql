
create database blog;
use blog;

create table blogName (
 id int  auto_increment  comment '自增id',
 usrID int comment '用户ID',
 usrName varchar(20) null comment '用户名字',
 blogName varchar(100) not null comment '博客名字',
 createDate timestamp not null comment '博客创建时间',
 updateDate timestamp null default current_timestamp comment  '博客修改时间',
 primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;