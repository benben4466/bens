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

CREATE TABLE `sys_hr_organization`
(
    `org_id`         bigint         NOT NULL COMMENT '主键',
    `org_parent_id`  bigint         NOT NULL COMMENT '父id，一级节点父id是-1',
    `org_pids`       varchar(500)   NOT NULL COMMENT '父ids',
    `org_name`       varchar(100)   NOT NULL COMMENT '组织名称',
    `org_short_name` varchar(100) NULL DEFAULT NULL COMMENT '组织机构简称',
    `org_code`       varchar(50)    NOT NULL COMMENT '组织编码',
    `org_sort`       decimal(10, 2) NOT NULL COMMENT '排序',
    `status_flag`    tinyint        NOT NULL DEFAULT 1 COMMENT '状态：1-启用，2-禁用',
    `org_type`       tinyint NULL DEFAULT 1 COMMENT '组织机构类型：1-公司，2-部门',
    `tax_no`         varchar(30) NULL DEFAULT NULL COMMENT '税号',
    `remark`         varchar(255) NULL DEFAULT NULL COMMENT '描述',
    `org_level`      int NULL DEFAULT NULL COMMENT '组织机构层级',
    `expand_field`   json NULL COMMENT '拓展字段',
    `del_flag`       char(1)        NOT NULL DEFAULT 'N' COMMENT '删除标记：Y-已删除，N-未删除',
    `create_time`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user`    bigint NULL DEFAULT NULL COMMENT '创建人',
    `update_time`    datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `update_user`    bigint NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`org_id`)
) COMMENT = '组织机构信息';

CREATE TABLE `sys_menu`
(
    `menu_id`          bigint                                                   NOT NULL COMMENT '主键',
    `menu_parent_id`   bigint                                                   NOT NULL COMMENT '父id，顶级节点的父id是-1',
    `menu_pids`        varchar(1000)                                            NOT NULL COMMENT '父id集合，中括号包住，逗号分隔',
    `menu_name`        varchar(100)                                             NOT NULL COMMENT '菜单的名称',
    `menu_code`        varchar(50)                                              NOT NULL COMMENT '菜单编码',
    `menu_type`        tinyint NULL DEFAULT NULL COMMENT '菜单类型：10-后台菜单，20-纯前台路由界面，30-内部链接，40-外部链接',
    `menu_sort`        decimal(20, 2)                                           NOT NULL DEFAULT 100.00 COMMENT '排序',
    `antdv_component`  varchar(255) NULL DEFAULT NULL COMMENT '前端组件名',
    `antdv_router`     varchar(255) NULL DEFAULT NULL COMMENT '路由地址，浏览器显示的URL，例如/menu',
    `antdv_icon`       varchar(255) NULL DEFAULT 'icon-default' COMMENT '图标编码',
    `antdv_link_url`   varchar(255) NULL DEFAULT NULL COMMENT '外部链接地址',
    `antdv_active_url` varchar(255) NULL DEFAULT NULL COMMENT '用于非菜单显示页面的重定向url设置',
    `antdv_visible`    char(1) NULL DEFAULT 'Y' COMMENT '是否可见(分离版用)：Y-是，N-否',
    `status_flag`      tinyint                                                  NOT NULL DEFAULT 1 COMMENT '状态：1-启用，2-禁用',
    `remark`           varchar(255) NULL DEFAULT NULL COMMENT '备注',
    `expand_field`     json NULL COMMENT '拓展字段',
    `del_flag`         char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'N' COMMENT '删除标记：Y-已删除，N-未删除',
    `create_time`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user`      bigint NULL DEFAULT NULL COMMENT '创建人',
    `update_time`      datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `update_user`      bigint NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`menu_id`)
) COMMENT = '系统菜单';

CREATE TABLE `sys_menu_options`
(
    `menu_option_id` bigint       NOT NULL COMMENT '主键',
    `menu_id`        bigint       NOT NULL COMMENT '菜单ID',
    `option_name`    varchar(100) NOT NULL COMMENT '功能或操作的名称',
    `option_code`    varchar(100) NOT NULL COMMENT '功能或操作的编码',
    `create_time`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user`    bigint NULL DEFAULT NULL COMMENT '创建人',
    `update_time`    datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user`    bigint NULL DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`menu_option_id`)
) COMMENT = '菜单下的功能操作';

CREATE TABLE `sys_user_role`
(
    `user_role_id` bigint  NOT NULL COMMENT '主键',
    `user_id`      bigint  NOT NULL COMMENT '用户ID',
    `role_id`      bigint  NOT NULL COMMENT '角色ID',
    `role_type`    tinyint NOT NULL DEFAULT 10 COMMENT '角色类型：10-系统角色，15-业务角色，20-公司角色',
    `role_org_id`  bigint NULL DEFAULT NULL COMMENT '用户所属机构ID',
    `create_time`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user`  bigint NULL DEFAULT NULL COMMENT '创建人',
    `update_time`  datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user`  bigint NULL DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (user_role_id)
) COMMENT = '用户角色关联';

