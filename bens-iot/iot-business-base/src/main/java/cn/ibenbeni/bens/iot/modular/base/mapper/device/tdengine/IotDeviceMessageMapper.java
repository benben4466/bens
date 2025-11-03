package cn.ibenbeni.bens.iot.modular.base.mapper.device.tdengine;

import cn.ibenbeni.bens.iot.api.core.tdengine.annotation.TDengineDS;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceMessageDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotDeviceMessagePageReq;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 分页查询设备消息
     *
     * @param page     分页参数
     * @param pageReq  查询参数
     * @param qStartTs 查询开始时间戳（来自于：pageReq的times[0]）
     * @param qEndTs   查询结束时间戳（来自于：pageReq的times[1]）
     * @return 设备消息分页数据
     */
    IPage<IotDeviceMessageDO> selectPage(IPage<IotDeviceMessageDO> page, @Param("pageReq") IotDeviceMessagePageReq pageReq, @Param("qStartTs") Long qStartTs, @Param("qEndTs") Long qEndTs);

}
