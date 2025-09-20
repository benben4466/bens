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
    `last_login_ip`   varchar(255)   NULL     DEFAULT NULL COMMENT '最后登陆IP',
    `last_login_time` datetime       NULL     DEFAULT NULL COMMENT '最后登陆时间',
    `version_flag`    bigint         NULL     DEFAULT NULL COMMENT '乐观锁',
    `create_time`     datetime       NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user`     bigint         NULL     DEFAULT NULL COMMENT '创建人',
    `update_time`     datetime       NULL     DEFAULT NULL COMMENT '更新时间',
    `update_user`     bigint         NULL     DEFAULT NULL COMMENT '更新人',
    `del_flag`        char(1)        NOT NULL DEFAULT 'N' COMMENT '删除标记',
    `tenant_id`       bigint         NULL     DEFAULT NULL COMMENT '租户编号',
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
    `tenant_id`             bigint       NULL DEFAULT NULL COMMENT '租户ID',
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
    PRIMARY KEY (`user_org_id`)
) COMMENT = '用户组织机构关联';

CREATE TABLE `sys_position`
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
    `permission_code`   varchar(50)                                              NOT NULL DEFAULT '' COMMENT '权限编码',
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

CREATE TABLE `sys_user_role`
(
    `user_role_id` bigint      NOT NULL COMMENT '主键',
    `user_id`      bigint      NOT NULL COMMENT '用户ID',
    `role_id`      bigint      NOT NULL COMMENT '角色ID',
    `create_time`  datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_user`  bigint      NULL DEFAULT NULL COMMENT '创建人',
    `update_time`  datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user`  bigint      NULL DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (user_role_id)
) COMMENT = '用户角色关联';

CREATE TABLE `sys_role_menu`
(
    `role_menu_id` bigint      NOT NULL COMMENT '主键',
    `role_id`      bigint      NOT NULL COMMENT '角色ID',
    `menu_id`      bigint      NOT NULL COMMENT '菜单ID',
    `create_time`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_user`  bigint      NULL DEFAULT NULL COMMENT '创建人',
    `update_time`  datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `update_user`  bigint      NULL DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`role_menu_id`)
) COMMENT = '角色菜单关联';

CREATE TABLE `sys_dict_type`
(
    `dict_type_id`   bigint         NOT NULL COMMENT '主键',
    `dict_type_name` varchar(100)   NOT NULL COMMENT '字典类型名称',
    `dict_type_code` varchar(100)   NOT NULL COMMENT '字典类型编码',
    `status_flag`    tinyint        NOT NULL DEFAULT 1 COMMENT '状态(1=启用; 2=禁用)',
    `dict_type_sort` decimal(10, 2) NOT NULL DEFAULT 999 COMMENT '排序',
    `remark`         varchar(255)   NULL     DEFAULT NULL COMMENT '备注',
    `del_flag`       char(1)        NOT NULL DEFAULT 'N' COMMENT '删除标记(Y=已删除; N=未删除)',
    `version_flag`   bigint         NULL     DEFAULT NULL COMMENT '乐观锁',
    `create_time`    datetime(0)    NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user`    bigint         NULL     DEFAULT NULL COMMENT '创建人',
    `update_time`    datetime(0)    NULL     DEFAULT NULL COMMENT '修改时间',
    `update_user`    bigint         NULL     DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`dict_type_id`)
) COMMENT = '字典类型';

CREATE TABLE `sys_dict`
(
    `dict_id`         bigint         NOT NULL COMMENT '主键',
    `dict_type_code`  varchar(100)   NOT NULL COMMENT '字典类型编码',
    `dict_code`       varchar(255)   NOT NULL COMMENT '字典编码',
    `dict_value`      varchar(255)   NOT NULL COMMENT '字典值',
    `dict_color_type` varchar(100)   NULL     DEFAULT '' COMMENT '颜色类型(前端使用)',
    `dict_sort`       decimal(10, 2) NOT NULL COMMENT '排序',
    `status_flag`     tinyint        NOT NULL DEFAULT 1 COMMENT '状态(1=启用; 2=禁用)',
    `remark`          varchar(255)   NULL     DEFAULT NULL COMMENT '备注',
    `del_flag`        char(1)        NOT NULL DEFAULT 'N' COMMENT '删除标记(Y=已删除; N=未删除)',
    `version_flag`    bigint         NULL     DEFAULT NULL COMMENT '乐观锁',
    `create_time`     datetime                DEFAULT NULL COMMENT '创建时间',
    `create_user`     bigint                  DEFAULT NULL COMMENT '创建人',
    `update_time`     datetime                DEFAULT NULL COMMENT '修改时间',
    `update_user`     bigint                  DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`dict_id`)
) COMMENT ='字典';

CREATE TABLE `sys_config_type`
(
    `config_type_id`   bigint         NOT NULL COMMENT '主键',
    `config_type_name` varchar(255)   NOT NULL COMMENT '参数配置类型名称',
    `config_type_code` varchar(100)   NOT NULL COMMENT '参数配置类型编码',
    `config_type`      tinyint        NOT NULL COMMENT '参数配置类型',
    `visible_flag`     bit(1)         NOT NULL COMMENT '是否可见',
    `config_type_sort` decimal(10, 2) NOT NULL DEFAULT 999 COMMENT '显示排序',
    `remark`           varchar(255)   NULL     DEFAULT NULL COMMENT '备注',
    `del_flag`         char(1)        NOT NULL DEFAULT 'N' COMMENT '是否删除(Y=被删除，N=未删除)',
    `version_flag`     bigint         NULL     DEFAULT NULL COMMENT '乐观锁',
    `create_time`      datetime(0)    NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user`      bigint         NULL     DEFAULT NULL COMMENT '创建人',
    `update_time`      datetime(0)    NULL     DEFAULT NULL COMMENT '修改时间',
    `update_user`      bigint         NULL     DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`config_type_id`)
) COMMENT = '参数配置类型';