CREATE TABLE `sys_role_menu`
(
    `role_menu_id` bigint NOT NULL COMMENT '主键',
    `role_id`      bigint NOT NULL COMMENT '角色ID',
    `menu_id`      bigint NOT NULL COMMENT '菜单ID',
    `create_time`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user`  bigint NULL DEFAULT NULL COMMENT '创建人',
    `update_time`  datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user`  bigint NULL DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`role_menu_id`)
) COMMENT = '角色菜单关联';

CREATE TABLE `sys_role_data_scope`
(
    `role_data_scope_id` bigint NOT NULL COMMENT '主键',
    `role_id`            bigint NOT NULL COMMENT '角色ID',
    `organization_id`    bigint NOT NULL COMMENT '组织ID(角色所属组织)',
    `create_time`        datetime NULL DEFAULT NULL COMMENT '创建时间',
    `create_user`        bigint NULL DEFAULT NULL COMMENT '创建人',
    `update_time`        datetime NULL DEFAULT NULL COMMENT '修改时间',
    `update_user`        bigint NULL DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`role_data_scope_id`) USING BTREE
) COMMENT = '角色数据范围';

CREATE TABLE `sys_role_menu_options`
(
    `role_menu_option_id` bigint NOT NULL COMMENT '主键',
    `role_id`             bigint NOT NULL COMMENT '角色ID',
    `menu_option_id`      bigint NOT NULL COMMENT '菜单功能ID',
    `menu_id`             bigint NULL DEFAULT NULL COMMENT '功能所属的菜单ID(冗余)',
    `create_time`         datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user`         bigint NULL DEFAULT NULL COMMENT '创建人',
    `update_time`         datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user`         bigint NULL DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`role_menu_option_id`)
) COMMENT = '角色和菜单下的功能关联';

CREATE TABLE `sys_role_limit`
(
    `role_limit_id` bigint  NOT NULL COMMENT '主键',
    `role_id`       bigint  NOT NULL COMMENT '角色ID',
    `limit_type`    tinyint NOT NULL COMMENT '角色限制类型：1-角色可分配的菜单，2-角色可分配的功能',
    `business_id`   bigint  NOT NULL COMMENT '业务ID，为菜单ID或菜单功能ID',
    `create_time`   datetime NULL DEFAULT NULL COMMENT '创建时间',
    `create_user`   bigint NULL DEFAULT NULL COMMENT '创建人',
    `update_time`   datetime NULL DEFAULT NULL COMMENT '修改时间',
    `update_user`   bigint NULL DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`role_limit_id`)
) COMMENT = '角色权限限制';

CREATE TABLE `sys_dict_type`
(
    `dict_type_id`    bigint         NOT NULL COMMENT '主键',
    `dict_type_name`  varchar(100)   NOT NULL COMMENT '字典类型名称',
    `dict_type_code`  varchar(100)   NOT NULL COMMENT '字典类型编码',
    `dict_type_class` tinyint        NOT NULL COMMENT '字典类型编码: 1=业务类型；2=系统类型',
    `status_flag`     tinyint        NOT NULL DEFAULT 1 COMMENT '状态：1-启用，2-禁用',
    `dict_type_sort`  decimal(10, 2) NOT NULL COMMENT '排序',
    `remark`          varchar(255) NULL DEFAULT NULL COMMENT '备注',
    `del_flag`        char(1)        NOT NULL DEFAULT 'N' COMMENT '删除标记：Y-已删除，N-未删除',
    `create_time`     datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user`     bigint NULL DEFAULT NULL COMMENT '创建人',
    `update_time`     datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user`     bigint NULL DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`dict_type_id`)
) COMMENT = '字典类型';

CREATE TABLE `sys_dict`
(
    `dict_id`         bigint         NOT NULL COMMENT '主键',
    `dict_type_id`    bigint         NOT NULL COMMENT '字典类型ID',
    `dict_name`       varchar(255)   NOT NULL COMMENT '字典名称',
    `dict_value`      varchar(255)   NOT NULL COMMENT '字典值',
    `dict_short_name` varchar(255)            DEFAULT NULL COMMENT '字典简称名称',
    `dict_sort`       decimal(10, 2) NOT NULL COMMENT '排序',
    `status_flag`     tinyint        NOT NULL DEFAULT '1' COMMENT '状态：1-启用，2-禁用',
    `del_flag`        char(1)        NOT NULL DEFAULT 'N' COMMENT '删除标记：Y-已删除，N-未删除',
    `create_time`     datetime                DEFAULT NULL COMMENT '创建时间',
    `create_user`     bigint                  DEFAULT NULL COMMENT '创建人',
    `update_time`     datetime                DEFAULT NULL COMMENT '修改时间',
    `update_user`     bigint                  DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`dict_id`)
) COMMENT='字典';
