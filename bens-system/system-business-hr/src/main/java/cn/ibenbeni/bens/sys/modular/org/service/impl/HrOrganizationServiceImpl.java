package cn.ibenbeni.bens.sys.modular.org.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.constants.TreeConstants;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.util.CollectionUtils;
import cn.ibenbeni.bens.sys.api.callback.RemoveOrgCallbackApi;
import cn.ibenbeni.bens.sys.api.exception.SysException;
import cn.ibenbeni.bens.sys.api.exception.enums.OrganizationExceptionEnum;
import cn.ibenbeni.bens.sys.modular.org.entity.HrOrganizationDO;
import cn.ibenbeni.bens.sys.modular.org.mapper.HrOrganizationMapper;
import cn.ibenbeni.bens.sys.modular.org.pojo.request.OrgPageReq;
import cn.ibenbeni.bens.sys.modular.org.pojo.request.OrganizationSaveReq;
import cn.ibenbeni.bens.sys.modular.org.pojo.request.OrgListReq;
import cn.ibenbeni.bens.sys.modular.org.service.HrOrganizationService;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 组织机构信息业务实现层
 *
 * @author benben
 * @date 2025/5/27  下午9:08
 */
@Service
public class HrOrganizationServiceImpl extends ServiceImpl<HrOrganizationMapper, HrOrganizationDO> implements HrOrganizationService {

    @Resource
    private HrOrganizationMapper hrOrganizationMapper;

    // region 公共方法

    @Override
    public Long createOrg(OrganizationSaveReq createReqVO) {
        // 默认填充根节点
        if (createReqVO.getOrgParentId() == null) {
            createReqVO.setOrgParentId(TreeConstants.DEFAULT_PARENT_ID);
        }

        // 校验父组织
        this.validateParentOrg(null, createReqVO.getOrgParentId());
        // 校验组织名称唯一
        this.validateOrgNameUnique(null, createReqVO.getOrgName());
        // 校验组织编码唯一
        this.validateOrgCodeUnique(null, createReqVO.getOrgCode());

        HrOrganizationDO org = BeanUtil.toBean(createReqVO, HrOrganizationDO.class);
        this.save(org);
        return org.getOrgId();
    }

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void deleteOrg(Long orgId) {
        // 校验组织是否存在
        this.validateOrgExists(orgId);
        // 校验是否存在子组织
        Long childOrgCount = hrOrganizationMapper.getChildOrgCount(orgId);
        if (childOrgCount > 0) {
            throw new SysException(OrganizationExceptionEnum.ORG_EXITS_CHILDREN);
        }

        this.baseDelete(CollUtil.set(false, orgId));
    }

    @Override
    public void updateOrg(OrganizationSaveReq updateReqVO) {
        HrOrganizationDO orgDO = this.validateOrgExists(updateReqVO.getOrgId());
        // 默认填充根节点
        if (updateReqVO.getOrgParentId() == null) {
            updateReqVO.setOrgParentId(TreeConstants.DEFAULT_PARENT_ID);
        }

        // 校验父组织
        this.validateParentOrg(updateReqVO.getOrgId(), updateReqVO.getOrgParentId());
        // 校验组织名称唯一
        this.validateOrgNameUnique(updateReqVO.getOrgId(), updateReqVO.getOrgName());
        // 校验组织编码唯一
        this.validateOrgCodeUnique(updateReqVO.getOrgId(), updateReqVO.getOrgCode());

        BeanUtil.copyProperties(updateReqVO, orgDO);
        this.updateById(orgDO);
    }

    @Override
    public HrOrganizationDO getOrg(Long orgId) {
        return this.getById(orgId);
    }

    @Override
    public List<HrOrganizationDO> getOrgList(Collection<Long> orgIds) {
        if (CollUtil.isEmpty(orgIds)) {
            return Collections.emptyList();
        }
        return this.listByIds(orgIds);
    }

    @Override
    public List<HrOrganizationDO> getOrgList(OrgListReq reqVO) {
        return hrOrganizationMapper.selectList(reqVO);
    }

    @Override
    public List<HrOrganizationDO> getChildOrgList(Collection<Long> ids) {
        List<HrOrganizationDO> children = new ArrayList<>();

        Collection<Long> parentIds = ids;
        for (int i = 0; i < Short.MAX_VALUE; i++) { // 使用 Short.MAX_VALUE 避免 bug 场景下，存在死循环
            // 查询当前层，所有的子组织
            List<HrOrganizationDO> orgs = hrOrganizationMapper.selectChildList(parentIds);
            // 若没有子组织，则结束循环、
            if (CollUtil.isEmpty(orgs)) {
                break;
            }
            children.addAll(orgs);

            // 若存在子组织，则进行下一层查询
            parentIds = CollectionUtils.convertSet(orgs, HrOrganizationDO::getOrgId);
        }

        return children;
    }

    @Override
    public Set<Long> getChildOrgIdListFromCache(Long orgId) {
        List<HrOrganizationDO> children = this.getChildOrgList(orgId);
        return CollectionUtils.convertSet(children, HrOrganizationDO::getOrgId);
    }

    @Override
    public PageResult<HrOrganizationDO> getOrgPage(OrgPageReq reqVO) {
        return hrOrganizationMapper.selectPage(reqVO);
    }

