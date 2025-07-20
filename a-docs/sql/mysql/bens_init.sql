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
    `version_flag`    bigint         NULL     DEFAULT NULL COMMENT '乐观锁',
    `create_time`     datetime       NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user`     bigint         NULL     DEFAULT NULL COMMENT '创建人',
    `update_time`     datetime       NULL     DEFAULT NULL COMMENT '更新时间',
    `update_user`     bigint         NULL     DEFAULT NULL COMMENT '更新人',
    `del_flag`        char(1)        NOT NULL DEFAULT 'N' COMMENT '删除标记',
    PRIMARY KEY (`user_id`)
) COMMENT ='系统用户';

create table sys_user_password_record
(
    `record_id`             bigint       not null comment '主键',
    `user_id`               bigint       not null comment '用户ID',
    `history_password`      varchar(255) not null comment '历史密码',
    `history_password_salt` varchar(255) not null comment '历史密码盐',
    `update_password_time`  timestamp    not null comment '更新密码时间',
    `create_time`           datetime     NULL DEFAULT NULL COMMENT '创建时间',
    `create_user`           bigint       NULL DEFAULT NULL COMMENT '创建人',
    `update_time`           datetime     NULL DEFAULT NULL COMMENT '更新时间',
    `update_user`           bigint       NULL DEFAULT NULL COMMENT '更新人',
    primary key (`record_id`)
) COMMENT ='系统用户修改密码记录表';

CREATE TABLE `sys_hr_organization`
(
    `org_id`         bigint         NOT NULL COMMENT '主键',
    `org_parent_id`  bigint         NOT NULL COMMENT '父id，一级节点父id是-1',
    `org_name`       varchar(100)   NOT NULL COMMENT '组织名称',
    `org_short_name` varchar(100)   NULL     DEFAULT NULL COMMENT '组织机构简称',
    `org_code`       varchar(50)    NOT NULL COMMENT '组织编码',
    `org_sort`       decimal(10, 2) NOT NULL COMMENT '排序',
    `org_type`       tinyint        NULL     DEFAULT 1 COMMENT '组织机构类型, 1=公司; 2=部门',
    `tax_no`         varchar(30)    NULL     DEFAULT NULL COMMENT '税号',
    `contacts`       varchar(50)    NULL     DEFAULT NULL COMMENT '联系人',
    `contacts_phone` varchar(30)    NULL     DEFAULT NULL COMMENT '联系人号码',
    `contacts_email` varchar(30)    NULL     DEFAULT NULL COMMENT '联系人邮箱',
    `status_flag`    tinyint        NOT NULL DEFAULT 1 COMMENT '状态：1-启用，2-禁用',
    `remark`         varchar(255)   NULL     DEFAULT NULL COMMENT '描述',
    `expand_field`   json           NULL COMMENT '拓展字段',
    `version_flag`   bigint         NULL     DEFAULT NULL COMMENT '乐观锁',
    `del_flag`       char(1)        NOT NULL DEFAULT 'N' COMMENT '删除标记：Y-已删除，N-未删除',
    `create_time`    datetime(0)    NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user`    bigint         NULL     DEFAULT NULL COMMENT '创建人',
    `update_time`    datetime(0)    NULL     DEFAULT NULL COMMENT '更新时间',
    `update_user`    bigint         NULL     DEFAULT NULL COMMENT '更新人',
    `tenant_id`      bigint         NULL     DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`org_id`)
) COMMENT = '组织机构信息';

CREATE TABLE `sys_user_org`
(
    `user_org_id` bigint      NOT NULL COMMENT '主键',
    `user_id`     bigint      NOT NULL COMMENT '用户主键',
    `org_id`      bigint      NOT NULL COMMENT '组织主键',
    `position_id` bigint      NULL     DEFAULT NULL COMMENT '职位ID',
    `main_flag`   char(1)     NOT NULL DEFAULT 'N' COMMENT '是否是主部门, Y=是; N=不是;',
    `status_flag` tinyint     NOT NULL DEFAULT 1 COMMENT '是否启用',
    `create_time` datetime(0) NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user` bigint      NULL     DEFAULT NULL COMMENT '创建人',
    `update_time` datetime(0) NULL     DEFAULT NULL COMMENT '更新时间',
    `update_user` bigint      NULL     DEFAULT NULL COMMENT '更新人',
    `tenant_id`   bigint      NULL     DEFAULT NULL COMMENT '租户ID',
    PRIMARY KEY (`user_org_id`)
) COMMENT = '用户组织机构关联';

CREATE TABLE `sys_post`
(
    `position_id`   bigint         NOT NULL COMMENT '主键',
    `position_name` varchar(100)   NOT NULL COMMENT '职位名称',
    `position_code` varchar(100)   NOT NULL COMMENT '职位编码',
    `position_sort` decimal(10, 2) NOT NULL COMMENT '排序',
    `status_flag`   tinyint        NOT NULL DEFAULT 1 COMMENT '状态,1=启用，2=禁用',
    `remark`        varchar(255)   NULL     DEFAULT NULL COMMENT '备注',
    `version_flag`  bigint         NULL     DEFAULT NULL COMMENT '乐观锁',
    `del_flag`      char(1)        NOT NULL DEFAULT 'N' COMMENT '删除标记,Y=已删除，N=未删除',
    `create_time`   datetime(0)    NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user`   bigint         NULL     DEFAULT NULL COMMENT '创建人',
    `update_time`   datetime(0)    NULL     DEFAULT NULL COMMENT '更新时间',
    `update_user`   bigint         NULL     DEFAULT NULL COMMENT '更新人',
    `tenant_id`     bigint         NULL     DEFAULT NULL COMMENT '租户号',
    PRIMARY KEY (`position_id`)
) COMMENT = '职位信息';

