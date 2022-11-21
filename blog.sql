create table 'blog' (
 'id' int  auto_increment primary key comment '自增id',
 'blogName' varchar(100) default not null comment '博客名字',
 'createDate' timestamp not null comment '博客创建时间',
 'updateDate' timestamp null default current_timestamp comment  '博客修改时间',
)