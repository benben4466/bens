package cn.ibenbeni.bens.ip.sdk.util;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.ibenbeni.bens.ip.enums.AreaTypeEnum;
import cn.ibenbeni.bens.ip.sdk.Area;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区域工具类
 */
@Slf4j
public class AreaUtils {

    private final static AreaUtils INSTANCE = new AreaUtils();

    /**
     * 区域数据缓存
     */
    private static Map<String, Area> areas;

    private AreaUtils() {
        long startTime = System.currentTimeMillis();
        areas = new HashMap<>();
        areas.put(Area.ID_GLOBAL, new Area(Area.ID_GLOBAL, "全球", AreaTypeEnum.GLOBAL.getAreaType(), null, new ArrayList<>()));

        // 从CSV加载数据
        List<CsvRow> rows = CsvUtil.getReader().read(ResourceUtil.getUtf8Reader("area.csv")).getRows();
        rows.remove(0); // 删除标题行
        for (CsvRow row : rows) {
            Area area = Area.builder()
                    .areaCode(row.get(0).trim())
                    .areaName(row.get(1).trim())
                    .areaType(Integer.valueOf(row.get(2).trim()))
                    .parent(null)
                    .children(new ArrayList<>())
                    .build();
            areas.put(area.getAreaCode(), area);
        }

        // 构建父子关系
        for (CsvRow row : rows) {
            Area area = areas.get(row.get(0).trim());  // 自身
            Area parent = areas.get(row.get(3).trim()); // 父级
            Assert.isTrue(area != parent, "{}:父子节点相同", area.getAreaName());
            area.setParent(parent);
            parent.getChildren().add(area);
        }
        long endTime = System.currentTimeMillis();
        log.info("启动加载区域数据完成,耗时: {}毫秒", endTime - startTime);
    }

    /**
     * 获取区域
     *
     * @param areaCode 区域编号
     * @return 区域
     */
    public static Area getArea(String areaCode) {
        return areas.get(areaCode);
    }

}
