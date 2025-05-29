package cn.ibenbeni.bens.sys.modular.org.factory;

import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.rule.constants.SymbolConstant;
import cn.ibenbeni.bens.rule.constants.TreeConstants;
import cn.ibenbeni.bens.sys.api.OrganizationServiceApi;
import cn.ibenbeni.bens.sys.modular.org.entity.HrOrganization;
import cn.ibenbeni.bens.sys.modular.org.pojo.request.HrOrganizationRequest;
import cn.ibenbeni.bens.sys.modular.org.service.HrOrganizationService;

/**
 * 组织机构创建工厂
 *
 * @author benben
 * @date 2025/5/28  下午10:12
 */
public final class OrganizationFactory {

    /**
     * 填充该节点的父Id集合
     * <p>若顶级节点，则pids为 [-1],</p>
     * <p>非顶级节点，则pids为 父节点的pids + [pid] + ,</p>
     * <p>非顶级节点示例：[-1],[1671418869810540546],[1671419890196623362], 后期可便于修改为同一个部门属于两个上级部门</p>
     */
    public static void fillParentIds(HrOrganization hrOrganization) {
        // 若父节点为顶级节点
        if (TreeConstants.DEFAULT_PARENT_ID.equals(hrOrganization.getOrgParentId())) {
            hrOrganization.setOrgPids(SymbolConstant.LEFT_SQUARE_BRACKETS + TreeConstants.DEFAULT_PARENT_ID + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA);
        }
        // 非顶级节点
        else {
            HrOrganizationService hrOrganizationService = SpringUtil.getBean(HrOrganizationService.class);

            // 获取父组织机构
            HrOrganizationRequest hrOrganizationRequest = new HrOrganizationRequest();
            hrOrganizationRequest.setOrgId(hrOrganization.getOrgParentId());
            HrOrganization dbHrOrganization = hrOrganizationService.detail(hrOrganizationRequest);

            // 设置当前节点父节点ID集合
            hrOrganization.setOrgPids(dbHrOrganization.getOrgPids() + SymbolConstant.LEFT_SQUARE_BRACKETS + hrOrganization.getOrgParentId() + SymbolConstant.RIGHT_SQUARE_BRACKETS + SymbolConstant.COMMA);
        }
    }

}
