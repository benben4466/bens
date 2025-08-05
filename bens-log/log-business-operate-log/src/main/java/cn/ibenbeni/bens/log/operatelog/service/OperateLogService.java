package cn.ibenbeni.bens.log.operatelog.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.log.api.OperateLogServiceApi;
import cn.ibenbeni.bens.log.operatelog.entity.OperateLogDO;
import cn.ibenbeni.bens.log.operatelog.pojo.request.OperateLogPageReq;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 操作日志-服务类
 */
public interface OperateLogService extends IService<OperateLogDO>, OperateLogServiceApi {

    /**
     * 获得操作日志分页列表
     */
    PageResult<OperateLogDO> getOperateLogPage(OperateLogPageReq req);

}
