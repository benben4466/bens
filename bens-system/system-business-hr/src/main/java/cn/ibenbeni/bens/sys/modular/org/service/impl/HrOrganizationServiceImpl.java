package cn.ibenbeni.bens.sys.modular.org.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.db.api.factory.PageResultFactory;
import cn.ibenbeni.bens.db.api.pojo.entity.BaseEntity;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.constants.TreeConstants;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.sys.api.callback.RemoveOrgCallbackApi;
import cn.ibenbeni.bens.sys.api.pojo.org.HrOrganizationDTO;
import cn.ibenbeni.bens.sys.modular.org.constants.OrgConstants;
import cn.ibenbeni.bens.sys.modular.org.entity.HrOrganization;
import cn.ibenbeni.bens.sys.modular.org.enums.OrgExceptionEnum;
import cn.ibenbeni.bens.sys.modular.org.factory.OrganizationFactory;
import cn.ibenbeni.bens.sys.modular.org.mapper.HrOrganizationMapper;
import cn.ibenbeni.bens.sys.modular.org.pojo.request.HrOrganizationRequest;
import cn.ibenbeni.bens.sys.modular.org.service.HrOrganizationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 组织机构信息业务实现层
 *
 * @author benben
 * @date 2025/5/27  下午9:08
 */
@Service
public class HrOrganizationServiceImpl extends ServiceImpl<HrOrganizationMapper, HrOrganization> implements HrOrganizationService {

    @Resource
    private HrOrganizationMapper hrOrganizationMapper;

    @Override
    public String getOrgNameById(Long orgId) {
        if (ObjectUtil.isEmpty(orgId)) {
            return OrgConstants.NONE_PARENT_ORG;
        }

        if (TreeConstants.DEFAULT_PARENT_ID.equals(orgId)) {
            return OrgConstants.NONE_PARENT_ORG;
        }

        LambdaQueryWrapper<HrOrganization> queryWrapper = Wrappers.lambdaQuery(HrOrganization.class).eq(HrOrganization::getOrgId, orgId).select(HrOrganization::getOrgName);
        HrOrganization dbHrOrganization = this.getOne(queryWrapper);
        if (dbHrOrganization == null) {
            return OrgConstants.NONE_PARENT_ORG;
        }

        return dbHrOrganization.getOrgName();
    }

    @Override
    public HrOrganizationDTO getOrgInfo(Long orgId) {
        if (ObjectUtil.isEmpty(orgId)) {
            return new HrOrganizationDTO();
        }

        LambdaQueryWrapper<HrOrganization> queryWrapper = Wrappers.lambdaQuery(HrOrganization.class).eq(HrOrganization::getOrgId, orgId).select(HrOrganization::getOrgId, HrOrganization::getOrgPids, HrOrganization::getOrgName, HrOrganization::getOrgShortName, HrOrganization::getOrgCode, HrOrganization::getOrgSort);
        HrOrganization dbHrOrganization = this.getOne(queryWrapper);
        if (ObjectUtil.isEmpty(dbHrOrganization)) {
            return new HrOrganizationDTO();
        }
        return BeanUtil.toBean(dbHrOrganization, HrOrganizationDTO.class);
    }

    @Override
    public List<HrOrganizationDTO> getOrgNameList(Collection<Long> orgIdList) {
        if (ObjectUtil.isEmpty(orgIdList)) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<HrOrganization> queryWrapper = Wrappers.lambdaQuery(HrOrganization.class)
                .in(HrOrganization::getOrgId, orgIdList)
                .select(HrOrganization::getOrgId, HrOrganization::getOrgPids, HrOrganization::getOrgName, HrOrganization::getOrgShortName, HrOrganization::getOrgCode, HrOrganization::getOrgSort);
        List<HrOrganization> dbList = this.list(queryWrapper);
        if (ObjectUtil.isEmpty(dbList)) {
            return new ArrayList<>();
        }
        return BeanUtil.copyToList(dbList, HrOrganizationDTO.class);
    }

