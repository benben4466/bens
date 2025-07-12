package cn.ibenbeni.bens.sys.api.callback;

import java.util.Set;

/**
 * 删掉职位-回调方法
 *
 * @author: benben
 * @time: 2025/7/12 下午2:03
 */
public interface RemovePositionCallbackApi {

    /**
     * 校验是否绑定了职位相关的信息，如果绑定了则报错
     */
    void validateHavePositionBind(Set<Long> beRemovedPositionIdList);

    /**
     * 删除职位后的回调
     */
    void removePositionAction(Set<Long> beRemovedPositionIdList);

}
