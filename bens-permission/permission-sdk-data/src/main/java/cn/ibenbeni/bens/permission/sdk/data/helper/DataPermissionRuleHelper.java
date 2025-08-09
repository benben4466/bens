package cn.ibenbeni.bens.permission.sdk.data.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.ibenbeni.bens.permission.sdk.data.annotation.DataPermission;
import cn.ibenbeni.bens.permission.sdk.data.context.DataPermissionContextHolder;
import cn.ibenbeni.bens.permission.sdk.data.rule.DataPermissionRule;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据权限规则助手类
 * <p>
 * 1.提供{@link DataPermissionRule}的容器，提供管理能力
 * </p>
 */
@RequiredArgsConstructor
public class DataPermissionRuleHelper {

    /**
     * 数据权限规则容器
     */
    private final List<DataPermissionRule> rules;

    /**
     * 获得所有数据权限规则数组
     */
    public List<DataPermissionRule> getDataPermissionRules() {
        return rules;
    }

    /**
     * 获得指定Mapper方法对应的数据权限规则数组
     *
     * @param mappedStatementId 指定Mapper的编号
     */
    public List<DataPermissionRule> getDataPermissionRule(String mappedStatementId) {
        // 无数据权限
        if (CollUtil.isEmpty(rules)) {
            return Collections.emptyList();
        }

        // 获取当前使用的数据权限注解，默认开启
        DataPermission dataPermission = DataPermissionContextHolder.get();
        if (dataPermission == null) {
            return rules;
        }

        // 已配置，但禁用
        if (!dataPermission.enable()) {
            return Collections.emptyList();
        }

        // 已配置，只选择部分规则
        if (ArrayUtil.isNotEmpty(dataPermission.includeRules())) {
            return rules.stream()
                    .filter(rule -> ArrayUtil.contains(dataPermission.includeRules(), rule.getClass()))
                    .collect(Collectors.toList());
        }

        // 已配置，排除部分规则
        if (ArrayUtil.isNotEmpty(dataPermission.excludeRules())) {
            return rules.stream()
                    .filter(rule -> !ArrayUtil.contains(dataPermission.excludeRules(), rule.getClass()))
                    .collect(Collectors.toList());
        }

        return rules;
    }

}
