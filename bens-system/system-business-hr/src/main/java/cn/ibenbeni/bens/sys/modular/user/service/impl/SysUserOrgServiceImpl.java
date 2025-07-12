package cn.ibenbeni.bens.sys.modular.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.enums.YesOrNotEnum;
import cn.ibenbeni.bens.sys.api.exception.SysException;
import cn.ibenbeni.bens.sys.api.exception.enums.SysUserOrgExceptionEnum;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserOrgDO;
import cn.ibenbeni.bens.sys.modular.user.mapper.SysUserOrgMapper;
import cn.ibenbeni.bens.sys.modular.user.pojo.request.org.UserOrgSaveReq;
import cn.ibenbeni.bens.sys.modular.user.service.SysUserOrgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 用户组织机构关联 服务实现类
 *
 * @author: benben
 * @time: 2025/7/8 下午10:02
 */
@Slf4j
@Service
public class SysUserOrgServiceImpl extends ServiceImpl<SysUserOrgMapper, SysUserOrgDO> implements SysUserOrgService {

    // region 属性

    @Resource
    private SysUserOrgMapper sysUserOrgMapper;

    // endregion

    // region 公共方法

    @Override
    public Long createUserOrg(UserOrgSaveReq req) {
        // 校验组织关系
        this.validateUserOrgForCreateOrUpdate(req.getUserId(), CollUtil.toList(req));

        SysUserOrgDO userOrg = BeanUtil.toBean(req, SysUserOrgDO.class);
        this.save(userOrg);

        return userOrg.getUserOrgId();
    }

    @Override
    public void deleteUserOrg(Long id) {
        SysUserOrgDO userOrgDO = this.validateUserOrgExist(id);
        this.removeById(userOrgDO.getUserOrgId());
    }

    @Override
    public void deleteUserOrgList(List<Long> idList) {
        this.removeByIds(idList);
    }

    @Override
    public void updateUserOrg(UserOrgSaveReq req) {
        // 校验组织关系
        this.validateUserOrgForCreateOrUpdate(req.getUserId(), CollUtil.toList(req));

        SysUserOrgDO userOrgDO = this.getById(req.getUserOrgId());
        BeanUtil.copyProperties(req, userOrgDO);
        this.updateById(userOrgDO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserOrg(Long userId, List<SysUserOrgDO> userOrgList) {
        List<SysUserOrgDO> newUserOrgList = this.validateUserOrgParam(userId, userOrgList);

        // 删除旧用户组织关系
        sysUserOrgMapper.deleteByUserIds(CollUtil.set(false, userId));

        // 绑定新用户组织关系
        this.saveBatch(newUserOrgList);
    }

    // endregion

    // region 删除信息回调

    @Override
    public void validateHaveOrgBind(Set<Long> beRemovedOrgIdList) {
    }

    @Override
    public void removeOrgAction(Set<Long> beRemovedOrgIdList) {
        sysUserOrgMapper.deleteByOrgIds(beRemovedOrgIdList);
    }

    @Override
    public void validateHaveUserBind(Set<Long> beRemovedUserIdList) {
    }

    @Override
    public void removeUserAction(Set<Long> beRemovedUserIdList) {
        sysUserOrgMapper.deleteByUserIds(beRemovedUserIdList);
    }

    // endregion

    // region 私有方法

    /**
     * 新增/修改时用户组织关系
     *
     * @param userId 用户ID
     * @param list   用户组织关系
     */
    private void validateUserOrgForCreateOrUpdate(Long userId, List<UserOrgSaveReq> list) {
        if (CollUtil.isEmpty(list)) {
            throw new SysException(SysUserOrgExceptionEnum.USER_ORG_EMPTY);
        }

        for (UserOrgSaveReq userOrgReq : list) {
            // 1.判断添加的组织关系是否为主组织
            if (YesOrNotEnum.Y.getCode().equals(userOrgReq.getMainFlag())) {
                Long mainOrgCount = sysUserOrgMapper.getMainOrgCount(userId, userOrgReq.getUserOrgId());
                if (mainOrgCount > 0) {
                    throw new SysException(SysUserOrgExceptionEnum.MAIN_FLAG_ERROR);
                }
            }
            // 2.判断用户在组织内是否已添加该职位
            if (sysUserOrgMapper.userPositionExisted(userId, userOrgReq.getPositionId())) {
                throw new SysException(SysUserOrgExceptionEnum.USER_POSITION_EXISTED);
            }

            // 3.TODO 需要限制组织内只能任职一个岗位吗？
        }
    }

    private SysUserOrgDO validateUserOrgExist(Long userOrgId) {
        SysUserOrgDO userOrgDO = sysUserOrgMapper.selectById(userOrgId);
        if (userOrgDO == null) {
            throw new SysException(SysUserOrgExceptionEnum.USER_ORG_NOT_EXIST);
        }
        return userOrgDO;
    }

    /**
     * 校验用户组织关系参数
     *
     * @param userId      用户ID
     * @param userOrgList 用户组织关系
     * @return 用户组织关系集合
     */
    private List<SysUserOrgDO> validateUserOrgParam(Long userId, List<SysUserOrgDO> userOrgList) {
        long mainFlagCount = 0;
        for (SysUserOrgDO userOrgDO : userOrgList) {
            // 校验参数是否缺失
            if (ObjectUtil.hasEmpty(userOrgDO.getOrgId(), userOrgDO.getPositionId(), userOrgDO.getMainFlag())) {
                throw new SysException(SysUserOrgExceptionEnum.PARAM_MISSING);
            }

            // 统计主组织数量
            if (YesOrNotEnum.Y.getCode().equals(userOrgDO.getMainFlag())) {
                mainFlagCount++;
            }

            // 绑定用户ID, 防止外部误传
            userOrgDO.setUserId(userId);
            // 默认启用
            if (userOrgDO.getStatusFlag() == null) {
                userOrgDO.setStatusFlag(StatusEnum.ENABLE.getCode());
            }
        }

        if (mainFlagCount > 1 || mainFlagCount == 0) {
            throw new SysException(SysUserOrgExceptionEnum.MAIN_FLAG_ERROR);
        }

        return userOrgList;
    }

    // endregion

}
