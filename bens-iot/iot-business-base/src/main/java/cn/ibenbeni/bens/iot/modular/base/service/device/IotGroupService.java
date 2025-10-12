package cn.ibenbeni.bens.iot.modular.base.service.device;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotGroupDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotGroupPageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotGroupSaveReq;

import java.util.Set;

/**
 * IOT设备分组-服务
 */
public interface IotGroupService {

    /**
     * 创建设备分组
     *
     * @return 设备分组ID
     */
    Long createGroup(IotGroupSaveReq saveReq);

    /**
     * 删除设备分组
     *
     * @param groupId 设备分组ID
     */
    void deleteGroup(Long groupId);

    /**
     * 批量删除设备分组
     *
     * @param groupIdSet 设备分组ID集合
     */
    void deleteGroup(Set<Long> groupIdSet);

    /**
     * 更新设备分组
     */
    void updateGroup(IotGroupSaveReq updateReq);

    /**
     * 获取设备分组信息
     *
     * @param groupId 设备分组ID
     * @return 设备分组信息
     */
    IotGroupDO getGroup(Long groupId);

    /**
     * 获取设备分组信息分页列表
     */
    PageResult<IotGroupDO> pageGroup(IotGroupPageReq pageReq);

}
