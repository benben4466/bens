package cn.ibenbeni.bens.iot.modular.base.mapper.device.mysql;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotGroupDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotGroupPageReq;

/**
 * IOT设备分组-Mapper
 */
public interface IotGroupMapper extends BaseMapperX<IotGroupDO> {

    default PageResult<IotGroupDO> pageGroup(IotGroupPageReq pageReq) {
        return selectPage(pageReq, new LambdaQueryWrapperX<IotGroupDO>()
                .likeIfPresent(IotGroupDO::getGroupName, pageReq.getGroupName())
                .orderByDesc(IotGroupDO::getGroupOrder)
                .orderByDesc(IotGroupDO::getGroupId)
        );
    }

}
