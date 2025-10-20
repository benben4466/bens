package cn.ibenbeni.bens.iot.modular.base.service.device.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.iot.api.exception.IotException;
import cn.ibenbeni.bens.iot.api.exception.enums.IotExceptionEnum;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotGroupDO;
import cn.ibenbeni.bens.iot.modular.base.mapper.device.mysql.IotGroupMapper;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotGroupPageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotGroupSaveReq;
import cn.ibenbeni.bens.iot.modular.base.service.device.IotGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * IOT设备分组-服务实现类
 */
@Slf4j
@Service
public class IotGroupServiceImpl implements IotGroupService {

    @Resource
    private IotGroupMapper groupMapper;

    // region 公共方法

    @Override
    public Long createGroup(IotGroupSaveReq saveReq) {
        IotGroupDO iotGroup = BeanUtil.toBean(saveReq, IotGroupDO.class);
        groupMapper.insert(iotGroup);
        return iotGroup.getGroupId();
    }

    @Override
    public void deleteGroup(Long groupId) {
        // 校验是否存在
        validateDeviceGroupExists(groupId);
        groupMapper.deleteById(groupId);
    }

    @Override
    public void deleteGroup(Set<Long> groupIdSet) {
        groupMapper.deleteByIds(groupIdSet);

    }

    @Override
    public void updateGroup(IotGroupSaveReq updateReq) {
        // 校验是否存在
        validateDeviceGroupExists(updateReq.getGroupId());

        IotGroupDO updateIotGroup = BeanUtil.toBean(updateReq, IotGroupDO.class);
        groupMapper.updateById(updateIotGroup);
    }

    @Override
    public IotGroupDO getGroup(Long groupId) {
        return groupMapper.selectById(groupId);
    }

    @Override
    public PageResult<IotGroupDO> pageGroup(IotGroupPageReq pageReq) {
        return groupMapper.pageGroup(pageReq);
    }
    // endregion


    // region 私有方法

    private void validateDeviceGroupExists(Long groupId) {
        if (groupMapper.selectById(groupId) == null) {
            throw new IotException(IotExceptionEnum.DEVICE_GROUP_NOT_EXISTED);
        }
    }

    // endregion

}
