package cn.ibenbeni.bens.iot.modular.base.mapper.device.tdengine;

import cn.ibenbeni.bens.iot.api.core.tdengine.annotation.TDengineDS;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceMessageDO;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;

/**
 * 设备消息 {@link IotDeviceMessageDO} Mapper 接口
 */
@TDengineDS
@InterceptorIgnore(tenantLine = "true") // 避免 SQL 解析，因为 JSqlParser 对 TDengine 的 SQL 解析会报错
public interface IotDeviceMessageMapper {

    /**
     * 创建设备消息超级表
     */
    void createSTable();

    /**
     * 插入设备消息
     */
    void insert(IotDeviceMessageDO message);

    /**
     * 查询设备消息表是否存在
     *
     * @return 存在=返回表名；不存在=返回null；
     */
    String showSTable();

}