    @Override
    public void validateOrgList(Collection<Long> orgIds) {
        if (CollUtil.isEmpty(orgIds)) {
            return;
        }

        Map<Long, HrOrganizationDO> orgMap = this.getOrgMap(orgIds);
        for (Long orgId : orgIds) {
            HrOrganizationDO org = orgMap.get(orgId);
            // 校验组织是否存在
            if (org == null) {
                throw new SysException(OrganizationExceptionEnum.ORG_NOT_EXISTED);
            }

            // 校验禁用状态
            if (StatusEnum.DISABLE.getCode().equals(org.getStatusFlag())) {
                throw new SysException(OrganizationExceptionEnum.ORG_NOT_EXISTED, org.getOrgName());
            }
        }
    }

    @Override
    public Set<Long> listChildDeptId(Set<Long> deptIdSet) {
        List<HrOrganizationDO> childOrgList = getChildOrgList(deptIdSet);
        return CollectionUtils.convertSet(childOrgList, HrOrganizationDO::getOrgId);
    }

    // endregion

    // region 私有方法

    /**
     * 校验父部门
     *
     * @param orgId       组织ID
     * @param parentOrgId 父组织ID
     */
    private void validateParentOrg(Long orgId, Long parentOrgId) {
        // 根节点不用校验，默认为根节点
        if (parentOrgId == null || TreeConstants.DEFAULT_PARENT_ID.equals(parentOrgId)) {
            return;
        }

        // 1.不能设置自己为父部门
        if (ObjectUtil.equal(orgId, parentOrgId)) {
            throw new SysException(OrganizationExceptionEnum.ORG_PARENT_ERROR);
        }

        // 2.父部门不存在
        HrOrganizationDO parentOrg = hrOrganizationMapper.selectById(parentOrgId);
        if (parentOrg == null) {
            throw new SysException(OrganizationExceptionEnum.ORG_NOT_EXISTED);
        }

        // 3.校验父部门是否为自己的子部门
        if (orgId == null) {
            // orgId为空是新增请求，不用校验
            return;
        }

        // TODO 可提供方法直接查询parentOrgId的所有父上级
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 校验是否存在环路
            parentOrgId = parentOrg.getOrgParentId();
            if (ObjectUtil.equal(orgId, parentOrgId)) {
                throw new SysException(OrganizationExceptionEnum.ORG_PARENT_IS_CHILD);
            }

            // 继续查找下一个组织
            if (parentOrgId == null || TreeConstants.DEFAULT_PARENT_ID.equals(parentOrgId)) {
                break;
            }
            parentOrg = hrOrganizationMapper.selectById(parentOrgId);
            if (parentOrg == null) {
                break;
            }
        }

    }

    /**
     * 校验组织名称唯一
     *
     * @param orgId   组织ID
     * @param orgName 组织名称
     */
    private void validateOrgNameUnique(Long orgId, String orgName) {
        HrOrganizationDO orgDO = hrOrganizationMapper.selectByOrgName(orgName);
        if (orgDO != null && !orgDO.getOrgId().equals(orgId)) {
            throw new SysException(OrganizationExceptionEnum.ORG_NAME_DUPLICATE);
        }
    }

    /**
     * 校验组织编码唯一
     *
     * @param orgId   组织ID
     * @param orgCode 组织编码
     */
    private void validateOrgCodeUnique(Long orgId, String orgCode) {
        HrOrganizationDO orgDO = hrOrganizationMapper.selectByOrgCode(orgCode);
        if (orgDO == null) {
            return;
        }

        // 1.orgId为空是创建请求，若存在orgDO则说明已存在组织
        // 2.orgId非空是修改请求，若存在orgDO则说明已存在组织，需验证ID是否一致
        if (orgId == null || ObjectUtil.notEqual(orgId, orgDO.getOrgId())) {
            throw new SysException(OrganizationExceptionEnum.ORG_CODE_DUPLICATE);
        }
    }

    /**
     * 校验组织存在
     *
     * @param orgId 组织ID
     */
    private HrOrganizationDO validateOrgExists(Long orgId) {
        if (orgId == null) {
            return null;
        }
        HrOrganizationDO org = this.getById(orgId);
        if (org == null) {
            throw new SysException(OrganizationExceptionEnum.ORG_NOT_EXISTED);
        }

        return org;
    }

    /**
     * 批量删除组织机构
     */
    private void baseDelete(Set<Long> orgIdSet) {
        Map<String, RemoveOrgCallbackApi> removeOrgCallbackApiMap = SpringUtil.getBeansOfType(RemoveOrgCallbackApi.class);
        // 校验是否有其他业务绑定了组织机构信息
        for (RemoveOrgCallbackApi removeOrgCallbackApi : removeOrgCallbackApiMap.values()) {
            removeOrgCallbackApi.validateHaveOrgBind(orgIdSet);
        }

        // 删除组织机构
        this.removeBatchByIds(orgIdSet);

        // 联动删除所有和本组织机构相关其他业务数据
        for (RemoveOrgCallbackApi removeOrgCallbackApi : removeOrgCallbackApiMap.values()) {
            removeOrgCallbackApi.removeOrgAction(orgIdSet);
        }
    }

    /**
     * 判断组织编码是否重复
     *
     * @param orgCode 组织编码
     * @return true=重复，false=不重复
     */
    private boolean isRepetitiveByOrgCode(String orgCode) {
        HrOrganizationDO org = hrOrganizationMapper.selectByOrgCode(orgCode);
        return org != null;
    }

    // endregion

}
