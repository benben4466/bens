package cn.ibenbeni.bens.log.operatelog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.log.api.pojo.dto.request.OperateLogCreateReqDTO;
import cn.ibenbeni.bens.log.api.pojo.dto.request.OperateLogPageReqDTO;
import cn.ibenbeni.bens.log.api.pojo.dto.response.OperateLogRespDTO;
import cn.ibenbeni.bens.log.operatelog.entity.OperateLogDO;
import cn.ibenbeni.bens.log.operatelog.mapper.OperateLogMapper;
import cn.ibenbeni.bens.log.operatelog.pojo.request.OperateLogPageReq;
import cn.ibenbeni.bens.log.operatelog.service.OperateLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 操作日志-服务实现类
 */
@Service
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLogDO> implements OperateLogService {

    // region 属性

    @Resource
    private OperateLogMapper operateLogMapper;

    // endregion

    // region 公共方法

    @Override
    public void createOperateLog(OperateLogCreateReqDTO reqDTO) {
        OperateLogDO operateLog = BeanUtil.toBean(reqDTO, OperateLogDO.class);
        save(operateLog);
    }

    @Override
    public PageResult<OperateLogDO> getOperateLogPage(OperateLogPageReq req) {
        return operateLogMapper.selectPage(req);
    }

    @Override
    public PageResult<OperateLogRespDTO> getOperateLogPage(OperateLogPageReqDTO reqDTO) {
        PageResult<OperateLogDO> operateLogPage = operateLogMapper.selectPage(reqDTO);
        return DbUtil.toBean(operateLogPage, OperateLogRespDTO.class);
    }

    // endregion

    // region 私有方法
    // endregion

}
