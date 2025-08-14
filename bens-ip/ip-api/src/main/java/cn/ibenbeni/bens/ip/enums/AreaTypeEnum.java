package cn.ibenbeni.bens.ip.enums;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AreaTypeEnum implements ReadableEnum<AreaTypeEnum> {

    GLOBAL(0, "全球"),
    COUNTRY(1, "国家"),
    PROVINCE(2, "省份"),
    CITY(3, "城市"),
    DISTRICT(4, "地区"),

    ;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(AreaTypeEnum::getAreaType).toArray(Integer[]::new);

    /**
     * 区域类型
     */
    private final Integer areaType;

    /**
     * 区域名称
     */
    private final String areaName;

    @Override
    public Object getKey() {
        return areaType;
    }

    @Override
    public Object getName() {
        return areaName;
    }

    @Override
    public AreaTypeEnum parseToEnum(String areaType) {
        if (StrUtil.isBlank(areaType)) {
            return null;
        }
        return ArrayUtil.firstMatch(item -> item.areaType.equals(Integer.parseInt(areaType)), values());
    }

    @Override
    public Object[] compareValueArray() {
        return ARRAYS;
    }

}
