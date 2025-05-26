DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `user_id`              bigint  NOT NULL COMMENT '主键',
    `real_name`            varchar(255) NULL DEFAULT NULL COMMENT '姓名',
    `nick_name`            varchar(255) NULL DEFAULT NULL COMMENT '昵称',
    `account`              varchar(255) NULL DEFAULT NULL COMMENT '账号',
    `password`             varchar(255) NULL DEFAULT NULL COMMENT '密码',
    `password_salt`        varchar(255) NULL DEFAULT NULL COMMENT '密码盐',
    `avatar`               varchar(255) NULL DEFAULT NULL COMMENT '头像',
    `birthday`             datetime NULL DEFAULT NULL COMMENT '生日',
    `sex`                  char(1) NULL DEFAULT NULL COMMENT '性别',
    `email`                varchar(255) NULL DEFAULT NULL COMMENT '邮箱',
    `phone`                varchar(255) NULL DEFAULT NULL COMMENT '手机号码',
    `super_admin_flag`     char(1) NULL DEFAULT 'N' COMMENT '是否是超级管理员',
    `status_flag`          tinyint NULL DEFAULT NULL COMMENT '状态',
    `freeze_deadline_time` datetime NULL DEFAULT NULL COMMENT '冻结截止时间',
    `login_count`          bigint NULL DEFAULT NULL COMMENT '登录次数',
    `last_login_ip`        varchar(255) NULL DEFAULT NULL COMMENT '最后登陆IP',
    `last_login_time`      datetime NULL DEFAULT NULL COMMENT '最后登陆时间',
    `user_sort`            decimal(10, 2) NULL DEFAULT NULL COMMENT '用户的排序',
    `create_time`          datetime NULL DEFAULT NULL COMMENT '创建时间',
    `create_user`          bigint NULL DEFAULT NULL COMMENT '创建人',
    `update_time`          datetime NULL DEFAULT NULL COMMENT '更新时间',
    `update_user`          bigint NULL DEFAULT NULL COMMENT '更新人',
    `del_flag`             char(1) NOT NULL DEFAULT 'N' COMMENT '删除标记',
    PRIMARY KEY (`user_id`)
) COMMENT='系统用户';

DROP TABLE IF EXISTS `sys_user_password_record`;
create table sys_user_password_record
(
    `record_id`             bigint       not null comment '主键',
    `user_id`               bigint       not null comment '用户ID',
    `history_password`      varchar(255) not null comment '历史密码',
    `history_password_salt` varchar(255) not null comment '历史密码盐',
    `update_password_time`  timestamp    not null comment '更新密码时间',
    `create_time`           datetime NULL DEFAULT NULL COMMENT '创建时间',
    `create_user`           bigint NULL DEFAULT NULL COMMENT '创建人',
    `update_time`           datetime NULL DEFAULT NULL COMMENT '更新时间',
    `update_user`           bigint NULL DEFAULT NULL COMMENT '更新人',
    primary key (`record_id`)
) COMMENT='系统用户修改密码记录表';

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `role_id`         bigint         NOT NULL COMMENT '主键ID',
    `role_name`       varchar(100)   NOT NULL COMMENT '角色名称',
    `role_code`       varchar(100)   NOT NULL COMMENT '角色编码',
    `role_sort`       decimal(10, 2) NOT NULL COMMENT '显示顺序',
    `data_scope_type` tinyint        NOT NULL DEFAULT 1 COMMENT '数据范围类型：10-仅本人数据，20-本部门数据，30-本部门及以下数据，40-指定部门数据，50-全部数据',
    `status_flag`     tinyint        NOT NULL DEFAULT 1 COMMENT '状态：1-启用，2-禁用',
    `role_type`       tinyint        NOT NULL COMMENT '角色类型：10-系统角色，15-业务角色，20-公司角色',
    `role_company_id` bigint NULL DEFAULT NULL COMMENT '角色所属公司id，当角色类型为20时传此值',
    `expand_field`    json NULL COMMENT '拓展字段',
    `del_flag`        char(1)        NOT NULL DEFAULT 'N' COMMENT '删除标记：Y-已删除，N-未删除',
    `create_time`     datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user`     bigint NULL DEFAULT NULL COMMENT '创建人',
    `update_time`     datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `update_user`     bigint NULL DEFAULT NULL COMMENT '更新人',
    `remark`          varchar(255) NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`role_id`)
) COMMENT='系统角色';
