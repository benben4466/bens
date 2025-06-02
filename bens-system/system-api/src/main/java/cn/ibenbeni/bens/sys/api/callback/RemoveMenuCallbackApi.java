package cn.ibenbeni.bens.sys.api.callback;

import java.util.Set;

/**
 * 删除菜单的回调方法
 *
 * @author: benben
 * @time: 2025/6/1 下午2:11
 */
public interface RemoveMenuCallbackApi {

    /**
     * 删除菜单关联的业务绑定操作
     */
    void removeMenuAction(Set<Long> beRemovedMenuIdList);

}