CREATE TABLE `sys_menu`
(
    `menu_id`           bigint                                                   NOT NULL COMMENT '主键',
    `menu_parent_id`    bigint                                                   NOT NULL COMMENT '父id，顶级节点的父id是-1',
    `menu_name`         varchar(100)                                             NOT NULL COMMENT '菜单的名称',
    `permission_code`   varchar(50)                                              NOT NULL COMMENT '权限编码',
    `menu_type`         tinyint                                                  NULL     DEFAULT NULL COMMENT '菜单类型',
    `menu_sort`         decimal(20, 2)                                           NOT NULL DEFAULT 100.00 COMMENT '排序',
    `component_path`    varchar(255)                                             NULL     DEFAULT NULL COMMENT '组件地址',
    `component_router`  varchar(255)                                             NULL     DEFAULT NULL COMMENT '路由地址，浏览器显示的URL，例如/menu',
    `component_icon`    varchar(255)                                             NULL     DEFAULT 'icon-default' COMMENT '图标编码',
    `component_visible` char(1)                                                  NULL     DEFAULT 'Y' COMMENT '是否可见(Y=是;N=否)',
    `keep_alive`        char(1)                                                  NOT NULL DEFAULT 1 COMMENT '是否缓存',
    `always_show`       char(1)                                                  NOT NULL DEFAULT 1 COMMENT '是否总是显示',
    `status_flag`       tinyint                                                  NOT NULL DEFAULT 1 COMMENT '状态(1=启用;2=禁用)',
    `remark`            varchar(255)                                             NULL     DEFAULT NULL COMMENT '备注',
    `expand_field`      json                                                     NULL COMMENT '拓展字段',
    `version_flag`      bigint                                                   NULL     DEFAULT NULL COMMENT '乐观锁',
    `del_flag`          char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'N' COMMENT '删除标记(Y=已删除;N=未删除)',
    `create_time`       datetime(0)                                              NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user`       bigint                                                   NULL     DEFAULT NULL COMMENT '创建人',
    `update_time`       datetime(0)                                              NULL     DEFAULT NULL COMMENT '更新时间',
    `update_user`       bigint                                                   NULL     DEFAULT NULL COMMENT '更新人',
    `tenant_id`         bigint                                                   NULL     DEFAULT NULL COMMENT '租户号',
    PRIMARY KEY (`menu_id`)
) COMMENT = '系统菜单';

CREATE TABLE `sys_role`
(
    `role_id`             bigint         NOT NULL COMMENT '主键',
    `role_name`           varchar(100)   NOT NULL COMMENT '角色名称',
    `role_code`           varchar(100)   NOT NULL COMMENT '角色编码',
    `role_sort`           decimal(10, 2) NOT NULL DEFAULT 999 COMMENT '显示顺序',
    `data_scope_type`     tinyint        NOT NULL DEFAULT 10 COMMENT '数据范围类型(10=仅本人数据，20=本部门数据，30=本部门及以下数据，40=指定部门数据，50=全部数据)',
    `data_scope_dept_ids` varchar(500)   NOT NULL DEFAULT '' COMMENT '数据范围(指定部门数组)',
    `role_type`           tinyint        NOT NULL COMMENT '角色类型',
    `status_flag`         tinyint        NOT NULL DEFAULT 1 COMMENT '状态(1=启用，2=禁用)',
    `remark`              varchar(255)   NULL     DEFAULT NULL COMMENT '备注',
    `expand_field`        json           NULL COMMENT '拓展字段',
    `version_flag`        bigint         NULL     DEFAULT NULL COMMENT '乐观锁',
    `del_flag`            char(1)        NOT NULL DEFAULT 'N' COMMENT '删除标记(Y=已删除，N=未删除)',
    `create_time`         datetime(0)    NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user`         bigint         NULL     DEFAULT NULL COMMENT '创建人',
    `update_time`         datetime(0)    NULL     DEFAULT NULL COMMENT '更新时间',
    `update_user`         bigint         NULL     DEFAULT NULL COMMENT '更新人',
    `tenant_id`           bigint         NULL     DEFAULT NULL COMMENT '租户号',
    PRIMARY KEY (`role_id`)
) COMMENT ='系统角色';
