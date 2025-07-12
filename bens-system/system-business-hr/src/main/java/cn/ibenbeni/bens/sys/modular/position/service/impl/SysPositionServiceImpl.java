package cn.ibenbeni.bens.sys.modular.position.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.util.CollectionUtils;
import cn.ibenbeni.bens.sys.api.exception.SysException;
import cn.ibenbeni.bens.sys.api.exception.enums.PositionExceptionEnum;
import cn.ibenbeni.bens.sys.modular.position.entity.SysPositionDO;
import cn.ibenbeni.bens.sys.modular.position.mapper.SysPositionMapper;
import cn.ibenbeni.bens.sys.modular.position.pojo.request.PositionPageReq;
import cn.ibenbeni.bens.sys.modular.position.pojo.request.PositionSaveReq;
import cn.ibenbeni.bens.sys.modular.position.service.SysPositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 职位信息-服务实现类
 *
 * @author: benben
 * @time: 2025/7/12 下午1:38
 */
@Slf4j
@Service
public class SysPositionServiceImpl extends ServiceImpl<SysPositionMapper, SysPositionDO> implements SysPositionService {

    // region 属性

    @Resource
    private SysPositionMapper sysPositionMapper;

    // endregion

    // region 公共方法
    @Override
    public Long createPosition(PositionSaveReq req) {
        // 校验职位信息
        this.validatePositionForCreateOrUpdate(null, req.getPositionName(), req.getPositionCode());

        SysPositionDO position = BeanUtil.toBean(req, SysPositionDO.class);
        this.save(position);
        return position.getPositionId();
    }

    @Override
    public void deletePosition(Long positionId) {
        // 校验职位是否存在
        this.validatePositionExist(positionId);

        this.removeById(positionId);
    }

    @Override
    public void deletePositionList(Set<Long> positionIdSet) {
        this.removeByIds(positionIdSet);
    }

    @Override
    public void updatePosition(PositionSaveReq req) {
        // 校验职位信息
        this.validatePositionForCreateOrUpdate(req.getPositionId(), req.getPositionName(), req.getPositionCode());

        SysPositionDO position = this.getById(req.getPositionId());
        BeanUtil.copyProperties(req, position);
        this.updateById(position);
    }

    @Override
    public SysPositionDO getPosition(Long positionId) {
        return this.getById(positionId);
    }

    @Override
    public List<SysPositionDO> getPositionList(Set<Long> positionIds) {
        if (CollUtil.isEmpty(positionIds)) {
            return new ArrayList<>();
        }
        return this.listByIds(positionIds);
    }

    @Override
    public List<SysPositionDO> getPositionList(Set<Long> positionIds, Set<Integer> statusFlags) {
        return sysPositionMapper.selectList(positionIds, statusFlags);
    }

    @Override
    public PageResult<SysPositionDO> getPositionPage(PositionPageReq req) {
        return sysPositionMapper.selectPage(req);
    }

    @Override
    public void validatePositionList(Set<Long> positionIdSet) {
        if (CollUtil.isEmpty(positionIdSet)) {
            return;
        }

        List<SysPositionDO> list = this.listByIds(positionIdSet);
        Map<Long, SysPositionDO> positionIdMap = CollectionUtils.convertMap(list, SysPositionDO::getPositionId);
        for (Long positionId : positionIdSet) {
            SysPositionDO position = positionIdMap.get(positionId);
            // 职位不存在
            if (position == null) {
                throw new SysException(PositionExceptionEnum.POSITION_NOT_EXISTED);
            }
            // 职位被禁用
            if (StatusEnum.DISABLE.getCode().equals(position.getStatusFlag())) {
                throw new SysException(PositionExceptionEnum.POSITION_DISABLE);
            }
        }
    }
    // endregion


    // region 私有方法

    private void validatePositionExist(Long positionId) {
        if (this.getById(positionId) == null) {
            throw new SysException(PositionExceptionEnum.POSITION_NOT_EXISTED);
        }
    }

    private void validatePositionNameUnique(Long positionId, String positionName) {
        if (positionId == null) {
            throw new SysException(PositionExceptionEnum.POSITION_NAME_DUPLICATE);
        }

        SysPositionDO position = sysPositionMapper.selectByName(positionName);
        if (position == null) {
            return;
        }

        // 职位ID不同，说明职位已存在
        if (ObjectUtil.notEqual(positionId, position.getPositionId())) {
            throw new SysException(PositionExceptionEnum.POSITION_NAME_DUPLICATE);
        }
    }

    private void validatePositionCodeUnique(Long positionId, String positionCode) {
        if (positionId == null) {
            throw new SysException(PositionExceptionEnum.POSITION_CODE_DUPLICATE);
        }

        SysPositionDO position = sysPositionMapper.selectByCode(positionCode);
        if (position == null) {
            return;
        }

        // 职位ID不同，说明职位已存在
        if (ObjectUtil.notEqual(positionId, position.getPositionId())) {
            throw new SysException(PositionExceptionEnum.POSITION_CODE_DUPLICATE);
        }
    }

    /**
     * 校验职位信息
     *
     * @param positionId   职位ID
     * @param positionName 职位名称
     * @param positionCode 职位编码
     */
    private void validatePositionForCreateOrUpdate(Long positionId, String positionName, String positionCode) {
        // 校验职位是否存在（更新时校验）
        if (positionId != null) {
            this.validatePositionExist(positionId);
        }
        // 校验职位名称唯一
        this.validatePositionNameUnique(positionId, positionName);
        // 校验职位编码唯一
        this.validatePositionCodeUnique(positionId, positionCode);
    }


    // endregion

}
