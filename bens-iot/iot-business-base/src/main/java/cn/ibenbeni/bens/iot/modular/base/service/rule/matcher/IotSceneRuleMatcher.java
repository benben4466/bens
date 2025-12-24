package cn.ibenbeni.bens.iot.modular.base.service.rule.matcher;

/**
 * IOT-场景规则匹配器
 * <p>定义所有匹配器的通用功能</p>
 */
public interface IotSceneRuleMatcher {

    /**
     * 获取匹配优先级
     * <p>数值越小，优先级越高</p>
     * <p>作用：用于在多个匹配器支持同一类型时，确定优先级</p>
     */
    default int getPriority() {
        return 100;
    }

    /**
     * 是否启用匹配器
     * <p>默认启用</p>
     * <p>作用：用于在动态配置中，临时禁用匹配器</p>
     */
    default boolean isEnabled() {
        return true;
    }

}