CREATE TABLE `sys_config`
(
    `config_id`        bigint         NOT NULL COMMENT '主键',
    `config_type_code` varchar(100)   NOT NULL COMMENT '参数配置类型编码',
    `config_name`      varchar(255)   NOT NULL COMMENT '参数配置名称',
    `config_code`      varchar(100)   NOT NULL COMMENT '参数配置编码',
    `config_value`     varchar(3500)  NOT NULL COMMENT '参数配置值',
    `config_type`      tinyint        NOT NULL COMMENT '参数类型',
    `visible_flag`     bit(1)         NOT NULL COMMENT '是否可见',
    `config_sort`      decimal(10, 2) NOT NULL COMMENT '显示排序',
    `remark`           varchar(255)   NULL     DEFAULT NULL COMMENT '备注',
    `del_flag`         char(1)        NOT NULL DEFAULT 'N' COMMENT '是否删除(Y=被删除，N=未删除)',
    `version_flag`     bigint         NULL     DEFAULT NULL COMMENT '乐观锁',
    `create_time`      datetime(0)    NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user`      bigint         NULL     DEFAULT NULL COMMENT '创建人',
    `update_time`      datetime(0)    NULL     DEFAULT NULL COMMENT '修改时间',
    `update_user`      bigint         NULL     DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`config_id`)
) COMMENT = '参数配置';

CREATE TABLE `sys_file_config`
(
    `file_config_id`   bigint        NOT NULL COMMENT '主键',
    `file_config_name` varchar(100)  NOT NULL COMMENT '文件配置名称',
    `file_config_code` varchar(100)  NOT NULL COMMENT '文件配置编码',
    `file_storage`     tinyint       NOT NULL COMMENT '文件存储器',
    `master_flag`      bit(1)        NOT NULL COMMENT '是否为主配置',
    `storage_config`   varchar(4096) NOT NULL COMMENT '存储配置',
    `remark`           varchar(255)  NULL     DEFAULT NULL COMMENT '备注',
    `del_flag`         char(1)       NOT NULL DEFAULT 'N' COMMENT '是否删除：Y=被删除，N=未删除',
    `version_flag`     bigint        NULL     DEFAULT NULL COMMENT '乐观锁',
    `create_time`      datetime(0)   NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user`      bigint        NULL     DEFAULT NULL COMMENT '创建人',
    `update_time`      datetime(0)   NULL     DEFAULT NULL COMMENT '修改时间',
    `update_user`      bigint        NULL     DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`file_config_id`) USING BTREE
) COMMENT = '文件配置表';

