-- 图书信息
create table if not exists `book_info`
(
    bookId bigint auto_increment comment 'id' primary key,
    `bookName` varchar(256) not null comment '图书名',
    `bookInfo` varchar(512) null comment '图书信息',
    `author` varchar(64) not null comment '作者',
    `borrowState` varchar(128) not null default '未借阅' comment '借阅状态',
    `tags` varchar(512) not null comment '标签',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` int default 0 not null comment '是否删除（0-未删，1-删除）'
    ) comment '图书信息';



-- 用户信息
create table if not exists `user_info`
(
    userId bigint auto_increment comment 'id' primary key,
    `userName` varchar(256) not null comment '用户名',
    `userRole` varchar(64) not null comment '角色',
    `userAccount` varchar(256) null comment '用户账号',
    `password` varchar(256) not null comment '密码',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` int default 0 not null comment '是否删除（0-未删，1-删除）'
    ) comment '用户信息';

-- 借阅信息
create table if not exists `borrow_info`
(
    bookId bigint not null comment 'bookId',
    userId bigint not null comment 'userId',
    `borrowState` varchar(64) not null default '已借阅' comment '借阅状态',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` int default 0 not null comment '是否删除（0-未删，1-删除）'
) comment '借阅信息';
