package cn.ibenbeni.bens.log.api;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.log.api.pojo.dto.request.OperateLogCreateReqDTO;
import cn.ibenbeni.bens.log.api.pojo.dto.request.OperateLogPageReqDTO;
import cn.ibenbeni.bens.log.api.pojo.dto.response.OperateLogRespDTO;
import org.springframework.scheduling.annotation.Async;

import javax.validation.Valid;

/**
 * 操作日志服务API
 */
public interface OperateLogServiceApi {

    /**
     * 记录操作日志
     */
    void createOperateLog(@Valid OperateLogCreateReqDTO reqDTO);

    /**
     * 获取操作日志分页列表
     */
    PageResult<OperateLogRespDTO> getOperateLogPage(OperateLogPageReqDTO reqDTO);

    /**
     * 异步记录操作日志
     */
    @Async
    default void createOperateLogAsync(@Valid OperateLogCreateReqDTO reqDTO) {
        createOperateLog(reqDTO);
    }

}
