package cn.ibenbeni.bens.rule.tree.buildpids;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.rule.constants.PidBuildConstants;
import cn.ibenbeni.bens.rule.constants.SymbolConstant;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 构建pids结构的工具，自动填充到参数中pids
 *
 * @author: benben
 * @time: 2025/6/1 下午4:24
 */
public final class PidStructureBuildUtil {

    /**
     * 构造pids结构
     *
     * @param list
     * @param <T>
     */
    public static <T extends BasePidBuildModel> void createPidStructure(List<T> list) {
        if (CollUtil.isEmpty(list)) {
            return;
        }

        // 保存每一个数据的pids路径（成品），key是节点ID，value是pids路径字符串
        Map<String, String> routeMapMap = new HashMap<>();

        // 暂时未找到父级的pids的对象，key是节点id，value是实体对象
        Map<String, T> waitMap = new HashMap<>();

        for (T itemModel : list) {
            String pids = "";
            String parentId = itemModel.pidBuildParentId();

            // 父ID为空 或为顶级节点
            if (StrUtil.isBlank(parentId) || PidBuildConstants.TOP_FLAG.equals(parentId)) {
                pids = PidBuildConstants.TOP_PIDS;
                itemModel.setPidBuildPidStructure(pids);
                routeMapMap.put(itemModel.pidBuildNodeId(), pids);
                continue;
            }

            // 父ID非顶级节点
            if (routeMapMap.containsKey(parentId)) {
                pids = routeMapMap.get(parentId) + SymbolConstant.LEFT_SQUARE_BRACKETS + parentId + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA;
                itemModel.setPidBuildPidStructure(pids);
                routeMapMap.put(itemModel.pidBuildNodeId(), pids);
            } else {
                waitMap.put(itemModel.pidBuildNodeId(), itemModel);
            }
        }

        int size = waitMap.size();
        while (size != 0) {
            Iterator<Map.Entry<String, T>> iterator = waitMap.entrySet().iterator();
            while (iterator.hasNext()) {
                T waitProcessItem = iterator.next().getValue();

                // 再次寻找父级的pids
                if (!routeMapMap.containsKey(waitProcessItem.pidBuildParentId())) {
                    continue;
                }

                // 父级的pids有了，组装自己的pids
                String pids = routeMapMap.get(waitProcessItem.pidBuildParentId()) + SymbolConstant.LEFT_SQUARE_BRACKETS + waitProcessItem.pidBuildParentId() + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA;
                waitProcessItem.setPidBuildPidStructure(pids);

                // 放入map，以便它的下级在回溯的时候能够找到它
                routeMapMap.put(waitProcessItem.pidBuildNodeId(), pids);

                size--;
                iterator.remove();
            }
        }
    }

}