CREATE TABLE `sys_file`
(
    `file_id`          bigint        NOT NULL COMMENT '主键',
    `file_config_code` varchar(100)  NOT NULL COMMENT '文件配置编码',
    `file_origin_name` varchar(255)  NOT NULL COMMENT '文件原始名称(上传时候的文件全名)',
    `file_object_name` varchar(255)  NULL     DEFAULT NULL COMMENT '文件存储名称',
    `file_path`        varchar(255)  NULL     DEFAULT NULL COMMENT '文件路径',
    `file_url`         varchar(1024) NOT NULL COMMENT '文件URL',
    `file_size_kb`     bigint        NULL     DEFAULT NULL COMMENT '文件大小(单位:KB)',
    `file_type`        varchar(255)  NULL     DEFAULT NULL COMMENT '文件类型',
    `file_suffix`      varchar(255)  NULL     DEFAULT NULL COMMENT '文件后缀(例如txt)',
    `del_flag`         char(1)       NOT NULL DEFAULT 'N' COMMENT '是否删除(Y=被删除，N=未删除)',
    `version_flag`     bigint        NULL     DEFAULT NULL COMMENT '乐观锁',
    `create_time`      datetime(0)   NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user`      bigint        NULL     DEFAULT NULL COMMENT '创建人',
    `update_time`      datetime(0)   NULL     DEFAULT NULL COMMENT '修改时间',
    `update_user`      bigint        NULL     DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`file_id`)
) COMMENT = '文件信息';

CREATE TABLE `sys_login_log`
(
    `llg_id`       bigint       NOT NULL COMMENT '主键',
    `llg_type`     int          NOT NULL COMMENT '日志类型',
    `trace_id`     varchar(64)  NOT NULL DEFAULT '' COMMENT '链路追踪编号',
    `login_result` tinyint      NOT NULL COMMENT '登陆结果',
    `user_id`      bigint       NULL     DEFAULT NULL COMMENT '用户ID',
    `user_account` varchar(50)  NOT NULL DEFAULT '' COMMENT '用户账号',
    `user_type`    tinyint      NOT NULL DEFAULT 0 COMMENT '用户类型',
    `login_ip`     varchar(50)  NOT NULL COMMENT '用户登陆IP',
    `user_agent`   varchar(512) NOT NULL COMMENT '浏览器UA',
    `del_flag`     char(1)      NOT NULL DEFAULT 'N' COMMENT '删除标记：Y-已删除，N-未删除',
    `version_flag` bigint       NULL     DEFAULT NULL COMMENT '乐观锁',
    `create_time`  datetime(0)  NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user`  bigint       NULL     DEFAULT NULL COMMENT '创建人',
    `update_time`  datetime(0)  NULL     DEFAULT NULL COMMENT '更新时间',
    `update_user`  bigint       NULL     DEFAULT NULL COMMENT '更新人',
    `tenant_id`    bigint       NULL     DEFAULT NULL COMMENT '租户号',
    PRIMARY KEY (`llg_id`)
) COMMENT = '系统登录记录';

CREATE TABLE `sys_operate_log`
(
    `olg_id`         bigint        NOT NULL COMMENT '主键',
    `trace_id`       varchar(64)   NOT NULL DEFAULT '' COMMENT '链路追踪编号',
    `request_url`    varchar(255)  NULL     DEFAULT NULL COMMENT '请求地址',
    `request_method` varchar(255)  NULL     DEFAULT NULL COMMENT '请求方式',
    `request_params` longtext      NULL COMMENT '请求参数',
    `request_result` longtext      NULL COMMENT '请求响应',
    `module_no`      varchar(255)  NULL COMMENT '操作模块编号',
    `sub_module_no`  varchar(255)  NULL COMMENT '操作子模块编号',
    `biz_id`         bigint        NOT NULL COMMENT '操作模块业务ID',
    `op_action`      varchar(2000) NOT NULL DEFAULT '' COMMENT '操作内容',
    `user_id`        bigint        NULL     DEFAULT NULL COMMENT '用户ID',
    `user_account`   varchar(50)   NOT NULL DEFAULT '' COMMENT '用户账号',
    `user_type`      tinyint       NOT NULL DEFAULT 10 COMMENT '用户类型',
    `user_ip`        varchar(50)   NOT NULL COMMENT '用户IP',
    `user_agent`     varchar(512)  NOT NULL COMMENT '浏览器UA',
    `server_ip`      varchar(255)  NULL     DEFAULT NULL COMMENT '当前服务器的ip',
    `del_flag`       char(1)       NOT NULL DEFAULT 'N' COMMENT '删除标记：Y-已删除，N-未删除',
    `expand_field`   varchar(2000) NULL COMMENT '拓展字段',
    `version_flag`   bigint        NULL     DEFAULT NULL COMMENT '乐观锁',
    `create_time`    datetime(0)   NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user`    bigint        NULL     DEFAULT NULL COMMENT '创建人',
    `update_time`    datetime(0)   NULL     DEFAULT NULL COMMENT '更新时间',
    `update_user`    bigint        NULL     DEFAULT NULL COMMENT '更新人',
    `tenant_id`      bigint        NULL     DEFAULT NULL COMMENT '租户号',
    PRIMARY KEY (`olg_id`)
) COMMENT = '操作日志记录';

CREATE TABLE `sys_tenant_package`
(
    `package_id`       bigint       NOT NULL COMMENT '租户套餐编号',
    `package_name`     varchar(255) NOT NULL COMMENT '租户套餐名称',
    `package_menu_ids` varchar(255) NOT NULL COMMENT '租户套餐关联的菜单编号',
    `status_flag`      tinyint      NULL     DEFAULT NULL COMMENT '租户状态(1=正常;2=禁用;)',
    `remark`           varchar(255) NULL     DEFAULT NULL COMMENT '备注',
    `del_flag`         char(1)      NOT NULL DEFAULT 'N' COMMENT '删除标记(Y=已删除;N=未删除)',
    `version_flag`     bigint       NULL     DEFAULT NULL COMMENT '乐观锁',
    `create_time`      datetime(0)  NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user`      bigint       NULL     DEFAULT NULL COMMENT '创建人',
    `update_time`      datetime(0)  NULL     DEFAULT NULL COMMENT '更新时间',
    `update_user`      bigint       NULL     DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`package_id`)
) COMMENT = '租户套餐表';