    @Override
    public void add(HrOrganizationRequest hrOrganizationRequest) {
        // TODO orgCode（组织编码）唯一暂时用代码实现，以后改为注解
        if (this.isRepetitiveByOrgCode(hrOrganizationRequest.getOrgCode())) {
            throw new ServiceException(OrgExceptionEnum.HR_ORGANIZATION_ORG_CODE_EXISTED);
        }

        HrOrganization hrOrganization = BeanUtil.toBean(hrOrganizationRequest, HrOrganization.class);
        // 填充父ID集合
        OrganizationFactory.fillParentIds(hrOrganization);

        this.save(hrOrganization);

        // TODO 发布一个新增组织机构的事件
        // TODO 记录日志
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void del(HrOrganizationRequest hrOrganizationRequest) {
        // 父组织机构
        HrOrganization dbHrOrganization = this.detail(hrOrganizationRequest);
        if (dbHrOrganization == null) {
            throw new ServiceException(OrgExceptionEnum.HR_ORGANIZATION_NOT_EXISTED);
        }

        // 查询被删除组织机构的所有子级节点
        Set<Long> totalOrgIdSet = hrOrganizationMapper.getChildIdsByOrgId(dbHrOrganization.getOrgId());
        totalOrgIdSet.add(hrOrganizationRequest.getOrgId());

        // 执行删除操作
        this.baseDelete(totalOrgIdSet);

        // TODO 发布删除机构的事件
        // TODO 记录日志
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchDelete(HrOrganizationRequest hrOrganizationRequest) {
        Set<Long> orgIdList = hrOrganizationRequest.getOrgIdList();
        // 查询每个组织机构ID的子节点
        for (Long orgId : orgIdList) {
            Set<Long> childIds = hrOrganizationMapper.getChildIdsByOrgId(orgId);
            orgIdList.addAll(childIds);
        }

        this.baseDelete(orgIdList);

        // TODO 发布删除机构的事件
        // TODO 记录日志
    }

    @Override
    public void edit(HrOrganizationRequest hrOrganizationRequest) {
        HrOrganization dbHrOrganization = this.getById(hrOrganizationRequest.getOrgId());
        // 若修改组织编码，则需保证唯一性
        if (StrUtil.isNotBlank(hrOrganizationRequest.getOrgCode())
                && !dbHrOrganization.getOrgCode().equals(hrOrganizationRequest.getOrgCode())
                && this.isRepetitiveByOrgCode(hrOrganizationRequest.getOrgCode())) {
            throw new ServiceException(OrgExceptionEnum.HR_ORGANIZATION_ORG_CODE_EXISTED);
        }

        BeanUtil.copyProperties(hrOrganizationRequest, dbHrOrganization);
        // 填充父ID集合
        OrganizationFactory.fillParentIds(dbHrOrganization);

        this.updateById(dbHrOrganization);

        // TODO 发布编辑机构的事件
        // TODO 记录日志
    }

    @Override
    public HrOrganization detail(HrOrganizationRequest hrOrganizationRequest) {
        // TODO 暂时这么实现，以后使用Spring校验注解实现
        if (hrOrganizationRequest.getOrgId() == null) {
            throw new ServiceException(OrgExceptionEnum.HR_ORGANIZATION_NOT_EXISTED);
        }

        LambdaQueryWrapper<HrOrganization> queryWrapper = Wrappers.lambdaQuery(HrOrganization.class)
                .eq(HrOrganization::getOrgId, hrOrganizationRequest.getOrgId())
                .select(HrOrganization::getOrgId, HrOrganization::getOrgName, HrOrganization::getOrgShortName, HrOrganization::getOrgCode,
                        HrOrganization::getOrgParentId, HrOrganization::getOrgSort, HrOrganization::getOrgType, HrOrganization::getStatusFlag,
                        HrOrganization::getTaxNo, HrOrganization::getRemark, HrOrganization::getOrgPids);

        HrOrganization dbHrOrganization = this.getOne(queryWrapper, false);
        if (dbHrOrganization == null) {
            throw new ServiceException(OrgExceptionEnum.HR_ORGANIZATION_NOT_EXISTED);
        }

        // 填充上级机构名称
        String orgParentName = this.getOrgNameById(dbHrOrganization.getOrgParentId());
        dbHrOrganization.setParentOrgName(orgParentName);

        return dbHrOrganization;
    }

    @Override
    public List<HrOrganization> findList(HrOrganizationRequest hrOrganizationRequest) {
        return this.list(this.createWrapper(hrOrganizationRequest));
    }

    @Override
    public PageResult<HrOrganization> findPage(HrOrganizationRequest hrOrganizationRequest) {
        LambdaQueryWrapper<HrOrganization> queryWrapper = this.createWrapper(hrOrganizationRequest);

        // 只查询需要的字段
        queryWrapper.select(HrOrganization::getOrgId, HrOrganization::getOrgName, HrOrganization::getOrgCode, HrOrganization::getStatusFlag,
                HrOrganization::getOrgType, HrOrganization::getOrgSort, BaseEntity::getCreateTime);

        // 分页查询
        Page<HrOrganization> pageCondition = new Page<>(hrOrganizationRequest.getPageNo(), hrOrganizationRequest.getPageSize());
        Page<HrOrganization> hrOrganizationPage = this.page(pageCondition);

        return PageResultFactory.createPageResult(hrOrganizationPage);
    }

    /**
     * 判断组织编码是否重复
     *
     * @param orgCode 组织编码
     * @return true=重复，false=不重复
     */
    private boolean isRepetitiveByOrgCode(String orgCode) {
        LambdaQueryWrapper<HrOrganization> queryWrapper = Wrappers.lambdaQuery(HrOrganization.class)
                .eq(HrOrganization::getOrgCode, orgCode);
        HrOrganization one = this.getOne(queryWrapper);
        return one != null;
    }

    /**
     * 批量删除组织机构
     */
    private void baseDelete(Set<Long> orgIdSet) {
        Map<String, RemoveOrgCallbackApi> removeOrgCallbackApiMap = SpringUtil.getBeansOfType(RemoveOrgCallbackApi.class);
        // 校验是否有其他业务绑定了组织机构信息
        for (RemoveOrgCallbackApi removeOrgCallbackApi : removeOrgCallbackApiMap.values()) {
            removeOrgCallbackApi.validateHaveBind(orgIdSet);
        }

        // 联动删除所有和本组织机构相关其他业务数据
        for (RemoveOrgCallbackApi removeOrgCallbackApi : removeOrgCallbackApiMap.values()) {
            removeOrgCallbackApi.removeAction(orgIdSet);
        }

        // 删除组织机构
        this.removeBatchByIds(orgIdSet);
    }

    private HrOrganization queryById(Long orgId) {
        HrOrganization dbHrOrganization = this.getById(orgId);
        if (dbHrOrganization == null) {
            throw new ServiceException(OrgExceptionEnum.HR_ORGANIZATION_NOT_EXISTED);
        }
        return dbHrOrganization;
    }

    /**
     * 创建查询wrapper
     */
    private LambdaQueryWrapper<HrOrganization> createWrapper(HrOrganizationRequest hrOrganizationRequest) {
        return Wrappers.lambdaQuery(HrOrganization.class)
                .eq(ObjectUtil.isNotEmpty(hrOrganizationRequest.getStatusFlag()), HrOrganization::getStatusFlag, hrOrganizationRequest.getStatusFlag())
                .orderByAsc(HrOrganization::getOrgSort);
    }

}
