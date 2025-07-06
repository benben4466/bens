CREATE TABLE `sys_user`
(
    `user_id`         bigint         NOT NULL COMMENT '主键',
    `nick_name`       varchar(255)   NULL     DEFAULT NULL COMMENT '昵称',
    `account`         varchar(255)   NULL     DEFAULT NULL COMMENT '账号',
    `password`        varchar(255)   NULL     DEFAULT NULL COMMENT '密码',
    `password_salt`   varchar(255)   NULL     DEFAULT NULL COMMENT '密码盐',
    `avatar`          varchar(255)   NULL     DEFAULT NULL COMMENT '头像',
    `birthday`        datetime       NULL     DEFAULT NULL COMMENT '生日',
    `sex`             char(1)        NULL     DEFAULT NULL COMMENT '性别',
    `email`           varchar(255)   NULL     DEFAULT NULL COMMENT '邮箱',
    `phone`           varchar(255)   NULL     DEFAULT NULL COMMENT '手机号码',
    `status_flag`     tinyint        NULL     DEFAULT NULL COMMENT '状态',
    `user_sort`       decimal(10, 2) NULL     DEFAULT 999 COMMENT '用户排序',
    `tenant_id`       bigint         NULL     DEFAULT NULL COMMENT '租户编号',
    `last_login_ip`   varchar(255)   NULL     DEFAULT NULL COMMENT '最后登陆IP',
    `last_login_time` datetime       NULL     DEFAULT NULL COMMENT '最后登陆时间',
    `create_time`     datetime       NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user`     bigint         NULL     DEFAULT NULL COMMENT '创建人',
    `update_time`     datetime       NULL     DEFAULT NULL COMMENT '更新时间',
    `update_user`     bigint         NULL     DEFAULT NULL COMMENT '更新人',
    `del_flag`        char(1)        NOT NULL DEFAULT 'N' COMMENT '删除标记',
    PRIMARY KEY (`user_id`)
) COMMENT ='系统用户';