CREATE TABLE `sys_tenant`
(
    `tenant_id`         bigint       NOT NULL COMMENT '租户编号',
    `tenant_package_id` bigint       NOT NULL COMMENT '租户套餐ID',
    `tenant_name`       varchar(255) NOT NULL COMMENT '租户名称',
    `contact_user_id`   bigint       NULL     DEFAULT NULL COMMENT '租户管理用户ID',
    `contact_name`      varchar(255) NOT NULL COMMENT '租户联系人名称',
    `contact_mobile`    varchar(255) NOT NULL COMMENT '租户联系人手机号码',
    `status_flag`       tinyint      NULL     DEFAULT NULL COMMENT '租户状态(1=正常;2=禁用;)',
    `tenant_website`    varchar(255) NULL COMMENT '租户绑定的域名',
    `expire_time`       datetime(0)  NOT NULL COMMENT '租户过期时间',
    `account_count`     bigint       NOT NULL COMMENT '授权的账号数量',
    `remark`            varchar(255) NULL     DEFAULT NULL COMMENT '备注',
    `del_flag`          char(1)      NOT NULL DEFAULT 'N' COMMENT '删除标记(Y=已删除;N=未删除)',
    `version_flag`      bigint       NULL     DEFAULT NULL COMMENT '乐观锁',
    `create_time`       datetime(0)  NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user`       bigint       NULL     DEFAULT NULL COMMENT '创建人',
    `update_time`       datetime(0)  NULL     DEFAULT NULL COMMENT '更新时间',
    `update_user`       bigint       NULL     DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`tenant_id`)
) COMMENT = '租户表';

CREATE TABLE `sys_notice`
(
    `notice_id`      bigint      NOT NULL COMMENT '公告ID',
    `notice_title`   varchar(50) NOT NULL COMMENT '公告标题',
    `notice_content` text        NOT NULL COMMENT '公告内容',
    `notice_type`    tinyint     NOT NULL COMMENT '公告类型（1通知 2公告）',
    `status_flag`    tinyint     NOT NULL DEFAULT 0 COMMENT '公告状态(0=正常;1=关闭)',
    `version_flag`   bigint      NULL     DEFAULT NULL COMMENT '乐观锁',
    `create_time`    datetime    NULL     DEFAULT NULL COMMENT '创建时间',
    `create_user`    bigint      NULL     DEFAULT NULL COMMENT '创建人',
    `update_time`    datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_user`    bigint      NULL     DEFAULT NULL COMMENT '更新人',
    `del_flag`       char(1)     NOT NULL DEFAULT 'N' COMMENT '删除标记',
    `tenant_id`      bigint      NULL     DEFAULT NULL COMMENT '租户编号',
    PRIMARY KEY (`notice_id`)
) COMMENT = '通知公告表';
