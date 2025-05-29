package cn.ibenbeni.bens.sys.modular.org.mapper;

import cn.ibenbeni.bens.sys.modular.org.entity.HrOrganization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * 组织机构信息Mapper接口
 *
 * @author benben
 * @date 2025/5/27  下午9:06
 */
public interface HrOrganizationMapper extends BaseMapper<HrOrganization> {

    /**
     * 根据机构ID获取子机构ID集合，不含orgId
     * <p>自动忽略顶级父节点，即忽略-1</p>
     *
     * @param orgId 组织机构ID
     * @return 父机构ID集合
     */
    Set<Long> getChildIdsByOrgId(@Param("orgId") Long orgId);

}
